package ontrack.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a chat message associated with a task.
 */
public class ChatMessage {
    private final String id;
    private final String taskId; // ID of the task this message belongs to
    private final String sender; // Could be student ID, tutor ID, or a role like "Student", "Tutor"
    private final String content;
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
        this.timestamp = LocalDateTime.now(); // Automatically set timestamp on creation
    }

    // Constructor allowing specific timestamp (useful for testing or loading data)
    public ChatMessage(String id, String taskId, String sender, String content, LocalDateTime timestamp) {
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
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null.");
        }
        this.id = id;
        this.taskId = taskId;
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }


    public String getId() {
        return id;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage that = (ChatMessage) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
               "id='" + id + '\'' +
               ", taskId='" + taskId + '\'' +
               ", sender='" + sender + '\'' +
               ", content='" + content + '\'' +
               ", timestamp=" + timestamp +
               '}';
    }
}
