package ontrack.service;

import ontrack.model.ChatMessage;
import ontrack.model.Status;
import ontrack.model.Task;
import ontrack.model.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for OnTrackService.
 * Tests follow the Right-BICEP principles:
 * - Right: Are the results right?
 * - B: Boundary conditions
 * - I: Inverse relationships
 * - C: Cross-check results
 * - E: Error conditions
 * - P: Performance characteristics
 */
public class OnTrackServiceTest {

    private OnTrackService service;
    private Unit unit1;
    private Unit unit2;
    private Task task1;
    private Task task2;
    private Task task3;

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
        ChatMessage message1 = new ChatMessage("M1", "T1", "Student", "Hello, I need help with Task 1");
        ChatMessage message2 = new ChatMessage("M2", "T1", "Tutor", "Sure, what do you need help with?");
        
        // Add chat messages to service
        service.addChatMessage(message1);
        service.addChatMessage(message2);
    }

    /**
     * Test for getAllUnits function.
     * Tests if the function correctly returns all units.
     * Right-BICEP: Right - Are the results right?
     */
    @Test
    public void testGetAllUnits() {
        // Get all units
        List<Unit> units = service.getAllUnits();
        
        // Check if the list contains the expected number of units
        assertEquals(2, units.size(), "Should return 2 units");
        
        // Check if the list contains the expected units
        assertTrue(units.contains(unit1), "Should contain unit1");
        assertTrue(units.contains(unit2), "Should contain unit2");
    }

    /**
     * Test for getAllUnits function with no units.
     * Tests the boundary condition of an empty list.
     * Right-BICEP: B - Boundary conditions
     */
    @Test
    public void testGetAllUnitsEmpty() {
        // Create a new service with no units
        OnTrackService emptyService = new OnTrackService();
        
        // Get all units
        List<Unit> units = emptyService.getAllUnits();
        
        // Check if the list is empty
        assertTrue(units.isEmpty(), "Should return an empty list");
    }
    
    /**
     * Test for getTasksByUnitByTargetGrade function.
     * Tests if the function correctly returns tasks filtered by unit and target grade.
     * Right-BICEP: Right - Are the results right?
     */
    @Test
    public void testGetTasksByUnitByTargetGrade() {
        // Get tasks for unit1 with target grade "HD"
        List<Task> hdTasks = service.getTasksByUnitByTargetGrade(unit1.getId(), "HD");
        
        // Check if the list contains the expected number of tasks
        assertEquals(1, hdTasks.size(), "Should return 1 task");
        
        // Check if the list contains the expected task
        assertTrue(hdTasks.contains(task1), "Should contain task1");
        
        // Get tasks for unit1 with target grade "D"
        List<Task> dTasks = service.getTasksByUnitByTargetGrade(unit1.getId(), "D");
        
        // Check if the list contains the expected number of tasks
        assertEquals(1, dTasks.size(), "Should return 1 task");
        
        // Check if the list contains the expected task
        assertTrue(dTasks.contains(task2), "Should contain task2");
    }
    
    /**
     * Test for getTasksByUnitByTargetGrade function with case-insensitive target grade.
     * Tests if the function correctly handles case-insensitive comparison.
     * Right-BICEP: Right - Are the results right?
     */
    @Test
    public void testGetTasksByUnitByTargetGradeCaseInsensitive() {
        // Get tasks for unit1 with target grade "hd" (lowercase)
        List<Task> hdTasks = service.getTasksByUnitByTargetGrade(unit1.getId(), "hd");
        
        // Check if the list contains the expected number of tasks
        assertEquals(1, hdTasks.size(), "Should return 1 task despite case difference");
        
        // Check if the list contains the expected task
        assertTrue(hdTasks.contains(task1), "Should contain task1");
    }
    
    /**
     * Test for getTasksByUnitByTargetGrade function with non-existent unit.
     * Tests the boundary condition of a non-existent unit.
     * Right-BICEP: B - Boundary conditions
     */
    @Test
    public void testGetTasksByUnitByTargetGradeNonExistentUnit() {
        // Get tasks for a non-existent unit
        List<Task> tasks = service.getTasksByUnitByTargetGrade("NON_EXISTENT", "HD");
        
        // Check if the list is empty
        assertTrue(tasks.isEmpty(), "Should return an empty list for non-existent unit");
    }
    
    /**
     * Test for getTasksByUnitByTargetGrade function with non-existent target grade.
     * Tests the boundary condition of a non-existent target grade.
     * Right-BICEP: B - Boundary conditions
     */
    @Test
    public void testGetTasksByUnitByTargetGradeNonExistentGrade() {
        // Get tasks for unit1 with a non-existent target grade
        List<Task> tasks = service.getTasksByUnitByTargetGrade(unit1.getId(), "NON_EXISTENT");
        
        // Check if the list is empty
        assertTrue(tasks.isEmpty(), "Should return an empty list for non-existent target grade");
    }
    
    /**
     * Test for getTasksByUnitByTargetGrade function with null parameters.
     * Tests error conditions.
     * Right-BICEP: E - Error conditions
     */
    @Test
    public void testGetTasksByUnitByTargetGradeNullParams() {
        // Test with null unit ID
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getTasksByUnitByTargetGrade(null, "HD");
        });
        assertEquals("Unit ID cannot be null or empty.", exception.getMessage());
        
        // Test with null target grade
        exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getTasksByUnitByTargetGrade(unit1.getId(), null);
        });
        assertEquals("Target grade cannot be null or empty.", exception.getMessage());
    }
    
    /**
     * Test for getTasksByUnitByTargetGrade function with empty parameters.
     * Tests error conditions.
     * Right-BICEP: E - Error conditions
     */
    @Test
    public void testGetTasksByUnitByTargetGradeEmptyParams() {
        // Test with empty unit ID
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getTasksByUnitByTargetGrade("", "HD");
        });
        assertEquals("Unit ID cannot be null or empty.", exception.getMessage());
        
        // Test with empty target grade
        exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getTasksByUnitByTargetGrade(unit1.getId(), "");
        });
        assertEquals("Target grade cannot be null or empty.", exception.getMessage());
    }
    
    /**
     * Test for getChatMessagesByTask function.
     * Tests if the function correctly returns chat messages for a task.
     * Right-BICEP: Right - Are the results right?
     */
    @Test
    public void testGetChatMessagesByTask() {
        // Get chat messages for task1
        List<ChatMessage> messages = service.getChatMessagesByTask("T1");
        
        // Check if the list contains the expected number of messages
        assertEquals(2, messages.size(), "Should return 2 messages");
        
        // Check if the messages have the correct task ID
        for (ChatMessage message : messages) {
            assertEquals("T1", message.getTaskId(), "Message should be for task T1");
        }
    }
    
    /**
     * Test for getChatMessagesByTask function with a task that has no messages.
     * Tests the boundary condition of a task with no messages.
     * Right-BICEP: B - Boundary conditions
     */
    @Test
    public void testGetChatMessagesByTaskNoMessages() {
        // Get chat messages for task2 (which has no messages)
        List<ChatMessage> messages = service.getChatMessagesByTask("T2");
        
        // Check if the list is empty
        assertTrue(messages.isEmpty(), "Should return an empty list for a task with no messages");
    }
    
    /**
     * Test for getChatMessagesByTask function with a non-existent task.
     * Tests the boundary condition of a non-existent task.
     * Right-BICEP: B - Boundary conditions
     */
    @Test
    public void testGetChatMessagesByTaskNonExistentTask() {
        // Get chat messages for a non-existent task
        List<ChatMessage> messages = service.getChatMessagesByTask("NON_EXISTENT");
        
        // Check if the list is empty
        assertTrue(messages.isEmpty(), "Should return an empty list for a non-existent task");
    }
    
    /**
     * Test for getChatMessagesByTask function with null task ID.
     * Tests error conditions.
     * Right-BICEP: E - Error conditions
     */
    @Test
    public void testGetChatMessagesByTaskNullTaskId() {
        // Test with null task ID
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getChatMessagesByTask(null);
        });
        assertEquals("Task ID cannot be null or empty.", exception.getMessage());
    }
    
    /**
     * Test for getChatMessagesByTask function with empty task ID.
     * Tests error conditions.
     * Right-BICEP: E - Error conditions
     */
    @Test
    public void testGetChatMessagesByTaskEmptyTaskId() {
        // Test with empty task ID
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getChatMessagesByTask("");
        });
        assertEquals("Task ID cannot be null or empty.", exception.getMessage());
    }
    
    /**
     * Test for chooseUnitTargetGrade function.
     * Tests if the function correctly sets the target grade for a unit.
     * Right-BICEP: Right - Are the results right?
     */
    @Test
    public void testChooseUnitTargetGrade() {
        // Set target grade for unit1
        boolean result = service.chooseUnitTargetGrade(unit1.getId(), "HD");
        
        // Check if the operation was successful
        assertTrue(result, "Should return true for successful operation");
        
        // Check if the target grade was set correctly
        assertEquals("HD", service.getUnitTargetGrade(unit1.getId()), "Target grade should be HD");
        
        // Change the target grade
        result = service.chooseUnitTargetGrade(unit1.getId(), "P");
        
        // Check if the operation was successful
        assertTrue(result, "Should return true for successful operation");
        
        // Check if the target grade was updated correctly
        assertEquals("P", service.getUnitTargetGrade(unit1.getId()), "Target grade should be updated to P");
    }
    
    /**
     * Test for chooseUnitTargetGrade function with a non-existent unit.
     * Tests the boundary condition of a non-existent unit.
     * Right-BICEP: B - Boundary conditions
     */
    @Test
    public void testChooseUnitTargetGradeNonExistentUnit() {
        // Try to set target grade for a non-existent unit
        boolean result = service.chooseUnitTargetGrade("NON_EXISTENT", "HD");
        
        // Check if the operation failed
        assertFalse(result, "Should return false for non-existent unit");
        
        // Check if the target grade was not set
        assertNull(service.getUnitTargetGrade("NON_EXISTENT"), "Target grade should be null for non-existent unit");
    }
    
    /**
     * Test for chooseUnitTargetGrade function with null parameters.
     * Tests error conditions.
     * Right-BICEP: E - Error conditions
     */
    @Test
    public void testChooseUnitTargetGradeNullParams() {
        // Test with null unit ID
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.chooseUnitTargetGrade(null, "HD");
        });
        assertEquals("Unit ID cannot be null or empty.", exception.getMessage());
        
        // Test with null target grade
        exception = assertThrows(IllegalArgumentException.class, () -> {
            service.chooseUnitTargetGrade(unit1.getId(), null);
        });
        assertEquals("Target grade cannot be null or empty.", exception.getMessage());
    }
    
    /**
     * Test for chooseUnitTargetGrade function with empty parameters.
     * Tests error conditions.
     * Right-BICEP: E - Error conditions
     */
    @Test
    public void testChooseUnitTargetGradeEmptyParams() {
        // Test with empty unit ID
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.chooseUnitTargetGrade("", "HD");
        });
        assertEquals("Unit ID cannot be null or empty.", exception.getMessage());
        
        // Test with empty target grade
        exception = assertThrows(IllegalArgumentException.class, () -> {
            service.chooseUnitTargetGrade(unit1.getId(), "");
        });
        assertEquals("Target grade cannot be null or empty.", exception.getMessage());
    }
    
    /**
     * Test for getUnitTargetGrade function.
     * Tests if the function correctly returns the target grade for a unit.
     * Right-BICEP: Right - Are the results right?
     */
    @Test
    public void testGetUnitTargetGrade() {
        // Initially, the target grade should be null
        assertNull(service.getUnitTargetGrade(unit1.getId()), "Target grade should initially be null");
        
        // Set a target grade
        service.chooseUnitTargetGrade(unit1.getId(), "HD");
        
        // Check if the target grade is returned correctly
        assertEquals("HD", service.getUnitTargetGrade(unit1.getId()), "Target grade should be HD");
    }
    
    /**
     * Test for getUnitTargetGrade function with a non-existent unit.
     * Tests the boundary condition of a non-existent unit.
     * Right-BICEP: B - Boundary conditions
     */
    @Test
    public void testGetUnitTargetGradeNonExistentUnit() {
        // Try to get target grade for a non-existent unit
        String targetGrade = service.getUnitTargetGrade("NON_EXISTENT");
        
        // Check if the result is null
        assertNull(targetGrade, "Target grade should be null for non-existent unit");
    }
    
    /**
     * Test for getUnitTargetGrade function with null unit ID.
     * Tests error conditions.
     * Right-BICEP: E - Error conditions
     */
    @Test
    public void testGetUnitTargetGradeNullUnitId() {
        // Test with null unit ID
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getUnitTargetGrade(null);
        });
        assertEquals("Unit ID cannot be null or empty.", exception.getMessage());
    }
    
    /**
     * Test for getUnitTargetGrade function with empty unit ID.
     * Tests error conditions.
     * Right-BICEP: E - Error conditions
     */
    @Test
    public void testGetUnitTargetGradeEmptyUnitId() {
        // Test with empty unit ID
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.getUnitTargetGrade("");
        });
        assertEquals("Unit ID cannot be null or empty.", exception.getMessage());
    }
    
    /**
     * Test for changeTaskStatus function.
     * Tests if the function correctly changes the status of a task.
     * Right-BICEP: Right - Are the results right?
     */
    @Test
    public void testChangeTaskStatus() {
        // Initially, the status should be NOT_STARTED
        assertEquals(Status.NOT_STARTED, task1.getStatus(), "Initial status should be NOT_STARTED");
        
        // Change the status to WORKING_ON_IT
        boolean result = service.changeTaskStatus(task1.getId(), Status.WORKING_ON_IT);
        
        // Check if the operation was successful
        assertTrue(result, "Should return true for successful operation");
        
        // Check if the status was changed correctly
        assertEquals(Status.WORKING_ON_IT, task1.getStatus(), "Status should be changed to WORKING_ON_IT");
        
        // Change the status to NEED_HELP
        result = service.changeTaskStatus(task1.getId(), Status.NEED_HELP);
        
        // Check if the operation was successful
        assertTrue(result, "Should return true for successful operation");
        
        // Check if the status was changed correctly
        assertEquals(Status.NEED_HELP, task1.getStatus(), "Status should be changed to NEED_HELP");
        
        // Change the status to READY_FOR_FEEDBACK
        result = service.changeTaskStatus(task1.getId(), Status.READY_FOR_FEEDBACK);
        
        // Check if the operation was successful
        assertTrue(result, "Should return true for successful operation");
        
        // Check if the status was changed correctly
        assertEquals(Status.READY_FOR_FEEDBACK, task1.getStatus(), "Status should be changed to READY_FOR_FEEDBACK");
    }
    
    /**
     * Test for changeTaskStatus function with a non-existent task.
     * Tests the boundary condition of a non-existent task.
     * Right-BICEP: B - Boundary conditions
     */
    @Test
    public void testChangeTaskStatusNonExistentTask() {
        // Try to change the status of a non-existent task
        boolean result = service.changeTaskStatus("NON_EXISTENT", Status.WORKING_ON_IT);
        
        // Check if the operation failed
        assertFalse(result, "Should return false for non-existent task");
    }
    
    /**
     * Test for changeTaskStatus function with null parameters.
     * Tests error conditions.
     * Right-BICEP: E - Error conditions
     */
    @Test
    public void testChangeTaskStatusNullParams() {
        // Test with null task ID
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.changeTaskStatus(null, Status.WORKING_ON_IT);
        });
        assertEquals("Task ID cannot be null or empty.", exception.getMessage());
        
        // Test with null status
        exception = assertThrows(IllegalArgumentException.class, () -> {
            service.changeTaskStatus(task1.getId(), null);
        });
        assertEquals("Status cannot be null.", exception.getMessage());
    }
    
    /**
     * Test for changeTaskStatus function with empty task ID.
     * Tests error conditions.
     * Right-BICEP: E - Error conditions
     */
    @Test
    public void testChangeTaskStatusEmptyTaskId() {
        // Test with empty task ID
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.changeTaskStatus("", Status.WORKING_ON_IT);
        });
        assertEquals("Task ID cannot be null or empty.", exception.getMessage());
    }
    
    /**
     * Test for submitUnitPortfolio function.
     * Tests if the function correctly checks if all tasks are ready for feedback.
     * Right-BICEP: Right - Are the results right?
     */
    @Test
    public void testSubmitUnitPortfolio() {
        // Initially, no tasks are ready for feedback
        boolean result = service.submitUnitPortfolio(unit1.getId());
        
        // Check if the operation failed
        assertFalse(result, "Should return false when not all tasks are ready for feedback");
        
        // Set all tasks for unit1 to READY_FOR_FEEDBACK
        service.changeTaskStatus(task1.getId(), Status.READY_FOR_FEEDBACK);
        service.changeTaskStatus(task2.getId(), Status.READY_FOR_FEEDBACK);
        
        // Try to submit the portfolio again
        result = service.submitUnitPortfolio(unit1.getId());
        
        // Check if the operation was successful
        assertTrue(result, "Should return true when all tasks are ready for feedback");
    }
    
    /**
     * Test for submitUnitPortfolio function with a non-existent unit.
     * Tests the boundary condition of a non-existent unit.
     * Right-BICEP: B - Boundary conditions
     */
    @Test
    public void testSubmitUnitPortfolioNonExistentUnit() {
        // Try to submit a portfolio for a non-existent unit
        boolean result = service.submitUnitPortfolio("NON_EXISTENT");
        
        // Check if the operation failed
        assertFalse(result, "Should return false for non-existent unit");
    }
    
    /**
     * Test for submitUnitPortfolio function with a unit that has no tasks.
     * Tests the boundary condition of a unit with no tasks.
     * Right-BICEP: B - Boundary conditions
     */
    @Test
    public void testSubmitUnitPortfolioNoTasks() {
        // Create a new unit with no tasks
        Unit unitWithNoTasks = new Unit("SIT123", "Unit with no tasks");
        service.addUnit(unitWithNoTasks);
        
        // Try to submit a portfolio for the unit
        boolean result = service.submitUnitPortfolio(unitWithNoTasks.getId());
        
        // Check if the operation failed
        assertFalse(result, "Should return false for a unit with no tasks");
    }
    
    /**
     * Test for submitUnitPortfolio function with a unit that has some tasks not ready for feedback.
     * Tests the boundary condition of a unit with some tasks not ready for feedback.
     * Right-BICEP: B - Boundary conditions
     */
    @Test
    public void testSubmitUnitPortfolioSomeTasksNotReady() {
        // Set one task to READY_FOR_FEEDBACK and leave the other as NOT_STARTED
        service.changeTaskStatus(task1.getId(), Status.READY_FOR_FEEDBACK);
        
        // Try to submit the portfolio
        boolean result = service.submitUnitPortfolio(unit1.getId());
        
        // Check if the operation failed
        assertFalse(result, "Should return false when some tasks are not ready for feedback");
    }
    
    /**
     * Test for submitUnitPortfolio function with null unit ID.
     * Tests error conditions.
     * Right-BICEP: E - Error conditions
     */
    @Test
    public void testSubmitUnitPortfolioNullUnitId() {
        // Test with null unit ID
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.submitUnitPortfolio(null);
        });
        assertEquals("Unit ID cannot be null or empty.", exception.getMessage());
    }
    
    /**
     * Test for submitUnitPortfolio function with empty unit ID.
     * Tests error conditions.
     * Right-BICEP: E - Error conditions
     */
    @Test
    public void testSubmitUnitPortfolioEmptyUnitId() {
        // Test with empty unit ID
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.submitUnitPortfolio("");
        });
        assertEquals("Unit ID cannot be null or empty.", exception.getMessage());
    }
    
    /**
     * Test for inverse relationship between changeTaskStatus and submitUnitPortfolio.
     * Tests if changing a task status from READY_FOR_FEEDBACK to another status affects portfolio submission.
     * Right-BICEP: I - Inverse relationships
     */
    @Test
    public void testInverseRelationshipBetweenChangeTaskStatusAndSubmitUnitPortfolio() {
        // Set all tasks for unit1 to READY_FOR_FEEDBACK
        service.changeTaskStatus(task1.getId(), Status.READY_FOR_FEEDBACK);
        service.changeTaskStatus(task2.getId(), Status.READY_FOR_FEEDBACK);
        
        // Check if the portfolio can be submitted
        boolean result = service.submitUnitPortfolio(unit1.getId());
        assertTrue(result, "Should return true when all tasks are ready for feedback");
        
        // Change one task status back to WORKING_ON_IT
        service.changeTaskStatus(task1.getId(), Status.WORKING_ON_IT);
        
        // Check if the portfolio can no longer be submitted
        result = service.submitUnitPortfolio(unit1.getId());
        assertFalse(result, "Should return false after changing a task status from READY_FOR_FEEDBACK");
    }
}
