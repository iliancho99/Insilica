package com.insilicokdd.gui;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;


public class FComponent {

    public static JButton getJButton(JFrame jFrame, String text, int fontSize, int width, int height, int x, int y,
                                     ActionListener aListener) {
        JButton jButton = new JButton(text);
        jButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
        jButton.setSize(width, height);
        jButton.setLocation(x, y);
        jButton.addActionListener(aListener);
        jFrame.getContentPane().add(jButton);
        return jButton;
    }

    public static JLabel getJLabel(JFrame jFrame, String text, int fontSize, int width, int height, int x, int y) {
        JLabel jLabel = new JLabel(text);
        jLabel.setFont(new Font("Arial", Font.PLAIN, fontSize));
        jLabel.setSize(width, height);
        jLabel.setLocation(x, y);
        jFrame.getContentPane().add(jLabel);
        return jLabel;
    }

    public static JComboBox getJComboBox(JFrame jframe, String[] a, int fontSize, int width, int height, int x, int y) {
        JComboBox jComboBox = new JComboBox(a);

        jComboBox.setFont(new Font("Arial", Font.PLAIN, fontSize));
        jComboBox.setSize(width, height);
        jComboBox.setLocation(x, y);
        jframe.getContentPane().add(jComboBox);
        return jComboBox;
    }

    public static JList getJList(JFrame jFrame, MouseListener mouseListener, Vector model, int x, int y) {
        JList jlist = new JList<>(model);
        jlist.setSize(200, 250);
        jlist.setLocation(x, y);
        jlist.addMouseListener(mouseListener);
        jlist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jlist.setSelectedIndex(0);
        jlist.setBackground(new Color(255, 255, 255));
        jlist.setForeground(new Color(0, 0, 77));
        jFrame.getContentPane().add(jlist);
        return jlist;
    }

    public static JTextArea getJTextArea(JFrame jFrame, int fontSize, int width, int height, int x, int y) {
        JTextArea jTextArea = new JTextArea();
        jTextArea.setFont(new Font("Arial", Font.PLAIN, fontSize));
        jTextArea.setSize(width, height);
        jTextArea.setLocation(x, y);
        jTextArea.setLineWrap(true);
        jFrame.getContentPane().add(jTextArea);
        return jTextArea;
    }

    public static JRadioButton getJRadionButton(JFrame jFrame, String text, int fontSize, int width, int height, int x, int y,
                                                boolean selected) {
        JRadioButton jRadionButton = new JRadioButton(text);
        jRadionButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
        jRadionButton.setSelected(selected);
        jRadionButton.setSize(width, height);
        jRadionButton.setLocation(x, y);
        jFrame.getContentPane().add(jRadionButton);
        return jRadionButton;
    }

    public static void insertField(JTextField field, List<JTextField> fieldList) {
        fieldList.add(field);
    }

    public static JTextField getJTextField(JFrame jFrame, int fontSize, int width, int height, int x, int y, List<JTextField> fieldList) {
        JTextField jTextField = new JTextField();
        jTextField.setFont(new Font("Verdana", Font.PLAIN, fontSize));
        jTextField.setSize(width, height);
        jTextField.setLocation(x, y);
        jFrame.getContentPane().add(jTextField);
        insertField(jTextField, fieldList);
        return jTextField;
    }

    public static JPasswordField getJTextPassword(JFrame jFrame, int fontSize, int width, int height, int x, int y) {
        JPasswordField jTextField = new JPasswordField();
        jTextField.setFont(new Font("Verdana", Font.PLAIN, fontSize));
        jTextField.setSize(width, height);
        jTextField.setLocation(x, y);
        jFrame.getContentPane().add(jTextField);
        return jTextField;
    }

    public static JTextField getJTextField(JFrame jFrame, int fontSize, int width, int height, int x, int y) {
        JTextField jTextField = new JTextField();
        jTextField.setFont(new Font("Verdana", Font.PLAIN, fontSize));
        jTextField.setSize(width, height);
        jTextField.setLocation(x, y);
        jFrame.getContentPane().add(jTextField);
        return jTextField;
    }



    public static ButtonGroup getButtonGroup(JRadioButton val1, JRadioButton val2) {
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(val1);
        buttonGroup.add(val2);
        return buttonGroup;
    }


    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (java.text.ParseException e) {
            return null;
        }
    }


    public static String convertCbtoString(String year, String month, String day) {
        Date date = FComponent.parseDate(new StringBuilder().append(year)
                .append("-")
                .append(month).append("-").append(day).toString());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static void addToJPanel(JPanel jPanel, JLabel jLabel) {
        jPanel.add(jLabel);
    }


    public static Date convertDate(JComboBox cbYear, JComboBox cbMonth, JComboBox cbDay) {
        String year = (String) cbYear.getSelectedItem();
        String month = (String) cbMonth.getSelectedItem();
        String day = (String) cbDay.getSelectedItem();
        StringBuilder sb = new StringBuilder(year).append("-").append(month).append("-")
                .append(day);

        LocalDate localDate = LocalDate.parse(sb.toString());
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }


}