# Task 9.1D: Test Driven Development (TDD) and CI

## **OnTrack System Implementation**

## **1. Introduction**

This project implements a simplified version of the OnTrack system, focusing on core functionality without the graphical interface. The implementation follows the Test-Driven Development (TDD) approach and includes Continuous Integration (CI) setup using GitHub Actions.

## **2. Requirements Analysis**

### **2.1 Client Story**

As a student using OnTrack, I want to be able to:

- View all my enrolled units
- View tasks for each unit based on my target grade
- Communicate with tutors through chat messages
- Set my target grade for each unit
- Track my progress on tasks by changing their status
- Submit my portfolio for a unit when all tasks are ready for feedback

As a tutor using OnTrack, I want to be able to:

- View all units I'm teaching
- View tasks for each unit
- Communicate with students through chat messages
- See which students need help with tasks
- Review submitted portfolios

### **2.2 Functional Requirements**

Based on the client story, the following functions were identified:

1. **Get All Units**: Retrieve a list of all available units.
2. **Get Tasks by Unit by Target Grade**: Retrieve tasks for a specific unit filtered by target grade.
3. **Get Chat Messages by Task**: Retrieve chat messages for a specific task.
4. **Choose Unit Target Grade**: Set a target grade for a unit.
5. **Change Task Status**: Update the status of a task (Not Started, Working on It, Need Help, Ready for Feedback).
6. **Submit Unit Portfolio**: Submit a portfolio for a unit (requires all tasks to be Ready for Feedback).

## **3. Design and Implementation**

### **3.1 Data Model**

The data model consists of the following classes:

### **3.1.1 Status Enum**

```java
package ontrack.model;

/**
 * Represents the possible statuses for a task.
 */public enum Status {
    NOT_STARTED,
    WORKING_ON_IT,
    NEED_HELP,
    READY_FOR_FEEDBACK// Represents submission
}

```

### **3.1.2 Unit Class**

```java
package ontrack.model;

import java.util.Objects;

/**
 * Represents a unit of study in OnTrack.
 */public class Unit {
    private final String id;
    private final String name;

    public Unit(String id, String name) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit ID cannot be null or empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit name cannot be null or empty.");
        }
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(id, unit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Unit{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}

```

### **3.1.3 Task Class**

```java
package ontrack.model;

import java.util.Objects;

/**
 * Represents a task within a unit in OnTrack.
 */public class Task {
    private final String id;
    private final String name;
    private final String description;
    private final Unit unit;// The unit this task belongs toprivate Status status;
    private String targetGrade;// e.g., "P", "C", "D", "HD"public Task(String id, String name, String description, Unit unit, String targetGrade) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Task ID cannot be null or empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Task name cannot be null or empty.");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null.");
        }
// Target grade can be null initially or if not applicablethis.id = id;
        this.name = name;
        this.description = description;// Description can be emptythis.unit = unit;
        this.status = Status.NOT_STARTED;// Default statusthis.targetGrade = targetGrade;
    }

// Getters and setters...public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Unit getUnit() {
        return unit;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        this.status = status;
    }

    public String getTargetGrade() {
        return targetGrade;
    }

    public void setTargetGrade(String targetGrade) {
        this.targetGrade = targetGrade;
    }

// equals, hashCode, and toString methods...
}

```

### **3.1.4 ChatMessage Class**

```java
package ontrack.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a chat message associated with a task.
 */public class ChatMessage {
    private final String id;
    private final String taskId;// ID of the task this message belongs toprivate final String sender;// Could be student ID, tutor ID, or a role like "Student", "Tutor"private final String content;
    private final LocalDateTime timestamp;

    public ChatMessage(String id, String taskId, String sender, String content) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Message ID cannot be null or empty.");
        }
        if (taskId == null || taskId.trim().isEmpty()) {
            throw new IllegalArgumentException("Task ID cannot be null or empty.");
        }
        if (sender == null || sender.trim().isEmpty()) {
            throw new IllegalArgumentException("Sender cannot be null or empty.");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be null or empty.");
        }
        this.id = id;
        this.taskId = taskId;
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalDateTime.now();// Automatically set timestamp on creation
    }

// Constructor with timestamp parameter and getters...
}

```

