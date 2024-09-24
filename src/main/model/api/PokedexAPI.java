package main.model.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import main.model.pokemon.Pokemon;
import main.model.pokemon.PokemonType;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PokedexAPI {
    private static final String URL = "https://pokeapi.co/api/v2/pokemon/";
    private final Gson gson;
    //Number of Pokemon to call at a time.
    public PokedexAPI(){
        this.gson = new Gson();
    }

    public List<Pokemon> getPokemonList(int offset, int limit) throws Exception {
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
            try {
                pokemonList.add(createPokemon(pokemonObject));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            List<PokemonType> types = new ArrayList<>();
            //Pokemon pokemon = new Pokemon(id, name, types);
            //pokemonList.add(pokemon);
        }
        return pokemonList;
    }

    private Pokemon createPokemon(JsonObject pokemonObject) throws Exception {
        String name = pokemonObject.get("name").getAsString();
        String url = pokemonObject.get("url").getAsString();
        int id = extractIdFromUrl(url);
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
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

        JsonObject pokemonDetail = gson.fromJson(response.toString(), JsonObject.class);
        int height = pokemonDetail.get("height").getAsInt();
        int weight = pokemonDetail.get("weight").getAsInt();

        List<PokemonType> types = new ArrayList<>();
        JsonArray typesArray = pokemonDetail.getAsJsonArray("types");
        for (int j = 0; j < typesArray.size(); j++) {
            String typeName = typesArray.get(j).getAsJsonObject().getAsJsonObject("type").get("name").getAsString();
            PokemonType type = PokemonType.valueOf(typeName.toUpperCase());
            types.add(type);
        }
        return new Pokemon(id, name, types, height, weight);
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
