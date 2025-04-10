package net.java.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // Importing List

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByDateBeforeAndTimeBefore(String currentDate, String currentTime);
    // Additional query methods can be defined here if needed
}
