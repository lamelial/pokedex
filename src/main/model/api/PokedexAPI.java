package main.model.api;

import main.model.pokemon.Pokemon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class PokedexAPI {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/pokemon/";
    private ParsePokemon parser;

    public PokedexAPI() {
        this.parser = new ParsePokemon();
    }

    public List<Pokemon> getPokemonList(int offset, int limit) throws Exception {
        String jsonResponse = getData(BASE_URL + "?offset=" + offset + "&limit=" + limit);
        return parser.parsePokemonList(jsonResponse);
    }

    public String getDetailedInfo(int pokemonID) throws Exception {
        String jsonResponse = getData(BASE_URL + pokemonID);
        return parser.parseDetailedInfo(jsonResponse);
    }
    private String getData(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed: HTTP error code: " + connection.getResponseCode());
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        return response.toString();
    }
}
