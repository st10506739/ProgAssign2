package wildlifesa;

public class InjuredAnimalRescue extends RescueCase {

    public static final double SURGERY_FEE = 5000.00;

    private final String injuryDescription;
    private final double veterinaryTreatmentCost;
    private final boolean surgeryRequired;

    public InjuredAnimalRescue(String caseId, String animalName, String species,
            String rescueLocation, String assignedRanger, int rescueDays,
            double dailyCareCost, String injuryDescription,
            double veterinaryTreatmentCost, boolean surgeryRequired) {
        super(caseId, animalName, species, rescueLocation, assignedRanger, rescueDays, dailyCareCost);
        requireText(injuryDescription, "Injury Description");
        requirePositive(veterinaryTreatmentCost, "Veterinary Treatment Cost");
        this.injuryDescription = injuryDescription.trim();
        this.veterinaryTreatmentCost = veterinaryTreatmentCost;
        this.surgeryRequired = surgeryRequired;
    }

    @Override
    public double calculateTotalCost() {
        double total = getBaseCareCost() + veterinaryTreatmentCost;
        if (surgeryRequired) {
            total += SURGERY_FEE;
        }
        return total;
    }

    @Override
    public String determinePriority() {
        if (surgeryRequired) {
            return "Critical";
        }
        return veterinaryTreatmentCost >= 10000 ? "High" : "Medium";
    }

    @Override
    public String getRescueType() {
        return "Injured Animal Rescue";
    }

    @Override
    public String getSpecificDetails() {
        return String.format(
                "Injury         : %s%n"
                + "Vet Cost       : %s%n"
                + "Surgery        : %s",
                injuryDescription, formatCurrency(veterinaryTreatmentCost),
                surgeryRequired ? "Required" : "Not required");
    }

    public String getInjuryDescription() { return injuryDescription; }
    public double getVeterinaryTreatmentCost() { return veterinaryTreatmentCost; }
    public boolean isSurgeryRequired() { return surgeryRequired; }
}