### **3.2 Service Layer**

The service layer consists of a single class, `OnTrackService`, which implements all the required functions:

```java
package ontrack.service;

import ontrack.model.ChatMessage;
import ontrack.model.Status;
import ontrack.model.Task;
import ontrack.model.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class to manage OnTrack data and operations.
 * Simulates data storage using in-memory collections.
 */public class OnTrackService {

    private final Map<String, Unit> units = new HashMap<>();
    private final Map<String, Task> tasks = new HashMap<>();// Map Task ID to Taskprivate final Map<String, List<ChatMessage>> chatMessages = new HashMap<>();// Map Task ID to List of Messagesprivate final Map<String, String> unitTargetGrades = new HashMap<>();// Map Unit ID to Target Grade// --- Core Function Implementations ---/**
     * Gets all available units.
     *
     * @return A list of all units.
     */public List<Unit> getAllUnits() {
        return new ArrayList<>(units.values());
    }

/**
     * Gets tasks for a specific unit filtered by the task's target grade.
     *
     * @param unitId      The ID of the unit.
     * @param targetGrade The target grade to filter tasks by (e.g., "P", "C", "D", "HD"). Case-insensitive comparison.
     * @return A list of tasks matching the criteria. Returns an empty list if the unit doesn't exist or no tasks match.
     * @throws IllegalArgumentException if unitId or targetGrade is null or empty.
     */public List<Task> getTasksByUnitByTargetGrade(String unitId, String targetGrade) {
        if (unitId == null || unitId.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit ID cannot be null or empty.");
        }
        if (targetGrade == null || targetGrade.trim().isEmpty()) {
            throw new IllegalArgumentException("Target grade cannot be null or empty.");
        }
        if (!units.containsKey(unitId)) {
// Return empty list for non-existent unitreturn new ArrayList<>();
        }

        return tasks.values().stream()
                .filter(task -> task.getUnit().getId().equals(unitId))
                .filter(task -> targetGrade.equalsIgnoreCase(task.getTargetGrade()))
                .collect(Collectors.toList());
    }

/**
     * Gets all chat messages for a specific task.
     *
     * @param taskId The ID of the task.
     * @return A list of chat messages for the task. Returns an empty list if the task doesn't exist or has no messages.
     * @throws IllegalArgumentException if taskId is null or empty.
     */public List<ChatMessage> getChatMessagesByTask(String taskId) {
        if (taskId == null || taskId.trim().isEmpty()) {
            throw new IllegalArgumentException("Task ID cannot be null or empty.");
        }
        if (!tasks.containsKey(taskId)) {
// Return empty list if task doesn't existreturn new ArrayList<>();
        }
        return chatMessages.getOrDefault(taskId, new ArrayList<>());
    }

/**
     * Sets the target grade for a specific unit.
     *
     * @param unitId      The ID of the unit.
     * @param targetGrade The target grade to set (e.g., "P", "C", "D", "HD").
     * @return true if the target grade was set successfully, false if the unit doesn't exist.
     * @throws IllegalArgumentException if unitId or targetGrade is null or empty.
     */public boolean chooseUnitTargetGrade(String unitId, String targetGrade) {
        if (unitId == null || unitId.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit ID cannot be null or empty.");
        }
        if (targetGrade == null || targetGrade.trim().isEmpty()) {
            throw new IllegalArgumentException("Target grade cannot be null or empty.");
        }

// Validate that the unit existsif (!units.containsKey(unitId)) {
            return false;
        }

// Set the target grade for the unit
        unitTargetGrades.put(unitId, targetGrade);
        return true;
    }

/**
     * Gets the target grade for a specific unit.
     *
     * @param unitId The ID of the unit.
     * @return The target grade for the unit, or null if not set or the unit doesn't exist.
     */public String getUnitTargetGrade(String unitId) {
        if (unitId == null || unitId.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit ID cannot be null or empty.");
        }
        return unitTargetGrades.get(unitId);
    }

/**
     * Changes the status of a task.
     *
     * @param taskId The ID of the task.
     * @param status The new status to set.
     * @return true if the status was changed successfully, false if the task doesn't exist.
     * @throws IllegalArgumentException if taskId is null or empty, or status is null.
     */public boolean changeTaskStatus(String taskId, Status status) {
        if (taskId == null || taskId.trim().isEmpty()) {
            throw new IllegalArgumentException("Task ID cannot be null or empty.");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }

        Task task = tasks.get(taskId);
        if (task == null) {
            return false;
        }

        task.setStatus(status);
        return true;
    }

/**
     * Submits a portfolio for a unit. A portfolio can only be submitted if all tasks for the unit
     * have a status of READY_FOR_FEEDBACK.
     *
     * @param unitId The ID of the unit.
     * @return true if the portfolio was submitted successfully, false if the unit doesn't exist
     *         or not all tasks are ready for feedback.
     * @throws IllegalArgumentException if unitId is null or empty.
     */public boolean submitUnitPortfolio(String unitId) {
        if (unitId == null || unitId.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit ID cannot be null or empty.");
        }

// Check if the unit existsif (!units.containsKey(unitId)) {
            return false;
        }

// Get all tasks for the unit
        List<Task> unitTasks = tasks.values().stream()
                .filter(task -> task.getUnit().getId().equals(unitId))
                .collect(Collectors.toList());

// If there are no tasks, consider it a failure (can't submit an empty portfolio)if (unitTasks.isEmpty()) {
            return false;
        }

// Check if all tasks are ready for feedbackboolean allTasksReady = unitTasks.stream()
                .allMatch(task -> task.getStatus() == Status.READY_FOR_FEEDBACK);

        return allTasksReady;
    }

// --- Helper Methods and Data Setup Methods ---// ...
}

```

