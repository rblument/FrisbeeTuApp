/*
 * Frisbee: Formal Logic Tutor
 * 
 *  (C) Richard Blumenthal, All rights reserved
 * 
 *  Unauthorized use, duplication or distribution without the authors'
 *  permission is strictly prohibited.
 * 
 *  Unless required by applicable law or agreed to in writing, this
 *  software is distributed on an "AS IS" basis without warranties
 *  or conditions of any kind, either expressed or implied.
 */
package edu.regis.frisbee.view;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 *
 * @author Sofia Reyes
 */
public class LogicEquivalence extends GPanel implements ActionListener {

    private DefaultTableModel model;
    private JButton eqLawsButton;
    private JLabel titleLabel;
    private JTextField userInput;

    public LogicEquivalence() {
        inputComponents();
        DisplayLawWindow();
        layoutComponents();
    }

    private void layoutComponents() {
        titleLabel = new JLabel("Practice Logical Equivalences");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 25));

        addc(titleLabel, 0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                5, 5, 5, 5);

        addc(userInput, 0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                5, 5, 5, 5);
        
        addc(eqLawsButton, 0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                5, 5, 5, 5);

    }
    
    public void inputComponents() {
        userInput = new JTextField();
        String hint1 = "Logical Equivalences";
        
       // userInput.setText(obscureText(hint1));
        
    }

    public void DisplayLawWindow() {
        eqLawsButton = new JButton("Reminder");
        eqLawsButton.setPreferredSize(new Dimension(150, 50));
        eqLawsButton.addActionListener(this);
    }

    public void TablePanelDisplay() {
        JFrame frame = new JFrame("Logical Equivalences Table");

        JTable table = new JTable(new MultiLineTableModel());

        table.getColumnModel().getColumn(1).setCellRenderer(new MultilineCellRenderer());

        JScrollPane pane = new JScrollPane(table);
        frame.add(pane);

        frame.setSize(500, 400);
        frame.setVisible(true);

    }

    static class MultiLineTableModel extends AbstractTableModel {

        private final String[] columnNames = {"Name", "Equivalence"};
        private final Object[][] data = {
            {"Identity Law", "p ⋀ T = p\np ∨ F = p"},
            {"Domination Law", "p ∨ T = T\np ⋀ F = F"},
            {"Idempotent Law", "p ∨ p = p\np ⋀ p= p"}
        };

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false; // Make the table non-editable
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == eqLawsButton) {
            TablePanelDisplay();
        }

    }
}
