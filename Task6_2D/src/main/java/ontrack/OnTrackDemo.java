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
 */
public class OnTrackDemo {

    private static OnTrackService service = new OnTrackService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize demo data
        initializeDemoData();
        
        // Display menu and handle user input
        boolean exit = false;
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
            
            // Wait for user to press Enter before continuing
            if (!exit) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        // Close scanner
        scanner.close();
    }

    /**
     * Initialize demo data for the OnTrack service.
     */
    private static void initializeDemoData() {
        // Create units
        Unit sit707 = new Unit("SIT707", "Software Quality and Testing");
        Unit sit737 = new Unit("SIT737", "Cloud Computing");
        
        // Add units to service
        service.addUnit(sit707);
        service.addUnit(sit737);
        
        // Create tasks for SIT707
        Task task1 = new Task("T1", "Task 1.1 - Unit Testing", "Implement unit tests for a given codebase", sit707, "P");
        Task task2 = new Task("T2", "Task 1.2 - Integration Testing", "Implement integration tests for a given system", sit707, "C");
        Task task3 = new Task("T3", "Task 2.1 - System Testing", "Design and implement system tests", sit707, "D");
        Task task4 = new Task("T4", "Task 2.2 - Acceptance Testing", "Create acceptance test cases", sit707, "HD");
        
        // Create tasks for SIT737
        Task task5 = new Task("T5", "Task 1 - Cloud Deployment", "Deploy an application to a cloud platform", sit737, "P");
        Task task6 = new Task("T6", "Task 2 - Containerization", "Containerize an application using Docker", sit737, "HD");
        
        // Add tasks to service
        service.addTask(task1);
        service.addTask(task2);
        service.addTask(task3);
        service.addTask(task4);
        service.addTask(task5);
        service.addTask(task6);
        
        // Create chat messages for Task 1
        ChatMessage message1 = new ChatMessage("M1", "T1", "Student", "I'm having trouble understanding how to write unit tests for the given codebase.");
        ChatMessage message2 = new ChatMessage("M2", "T1", "Tutor", "What specific part are you struggling with?");
        ChatMessage message3 = new ChatMessage("M3", "T1", "Student", "I'm not sure how to mock the database connection.");
        ChatMessage message4 = new ChatMessage("M4", "T1", "Tutor", "You can use a mocking framework like Mockito. Let me show you an example...");
        
        // Add chat messages to service
        service.addChatMessage(message1);
        service.addChatMessage(message2);
        service.addChatMessage(message3);
        service.addChatMessage(message4);
        
        // Set some task statuses
        service.changeTaskStatus("T1", Status.READY_FOR_FEEDBACK);
        service.changeTaskStatus("T2", Status.WORKING_ON_IT);
        service.changeTaskStatus("T5", Status.NEED_HELP);
        
        // Set unit target grades
        service.chooseUnitTargetGrade("SIT707", "HD");
        service.chooseUnitTargetGrade("SIT737", "D");
        
        System.out.println("Demo data initialized successfully.");
    }

    /**
     * Display the main menu.
     */
    private static void displayMenu() {
        System.out.println("\n===== OnTrack Demo =====");
        System.out.println("1. Get All Units");
        System.out.println("2. Get Tasks by Unit by Target Grade");
        System.out.println("3. Get Chat Messages by Task");
        System.out.println("4. Choose Unit Target Grade");
        System.out.println("5. Change Task Status");
        System.out.println("6. Submit Unit Portfolio");
        System.out.println("7. Exit");
        System.out.print("Enter your choice (1-7): ");
    }

    /**
     * Get the user's menu choice.
     * 
     * @return The user's choice as an integer.
     */
    private static int getUserChoice() {
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            return choice;
        } catch (NumberFormatException e) {
            return -1; // Invalid choice
        }
    }

    /**
     * Demonstrate the getAllUnits function.
     */
    private static void getAllUnits() {
        System.out.println("\n--- Get All Units ---");
        List<Unit> units = service.getAllUnits();
        
        if (units.isEmpty()) {
            System.out.println("No units found.");
        } else {
            System.out.println("Units:");
            for (Unit unit : units) {
                System.out.println("- " + unit.getId() + ": " + unit.getName());
            }
        }
    }

    /**
     * Demonstrate the getTasksByUnitByTargetGrade function.
     */
    private static void getTasksByUnitByTargetGrade() {
        System.out.println("\n--- Get Tasks by Unit by Target Grade ---");
        
        // Get all units
        List<Unit> units = service.getAllUnits();
        
        if (units.isEmpty()) {
            System.out.println("No units available.");
            return;
        }
        
        // Display units
        System.out.println("Available units:");
        for (int i = 0; i < units.size(); i++) {
            System.out.println((i + 1) + ". " + units.get(i).getId() + ": " + units.get(i).getName());
        }
        
        // Get unit selection
        System.out.print("Select a unit (1-" + units.size() + "): ");
        int unitIndex;
        try {
            unitIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (unitIndex < 0 || unitIndex >= units.size()) {
                System.out.println("Invalid selection.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }
        
        // Get target grade
        System.out.print("Enter target grade (P, C, D, HD): ");
        String targetGrade = scanner.nextLine().trim();
        
        // Get tasks
        Unit selectedUnit = units.get(unitIndex);
        List<Task> tasks = service.getTasksByUnitByTargetGrade(selectedUnit.getId(), targetGrade);
        
        if (tasks.isEmpty()) {
            System.out.println("No tasks found for unit " + selectedUnit.getId() + " with target grade " + targetGrade + ".");
        } else {
            System.out.println("Tasks for unit " + selectedUnit.getId() + " with target grade " + targetGrade + ":");
            for (Task task : tasks) {
                System.out.println("- " + task.getId() + ": " + task.getName() + " (Status: " + task.getStatus() + ")");
            }
        }
    }

    /**
     * Demonstrate the getChatMessagesByTask function.
     */
    private static void getChatMessagesByTask() {
        System.out.println("\n--- Get Chat Messages by Task ---");
        
        // Get task ID
        System.out.print("Enter task ID: ");
        String taskId = scanner.nextLine().trim();
        
        // Get chat messages
        List<ChatMessage> messages = service.getChatMessagesByTask(taskId);
        
        if (messages.isEmpty()) {
            System.out.println("No chat messages found for task " + taskId + ".");
        } else {
            System.out.println("Chat messages for task " + taskId + ":");
            for (ChatMessage message : messages) {
                System.out.println("[" + message.getSender() + "]: " + message.getContent());
            }
        }
    }

    /**
     * Demonstrate the chooseUnitTargetGrade function.
     */
    private static void chooseUnitTargetGrade() {
        System.out.println("\n--- Choose Unit Target Grade ---");
        
        // Get all units
        List<Unit> units = service.getAllUnits();
        
        if (units.isEmpty()) {
            System.out.println("No units available.");
            return;
        }
        
        // Display units
        System.out.println("Available units:");
        for (int i = 0; i < units.size(); i++) {
            Unit unit = units.get(i);
            String currentGrade = service.getUnitTargetGrade(unit.getId());
            System.out.println((i + 1) + ". " + unit.getId() + ": " + unit.getName() + 
                    (currentGrade != null ? " (Current target grade: " + currentGrade + ")" : " (No target grade set)"));
        }
        
        // Get unit selection
        System.out.print("Select a unit (1-" + units.size() + "): ");
        int unitIndex;
        try {
            unitIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (unitIndex < 0 || unitIndex >= units.size()) {
                System.out.println("Invalid selection.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }
        
        // Get target grade
        System.out.print("Enter target grade (P, C, D, HD): ");
        String targetGrade = scanner.nextLine().trim();
        
        // Set target grade
        Unit selectedUnit = units.get(unitIndex);
        boolean result = service.chooseUnitTargetGrade(selectedUnit.getId(), targetGrade);
        
        if (result) {
            System.out.println("Target grade for unit " + selectedUnit.getId() + " set to " + targetGrade + ".");
        } else {
            System.out.println("Failed to set target grade for unit " + selectedUnit.getId() + ".");
        }
    }

    /**
     * Demonstrate the changeTaskStatus function.
     */
    private static void changeTaskStatus() {
        System.out.println("\n--- Change Task Status ---");
        
        // Get task ID
        System.out.print("Enter task ID: ");
        String taskId = scanner.nextLine().trim();
        
        // Get current task status
        Task task = service.getTaskById(taskId);
        if (task == null) {
            System.out.println("Task not found.");
            return;
        }
        
        System.out.println("Current status of task " + taskId + ": " + task.getStatus());
        
        // Display available statuses
        System.out.println("Available statuses:");
        System.out.println("1. NOT_STARTED");
        System.out.println("2. WORKING_ON_IT");
        System.out.println("3. NEED_HELP");
        System.out.println("4. READY_FOR_FEEDBACK");
        
        // Get status selection
        System.out.print("Select a status (1-4): ");
        int statusIndex;
        try {
            statusIndex = Integer.parseInt(scanner.nextLine());
            if (statusIndex < 1 || statusIndex > 4) {
                System.out.println("Invalid selection.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }
        
        // Map selection to Status enum
        Status newStatus;
        switch (statusIndex) {
            case 1:
                newStatus = Status.NOT_STARTED;
                break;
            case 2:
                newStatus = Status.WORKING_ON_IT;
                break;
            case 3:
                newStatus = Status.NEED_HELP;
                break;
            case 4:
                newStatus = Status.READY_FOR_FEEDBACK;
                break;
            default:
                System.out.println("Invalid selection.");
                return;
        }
        
        // Change task status
        boolean result = service.changeTaskStatus(taskId, newStatus);
        
        if (result) {
            System.out.println("Status of task " + taskId + " changed to " + newStatus + ".");
        } else {
            System.out.println("Failed to change status of task " + taskId + ".");
        }
    }

    /**
     * Demonstrate the submitUnitPortfolio function.
     */
    private static void submitUnitPortfolio() {
        System.out.println("\n--- Submit Unit Portfolio ---");
        
        // Get all units
        List<Unit> units = service.getAllUnits();
        
        if (units.isEmpty()) {
            System.out.println("No units available.");
            return;
        }
        
        // Display units
        System.out.println("Available units:");
        for (int i = 0; i < units.size(); i++) {
            System.out.println((i + 1) + ". " + units.get(i).getId() + ": " + units.get(i).getName());
        }
        
        // Get unit selection
        System.out.print("Select a unit (1-" + units.size() + "): ");
        int unitIndex;
        try {
            unitIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (unitIndex < 0 || unitIndex >= units.size()) {
                System.out.println("Invalid selection.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }
        
        // Submit portfolio
        Unit selectedUnit = units.get(unitIndex);
        boolean result = service.submitUnitPortfolio(selectedUnit.getId());
        
        if (result) {
            System.out.println("Portfolio for unit " + selectedUnit.getId() + " submitted successfully.");
        } else {
            System.out.println("Failed to submit portfolio for unit " + selectedUnit.getId() + ".");
            System.out.println("Make sure all tasks for this unit have a status of READY_FOR_FEEDBACK.");
        }
    }
}
