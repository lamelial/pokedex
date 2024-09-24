package main.view;

import main.model.pokemon.PokemonType;

import javax.swing.*;
import java.awt.*;

public class PokedexListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof String) {
            String pokemonInfo = (String) value;
            String[] parts = pokemonInfo.split(", ");
            String typeInfo = parts[2]; // Adjust based on your format
            String[] types = typeInfo.split(": ")[1].split(", ");
            String typeName = (types[0].trim().toUpperCase());
            try {
                PokemonType type = PokemonType.valueOf(typeName);
                label.setBackground(type.getDisplayColour());
            } catch (IllegalArgumentException e) {
                label.setBackground(Color.WHITE);
            }
            label.setOpaque(true);
        }

        return label;
    }
}