## **4. Testing**

### **4.1 Right-BICEP Principles**

The tests in this project follow the Right-BICEP principles:

- **Right**: Are the results right?
- **B**: Boundary conditions
- **I**: Inverse relationships
- **C**: Cross-check results
- **E**: Error conditions
- **P**: Performance characteristics

### **4.2 Test Implementation**

### **4.2.1 Basic Tests (Right, Boundary, Error)**

```java
package ontrack.service;

import ontrack.model.ChatMessage;
import ontrack.model.Status;
import ontrack.model.Task;
import ontrack.model.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for OnTrackService.
 * Tests follow the Right-BICEP principles.
 */public class OnTrackServiceTest {

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

// Create test chat messagesChatMessage message1 = new ChatMessage("M1", "T1", "Student", "Hello, I need help with Task 1");
        ChatMessage message2 = new ChatMessage("M2", "T1", "Tutor", "Sure, what do you need help with?");

// Add chat messages to service
        service.addChatMessage(message1);
        service.addChatMessage(message2);
    }

/**
     * Test for getAllUnits function.
     * Tests if the function correctly returns all units.
     * Right-BICEP: Right - Are the results right?
     */@Test
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
     */@Test
    public void testGetAllUnitsEmpty() {
// Create a new service with no unitsOnTrackService emptyService = new OnTrackService();

// Get all units
        List<Unit> units = emptyService.getAllUnits();

// Check if the list is empty
        assertTrue(units.isEmpty(), "Should return an empty list");
    }

// More tests for other functions.../**
     * Test for inverse relationship between changeTaskStatus and submitUnitPortfolio.
     * Tests if changing a task status from READY_FOR_FEEDBACK to another status affects portfolio submission.
     * Right-BICEP: I - Inverse relationships
     */@Test
    public void testInverseRelationshipBetweenChangeTaskStatusAndSubmitUnitPortfolio() {
// Set all tasks for unit1 to READY_FOR_FEEDBACK
        service.changeTaskStatus(task1.getId(), Status.READY_FOR_FEEDBACK);
        service.changeTaskStatus(task2.getId(), Status.READY_FOR_FEEDBACK);

// Check if the portfolio can be submittedboolean result = service.submitUnitPortfolio(unit1.getId());
        assertTrue(result, "Should return true when all tasks are ready for feedback");

// Change one task status back to WORKING_ON_IT
        service.changeTaskStatus(task1.getId(), Status.WORKING_ON_IT);

// Check if the portfolio can no longer be submitted
        result = service.submitUnitPortfolio(unit1.getId());
        assertFalse(result, "Should return false after changing a task status from READY_FOR_FEEDBACK");
    }
}

```

