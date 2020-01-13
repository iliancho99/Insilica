package com.insilicokdd.machine_learning;

import com.insilicokdd.buttons.MainPageBtn;
import com.insilicokdd.gui.LayoutPlot;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

class DataSetDialog {
    private JDialog chooseDatasetDialog;
    private JButton selectDataSetBtn;
    private JButton selectDataSetBtn1;
    private JFileChooser j;

    DataSetDialog(GridBagLayout gridBagLayout, LayoutPlot plotLayout, GridBagConstraints gbc) {
        chooseDatasetDialog = new JDialog();
        chooseDatasetDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chooseDatasetDialog.setTitle("Dataset");
        chooseDatasetDialog.setLayout(gridBagLayout);


        addEscapeListener(chooseDatasetDialog);

        try {
            selectDataSetBtn = new MainPageBtn("Buttons/file_blank.png", "Select a dataset", 70, 70);
            selectDataSetBtn.setFont(new Font("Verdana", Font.PLAIN, 13));
            selectDataSetBtn1 = new MainPageBtn("Buttons/file_blank1.png", "Other (Diagnosis)", 70 , 70);
            selectDataSetBtn1.setFont(new Font("Verdana", Font.PLAIN, 13));
        } catch (IOException e) {
            e.printStackTrace();
        }


        selectDataSetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                selectDatasetBtnAction(evt);
            }
        });

        plotLayout.addobjects(selectDataSetBtn, chooseDatasetDialog, gridBagLayout, gbc, 1, 1, 0, 0, 1, 1);
    }

    public JDialog getDialog() {
        return chooseDatasetDialog;
    }

    private void selectDatasetBtnAction(ActionEvent evt) {
        j = new JFileChooser(FileSystemView.getFileSystemView());
        j.showOpenDialog(null);

        chooseDatasetDialog.setVisible(false);

    }

    public JFileChooser getJ() {
        return j;
    }


    // Close dialog on ESC_KEY
    public static void addEscapeListener(final JDialog dialog) {
        ActionListener escListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        };

        dialog.getRootPane().registerKeyboardAction(escListener,
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }
}
