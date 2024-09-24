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
        getContentPane().setBackground(new Color(240, 248, 255)); // constant for colours. potentially even UTIL file.

        JLabel imageLabel = new JLabel();
        try {
            URL imageUrl = new URL(pokemon.imageURL());
            Image image = ImageIO.read(imageUrl);
            image = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(image));
        } catch (Exception e) {
            e.printStackTrace();
            imageLabel.setText("Image not available");
        }
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout());
        imagePanel.add(imageLabel);
        add(imagePanel, BorderLayout.NORTH);

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
