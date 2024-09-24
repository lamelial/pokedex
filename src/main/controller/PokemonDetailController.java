package main.controller;

import main.model.api.PokedexAPI;
import main.model.pokemon.Pokemon;

public class PokemonDetailController {
    private final PokedexAPI api;

    public PokemonDetailController() {
        this.api = new PokedexAPI();
    }

    public String getDetailedInfo(Pokemon pokemon) {
        StringBuilder info = new StringBuilder();
        try {
            info.append("ID: ").append(pokemon.id()).append("\n")
                    .append("Name: ").append(pokemon.name()).append("\n")
                    .append("Height: ").append(pokemon.height()).append("\n")
                    .append("Weight: ").append(pokemon.weight()).append("\n")
                    .append("Types: ").append(pokemon.pokemonTypes().stream()
                            .map(type -> type.getDisplayName())
                            .reduce((a, b) -> a + ", " + b).orElse("None"))
                    .append("\n");
            String detail = api.getDetailedInfo(pokemon.id());
            String evolution = api.getEvolutionInfo(pokemon.id());

            info.append(detail).append("\n").append(evolution);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching details: " + e.getMessage();
        }
        return info.toString();
    }
}
