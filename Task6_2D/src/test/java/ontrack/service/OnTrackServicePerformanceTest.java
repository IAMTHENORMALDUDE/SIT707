package ontrack.service;

import ontrack.model.ChatMessage;
import ontrack.model.Status;
import ontrack.model.Task;
import ontrack.model.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Performance tests for OnTrackService.
 * Tests the performance characteristics of the service functions.
 * Right-BICEP: P - Performance characteristics
 */
public class OnTrackServicePerformanceTest {

    private OnTrackService service;
    private Unit unit;
    private List<Task> tasks;
    private static final int LARGE_TASK_COUNT = 1000; // Number of tasks to create for performance testing
    private static final int LARGE_MESSAGE_COUNT = 1000; // Number of messages to create for performance testing

    @BeforeEach
    public void setUp() {
        service = new OnTrackService();
        
        // Create a test unit
        unit = new Unit("SIT707", "Software Quality and Testing");
        service.addUnit(unit);
        
        // Create a large number of tasks for performance testing
        tasks = new ArrayList<>();
        for (int i = 0; i < LARGE_TASK_COUNT; i++) {
            String taskId = "T" + i;
            Task task = new Task(taskId, "Task " + i, "Description for Task " + i, unit, "HD");
            tasks.add(task);
            service.addTask(task);
            
            // Add some chat messages for each task
            for (int j = 0; j < 5; j++) { // 5 messages per task
                String messageId = "M" + i + "_" + j;
                ChatMessage message = new ChatMessage(messageId, taskId, "Student", "Message " + j + " for Task " + i);
                service.addChatMessage(message);
            }
        }
        
        // Create a large number of messages for one specific task for message retrieval testing
        String specificTaskId = "T0"; // Use the first task
        for (int i = 0; i < LARGE_MESSAGE_COUNT; i++) {
            String messageId = "ML_" + i;
            ChatMessage message = new ChatMessage(messageId, specificTaskId, "Student", "Large message set: Message " + i);
            service.addChatMessage(message);
        }
    }

    /**
     * Test the performance of getAllUnits function.
     * Right-BICEP: P - Performance characteristics
     */
    @Test
    public void testGetAllUnitsPerformance() {
        // Add more units for performance testing
        for (int i = 0; i < 1000; i++) {
            Unit additionalUnit = new Unit("UNIT" + i, "Unit " + i);
            service.addUnit(additionalUnit);
        }
        
        // Measure the time it takes to get all units
        Instant start = Instant.now();
        List<Unit> units = service.getAllUnits();
        Instant end = Instant.now();
        
        // Calculate the duration
        Duration duration = Duration.between(start, end);
        
        // Check if the operation completed within a reasonable time (e.g., 100 milliseconds)
        assertTrue(duration.toMillis() < 100, "Getting all units should complete within 100 milliseconds, took: " + duration.toMillis() + "ms");
        
        // Check if all units were retrieved
        assertEquals(1001, units.size(), "Should retrieve all 1001 units");
    }

    /**
     * Test the performance of getTasksByUnitByTargetGrade function.
     * Right-BICEP: P - Performance characteristics
     */
    @Test
    public void testGetTasksByUnitByTargetGradePerformance() {
        // Measure the time it takes to get tasks by unit and target grade
        Instant start = Instant.now();
        List<Task> hdTasks = service.getTasksByUnitByTargetGrade(unit.getId(), "HD");
        Instant end = Instant.now();
        
        // Calculate the duration
        Duration duration = Duration.between(start, end);
        
        // Check if the operation completed within a reasonable time (e.g., 100 milliseconds)
        assertTrue(duration.toMillis() < 100, "Getting tasks by unit and target grade should complete within 100 milliseconds, took: " + duration.toMillis() + "ms");
        
        // Check if all tasks were retrieved
        assertEquals(LARGE_TASK_COUNT, hdTasks.size(), "Should retrieve all tasks with target grade HD");
    }

    /**
     * Test the performance of getChatMessagesByTask function.
     * Right-BICEP: P - Performance characteristics
     */
    @Test
    public void testGetChatMessagesByTaskPerformance() {
        // Measure the time it takes to get chat messages for a task with a large number of messages
        Instant start = Instant.now();
        List<ChatMessage> messages = service.getChatMessagesByTask("T0");
        Instant end = Instant.now();
        
        // Calculate the duration
        Duration duration = Duration.between(start, end);
        
        // Check if the operation completed within a reasonable time (e.g., 100 milliseconds)
        assertTrue(duration.toMillis() < 100, "Getting chat messages for a task should complete within 100 milliseconds, took: " + duration.toMillis() + "ms");
        
        // Check if all messages were retrieved
        assertEquals(LARGE_MESSAGE_COUNT + 5, messages.size(), "Should retrieve all messages for the task");
    }

    /**
     * Test the performance of submitUnitPortfolio function.
     * Right-BICEP: P - Performance characteristics
     */
    @Test
    public void testSubmitUnitPortfolioPerformance() {
        // Set all tasks to READY_FOR_FEEDBACK
        for (Task task : tasks) {
            service.changeTaskStatus(task.getId(), Status.READY_FOR_FEEDBACK);
        }
        
        // Measure the time it takes to submit the portfolio
        Instant start = Instant.now();
        boolean result = service.submitUnitPortfolio(unit.getId());
        Instant end = Instant.now();
        
        // Calculate the duration
        Duration duration = Duration.between(start, end);
        
        // Check if the operation completed within a reasonable time (e.g., 100 milliseconds)
        assertTrue(duration.toMillis() < 100, "Submitting a unit portfolio should complete within 100 milliseconds, took: " + duration.toMillis() + "ms");
        
        // Check if the operation was successful
        assertTrue(result, "Should successfully submit the portfolio");
    }
}
