package main.view;

import main.controller.PokedexController;
import main.model.pokemon.Pokemon;
import main.model.pokemon.PokemonType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

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
    private int limit = 20;

    public PokedexView(PokedexController controller) {
        this.controller = controller;

        setTitle("Pokédex");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(230, 230, 250)); // Light lavender

        listModel = new DefaultListModel<>();
        pokemonJList = new JList<>(listModel);
        pokemonJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        pokemonJList.setCellRenderer(new PokedexListCellRenderer());
        add(new JScrollPane(pokemonJList), BorderLayout.CENTER);

        // Create a button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(176, 224, 230));
        buttonPanel.setLayout(new FlowLayout());

        loadButton = createButton("Load Pokémon", new Color(30, 144, 255), Color.WHITE);
        loadButton.addActionListener(e->loadPokemon());
        prevButton = createButton("Previous", new Color(30, 144, 255), Color.WHITE);
        prevButton.addActionListener(e->loadPreviousPage());
        nextButton = createButton("Next", new Color(30, 144, 255), Color.WHITE);
        nextButton.addActionListener(e->loadNextPage());
        selectButton = createButton("Select Pokémon", new Color(30, 144, 255), Color.WHITE);
        selectButton.addActionListener(e->selectPokemon());

        buttonPanel.add(loadButton);
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(selectButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, Color bgColour, Color fgColour) {
        JButton button = new JButton(text);
        button.setBackground(bgColour);
        button.setForeground(fgColour);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }

    private void loadNextPage() {
        currentPage++;
        loadPokemon();
    }

    private void loadPreviousPage() {
        if (currentPage > 0) {
            currentPage--;
            loadPokemon();
        }
    }

    private void selectPokemon() {
        if (!pokemonJList.isSelectionEmpty()) {
            int selectedIndex = pokemonJList.getSelectedIndex();
            System.out.println(controller.getLoadedPokemon().toString());
            Pokemon selectedPokemon = controller.getLoadedPokemon().get(selectedIndex);
            new PokemonDetailView(selectedPokemon).setVisible(true);
        }
    }


    private void loadPokemon() {
        try {
            List<Pokemon> pokemons = controller.loadPokemonList(getOffset(), limit);
            listModel.clear();
            for (Pokemon pokemon : pokemons) {
                String pokemonTypeInfo = pokemon.pokemonTypes().stream().map(PokemonType::getDisplayName)
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

    private int getOffset(){ // probably move this out of VIEW. no logic in view.
        return currentPage * limit;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PokedexController controller = new PokedexController();
            PokedexView mainFrame = new PokedexView(controller);
            mainFrame.setVisible(true);
        });
    }
}