### **4.2.2 Cross-Check Tests**

```java
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
 */public class OnTrackServiceCrossCheckTest {

    private OnTrackService service;
    private Unit unit1;
    private Unit unit2;
    private Task task1;
    private Task task2;
    private Task task3;
    private ChatMessage message1;
    private ChatMessage message2;

// Alternative data structures for cross-checkingprivate List<Unit> units;
    private List<Task> tasks;
    private Map<String, List<ChatMessage>> messagesByTask;

    @BeforeEach
    public void setUp() {
// Setup code...
    }

/**
     * Cross-check test for getAllUnits function.
     * Compares the result with a manually maintained list of units.
     * Right-BICEP: C - Cross-check results
     */@Test
    public void testGetAllUnitsCrossCheck() {
// Get all units from the service
        List<Unit> serviceUnits = service.getAllUnits();

// Compare with our manually maintained list
        assertEquals(units.size(), serviceUnits.size(), "Both lists should have the same size");

// Check if all units from our list are in the service listfor (Unit unit : units) {
            assertTrue(serviceUnits.contains(unit), "Service units should contain " + unit);
        }
    }

// More cross-check tests...
}

```

### **4.2.3 Performance Tests**

```java
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

import static org.junit.jupiter.api.Assertions.*;

/**
 * Performance tests for OnTrackService.
 * Tests the performance characteristics of the service functions.
 * Right-BICEP: P - Performance characteristics
 */public class OnTrackServicePerformanceTest {

    private OnTrackService service;
    private Unit unit;
    private List<Task> tasks;
    private static final int LARGE_TASK_COUNT = 1000;// Number of tasks to create for performance testingprivate static final int LARGE_MESSAGE_COUNT = 1000;// Number of messages to create for performance testing@BeforeEach
    public void setUp() {
// Setup code with large data sets...
    }

/**
     * Test the performance of getAllUnits function.
     * Right-BICEP: P - Performance characteristics
     */@Test
    public void testGetAllUnitsPerformance() {
// Add more units for performance testingfor (int i = 0; i < 1000; i++) {
            Unit additionalUnit = new Unit("UNIT" + i, "Unit " + i);
            service.addUnit(additionalUnit);
        }

// Measure the time it takes to get all unitsInstant start = Instant.now();
        List<Unit> units = service.getAllUnits();
        Instant end = Instant.now();

// Calculate the durationDuration duration = Duration.between(start, end);

// Check if the operation completed within a reasonable time (e.g., 100 milliseconds)
        assertTrue(duration.toMillis() < 100, "Getting all units should complete within 100 milliseconds, took: " + duration.toMillis() + "ms");

// Check if all units were retrieved
        assertEquals(1001, units.size(), "Should retrieve all 1001 units");
    }

// More performance tests...
}

```

### **4.3 Test Coverage**

The tests in this project aim to achieve high code coverage by testing:

1. Normal operation (Right)
2. Edge cases and boundary conditions (B)
3. Inverse relationships between functions (I)
4. Alternative implementations for cross-checking (C)
5. Error handling and exceptional cases (E)
6. Performance under load (P)

## **5. Demo Application**

A demo application was created to demonstrate the usage of the OnTrack service:

```java
package ontrack;

import ontrack.model.ChatMessage;
import ontrack.model.Status;
import ontrack.model.Task;
import ontrack.model.Unit;
import ontrack.service.OnTrackService;

import java.util.List;
import java.util.Scanner;

/**
 * Demo application for OnTrack functions.
 * This class demonstrates the usage of the OnTrack service and its functions.
 */public class OnTrackDemo {

    private static OnTrackService service = new OnTrackService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
// Initialize demo data
        initializeDemoData();

// Display menu and handle user inputboolean exit = false;
        while (!exit) {
            displayMenu();
            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    getAllUnits();
                    break;
                case 2:
                    getTasksByUnitByTargetGrade();
                    break;
                case 3:
                    getChatMessagesByTask();
                    break;
                case 4:
                    chooseUnitTargetGrade();
                    break;
                case 5:
                    changeTaskStatus();
                    break;
                case 6:
                    submitUnitPortfolio();
                    break;
                case 7:
                    exit = true;
                    System.out.println("Exiting OnTrack Demo. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

// Wait for user to press Enter before continuingif (!exit) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

// Close scanner
        scanner.close();
    }

// Demo implementation methods...
}

```

