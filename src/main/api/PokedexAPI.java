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
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        JsonArray results = jsonObject.getAsJsonArray("results");

        for (int i = 0; i < results.size(); i++) {
            JsonObject pokemonObject = results.get(i).getAsJsonObject();
            String name = pokemonObject.get("name").getAsString();
            String url = pokemonObject.get("url").getAsString();
            int id = extractIdFromUrl(url);
            System.out.println(name);
            List<PokemonType> types = new ArrayList<>();
            //Pokemon pokemon = new Pokemon(id, name, types);
            //pokemonList.add(pokemon);
        }
        return pokemonList;
    }

    private int extractIdFromUrl(String url) {
        System.out.println(url);
        return Arrays.stream(url.split("/"))
                .filter(part -> part.matches("\\d+"))
                .findFirst()
                .map(Integer::parseInt)
                .orElseThrow(() -> new IllegalArgumentException("Couldn't extract ID"));
    }

}
