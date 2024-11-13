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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author diegoberumen, blakewellington
 */
public class TruthTableViewDB extends GPanel implements ActionListener {
    JTable table;
    private JButton submitBut;
    private DefaultTableModel modelTable;
    private JScrollPane tableScrollPane;
    private JButton hintButton;
    private JButton mainMenuButton;
    private JPanel buttonPanel;
    
     public TruthTableViewDB() {
        initializeComponents();
        layoutComponents();
    }
     
    private void initializeComponents() {

        modelTable = new DefaultTableModel(new Object[][]{}, new String[] {"A", "B", "C", "A->B", "B->C", "(A->B) ^ (B->C)"}) {
            @Override
            public boolean isCellEditable(int row, int column){
                //every cell editable
                return true;
            }
            @Override
            public void setValueAt(Object aValue, int row, int column){
                String value = aValue.toString().toUpperCase();
                //only allow t or f as inputs 
                if (value.equals("T") || value.equals("F")){
                    super.setValueAt(value, row, column);
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter T or F only.");
                }
            }
        };
        
        table = new JTable(modelTable);
        table.setGridColor(Color.BLACK);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(600, 300));

        //initialize table with 8 rows and empty cells
        modelTable.setRowCount(8);
        
        //submit button
        submitBut = new JButton("Submit");
        submitBut.addActionListener(this);
        
        //hint button
        hintButton = new JButton("Hint");
        hintButton.addActionListener(this);
        
        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(this);
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(hintButton);
        buttonPanel.add(submitBut);
        buttonPanel.add(mainMenuButton);
    }
    
    private void layoutComponents() {
        
        addc(new JScrollPane(table), 0, 0, GridBagConstraints.REMAINDER, 1, 1.0, 1.0,
         GridBagConstraints.CENTER, GridBagConstraints.BOTH,
         10, 10, 10, 10);
        
        // Add the hint button to the left
        addc(hintButton, 0, 1, 1, 1, 0.5, 0.0,
             GridBagConstraints.CENTER, GridBagConstraints.NONE,
             5, 5, 5, 5);
        
         // Add the submit button in the middle
        addc(submitBut, 1, 1, 1, 1, 0.5, 0.0,
             GridBagConstraints.CENTER, GridBagConstraints.NONE,
             5, 5, 5, 5);
        
        // Add the main menu button to the right
        addc(mainMenuButton, 2, 1, 1, 1, 0.5, 0.0,
             GridBagConstraints.CENTER, GridBagConstraints.NONE,
             5, 5, 5, 5);
         
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitBut) {
            System.out.println("**** SUBMIT ******");
            handleSubmit();
        }
        
        if (e.getSource() == hintButton) {
            System.out.println("***** HINT *****");
            handleHint();
        }
        if (e.getSource() == mainMenuButton) { 
            System.out.println("**** MAIN ****");
            handleMainMenu();
        }
    
    }
    
     private void handleSubmit() {
        // Sample feedback on correctness; customize for your logic validation
        StringBuilder feedback = new StringBuilder("Feedback:\n");
        for (int i = 0; i < modelTable.getRowCount(); i++) {
            for (int j = 0; j < modelTable.getColumnCount(); j++) {
                String value = (String) modelTable.getValueAt(i, j);
                feedback.append("Row ").append(i + 1).append(", Col ").append(j + 1).append(": ").append(value).append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, feedback.toString());
    }
    
    /**
     * Handle hint button click to provide a helpful hint.
     */
    private void handleHint() {
        JOptionPane.showMessageDialog(this, "Hint: Review logical expressions like 'A->B' and '(¬A ∨ B)' to determine the truth values.");
    }
    
    /**
     * Handle main menu button click to navigate back to the main menu.
     */
    private void handleMainMenu() {
        // Logic to navigate back to the main menu
        JOptionPane.showMessageDialog(this, "Returning to Main Menu...");
    }
}
