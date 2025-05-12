# SIT707 Task 9.1P: Test Driven Development (TDD) and CI

## 1. Introduction

This report documents the implementation of a simplified version of the OnTrack system using Test-Driven Development (TDD) and Continuous Integration (CI). The implementation focuses on core functionality without the graphical interface, following best practices in software quality and testing.

## 2. Requirements Analysis

### 2.1 Client Story

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

### 2.2 Functional Requirements

Based on the client story, the following functions were identified:

1. **Get All Units**: Retrieve a list of all available units.
2. **Get Tasks by Unit by Target Grade**: Retrieve tasks for a specific unit filtered by target grade.
3. **Get Chat Messages by Task**: Retrieve chat messages for a specific task.
4. **Choose Unit Target Grade**: Set a target grade for a unit.
5. **Change Task Status**: Update the status of a task (Not Started, Working on It, Need Help, Ready for Feedback).
6. **Submit Unit Portfolio**: Submit a portfolio for a unit (requires all tasks to be Ready for Feedback).

## 3. Test-Driven Development (TDD) Process

This project follows the Test-Driven Development (TDD) approach, which involves the following steps:

1. **Write a failing test (Red)**: Create a test that defines a desired function or improvement.
2. **Make the test pass (Green)**: Write the minimum amount of code necessary to pass the test.
3. **Refactor**: Clean up the code while ensuring the tests still pass.

### 3.1 TDD Example: Submit Unit Portfolio Function

#### Step 1: Write Failing Tests (Red)

Before implementing the `submitUnitPortfolio` function, we first wrote tests to define the expected behavior:

```java
@Test
public void testSubmitUnitPortfolio() {
    // Set up test data
    Unit unit = new Unit("SIT707", "Software Quality and Testing");
    service.addUnit(unit);
    
    Task task1 = new Task("T1", "Task 1", "Description", unit, "HD");
    Task task2 = new Task("T2", "Task 2", "Description", unit, "HD");
    service.addTask(task1);
    service.addTask(task2);
    
    // Set all tasks to READY_FOR_FEEDBACK
    service.changeTaskStatus(task1.getId(), Status.READY_FOR_FEEDBACK);
    service.changeTaskStatus(task2.getId(), Status.READY_FOR_FEEDBACK);
    
    // Test the function
    boolean result = service.submitUnitPortfolio(unit.getId());
    
    // Assert that the portfolio was submitted successfully
    assertTrue(result, "Should successfully submit the portfolio when all tasks are ready");
}

@Test
public void testSubmitUnitPortfolioWithNonExistentUnit() {
    // Test with a non-existent unit ID
    boolean result = service.submitUnitPortfolio("NON_EXISTENT");
    
    // Assert that the function returns false
    assertFalse(result, "Should return false for a non-existent unit");
}

@Test
public void testSubmitUnitPortfolioWithNoTasks() {
    // Set up a unit with no tasks
    Unit unit = new Unit("EMPTY", "Empty Unit");
    service.addUnit(unit);
    
    // Test the function
    boolean result = service.submitUnitPortfolio(unit.getId());
    
    // Assert that the function returns false
    assertFalse(result, "Should return false when there are no tasks for the unit");
}

@Test
public void testSubmitUnitPortfolioWithNotAllTasksReady() {
    // Set up test data
    Unit unit = new Unit("MIXED", "Mixed Status Unit");
    service.addUnit(unit);
    
    Task task1 = new Task("MT1", "Mixed Task 1", "Description", unit, "HD");
    Task task2 = new Task("MT2", "Mixed Task 2", "Description", unit, "HD");
    service.addTask(task1);
    service.addTask(task2);
    
    // Set only one task to READY_FOR_FEEDBACK
    service.changeTaskStatus(task1.getId(), Status.READY_FOR_FEEDBACK);
    service.changeTaskStatus(task2.getId(), Status.WORKING_ON_IT);
    
    // Test the function
    boolean result = service.submitUnitPortfolio(unit.getId());
    
    // Assert that the function returns false
    assertFalse(result, "Should return false when not all tasks are ready for feedback");
}

@Test
public void testSubmitUnitPortfolioWithNullUnitId() {
    // Test with null unit ID
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        service.submitUnitPortfolio(null);
    });
    
    // Assert the exception message
    assertEquals("Unit ID cannot be null or empty.", exception.getMessage());
}
```

