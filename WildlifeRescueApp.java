package wildlifesa;

import java.util.Scanner;

/** Console application for the WildLife SA Rescue Operations System. */
public class WildlifeRescueApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final RescueManager manager = new RescueManager();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> createRescueCase();
                case "2" -> searchRescueCase();
                case "3" -> updateRescueStatus();
                case "4" -> displayAll();
                case "5" -> System.out.println(manager.generateReport());
                case "6" -> {
                    System.out.println("Goodbye.");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please enter a number from 1 to 6.");
            }
        }
    }

    private static void showMenu() {
        System.out.println();
        System.out.println("=".repeat(40));
        System.out.println("WILDLIFE RESCUE OPERATIONS SYSTEM");
        System.out.println("=".repeat(40));
        System.out.println();
        System.out.println("1. Create Rescue Case");
        System.out.println("2. Search Rescue Case");
        System.out.println("3. Update Rescue Status");
        System.out.println("4. Display All Rescue Cases");
        System.out.println("5. Rescue Report");
        System.out.println("6. Exit");
        System.out.println();
        System.out.print("Select an option: ");
    }

    // ---------------- Menu actions ----------------
    private static void createRescueCase() {
        System.out.println("\nRescue Types:");
        System.out.println("1. Injured Animal Rescue");
        System.out.println("2. Orphaned Animal Rescue");
        System.out.println("3. Endangered Species Rescue");
        System.out.print("Select rescue type: ");
        String type = scanner.nextLine().trim();
        if (!type.equals("1") && !type.equals("2") && !type.equals("3")) {
            System.out.println("Invalid rescue type.");
            return;
        }

        String id = readNonBlank("Rescue Case ID: ");
        if (manager.idExists(id)) {
            System.out.println("A rescue case with ID '" + id + "' already exists.");
            return;
        }
        System.out.print("Animal Name (optional): ");
        String name = scanner.nextLine();
        String species = readNonBlank("Species: ");
        String location = readNonBlank("Rescue Location: ");
        String ranger = readNonBlank("Assigned Ranger: ");
        int days = readPositiveInt("Number of Rescue Days: ");
        double dailyCost = readPositiveDouble("Daily Care Cost (R): ");

        RescueCase rescueCase;
        switch (type) {
            case "1" -> {
                String injury = readNonBlank("Injury Description: ");
                double vet = readPositiveDouble("Veterinary Treatment Cost (R): ");
                boolean surgery = readYesNo("Surgery Required?");
                rescueCase = new InjuredAnimalRescue(id, name, species, location, ranger,
                        days, dailyCost, injury, vet, surgery);
            }
            case "2" -> {
                int age = readPositiveInt("Estimated Age (Months): ");
                double feeding = readPositiveDouble("Feeding Cost (R): ");
                boolean foster = readYesNo("Foster Care Required?");
                rescueCase = new OrphanedAnimalRescue(id, name, species, location, ranger,
                        days, dailyCost, age, feeding, foster);
            }
            default -> {
                String classification = readClassification();
                double security = readPositiveDouble("Security Cost (R): ");
                boolean specialist = readYesNo("Specialist Team Required?");
                rescueCase = new EndangeredSpeciesRescue(id, name, species, location, ranger,
                        days, dailyCost, classification, security, specialist);
            }
        }

        if (manager.addCase(rescueCase)) {
            System.out.println("\nRescue case created successfully.");
            System.out.println(rescueCase.generateSummary());
        } else {
            System.out.println("Rescue case could not be added.");
        }
    }

    private static void searchRescueCase() {
        String id = readNonBlank("Enter Rescue Case ID to search: ");
        RescueCase rc = manager.findById(id);
        if (rc == null) {
            System.out.println("No rescue case found with ID '" + id + "'.");
        } else {
            System.out.println();
            System.out.println(rc.displayDetails());
        }
    }

    private static void updateRescueStatus() {
        String id = readNonBlank("Enter Rescue Case ID: ");
        RescueCase rc = manager.findById(id);
        if (rc == null) {
            System.out.println("No rescue case found with ID '" + id + "'.");
            return;
        }
        System.out.println("Current status: " + rc.getStatus());
        System.out.println("1. Start Rescue Operation");
        System.out.println("2. Place Under Observation");
        System.out.println("3. Complete Rescue Operation");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine().trim();

        boolean updated;
        switch (choice) {
            case "1" -> updated = rc.startRescue();
            case "2" -> updated = rc.placeUnderObservation();
            case "3" -> updated = rc.completeRescue();
            default -> {
                System.out.println("Invalid option.");
                return;
            }
        }
        if (updated) {
            System.out.println("Status updated.\n");
            System.out.println(rc.generateSummary());
        } else {
            System.out.println("That status change is not allowed from the current status ("
                    + rc.getStatus() + ").");
        }
    }

    private static void displayAll() {
        if (manager.getCaseCount() == 0) {
            System.out.println("No rescue cases recorded.");
            return;
        }
        for (RescueCase rc : manager.getAllCases()) {
            System.out.println();
            System.out.println("-".repeat(40));
            System.out.println(rc.displayDetails()); // polymorphism
        }
        System.out.println("-".repeat(40));
    }

    // ---------------- Input helpers with validation ----------------
    private static String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("This field cannot be blank.");
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value > 0) {
                    return value;
                }
                System.out.println("Value must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
                if (value > 0) {
                    return value;
                }
                System.out.println("Value must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (Y/N): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("Y")) return true;
            if (input.equalsIgnoreCase("N")) return false;
            System.out.println("Please enter Y or N.");
        }
    }

    private static String readClassification() {
        while (true) {
            System.out.println("Conservation Classification:");
            System.out.println("1. Critically Endangered");
            System.out.println("2. Endangered");
            System.out.println("3. Vulnerable");
            System.out.print("Select classification: ");
            switch (scanner.nextLine().trim()) {
                case "1": return "Critically Endangered";
                case "2": return "Endangered";
                case "3": return "Vulnerable";
                default: System.out.println("Invalid selection.");
            }
        }
    }
}
