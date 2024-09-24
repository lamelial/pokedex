package main.controller;

import main.api.PokedexAPI;
public class PokedexController {
    private final PokedexAPI api;
    public PokedexController(){
        api = new PokedexAPI();
    }
}