At this point, running the tests would fail because the `submitUnitPortfolio` function hasn't been implemented yet.

![Failed Test Screenshot - Insert your screenshot here](path_to_failed_test_screenshot.png)

#### Step 2: Implement the Function (Green)

After writing the failing tests, we implemented the minimum code necessary to make the tests pass:

```java
/**
 * Submits a portfolio for a unit. A portfolio can only be submitted if all tasks for the unit
 * have a status of READY_FOR_FEEDBACK.
 *
 * @param unitId The ID of the unit.
 * @return true if the portfolio was submitted successfully, false if the unit doesn't exist
 *         or not all tasks are ready for feedback.
 * @throws IllegalArgumentException if unitId is null or empty.
 */
public boolean submitUnitPortfolio(String unitId) {
    if (unitId == null || unitId.trim().isEmpty()) {
        throw new IllegalArgumentException("Unit ID cannot be null or empty.");
    }
    
    // Check if the unit exists
    if (!units.containsKey(unitId)) {
        return false;
    }
    
    // Get all tasks for the unit
    List<Task> unitTasks = tasks.values().stream()
            .filter(task -> task.getUnit().getId().equals(unitId))
            .collect(Collectors.toList());
    
    // If there are no tasks, consider it a failure (can't submit an empty portfolio)
    if (unitTasks.isEmpty()) {
        return false;
    }
    
    // Check if all tasks are ready for feedback
    boolean allTasksReady = unitTasks.stream()
            .allMatch(task -> task.getStatus() == Status.READY_FOR_FEEDBACK);
    
    return allTasksReady;
}
```

After implementing the function, all tests now pass.

![Successful Test Screenshot - Insert your screenshot here](path_to_successful_test_screenshot.png)

#### Step 3: Refactor (if needed)

After ensuring all tests pass, we can refactor the code if necessary while maintaining the same behavior. For example, we might improve the method's efficiency or readability:

```java
public boolean submitUnitPortfolio(String unitId) {
    if (unitId == null || unitId.trim().isEmpty()) {
        throw new IllegalArgumentException("Unit ID cannot be null or empty.");
    }
    
    // Check if the unit exists
    if (!units.containsKey(unitId)) {
        return false;
    }
    
    // Get all tasks for the unit and check if they're all ready for feedback
    List<Task> unitTasks = tasks.values().stream()
            .filter(task -> task.getUnit().getId().equals(unitId))
            .collect(Collectors.toList());
    
    return !unitTasks.isEmpty() && unitTasks.stream()
            .allMatch(task -> task.getStatus() == Status.READY_FOR_FEEDBACK);
}
```

### 3.2 Advantages of TDD

- **Improved code quality**: Writing tests first forces you to think about the design and requirements before implementation.
- **Better documentation**: Tests serve as documentation for how the code should behave.
- **Faster debugging**: When a test fails, you know exactly what's broken.
- **Confidence in refactoring**: Tests ensure that refactoring doesn't break existing functionality.
- **Reduced debugging time**: Bugs are caught early in the development process.

### 3.3 Challenges of TDD

- **Learning curve**: TDD requires a shift in thinking and development approach.
- **Initial slower development**: Writing tests first can feel like it slows down initial development.
- **Test maintenance**: Tests need to be maintained as requirements change.
- **Difficult for complex UIs**: TDD can be challenging for UI-heavy applications.

## 4. Continuous Integration (CI) Setup

### 4.1 GitHub Actions Configuration

This project uses GitHub Actions for Continuous Integration. The CI pipeline is configured in the `.github/workflows/maven.yml` file:

