package wildlifesa;

/** Operations that every rescue case type must support. */
public interface RescueOperations {

    /** Starts the rescue. Returns false if it cannot be started. */
    boolean startRescue();

    /** Completes the rescue. Returns false if it is already completed. */
    boolean completeRescue();

    /** Returns a summary of the rescue case. */
    String generateSummary();
}
