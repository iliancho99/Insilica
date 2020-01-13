package com.insilicokdd.buttons;

import javax.swing.*;
import javax.xml.soap.Text;
import java.awt.*;

public class MenuButton extends JButton {
    public MenuButton(String text) {
        super(text);
        setFont(new Font("Verdana", Font.BOLD, 13));
        setFocusPainted(false);
        setMargin(new Insets(0, 0, 0, 0));
        setContentAreaFilled(true);
        setBorderPainted(false);
        setOpaque(true);
    }
}
