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
    private JButton nextButton;
    private JButton prevButton;
    private JButton selectButton;
    private JList pokemonJList;
    private JLabel imageLabel;
    private PokedexController controller;
    private int currentPage = 0;

    public PokedexView(PokedexController controller) {
        this.controller = controller;
        setTitle("Pokédex");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        pokemonJList = new JList<>(listModel);
        pokemonJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(pokemonJList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        loadButton = new JButton("Load Pokémon");
        loadButton.addActionListener(e -> loadPokemon());
        buttonPanel.add(loadButton);

        prevButton = new JButton("Previous");
        prevButton.addActionListener(e -> loadPreviousPage());
        buttonPanel.add(prevButton);

        nextButton = new JButton("Next");
        nextButton.addActionListener(e -> loadNextPage());
        buttonPanel.add(nextButton);

        selectButton = new JButton("Select Pokémon");
        selectButton.addActionListener(e -> selectPokemon());
        buttonPanel.add(selectButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
    private void loadNextPage() {
        currentPage++;
        loadPokemon(); // Load Pokémon for the next page
    }

    private void loadPreviousPage() {
        if (currentPage > 0) {
            currentPage--;
            loadPokemon(); // Load Pokémon for the previous page
        }
    }

    private void selectPokemon() {
        int index = pokemonJList.getSelectedIndex();

    }

    private void loadPokemon() {
        try {
            List<Pokemon> pokemons = controller.loadPokemonList(getOffset());
            listModel.clear();
            for (Pokemon pokemon : pokemons) {
                String pokemonTypeInfo = pokemon.pokemonTypes().stream().map(t->t.getDisplayName())
                                                                        .reduce((a,b) -> a +", " + b)
                                                                        .orElse("No Types");;
                String pokemonInfo = String.format("%d, %s, Type: %s, Height: %d, Weight: %d",
                        pokemon.id(),
                        pokemon.name(),
                        pokemonTypeInfo,
                        pokemon.height(),
                        pokemon.weight());
                listModel.addElement(pokemonInfo);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading Pokémon: " + ex.getMessage());
        }
    }

    private int getOffset(){
        return currentPage * 20;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PokedexController controller = new PokedexController();
            PokedexView mainFrame = new PokedexView(controller);
            mainFrame.setVisible(true);
        });
    }
}
