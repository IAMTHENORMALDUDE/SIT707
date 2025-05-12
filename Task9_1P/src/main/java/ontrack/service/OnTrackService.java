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
 */
public class OnTrackService {

    private final Map<String, Unit> units = new HashMap<>();
    private final Map<String, Task> tasks = new HashMap<>(); // Map Task ID to Task
    private final Map<String, List<ChatMessage>> chatMessages = new HashMap<>(); // Map Task ID to List of Messages
    private final Map<String, String> unitTargetGrades = new HashMap<>(); // Map Unit ID to Target Grade

    // --- Data Setup Methods (for simulation) ---

    public void addUnit(Unit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null.");
        }
        units.put(unit.getId(), unit);
    }

    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }
        if (!units.containsKey(task.getUnit().getId())) {
            throw new IllegalArgumentException("Task belongs to a non-existent unit: " + task.getUnit().getId());
        }
        tasks.put(task.getId(), task);
    }

     public void addChatMessage(ChatMessage message) {
        if (message == null) {
            throw new IllegalArgumentException("ChatMessage cannot be null.");
        }
        if (!tasks.containsKey(message.getTaskId())) {
             throw new IllegalArgumentException("ChatMessage refers to a non-existent task: " + message.getTaskId());
        }
        chatMessages.computeIfAbsent(message.getTaskId(), k -> new ArrayList<>()).add(message);
    }

    // --- Core Function Implementations ---

    /**
     * Gets all available units.
     *
     * @return A list of all units.
     */
    public List<Unit> getAllUnits() {
        return new ArrayList<>(units.values());
    }

    // --- Helper Methods ---
    public Task getTaskById(String taskId) {
        return tasks.get(taskId); // Returns null if not found
    }

     public Unit getUnitById(String unitId) {
        return units.get(unitId); // Returns null if not found
    }

    /**
     * Gets tasks for a specific unit filtered by the task's target grade.
     *
     * @param unitId      The ID of the unit.
     * @param targetGrade The target grade to filter tasks by (e.g., "P", "C", "D", "HD"). Case-insensitive comparison.
     * @return A list of tasks matching the criteria. Returns an empty list if the unit doesn't exist or no tasks match.
     * @throws IllegalArgumentException if unitId or targetGrade is null or empty.
     */
    public List<Task> getTasksByUnitByTargetGrade(String unitId, String targetGrade) {
        if (unitId == null || unitId.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit ID cannot be null or empty.");
        }
        if (targetGrade == null || targetGrade.trim().isEmpty()) {
            throw new IllegalArgumentException("Target grade cannot be null or empty.");
        }
        if (!units.containsKey(unitId)) {
            // Or return empty list, depending on desired behavior for non-existent unit
             return new ArrayList<>();
           // throw new IllegalArgumentException("Unit not found: " + unitId);
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
     */
    public List<ChatMessage> getChatMessagesByTask(String taskId) {
        if (taskId == null || taskId.trim().isEmpty()) {
            throw new IllegalArgumentException("Task ID cannot be null or empty.");
        }
        if (!tasks.containsKey(taskId)) {
            // Return empty list if task doesn't exist
            return new ArrayList<>();
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
     */
    public boolean chooseUnitTargetGrade(String unitId, String targetGrade) {
        if (unitId == null || unitId.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit ID cannot be null or empty.");
        }
        if (targetGrade == null || targetGrade.trim().isEmpty()) {
            throw new IllegalArgumentException("Target grade cannot be null or empty.");
        }
        
        // Validate that the unit exists
        if (!units.containsKey(unitId)) {
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
     */
    public String getUnitTargetGrade(String unitId) {
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
     */
    public boolean changeTaskStatus(String taskId, Status status) {
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

}
