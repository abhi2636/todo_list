package net.java.backend;

import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
public class Controller {
    
    private final TaskRepository taskRepository;
    private final LoginRepository loginRepository;
    private final TaskAlertService taskAlertService; // Add this line

    public Controller(LoginRepository loginRepository, TaskRepository taskRepository, TaskAlertService taskAlertService) {
        this.loginRepository = loginRepository;
        this.taskRepository = taskRepository;
        this.taskAlertService = taskAlertService; // Add this line
    }
    
    @PostMapping("/tasks")
    public String new_task(@RequestBody Map<String, Object> taskDetails) {
        String taskName = (String) taskDetails.get("task_name");
        String date = (String) taskDetails.get("date");
        String time = (String) taskDetails.get("time");
        int status = (int) taskDetails.get("status");
        String user = (String) taskDetails.get("user"); // Get user from request

        Task newTask = new Task(taskName, date, time, status, user); // Include user
        taskRepository.save(newTask);
        
        return "Task added successfully!";
    }
    
    @PostMapping("/check-overdue-tasks")
    public String checkOverdueTasks() {
        taskAlertService.triggerOverdueCheck();
        return "Overdue tasks check triggered!";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody Map<String, String> userDetails) {
        String username = userDetails.get("username");
        String password = userDetails.get("password");

        // Check if the username already exists
        if (loginRepository.findByUsername(username).isPresent()) {
            logger.warning("Sign-up failed: Username already exists: " + username);
            return "Username already exists!";
        }

        // Create a new user and save it
        Login newUser = new Login(username, password);
        loginRepository.save(newUser);
        logger.info("Sign-up successful for user: " + username);
        return "Sign-up successful!";
    }

    private static final Logger logger = Logger.getLogger(Controller.class.getName());

    @GetMapping("/")
    public String root() {
        return "Welcome to the application!";
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Optional<Login> user = loginRepository.findByUsernameAndPassword(username, password);

        if (user.isPresent()) {
            logger.info("Login successful for user: " + username);
            return "Login successful!";
        } else {
            logger.warning("Invalid login attempt for user: " + username);
            return "Invalid username or password!";
        }
    }
}
