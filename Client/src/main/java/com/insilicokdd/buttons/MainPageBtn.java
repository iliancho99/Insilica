package com.insilicokdd.buttons;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class MainPageBtn extends JButton implements MouseListener {
    private boolean mouseInside;

    public MainPageBtn(String imgPath, String text, ActionListener listener, int imgWidth, int imgHeight) throws IOException {
        super(text);
        ClassLoader classLoader = getClass().getClassLoader();
        File imageFile = new File(Objects.requireNonNull(classLoader.getResource(imgPath)).getFile());
        setPreferredSize(new Dimension(200, 100));
        Image image = ImageIO.read(imageFile);
        Image openFilesImg = image.getScaledInstance(imgWidth, imgHeight, Image.SCALE_DEFAULT);
        setIcon(new ImageIcon(openFilesImg));
        setBackground(new Color(255, 255, 255));
//        setForeground(new Color(17, 0, 51));
        setForeground(new Color(0, 0, 77));

        setBorderPainted(false);
        setFont(new Font("Verdana", Font.BOLD, 16));
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
        addMouseListener(this);
        addActionListener(listener);
    }

    public MainPageBtn(String imgPath, String text, int imgWidth, int imgHeight) throws IOException {
        super(text);
        ClassLoader classLoader = getClass().getClassLoader();
        File imageFile = new File(Objects.requireNonNull(classLoader.getResource(imgPath)).getFile());
        setPreferredSize(new Dimension(150, 100));
        Image image = ImageIO.read(imageFile);
        setBorderPainted( false );
        setRolloverEnabled(false);
        setFocusPainted( false );
        setFocusable(false);

        Image openFilesImg = image.getScaledInstance(imgWidth, imgHeight, Image.SCALE_DEFAULT);
        setIcon(new ImageIcon(openFilesImg));
        setBackground(new Color(255, 255, 255));
//        setForeground(new Color(17, 0, 51));
        setForeground(new Color(0, 0, 77));

        setBorderPainted(false);
        setFont(new Font("Verdana", Font.BOLD, 16));
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
        addMouseListener(this);
    }

    public MainPageBtn(String imgPath, int imgWidth, int imgHeight) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File imageFile = new File(Objects.requireNonNull(classLoader.getResource(imgPath)).getFile());
        setPreferredSize(new Dimension(200, 100));
        Image image = ImageIO.read(imageFile);

        Image openFilesImg = image.getScaledInstance(imgWidth, imgHeight, Image.SCALE_DEFAULT);
        setIcon(new ImageIcon(openFilesImg));
        setBackground(new Color(255, 255, 255));
//        setForeground(new Color(17, 0, 51));
        setForeground(new Color(0, 0, 77));

        setBorderPainted(false);
        setFont(new Font("Verdana", Font.BOLD, 16));
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
        addMouseListener(this);
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
