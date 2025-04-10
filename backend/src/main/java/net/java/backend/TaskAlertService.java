package net.java.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TaskAlertService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmailService emailService;

    // Scheduled to run every hour (adjust as needed)
    @Scheduled(fixedRate = 3600000)
    public void checkForOverdueTasks() {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm").format(new Date());

        List<Task> overdueTasks = taskRepository.findByDateBeforeAndTimeBefore(currentDate, currentTime); // Ensure this method retrieves tasks correctly

        for (Task task : overdueTasks) {
            String subject = "Overdue Task Alert: " + task.getTaskName();
            String body = "The task '" + task.getTaskName() + "' is overdue. Please take action.";
            emailService.sendEmail("recipient@example.com", subject, body); // Replace with actual recipient
        }
    }

    public void triggerOverdueCheck() {
        checkForOverdueTasks();
    }
}
