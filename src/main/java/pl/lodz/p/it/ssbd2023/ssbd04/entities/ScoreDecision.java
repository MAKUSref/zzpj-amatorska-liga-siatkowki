package pl.lodz.p.it.ssbd2023.ssbd04.entities;

public enum ScoreDecision {
    APPROVED ("APPROVED"),
    DECLINE ("DECLINE") ,
    NONE ("NONE");

    public final String value;
    private ScoreDecision(String value) {
        this.value = value;
    }
    public boolean equalsName(String otherValue) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return value.equals(otherValue);
    }

    public String toString() {
        return this.value;
    }
}
