package main.model.api;

import main.model.pokemon.Pokemon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class PokedexAPI {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";
    private ParsePokemon parser;
    private final int offset = 0;
    private final int limit = 151;

    public PokedexAPI() {
        this.parser = new ParsePokemon();
    }

    public List<Pokemon> getPokemonList() throws Exception {
        String jsonResponse = getData(BASE_URL + "pokemon/" + "?offset=" + offset + "&limit=" + limit);
        return parser.parsePokemonList(jsonResponse);
    }

    public String getDetailedInfo(int pokemonID) throws Exception {
        String jsonResponse = getData(BASE_URL + "pokemon/" + pokemonID);
        return parser.parseDetailedInfo(jsonResponse);
    }

    public String getEvolutionInfo(int pokemonID) throws Exception {
        System.out.println(BASE_URL + "pokemon-species/" + pokemonID);

        String jsonResponse = getData(BASE_URL + "pokemon-species/" + pokemonID);
        String url = parser.getEvolutionURL(jsonResponse);

        if (url != null) {
            // get the evolution chain data
            String evolutionChainJson = getData(url);
            return parser.parseEvolution(evolutionChainJson);
        }
        return "No evolution information available.";
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
