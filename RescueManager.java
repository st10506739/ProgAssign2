package wildlifesa;

import java.util.ArrayList;

/** Manages all rescue cases stored in memory. */
public class RescueManager {

    private static final String LINE = "=".repeat(48);
    private static final String DASH = "-".repeat(48);

    private final ArrayList<RescueCase> rescueCases = new ArrayList<>();

    /** Returns false (and does not add) if the ID is blank or already exists. */
    public boolean addCase(RescueCase rescueCase) {
        if (rescueCase == null || idExists(rescueCase.getCaseId())) {
            return false;
        }
        rescueCases.add(rescueCase);
        return true;
    }

    public boolean idExists(String caseId) {
        return findById(caseId) != null;
    }

    /** Returns the matching case, or null if not found. */
    public RescueCase findById(String caseId) {
        if (caseId == null) {
            return null;
        }
        for (RescueCase rc : rescueCases) {
            if (rc.getCaseId().equalsIgnoreCase(caseId.trim())) {
                return rc;
            }
        }
        return null;
    }

    public boolean startRescue(String caseId) {
        RescueCase rc = findById(caseId);
        return rc != null && rc.startRescue();
    }

    public boolean completeRescue(String caseId) {
        RescueCase rc = findById(caseId);
        return rc != null && rc.completeRescue();
    }

    public boolean placeUnderObservation(String caseId) {
        RescueCase rc = findById(caseId);
        return rc != null && rc.placeUnderObservation();
    }

    public ArrayList<RescueCase> getAllCases() {
        return new ArrayList<>(rescueCases);
    }

    public int getCaseCount() {
        return rescueCases.size();
    }

    public double getTotalRescueCost() {
        double total = 0;
        for (RescueCase rc : rescueCases) {
            total += rc.calculateTotalCost(); // polymorphic call
        }
        return total;
    }

    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append(LINE).append(System.lineSeparator());
        sb.append("WILDLIFE RESCUE REPORT").append(System.lineSeparator());
        sb.append(LINE).append(System.lineSeparator());

        if (rescueCases.isEmpty()) {
            sb.append(System.lineSeparator()).append("No rescue cases recorded.")
                    .append(System.lineSeparator());
        }
        for (int i = 0; i < rescueCases.size(); i++) {
            sb.append(System.lineSeparator());
            sb.append(rescueCases.get(i).reportEntry()).append(System.lineSeparator());
            sb.append(System.lineSeparator());
            sb.append(i < rescueCases.size() - 1 ? DASH : LINE).append(System.lineSeparator());
        }
        if (rescueCases.isEmpty()) {
            sb.append(LINE).append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());
        sb.append("Total Rescue Cases : ").append(getCaseCount()).append(System.lineSeparator());
        sb.append("Total Rescue Cost  : ").append(RescueCase.formatCurrency(getTotalRescueCost()));
        return sb.toString();
    }
}
