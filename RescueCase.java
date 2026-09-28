package wildlifesa;

import java.util.Locale;

/** Abstract base class for all wildlife rescue cases. */
public abstract class RescueCase implements RescueOperations {

    private final String caseId;
    private String animalName;
    private String species;
    private String rescueLocation;
    private String assignedRanger;
    private int rescueDays;
    private double dailyCareCost;
    private RescueStatus status;

    protected RescueCase(String caseId, String animalName, String species,
            String rescueLocation, String assignedRanger,
            int rescueDays, double dailyCareCost) {
        requireText(caseId, "Rescue Case ID");
        requireText(species, "Species");
        requireText(rescueLocation, "Rescue Location");
        requireText(assignedRanger, "Assigned Ranger");
        requirePositive(rescueDays, "Number of Rescue Days");
        requirePositive(dailyCareCost, "Daily Care Cost");

        this.caseId = caseId.trim();
        this.animalName = (animalName == null || animalName.isBlank()) ? "Unnamed" : animalName.trim();
        this.species = species.trim();
        this.rescueLocation = rescueLocation.trim();
        this.assignedRanger = assignedRanger.trim();
        this.rescueDays = rescueDays;
        this.dailyCareCost = dailyCareCost;
        this.status = RescueStatus.REPORTED;
    }

    // ---------- Validation helpers (shared with subclasses) ----------
    protected static void requireText(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " cannot be blank.");
        }
    }

    protected static void requirePositive(double value, String field) {
        if (value <= 0) {
            throw new IllegalArgumentException(field + " must be greater than zero.");
        }
    }

    // ---------- Abstract behaviour: each type supplies its own ----------
    public abstract double calculateTotalCost();

    public abstract String determinePriority();

    public abstract String getRescueType();

    /** Information specific to the rescue type. */
    public abstract String getSpecificDetails();

    // ---------- Shared behaviour ----------
    /** Cost of daily care: days x daily cost. */
    protected double getBaseCareCost() {
        return rescueDays * dailyCareCost;
    }

    @Override
    public boolean startRescue() {
        if (status == RescueStatus.REPORTED || status == RescueStatus.UNDER_OBSERVATION) {
            status = RescueStatus.RESCUE_IN_PROGRESS;
            return true;
        }
        return false;
    }

    @Override
    public boolean completeRescue() {
        if (status == RescueStatus.RESCUE_COMPLETED) {
            return false;
        }
        status = RescueStatus.RESCUE_COMPLETED;
        return true;
    }

    /** Places the animal under observation (not allowed once completed). */
    public boolean placeUnderObservation() {
        if (status == RescueStatus.RESCUE_COMPLETED) {
            return false;
        }
        status = RescueStatus.UNDER_OBSERVATION;
        return true;
    }

    @Override
    public String generateSummary() {
        return String.format(
                "Rescue Case ID : %s%n"
                + "Rescue Type    : %s%n"
                + "Species        : %s%n"
                + "Assigned Ranger: %s%n"
                + "Rescue Priority: %s%n"
                + "Current Status : %s%n"
                + "Total Cost     : %s",
                caseId, getRescueType(), species, assignedRanger,
                determinePriority(), status, formatCurrency(calculateTotalCost()));
    }

    /** Full details of the case, including type-specific information. */
    public String displayDetails() {
        return String.format(
                "Case ID        : %s%n"
                + "Type           : %s%n"
                + "Animal Name    : %s%n"
                + "Species        : %s%n"
                + "Location       : %s%n"
                + "Ranger         : %s%n"
                + "Rescue Days    : %d%n"
                + "Daily Care Cost: %s%n"
                + "%s%n"
                + "Priority       : %s%n"
                + "Status         : %s%n"
                + "Total Cost     : %s",
                caseId, getRescueType(), animalName, species, rescueLocation,
                assignedRanger, rescueDays, formatCurrency(dailyCareCost),
                getSpecificDetails(), determinePriority(), status,
                formatCurrency(calculateTotalCost()));
    }

    /** Report entry for this case. */
    public String reportEntry() {
        return String.format(
                "Case ID   : %s%n"
                + "Type      : %s%n"
                + "Species   : %s%n"
                + "Location  : %s%n"
                + "Ranger    : %s%n"
                + "Priority  : %s%n"
                + "Status    : %s%n"
                + "Total Cost: %s",
                caseId, getRescueType(), species, rescueLocation, assignedRanger,
                determinePriority(), status, formatCurrency(calculateTotalCost()));
    }

    /** Formats like R20 500.00 */
    public static String formatCurrency(double amount) {
        return "R" + String.format(Locale.US, "%,.2f", amount).replace(',', ' ');
    }

    // ---------- Getters / setters (encapsulation) ----------
    public String getCaseId() { return caseId; }
    public String getAnimalName() { return animalName; }
    public String getSpecies() { return species; }
    public String getRescueLocation() { return rescueLocation; }
    public String getAssignedRanger() { return assignedRanger; }
    public int getRescueDays() { return rescueDays; }
    public double getDailyCareCost() { return dailyCareCost; }
    public RescueStatus getStatus() { return status; }

    public void setStatus(RescueStatus status) { this.status = status; }
}
