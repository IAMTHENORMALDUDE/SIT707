# OnTrack System Implementation

This project implements a simplified version of the OnTrack system, focusing on core functionality without the graphical interface. The implementation follows the Right-BICEP principles for testing and ensures good code coverage.

## Project Overview

The OnTrack system is a platform where students can view and submit tasks, and communicate with tutors. This implementation focuses on the following core functions:

1. **Get All Units**: Retrieve a list of all available units.
2. **Get Tasks by Unit by Target Grade**: Retrieve tasks for a specific unit filtered by target grade.
3. **Get Chat Messages by Task**: Retrieve chat messages for a specific task.
4. **Choose Unit Target Grade**: Set a target grade for a unit.
5. **Change Task Status**: Update the status of a task (Not Started, Working on It, Need Help, Ready for Feedback).
6. **Submit Unit Portfolio**: Submit a portfolio for a unit (requires all tasks to be Ready for Feedback).

## Project Structure

The project is organized as follows:

### Models

- `Status.java`: Enum representing the possible statuses for a task.
- `Unit.java`: Class representing a unit of study.
- `Task.java`: Class representing a task within a unit.
- `ChatMessage.java`: Class representing a chat message associated with a task.

### Service

- `OnTrackService.java`: Service class that implements the core functions of the OnTrack system.

### Demo

- `OnTrackDemo.java`: Demo application that demonstrates the usage of the OnTrack service.

### Tests

- `OnTrackServiceTest.java`: Tests for the OnTrack service following the Right-BICEP principles.
- `OnTrackServicePerformanceTest.java`: Performance tests for the OnTrack service.
- `OnTrackServiceCrossCheckTest.java`: Cross-check tests for the OnTrack service.

## Right-BICEP Principles

The tests in this project follow the Right-BICEP principles:

- **Right**: Are the results right?
- **B**: Boundary conditions
- **I**: Inverse relationships
- **C**: Cross-check results
- **E**: Error conditions
- **P**: Performance characteristics

Each test class focuses on specific aspects of these principles:

- `OnTrackServiceTest.java`: Focuses on Right, Boundary conditions, and Error conditions.
- `OnTrackServicePerformanceTest.java`: Focuses on Performance characteristics.
- `OnTrackServiceCrossCheckTest.java`: Focuses on Cross-check results.
- The Inverse relationships principle is demonstrated in the `testInverseRelationshipBetweenChangeTaskStatusAndSubmitUnitPortfolio` test.

## How to Run

### Prerequisites

- Java 8 or higher
- Maven

### Building the Project

```bash
mvn clean install
```

### Running the Demo

```bash
mvn exec:java -Dexec.mainClass="ontrack.OnTrackDemo"
```

### Running the Tests

```bash
mvn test
```

## Code Coverage

The tests in this project aim to achieve high code coverage by testing:

1. Normal operation (Right)
2. Edge cases and boundary conditions (B)
3. Inverse relationships between functions (I)
4. Alternative implementations for cross-checking (C)
5. Error handling and exceptional cases (E)
6. Performance under load (P)

## Implementation Details

### Data Storage

This implementation uses in-memory data structures to store the data:

- Units are stored in a Map with the unit ID as the key.
- Tasks are stored in a Map with the task ID as the key.
- Chat messages are stored in a Map with the task ID as the key and a List of messages as the value.
- Unit target grades are stored in a Map with the unit ID as the key.

### Error Handling

The implementation includes comprehensive error handling:

- Input validation for all function parameters.
- Appropriate error messages for invalid inputs.
- Graceful handling of non-existent entities.

### Performance Considerations

The implementation is designed to be efficient:

- Uses appropriate data structures for fast lookups.
- Avoids unnecessary computations.
- Handles large datasets efficiently.

## Future Enhancements

Possible future enhancements include:

1. Persistent storage (database integration).
2. User authentication and authorization.
3. RESTful API for integration with a frontend.
4. Additional features such as notifications, analytics, etc.
