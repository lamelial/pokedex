package main.controller;

import main.model.api.PokedexAPI;
import main.model.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class PokedexController {
    private final PokedexAPI api;
    private List<Pokemon> loadedPokemon;

    public PokedexController(){
        api = new PokedexAPI();
        loadedPokemon = new ArrayList<>();
    }

    public void loadPokemonList() {
        try {
            loadedPokemon = api.getPokemonList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getIDFromString(String string){
        return Arrays.stream(string.split(","))
                .filter(part -> part.matches("\\d+"))
                .findFirst()
                .map(Integer::parseInt)
                .orElseThrow(() -> new IllegalArgumentException("Couldn't extract ID"));
    }
    public void sortById() {
        loadedPokemon.sort(Comparator.comparingInt(Pokemon::id));
    }

    public void sortByName() {
        loadedPokemon.sort(Comparator.comparing(Pokemon::name));
    }

    public void sortByHeight() {
        loadedPokemon.sort(Comparator.comparingInt(Pokemon::height));
    }

    public void sortByWeight() {
        loadedPokemon.sort(Comparator.comparingInt(Pokemon::weight));
    }

    public List<Pokemon> getLoadedPokemon() {
        return loadedPokemon;
    }
}
