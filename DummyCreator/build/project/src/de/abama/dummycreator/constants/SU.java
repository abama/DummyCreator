package de.abama.dummycreator.constants;

public enum SU {
	
    PIECE ("Stück", "Ab # Stück"),
    METER   ("Meter", "Ab # Meter");

    private final String singular;
    private final String plural;
    
    SU(String singular, String plural) {
        this.singular = singular;
        this.plural = plural;
    }
    public String singular() { return singular; }
    public String plural() { return plural; }
}
