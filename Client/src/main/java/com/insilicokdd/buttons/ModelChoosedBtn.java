package com.insilicokdd.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ModelChoosedBtn extends JButton implements MouseListener {
    public ModelChoosedBtn(String text, ActionListener listener, MouseListener mouseListener) {
        super(text);
        setFont(new Font("Verdana", Font.BOLD, 11));
        setForeground(new Color(0, 0, 7));
        setBackground(new Color(255, 255, 255));
        addActionListener(listener);
        addMouseListener(mouseListener);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
