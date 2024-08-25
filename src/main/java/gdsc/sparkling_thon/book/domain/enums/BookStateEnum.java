package gdsc.sparkling_thon.book.domain.enums;

public enum BookStateEnum {
    NEW("NEW"),
    GOOD("GOOD"),
    POOR("POOR");

    private final String text;

    BookStateEnum(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
