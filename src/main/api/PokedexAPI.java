package main.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import main.pokemon.Pokemon;
import main.pokemon.PokemonType;

import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PokedexAPI {
    private static final String URL = "https://pokeapi.co/api/v2/pokemon/";
    private final Gson gson;
    //Number of Pokemon to call at a time.
    private final int limit = 20; // able to change this?
    public PokedexAPI(){
        this.gson = new Gson();
    }

    public List<Pokemon> getPokemonList(int offset) throws Exception {
        URL url = new URL(URL + "?offset=" + offset + "&limit=" + limit);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed: HTTP error code: " + connection.getResponseCode());
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return parsePokemonList(response.toString());
    }

    private List<Pokemon> parsePokemonList(String json) {
        List<Pokemon> pokemonList = new ArrayList<>();

        return pokemonList;
    }

}
