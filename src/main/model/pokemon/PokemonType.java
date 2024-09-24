package main.model.pokemon;

public enum PokemonType {
    BUG("Bug"),
    DRAGON("Dragon"),
    ELECTRIC("Electric"),
    FAIRY("Fairy"),
    FIGHTING("Fighting"),
    FIRE("Fire"),
    FLYING("Flying"),
    GHOST("Ghost"),
    GRASS("Grass"),
    GROUND("Ground"),
    ICE("Ice"),
    NORMAL("Normal"),
    POISON("Poison"),
    PSYCHIC("Psychic"),
    ROCK("Rock"),
    WATER("Water");

    private final String displayName;

    PokemonType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