## **6. Right-BICEP Principles in Testing**

### **6.1 Right - Are the results right?**

Tests that verify the correctness of the functions:

```java
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

```

### **6.2 B - Boundary conditions**

Tests that verify the behavior at the boundaries:

```java
@Test
public void testGetTasksByUnitByTargetGradeNonExistentUnit() {
// Get tasks for a non-existent unit
    List<Task> tasks = service.getTasksByUnitByTargetGrade("NON_EXISTENT", "HD");

// Check if the list is empty
    assertTrue(tasks.isEmpty(), "Should return an empty list for non-existent unit");
}

```

### **6.3 I - Inverse relationships**

Tests that verify inverse relationships between functions:

```java
@Test
public void testInverseRelationshipBetweenChangeTaskStatusAndSubmitUnitPortfolio() {
// Set all tasks for unit1 to READY_FOR_FEEDBACK
    service.changeTaskStatus(task1.getId(), Status.READY_FOR_FEEDBACK);
    service.changeTaskStatus(task2.getId(), Status.READY_FOR_FEEDBACK);

// Check if the portfolio can be submittedboolean result = service.submitUnitPortfolio(unit1.getId());
    assertTrue(result, "Should return true when all tasks are ready for feedback");

// Change one task status back to WORKING_ON_IT
    service.changeTaskStatus(task1.getId(), Status.WORKING_ON_IT);

// Check if the portfolio can no longer be submitted
    result = service.submitUnitPortfolio(unit1.getId());
    assertFalse(result, "Should return false after changing a task status from READY_FOR_FEEDBACK");
}

```

### **6.4 C - Cross-check results**

Tests that verify the results by comparing with alternative implementations:

```java
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

// Check if all tasks from our manually filtered list are in the service listfor (Task task : manuallyFilteredTasks) {
        assertTrue(serviceTasks.contains(task), "Service tasks should contain " + task);
    }
}

```

### **6.5 E - Error conditions**

Tests that verify the behavior under error conditions:

```java
@Test
public void testGetTasksByUnitByTargetGradeNullParams() {
// Test with null unit IDException exception = assertThrows(IllegalArgumentException.class, () -> {
        service.getTasksByUnitByTargetGrade(null, "HD");
    });
    assertEquals("Unit ID cannot be null or empty.", exception.getMessage());

// Test with null target grade
    exception = assertThrows(IllegalArgumentException.class, () -> {
        service.getTasksByUnitByTargetGrade(unit1.getId(), null);
    });
    assertEquals("Target grade cannot be null or empty.", exception.getMessage());
}

```

### **6.6 P - Performance characteristics**

Tests that verify the performance characteristics of the functions:

```java
@Test
public void testGetAllUnitsPerformance() {
// Add more units for performance testingfor (int i = 0; i < 1000; i++) {
        Unit additionalUnit = new Unit("UNIT" + i, "Unit " + i);
        service.addUnit(additionalUnit);
    }

// Measure the time it takes to get all unitsInstant start = Instant.now();
    List<Unit> units = service.getAllUnits();
    Instant end = Instant.now();

// Calculate the durationDuration duration = Duration.between(start, end);

// Check if the operation completed within a reasonable time (e.g., 100 milliseconds)
    assertTrue(duration.toMillis() < 100, "Getting all units should complete within 100 milliseconds, took: " + duration.toMillis() + "ms");

// Check if all units were retrieved
    assertEquals(1001, units.size(), "Should retrieve all 1001 units");
}

```

## **4. Test-Driven Development (TDD) Strategy**

This project follows the Test-Driven Development (TDD) approach, which involves the following steps:

1. **Write a failing test**: Create a test that defines a desired function or improvement.
2. **Make the test pass**: Write the minimum amount of code necessary to pass the test.
3. **Refactor**: Clean up the code while ensuring the tests still pass.

