package main.model.pokemon;

import java.util.List;

public record Pokemon(
        int id,
        String name,
        List<PokemonType> pokemonTypes,
        int height,
        int weight,
        String imageURl
) { }
