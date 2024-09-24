package main.view;

import main.controller.PokedexController;
import main.controller.PokemonDetailController;
import main.model.pokemon.Pokemon;
import main.model.pokemon.PokemonType;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;

public class PokemonDetailView extends JFrame {
    private PokemonDetailController controller;

    public PokemonDetailView(Pokemon pokemon) {
        this.controller = new PokemonDetailController();
        setTitle(pokemon.name());
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 248, 255)); // Light blue background

        add(createImagePanel(pokemon), BorderLayout.NORTH);
        add(createDetailsArea(pokemon), BorderLayout.CENTER);
    }

    private JPanel createImagePanel(Pokemon pokemon) {
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
        return imagePanel;
    }

    private JTextArea createDetailsArea(Pokemon pokemon) {
        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setText(getPokemonDetails(pokemon));
        return detailsArea;
    }

    private String getPokemonDetails(Pokemon pokemon) {
        return controller.getDetailedInfo(pokemon);
    }
}
