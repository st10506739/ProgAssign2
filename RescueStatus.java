package wildlifesa;

/** Possible statuses of a rescue case. */
public enum RescueStatus {
    REPORTED("Reported"),
    RESCUE_IN_PROGRESS("Rescue in Progress"),
    UNDER_OBSERVATION("Under Observation"),
    RESCUE_COMPLETED("Rescue Completed");

    private final String label;

    RescueStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
