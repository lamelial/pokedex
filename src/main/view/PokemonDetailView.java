package main.view;

import main.model.pokemon.Pokemon;
import main.model.pokemon.PokemonType;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;

public class PokemonDetailView extends JFrame {
    public PokemonDetailView(Pokemon pokemon) {
        setTitle(pokemon.name());
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a label for the Pokémon image
        JLabel imageLabel = new JLabel();
        try {
            // Fetch and set the Pokémon image
            String imageUrl = "https://img.pokemondb.net/sprites/home/normal/" + pokemon.name().toLowerCase() + ".png";
            Image image = ImageIO.read(new URL(imageUrl));
            imageLabel.setIcon(new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            imageLabel.setText("Image not available");
        }

        add(imageLabel, BorderLayout.NORTH);

        // Display Pokémon details
        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setText(String.format("ID: %d\nType: %s\nHeight: %d\nWeight: %d",
                pokemon.id(),
                pokemon.pokemonTypes().stream().map(PokemonType::getDisplayName).reduce((a, b) -> a + ", " + b).orElse(""),
                pokemon.height(),
                pokemon.weight()));
        add(detailsArea, BorderLayout.CENTER);
    }
}
