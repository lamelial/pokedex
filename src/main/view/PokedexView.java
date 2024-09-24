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
    private JButton sortByIdButton;
    private JButton sortByNameButton;
    private JButton sortByHeightButton;
    private JButton sortByWeightButton;
    private JButton selectButton;
    private JList pokemonJList;
    private JTextField searchField;
    private PokedexController controller;


    public PokedexView(PokedexController controller) {
        this.controller = controller;

        setTitle("Pokédex");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0, 230, 250));

        listModel = new DefaultListModel<>();
        pokemonJList = new JList<>(listModel);
        pokemonJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        pokemonJList.setCellRenderer(new PokedexListCellRenderer());
        add(new JScrollPane(pokemonJList), BorderLayout.CENTER);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        searchField = new JTextField(20);
        searchField.addActionListener(e -> filterPokemonList()); // Search on Enter
        topPanel.add(searchField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(176, 224, 230));
        buttonPanel.setLayout(new FlowLayout());

        selectButton = createButton("Select Pokémon", new Color(30, 144, 255), Color.WHITE);
        selectButton.addActionListener(e->selectPokemon());
        sortByIdButton = createButton("Sort by ID", new Color(30, 144, 255), Color.WHITE);
        sortByIdButton.addActionListener(e -> {
            controller.sortById();
            displayPokemon();
        });

        sortByNameButton = createButton("Sort by Name", new Color(30, 144, 255), Color.WHITE);
        sortByNameButton.addActionListener(e -> {
            controller.sortByName();
            displayPokemon();
        });

        sortByHeightButton = createButton("Sort by Height", new Color(30, 144, 255), Color.WHITE);
        sortByHeightButton.addActionListener(e -> {
            controller.sortByHeight();
            displayPokemon();
        });

        sortByWeightButton = createButton("Sort by Weight", new Color(30, 144, 255), Color.WHITE);
        sortByWeightButton.addActionListener(e -> {
            controller.sortByWeight();
            displayPokemon();
        });

        buttonPanel.add(selectButton);
        buttonPanel.add(sortByIdButton);
        buttonPanel.add(sortByNameButton);
        buttonPanel.add(sortByHeightButton);
        buttonPanel.add(sortByWeightButton);

        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        loadPokemon();
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

    private void selectPokemon() {
        if (!pokemonJList.isSelectionEmpty()) {
            String selected = pokemonJList.getSelectedValue().toString();
            int id = controller.getIDFromString(selected);
            try {
                Pokemon selectedPokemon = controller.getLoadedPokemon().stream().filter(p -> p.id() == id)
                        .reduce((a, b) -> { throw new RuntimeException("Selection Error");})
                        .orElseThrow(()-> {throw new RuntimeException("Selection Error");});
                new PokemonDetailView(selectedPokemon).setVisible(true);
            }
            catch(RuntimeException ex){
                JOptionPane.showMessageDialog(this, "Error selecting Pokémon: " + ex.getMessage());
            }
        }
    }
    private void filterPokemonList() {
        String searchTerm = searchField.getText().toLowerCase();
        listModel.clear();

        try {
            List<Pokemon> pokemons = controller.getLoadedPokemon();
            for (Pokemon pokemon : pokemons) {
                String idString = String.valueOf(pokemon.id());
                if (pokemon.name().toLowerCase().contains(searchTerm) ||
                        idString.contains(searchTerm)) {
                    listModel.addElement(createPokemonInfoString(pokemon));
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error filtering Pokémon: " + ex.getMessage());
        }
    }

    private void loadPokemon() {
        try {
            controller.loadPokemonList();
            displayPokemon();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading Pokémon: " + ex.getMessage());
        }
    }
    private void displayPokemon(){
        List<Pokemon> pokemons = controller.getLoadedPokemon();
        listModel.clear();
        for (Pokemon pokemon : pokemons) {
            listModel.addElement(createPokemonInfoString(pokemon));
        }
    }

    private String createPokemonInfoString(Pokemon pokemon) {
        String pokemonTypeInfo = pokemon.pokemonTypes().stream()
                .map(PokemonType::getDisplayName)
                .reduce((a, b) -> a + ", " + b)
                .orElse("No Types");
        return String.format("%d, %s, Type: %s, Height: %d, Weight: %d",
                pokemon.id(),
                pokemon.name(),
                pokemonTypeInfo,
                pokemon.height(),
                pokemon.weight());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PokedexController controller = new PokedexController();
            PokedexView mainFrame = new PokedexView(controller);
            mainFrame.setVisible(true);
        });
    }
}
