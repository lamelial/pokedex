package main.model.pokemon;

import java.awt.*;

public enum PokemonType {

    UG("Bug", Color.decode("#A6B91A")),
    DRAGON("Dragon", Color.decode("#6F35FC")),
    ELECTRIC("Electric", Color.decode("#F7D02C")),
    FAIRY("Fairy", Color.decode("#D685AD")),
    FIGHTING("Fighting", Color.decode("#C22E28")),
    FIRE("Fire", Color.decode("#EE8130")),
    FLYING("Flying", Color.decode("#A98FF3")),
    GHOST("Ghost", Color.decode("#735797")),
    GRASS("Grass", Color.decode("#7AC74C")),
    GROUND("Ground", Color.decode("#E2BF65")),
    ICE("Ice", Color.decode("#96D9D6")),
    NORMAL("Normal", Color.decode("#A8A77A")),
    POISON("Poison", Color.decode("#A33EA1")),
    PSYCHIC("Psychic", Color.decode("#F95587")),
    ROCK("Rock", Color.decode("#B6A136")),
    STEEL("Steel", Color.decode("#B7B7CE")),
    WATER("Water", Color.decode("#6390F0")),
    DARK("Dark", Color.decode("#705746"));

    private final String displayName;
    private final Color displayColour;

    PokemonType(String displayName, Color displayColour) {
        this.displayName = displayName;
        this.displayColour = displayColour;
    }

    public String getDisplayName() {
        return displayName;
    }
}
