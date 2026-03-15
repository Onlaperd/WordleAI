package team.onlapus.ua;

public enum CharStateEnum {
    GREEN("🟩"),
    YELLOW("🟨"),
    GRAY("⬛");

    private final String symbol;

    CharStateEnum(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