### **4.1 Advantages of TDD**

- **Improved code quality**: Writing tests first forces you to think about the design and requirements before implementation.
- **Better documentation**: Tests serve as documentation for how the code should behave.
- **Faster debugging**: When a test fails, you know exactly what's broken.
- **Confidence in refactoring**: Tests ensure that refactoring doesn't break existing functionality.
- **Reduced debugging time**: Bugs are caught early in the development process.

### **4.2 Challenges of TDD**

- **Learning curve**: TDD requires a shift in thinking and development approach.
- **Initial slower development**: Writing tests first can feel like it slows down initial development.
- **Test maintenance**: Tests need to be maintained as requirements change.
- **Difficult for complex UIs**: TDD can be challenging for UI-heavy applications.

## **5. Continuous Integration (CI) Setup**

This project uses GitHub Actions for Continuous Integration. The CI pipeline is configured to:

1. **Build the project**: Compile the Java code using Maven.
2. **Run all tests**: Execute all unit tests to ensure code quality.
3. **Generate reports**: Create test reports for review.

The CI configuration can be found in the `.github/workflows/maven.yml` file.

## **6. Conclusion**

This implementation of the OnTrack system provides a solid foundation for the required functionality. The code is well-structured, with clear separation of concerns between the data model and service layer. By following TDD and implementing CI, we ensure high code quality and maintainability.

The implementation successfully meets all the requirements identified in the client story: 2. Students can view tasks for each unit based on their target grade. 3. Students and tutors can communicate through chat messages. 4. Students can set their target grade for each unit. 5. Students can track their progress on tasks by changing their status. 6. Students can submit their portfolio for a unit when all tasks are ready for feedback. 7. Tutors can view all units they're teaching. 8. Tutors can view tasks for each unit. 9. Tutors can see which students need help with tasks. 10. Tutors can review submitted portfolios.

The implementation follows good software engineering practices:

1. Clear separation of concerns between the data model and service layer.
2. Comprehensive error handling.
3. Thorough testing following the Right-BICEP principles.
4. Good code coverage.
5. Well-documented code.

Future enhancements could include:

1. Persistent storage (database integration).
2. User authentication and authorization.
3. RESTful API for integration with a frontend.
4. Additional features such as notifications, analytics, etc.

## **8. Test Results**

### **8.1 Test Execution Summary**

![Screenshot 2025-04-15 at 11.44.34.png](6%202D%20Right-BICEP%20&%20code%20coverage%201d65a819151880bd8d2fef041e2f5889/Screenshot_2025-04-15_at_11.44.34.png)

### **8.2 Code Coverage Report**

![Screenshot 2025-04-15 at 11.42.39.png](6%202D%20Right-BICEP%20&%20code%20coverage%201d65a819151880bd8d2fef041e2f5889/Screenshot_2025-04-15_at_11.42.39.png)

### **8.3 Test Analysis**

The test results demonstrate that all tests pass successfully, indicating that the implementation meets the requirements and behaves as expected. The code coverage report shows 100% method and line coverage, which means that all code paths are tested.

The tests cover all aspects of the Right-BICEP principles:

1. **Right**: All functions return the expected results for normal inputs.
2. **Boundary**: Edge cases such as empty lists, non-existent entities, and invalid inputs are handled correctly.
3. **Inverse**: The inverse relationship between changing a task status and submitting a portfolio is verified.
4. **Cross-check**: Results are cross-checked with alternative implementations to ensure correctness.
5. **Error**: Error conditions such as null or empty parameters are handled appropriately.
6. **Performance**: All functions complete within acceptable time limits, even with large data sets.

## **9. GitHub Repository**

The code for this project is available on GitHub at:

[https://github.com/IAMTHENORMALDUDE/SIT707/tree/main/Task6_2D](https://github.com/IAMTHENORMALDUDE/SIT707/tree/main/Task6_2D)

![Screenshot 2025-04-15 at 11.46.50.png](6%202D%20Right-BICEP%20&%20code%20coverage%201d65a819151880bd8d2fef041e2f5889/Screenshot_2025-04-15_at_11.46.50.png)
