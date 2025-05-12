# Test-Driven Development (TDD) Process for OnTrack System

This document outlines the Test-Driven Development (TDD) process followed for implementing the OnTrack system. The process follows the classic Red-Green-Refactor cycle of TDD.

## The TDD Cycle

1. **Red**: Write a failing test that defines the functionality you want to implement
2. **Green**: Write the minimum amount of code to make the test pass
3. **Refactor**: Clean up the code while ensuring the tests still pass

## Example TDD Process for the `submitUnitPortfolio` Function

### Step 1: Write a Failing Test (Red)

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
```

### Step 2: Implement the Function (Green)

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

### Step 3: Write Additional Tests for Edge Cases

```java
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

### Step 4: Refactor if Needed

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

## Benefits of TDD in This Project

1. **Clear Requirements**: Writing tests first forced us to clearly define what each function should do.
2. **Comprehensive Test Coverage**: By starting with tests, we ensured all functionality is tested.
3. **Confidence in Changes**: The test suite provides confidence when making changes or refactoring.
4. **Documentation**: Tests serve as documentation for how the code should behave.
5. **Design Improvement**: TDD led to better-designed, more modular code.

## Continuous Integration (CI) Integration

The project uses GitHub Actions for CI, which automatically runs all tests whenever changes are pushed to the repository. This ensures that all tests continue to pass as the codebase evolves, maintaining the quality of the code.
