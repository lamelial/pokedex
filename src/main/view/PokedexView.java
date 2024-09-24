package main.view;

import javax.swing.*;
import java.awt.*;

public class PokedexView extends JFrame {

    public PokedexView() {
        setTitle("Pokédex");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setupComponents();

        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void setupComponents() {
        JPanel pokemonPanel = new JPanel();
        pokemonPanel.setLayout(new GridLayout(0, 1));

        pokemonPanel.add(new JLabel("Pokémon List:"));

        JScrollPane scrollPane = new JScrollPane(pokemonPanel);
        add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Refresh");
        add(refreshButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PokedexView mainFrame = new PokedexView();
            mainFrame.setVisible(true);
        });
    }
}
