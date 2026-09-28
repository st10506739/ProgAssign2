package wildlifesa;

public class OrphanedAnimalRescue extends RescueCase {

    public static final double FOSTER_CARE_FEE = 2500.00;

    private final int estimatedAgeMonths;
    private final double feedingCost;
    private final boolean fosterCareRequired;

    public OrphanedAnimalRescue(String caseId, String animalName, String species,
            String rescueLocation, String assignedRanger, int rescueDays,
            double dailyCareCost, int estimatedAgeMonths, double feedingCost,
            boolean fosterCareRequired) {
        super(caseId, animalName, species, rescueLocation, assignedRanger, rescueDays, dailyCareCost);
        requirePositive(estimatedAgeMonths, "Estimated Age (Months)");
        requirePositive(feedingCost, "Feeding Cost");
        this.estimatedAgeMonths = estimatedAgeMonths;
        this.feedingCost = feedingCost;
        this.fosterCareRequired = fosterCareRequired;
    }

    @Override
    public double calculateTotalCost() {
        double total = getBaseCareCost() + feedingCost;
        if (fosterCareRequired) {
            total += FOSTER_CARE_FEE;
        }
        return total;
    }

    @Override
    public String determinePriority() {
        if (estimatedAgeMonths <= 3) {
            return "Critical";
        }
        return estimatedAgeMonths <= 12 ? "High" : "Medium";
    }

    @Override
    public String getRescueType() {
        return "Orphaned Animal Rescue";
    }

    @Override
    public String getSpecificDetails() {
        return String.format(
                "Est. Age       : %d month(s)%n"
                + "Feeding Cost   : %s%n"
                + "Foster Care    : %s",
                estimatedAgeMonths, formatCurrency(feedingCost),
                fosterCareRequired ? "Required" : "Not required");
    }

    public int getEstimatedAgeMonths() { return estimatedAgeMonths; }
    public double getFeedingCost() { return feedingCost; }
    public boolean isFosterCareRequired() { return fosterCareRequired; }
}
