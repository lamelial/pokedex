package main.view;

import main.controller.PokedexController;
import main.model.pokemon.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PokedexView extends JFrame {
    private JList<String> pokemonList;
    private DefaultListModel<String> listModel;
    private JButton loadButton;
    private PokedexController controller;

    public PokedexView(PokedexController controller) {
        this.controller = controller;
        setTitle("Pokédex");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        pokemonList = new JList<>(listModel);
        add(new JScrollPane(pokemonList), BorderLayout.CENTER);

        loadButton = new JButton("Load Pokémon");
        loadButton.addActionListener(e -> loadPokemon());
        add(loadButton, BorderLayout.SOUTH);
    }

    private void loadPokemon() {
        try {
            List<Pokemon> pokemons = controller.loadPokemonList(0); // will need to make this adjustable!
            listModel.clear(); // Clear previous entries
            for (Pokemon pokemon : pokemons) {
                listModel.addElement(pokemon.name());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading Pokémon: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PokedexController controller = new PokedexController();
            PokedexView mainFrame = new PokedexView(controller);
            mainFrame.setVisible(true);
        });
    }
}
