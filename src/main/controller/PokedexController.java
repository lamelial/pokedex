package main.controller;

import main.api.PokedexAPI;
import main.pokemon.Pokemon;

import java.util.List;

public class PokedexController {
    private final PokedexAPI api;
    public PokedexController(){
        api = new PokedexAPI();
    }

    public List<Pokemon> getPokemonList(int offset) {
        try {
            return api.getPokemonList(offset);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

}
