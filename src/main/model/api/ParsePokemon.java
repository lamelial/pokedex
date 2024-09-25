package main.model.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import main.model.pokemon.Pokemon;
import main.model.pokemon.PokemonType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParsePokemon {
    private Gson gson;
    public ParsePokemon() {
        this.gson = new Gson();
    }
    public List<Pokemon> parsePokemonList(String json) {
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

        // Get imageURL
        JsonObject sprites = pokemonDetail.getAsJsonObject("sprites");
        String imageUrl = "";
        if (sprites != null && sprites.has("front_default")) {
            imageUrl = sprites.get("front_default").getAsString();
        }
        List<PokemonType> types = new ArrayList<>();
        JsonArray typesArray = pokemonDetail.getAsJsonArray("types");
        for (int j = 0; j < typesArray.size(); j++) {
            String typeName = typesArray.get(j).getAsJsonObject().getAsJsonObject("type").get("name").getAsString();
            PokemonType type = PokemonType.valueOf(typeName.toUpperCase());
            types.add(type);
        }
        return new Pokemon(id, name, types, height, weight, imageUrl);
    }

    private int extractIdFromUrl(String url) {
        System.out.println("Loading Pokemon:" + url);
        return Arrays.stream(url.split("/"))
                .filter(part -> part.matches("\\d+"))
                .findFirst()
                .map(Integer::parseInt)
                .orElseThrow(() -> new IllegalArgumentException("Couldn't extract ID"));
    }

    public String parseDetailedInfo(String json) {
        String abilities = parseAbilities(json);
        String stats = parseStats(json);
        return String.format("Abilities: %s\n\nStats: %s", abilities, stats);
    }

    private String parseAbilities(String json) {
        StringBuilder abilities = new StringBuilder();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        JsonArray abilitiesArray = jsonObject.getAsJsonArray("abilities");

        for (int i = 0; i < abilitiesArray.size(); i++) {
            JsonObject abilityObject = abilitiesArray.get(i).getAsJsonObject();
            String abilityName = abilityObject.getAsJsonObject("ability").get("name").getAsString();
            abilities.append(abilityName).append(", ");
        }
        // remove extra comma from abilities.
        if (abilities.length() > 0) {
            abilities.setLength(abilities.length() - 2);
        }
        return abilities.toString();
    }

    private String parseStats(String json) {
        StringBuilder stats = new StringBuilder();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        JsonArray statsArray = jsonObject.getAsJsonArray("stats");

        for (int i = 0; i < statsArray.size(); i++) {
            JsonObject statObject = statsArray.get(i).getAsJsonObject();
            String statName = statObject.getAsJsonObject("stat").get("name").getAsString();
            int baseStat = statObject.get("base_stat").getAsInt();
            stats.append(statName).append(": ").append(baseStat).append("\n"); // Use newline here
        }
        return stats.toString();
    }

    public String parseEvolution(String json) {

        JsonObject evolutionChainData = gson.fromJson(json, JsonObject.class);
        JsonObject chain = evolutionChainData.getAsJsonObject("chain");
        StringBuilder evolutionInfo = new StringBuilder();

        extractEvolutions(chain, evolutionInfo, 0);

        evolutionInfo.setLength(evolutionInfo.length());
        return evolutionInfo.toString();
    }
    public String getEvolutionURL(String json) {
        JsonObject speciesData = gson.fromJson(json, JsonObject.class);
        JsonObject evolutionChainUrl = speciesData.getAsJsonObject("evolution_chain");

        if (evolutionChainUrl != null) {
            return evolutionChainUrl.get("url").getAsString();
        }
        return "";
    }

    private void extractEvolutions(JsonObject current, StringBuilder evolutionInfo, int level) {
       if (current == null) return;
       String speciesName = current.getAsJsonObject("species").get("name").getAsString();
       evolutionInfo.append("  ".repeat(level)).append(speciesName).append("\n");
       if (current.has("evolves_to") && current.getAsJsonArray("evolves_to").size() > 0) {
           JsonArray evolvesToArray = current.getAsJsonArray("evolves_to");
           for (JsonElement element : evolvesToArray) {
               if (element.isJsonObject()) {
                   extractEvolutions(element.getAsJsonObject(), evolutionInfo, level + 1);
               }
           }
       }
    }
}
