package main.view;

import main.controller.PokedexController;
import main.model.pokemon.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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
        PokedexController controller = new PokedexController();
        List<Pokemon> pl = controller.getPokemonList(0);
        pl.stream().forEach(p->System.out.println(p.name()));
        SwingUtilities.invokeLater(() -> {
            PokedexView mainFrame = new PokedexView();
            mainFrame.setVisible(true);
        });
    }
}
