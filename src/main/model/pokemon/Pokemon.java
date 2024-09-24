package main.model.pokemon;

import java.util.List;

public record Pokemon(
        int id,
        String name,
        List<PokemonType> pokemonTypes,
        double height,
        double weight
) { }