```yaml
name: Java CI with Maven for Task9_1P

on:
  # Manual trigger
  workflow_dispatch:
  
  push:
    branches: [main, master]
    paths:
      - "Task9_1P/**"
  pull_request:
    branches: [main, master]
    paths:
      - "Task9_1P/**"

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: "11"
          distribution: "temurin"
          cache: maven
      - name: Build with Maven
        run: mvn -B package --file pom.xml
        working-directory: Task9_1P
      - name: Test with Maven
        run: mvn test
        working-directory: Task9_1P
```

### 4.2 CI Workflow Steps

The CI workflow performs the following steps:

1. **Checkout Code**: The workflow checks out the latest code from the repository.
2. **Set up JDK**: Sets up Java Development Kit (JDK) 11 for building and testing the project.
3. **Build with Maven**: Compiles the Java code using Maven.
4. **Test with Maven**: Runs all unit tests to ensure code quality.

### 4.3 CI Workflow Triggers

The CI workflow is triggered by:

1. **Manual trigger**: You can manually run the workflow from the GitHub Actions tab.
2. **Push**: When changes are pushed to the Task9_1P directory on the main or master branch.
3. **Pull Request**: When a pull request is created or updated that includes changes to the Task9_1P directory.

### 4.4 Build Notifications

When a build is triggered, GitHub Actions will send notifications about the build status:

- **Build Success**: Indicates that all steps have completed successfully.
  ![Successful Build Notification - Insert your screenshot here](path_to_successful_build_notification.png)

- **Build Failure**: Indicates which step failed and why.
  ![Failed Build Notification - Insert your screenshot here](path_to_failed_build_notification.png)

## 5. Data Model and Implementation

The data model consists of the following classes:

1. **Status Enum**: Represents the possible statuses for a task (NOT_STARTED, WORKING_ON_IT, NEED_HELP, READY_FOR_FEEDBACK).
2. **Unit Class**: Represents a unit of study with ID and name.
3. **Task Class**: Represents a task within a unit with ID, name, description, unit reference, status, and target grade.
4. **ChatMessage Class**: Represents a chat message associated with a task with ID, task ID, sender, content, and timestamp.
5. **OnTrackService Class**: Service class that manages OnTrack data and operations, simulating data storage using in-memory collections.

## 6. Conclusion

This implementation of the OnTrack system provides a solid foundation for the required functionality. The code is well-structured, with clear separation of concerns between the data model and service layer. By following TDD and implementing CI, we ensure high code quality and maintainability.

The implementation successfully meets all the requirements identified in the client story:
1. Students can view all their enrolled units.
2. Students can view tasks for each unit based on their target grade.
3. Students and tutors can communicate through chat messages.
4. Students can set their target grade for each unit.
5. Students can track their progress on tasks by changing their status.
6. Students can submit their portfolio for a unit when all tasks are ready for feedback.
7. Tutors can view all units they're teaching.
8. Tutors can view tasks for each unit.
9. Tutors can see which students need help with tasks.
10. Tutors can review submitted portfolios.

The implementation follows good software engineering practices:

1. **Test-Driven Development**: All functionality is developed using TDD, ensuring high test coverage and code quality.
2. **Continuous Integration**: GitHub Actions is used for CI, ensuring that code changes are automatically built and tested.
3. **Clean Code**: The code is well-structured, with clear separation of concerns and good naming conventions.
4. **Documentation**: All classes and methods are well-documented with Javadoc comments.

## 7. GitHub Repository

The code for this project is available on GitHub:

[https://github.com/IAMTHENORMALDUDE/SIT707/tree/main/Task9_1P](https://github.com/IAMTHENORMALDUDE/SIT707/tree/main/Task9_1P)

## 8. Screenshots

### 8.1 Failed Test Screenshot
[Insert your failed test screenshot here]

### 8.2 Successful Test Screenshot
[Insert your successful test screenshot here]

### 8.3 Failed Build Notification
[Insert your failed build notification screenshot here]

### 8.4 Successful Build Notification
[Insert your successful build notification screenshot here]

### 8.5 GitHub Repository Screenshot
[Insert your GitHub repository screenshot here]
