package wildlifesa;

public class EndangeredSpeciesRescue extends RescueCase {

    public static final double SPECIALIST_TEAM_FEE = 8000.00;

    private final String conservationClassification;
    private final double securityCost;
    private final boolean specialistTeamRequired;

    public EndangeredSpeciesRescue(String caseId, String animalName, String species,
            String rescueLocation, String assignedRanger, int rescueDays,
            double dailyCareCost, String conservationClassification,
            double securityCost, boolean specialistTeamRequired) {
        super(caseId, animalName, species, rescueLocation, assignedRanger, rescueDays, dailyCareCost);
        requireText(conservationClassification, "Conservation Classification");
        requirePositive(securityCost, "Security Cost");
        this.conservationClassification = conservationClassification.trim();
        this.securityCost = securityCost;
        this.specialistTeamRequired = specialistTeamRequired;
    }

    @Override
    public double calculateTotalCost() {
        double total = getBaseCareCost() + securityCost;
        if (specialistTeamRequired) {
            total += SPECIALIST_TEAM_FEE;
        }
        return total;
    }

    @Override
    public String determinePriority() {
        if (conservationClassification.equalsIgnoreCase("Critically Endangered")) {
            return "Critical";
        }
        if (conservationClassification.equalsIgnoreCase("Endangered")) {
            return "High";
        }
        return "Medium";
    }

    @Override
    public String getRescueType() {
        return "Endangered Species Rescue";
    }

    @Override
    public String getSpecificDetails() {
        return String.format(
                "Classification : %s%n"
                + "Security Cost  : %s%n"
                + "Specialist Team: %s",
                conservationClassification, formatCurrency(securityCost),
                specialistTeamRequired ? "Required" : "Not required");
    }

    public String getConservationClassification() { return conservationClassification; }
    public double getSecurityCost() { return securityCost; }
    public boolean isSpecialistTeamRequired() { return specialistTeamRequired; }
}
