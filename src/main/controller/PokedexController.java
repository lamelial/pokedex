package main.controller;

import main.model.api.PokedexAPI;
import main.model.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PokedexController {
    private final PokedexAPI api;
    private List<Pokemon> loadedPokemon;

    public PokedexController(){
        api = new PokedexAPI();
        loadedPokemon = new ArrayList<>(); // should this be a set.
    }

    public List<Pokemon> loadPokemonList() {
        try {
            loadedPokemon = api.getPokemonList();
            return loadedPokemon;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public List<Pokemon> getLoadedPokemon() {
        return loadedPokemon;
    }
    public int getIDFromString(String string){
        return Arrays.stream(string.split(","))
                .filter(part -> part.matches("\\d+"))
                .findFirst()
                .map(Integer::parseInt)
                .orElseThrow(() -> new IllegalArgumentException("Couldn't extract ID"));
    }
}
