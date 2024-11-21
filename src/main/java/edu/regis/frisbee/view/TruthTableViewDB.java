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
import edu.regis.frisbee.svc.ProblemService;
import edu.regis.frisbee.svc.ProblemService.Problem;
import java.awt.Component;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author diegoberumen, blakewellington
 */
public class TruthTableViewDB extends GPanel implements ActionListener {
    private final ProblemService problemService = new ProblemService();
    JTable table;
    private JButton submitBut;
    private DefaultTableModel modelTable;
    private JScrollPane tableScrollPane;
    private JButton hintButton;
    private JButton mainMenuButton;
    private JPanel buttonPanel;
    private JTextField title;
    
    public TruthTableViewDB() {
        initializeComponents();
        layoutComponents();
        loadProblem(1);
    }
     
    private void loadProblem(int id) { 
        Problem problem = problemService.getProblemById(id);
        if (problem != null) {
            // Update table headers
            modelTable.setColumnIdentifiers(problem.getHeaders());
            // Clear existing rows
            modelTable.setRowCount(0);
            // Add new rows
            for (String[] row : problem.getRows()) {
                modelTable.addRow(row);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Problem not found!");
        }
     }
     
    private void handleSubmit() {
        Problem currentProblem = problemService.getProblemById(1); // Assuming ID is 1 for now
        if (currentProblem == null) {
            JOptionPane.showMessageDialog(this, "No problem loaded!");
            return;
        }

        String[][] correctAnswers = currentProblem.getCorrectAnswers();
        int correctCount = 0;
        int totalCells = modelTable.getRowCount() * modelTable.getColumnCount();

        for (int i = 0; i < modelTable.getRowCount(); i++) {
            for (int j = 0; j < modelTable.getColumnCount(); j++) {
                String userAnswer = (String) modelTable.getValueAt(i, j);
                String correctAnswer = correctAnswers[i][j];

                if (userAnswer != null && userAnswer.equalsIgnoreCase(correctAnswer)) {
                    correctCount++;
                } else {
                    //table.setValueAt("", i, j); // Optional: Clear incorrect answers
                }
            }
        }
    
        int incorrectCount = totalCells - correctCount;
        String feedback = String.format("Correct: %d, Incorrect: %d", correctCount, incorrectCount);
        highlightIncorrectAnswers();

        JOptionPane.showMessageDialog(this, feedback);
    }
     
     
    private void highlightIncorrectAnswers() {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Problem currentProblem = problemService.getProblemById(1); // Assuming ID is 1
                if (currentProblem != null) {
                    String correctAnswer = currentProblem.getCorrectAnswers()[row][column];
                    String userAnswer = (String) table.getValueAt(row, column);
                    if (userAnswer == null || !userAnswer.equalsIgnoreCase(correctAnswer)) {
                        cell.setBackground(Color.RED); // Highlight incorrect answers
                    } else {
                        cell.setBackground(Color.green); // Reset for correct answers
                    }
                }
                return cell;
            }
        });
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
       //modelTable.setRowCount(8);
        
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
        //JLabel label = new JLabel("Practice Truth Table");
        
        
        //addc(label, 0,0, 1,1, 0.0,0.0,
	 //    GridBagConstraints.NORTHWEST,  GridBagConstraints.NONE,
	 //    5,5,5,5);	
        
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
