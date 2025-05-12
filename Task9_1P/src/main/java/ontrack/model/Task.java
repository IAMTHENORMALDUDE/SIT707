package ontrack.model;

import java.util.Objects;

/**
 * Represents a task within a unit in OnTrack.
 */
public class Task {
    private final String id;
    private final String name;
    private final String description;
    private final Unit unit; // The unit this task belongs to
    private Status status;
    private String targetGrade; // e.g., "P", "C", "D", "HD"

    public Task(String id, String name, String description, Unit unit, String targetGrade) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Task ID cannot be null or empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Task name cannot be null or empty.");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null.");
        }
        // Target grade can be null initially or if not applicable
        this.id = id;
        this.name = name;
        this.description = description; // Description can be empty
        this.unit = unit;
        this.status = Status.NOT_STARTED; // Default status
        this.targetGrade = targetGrade;
    }

    public String getId() {
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

    // Optional: Setter for target grade if it can be changed after creation
    public void setTargetGrade(String targetGrade) {
        this.targetGrade = targetGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(unit, task.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, unit);
    }

    @Override
    public String toString() {
        return "Task{" +
               "id='" + id + '\'' +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", unit=" + unit.getId() + // Avoid circular reference in toString
               ", status=" + status +
               ", targetGrade='" + targetGrade + '\'' +
               '}';
    }
}
