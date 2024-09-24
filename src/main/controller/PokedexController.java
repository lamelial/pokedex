package main.controller;

import main.model.api.PokedexAPI;
import main.model.pokemon.Pokemon;

import java.util.List;

public class PokedexController {
    private final PokedexAPI api;
    private final int limit = 20; // able to change this?

    public PokedexController(){
        api = new PokedexAPI();
    }

    public List<Pokemon> getPokemonList(int offset) {
        try {
            return api.getPokemonList(offset, limit);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

}
