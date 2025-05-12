package ontrack.service;

import ontrack.model.ChatMessage;
import ontrack.model.Status;
import ontrack.model.Task;
import ontrack.model.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Cross-check tests for OnTrackService.
 * Tests the correctness of functions by comparing results with alternative implementations.
 * Right-BICEP: C - Cross-check results
 */
public class OnTrackServiceCrossCheckTest {

    private OnTrackService service;
    private Unit unit1;
    private Unit unit2;
    private Task task1;
    private Task task2;
    private Task task3;
    private ChatMessage message1;
    private ChatMessage message2;
    
    // Alternative data structures for cross-checking
    private List<Unit> units;
    private List<Task> tasks;
    private Map<String, List<ChatMessage>> messagesByTask;

    @BeforeEach
    public void setUp() {
        service = new OnTrackService();
        
        // Create test units
        unit1 = new Unit("SIT707", "Software Quality and Testing");
        unit2 = new Unit("SIT737", "Cloud Computing");
        
        // Add units to service
        service.addUnit(unit1);
        service.addUnit(unit2);
        
        // Create test tasks
        task1 = new Task("T1", "Task 1", "Description for Task 1", unit1, "HD");
        task2 = new Task("T2", "Task 2", "Description for Task 2", unit1, "D");
        task3 = new Task("T3", "Task 3", "Description for Task 3", unit2, "HD");
        
        // Add tasks to service
        service.addTask(task1);
        service.addTask(task2);
        service.addTask(task3);
        
        // Create test chat messages
        message1 = new ChatMessage("M1", "T1", "Student", "Hello, I need help with Task 1");
        message2 = new ChatMessage("M2", "T1", "Tutor", "Sure, what do you need help with?");
        
        // Add chat messages to service
        service.addChatMessage(message1);
        service.addChatMessage(message2);
        
        // Set up alternative data structures for cross-checking
        units = new ArrayList<>();
        units.add(unit1);
        units.add(unit2);
        
        tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        
        messagesByTask = new HashMap<>();
        List<ChatMessage> messagesForTask1 = new ArrayList<>();
        messagesForTask1.add(message1);
        messagesForTask1.add(message2);
        messagesByTask.put("T1", messagesForTask1);
    }

    /**
     * Cross-check test for getAllUnits function.
     * Compares the result with a manually maintained list of units.
     * Right-BICEP: C - Cross-check results
     */
    @Test
    public void testGetAllUnitsCrossCheck() {
        // Get all units from the service
        List<Unit> serviceUnits = service.getAllUnits();
        
        // Compare with our manually maintained list
        assertEquals(units.size(), serviceUnits.size(), "Both lists should have the same size");
        
        // Check if all units from our list are in the service list
        for (Unit unit : units) {
            assertTrue(serviceUnits.contains(unit), "Service units should contain " + unit);
        }
    }

    /**
     * Cross-check test for getTasksByUnitByTargetGrade function.
     * Compares the result with a manually filtered list of tasks.
     * Right-BICEP: C - Cross-check results
     */
    @Test
    public void testGetTasksByUnitByTargetGradeCrossCheck() {
        // Get tasks for unit1 with target grade "HD" from the service
        List<Task> serviceTasks = service.getTasksByUnitByTargetGrade(unit1.getId(), "HD");
        
        // Manually filter tasks for unit1 with target grade "HD"
        List<Task> manuallyFilteredTasks = tasks.stream()
                .filter(task -> task.getUnit().getId().equals(unit1.getId()))
                .filter(task -> "HD".equalsIgnoreCase(task.getTargetGrade()))
                .collect(Collectors.toList());
        
        // Compare the results
        assertEquals(manuallyFilteredTasks.size(), serviceTasks.size(), "Both lists should have the same size");
        
        // Check if all tasks from our manually filtered list are in the service list
        for (Task task : manuallyFilteredTasks) {
            assertTrue(serviceTasks.contains(task), "Service tasks should contain " + task);
        }
    }

    /**
     * Cross-check test for getChatMessagesByTask function.
     * Compares the result with a manually maintained map of messages by task.
     * Right-BICEP: C - Cross-check results
     */
    @Test
    public void testGetChatMessagesByTaskCrossCheck() {
        // Get chat messages for task1 from the service
        List<ChatMessage> serviceMessages = service.getChatMessagesByTask("T1");
        
        // Get chat messages for task1 from our manually maintained map
        List<ChatMessage> manualMessages = messagesByTask.getOrDefault("T1", new ArrayList<>());
        
        // Compare the results
        assertEquals(manualMessages.size(), serviceMessages.size(), "Both lists should have the same size");
        
        // Check if all messages from our manually maintained list are in the service list
        for (ChatMessage message : manualMessages) {
            assertTrue(serviceMessages.contains(message), "Service messages should contain " + message);
        }
    }

    /**
     * Cross-check test for submitUnitPortfolio function.
     * Compares the result with a manual check of task statuses.
     * Right-BICEP: C - Cross-check results
     */
    @Test
    public void testSubmitUnitPortfolioCrossCheck() {
        // Set all tasks for unit1 to READY_FOR_FEEDBACK
        service.changeTaskStatus(task1.getId(), Status.READY_FOR_FEEDBACK);
        service.changeTaskStatus(task2.getId(), Status.READY_FOR_FEEDBACK);
        
        // Check if the portfolio can be submitted using the service
        boolean serviceResult = service.submitUnitPortfolio(unit1.getId());
        
        // Manually check if all tasks for unit1 are ready for feedback
        boolean manualCheck = tasks.stream()
                .filter(task -> task.getUnit().getId().equals(unit1.getId()))
                .allMatch(task -> task.getStatus() == Status.READY_FOR_FEEDBACK);
        
        // Compare the results
        assertEquals(manualCheck, serviceResult, "Both checks should yield the same result");
    }

    /**
     * Cross-check test for changeTaskStatus function.
     * Compares the result with direct manipulation of the task status.
     * Right-BICEP: C - Cross-check results
     */
    @Test
    public void testChangeTaskStatusCrossCheck() {
        // Change the status of task1 using the service
        service.changeTaskStatus(task1.getId(), Status.WORKING_ON_IT);
        
        // Get the task from the service
        Task serviceTask = service.getTaskById(task1.getId());
        
        // Directly change the status of our task1 reference
        task1.setStatus(Status.WORKING_ON_IT);
        
        // Compare the results
        assertEquals(task1.getStatus(), serviceTask.getStatus(), "Both tasks should have the same status");
    }
}
