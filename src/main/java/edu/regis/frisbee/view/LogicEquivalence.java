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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Sophie Holland
 */
public class LogicEquivalence extends GPanel implements ActionListener {
    
    private JLabel title;
    private JLabel instructions;
    private JLabel qOne;
    private JLabel qTwo;
    private JLabel qThree;
    private JLabel qFour;
    private JLabel qFive;
    private JLabel qSix;
    
    private JTextField qOneAnswer;
    private JTextField qTwoAnswer;
    private JTextField qThreeAnswer;
    private JTextField qFourAnswer;
    private JTextField qFiveAnswer;
    private JTextField qSixAnswer;
       
    private JButton reminderButt;
    private JButton closeButt;
    private JButton submitButt;
    private JButton hintButt;
    
    public LogicEquivalence() {
        initializeComponents();
        display();
    }
    
    private void initializeComponents() {
        title = new JLabel();
        title.setText("Practice: Logic Equivalences");
        
        instructions = new JLabel();
        instructions.setText("<html>Task – Simplify the following expressions. "
                + "You may or may not need to use the laws we just covered to "
                + "solve the problems. Use the buttons to the right for special "
                + "characters as needed. If you forget the laws, click the "
                + "“Reminder” button. If you get stuck, click “Hint” for extra "
                + "help. When you are done, click “Submit” to check your answers.<html>");
        
        qOne = new JLabel("What is the negation of 2≤x≤3?");
        qTwo = new JLabel("Is the following true or false? (p⇒q)∧(p⇒q)≡p");
        qThree = new JLabel("True or False? F→not q");
        qFour = new JLabel("True or False? p∨T");
        qFive = new JLabel("True or False? F∧p");
        qSix = new JLabel("True or False? not T∨F");
        
        qOneAnswer = new JTextField();
        qTwoAnswer = new JTextField();
        qThreeAnswer = new JTextField();
        qFourAnswer = new JTextField();
        qFiveAnswer = new JTextField();
        qSixAnswer = new JTextField();
        
        reminderButt = new JButton("Reminder");
        reminderButt.addActionListener(this);
        
        submitButt = new JButton("Submit");
        submitButt.addActionListener(this);
        
        hintButt = new JButton("Hint");
        hintButt.addActionListener(this);
    }
    
    private void display() {   
        title.setFont(new Font("Arial", Font.PLAIN, 24));

        addc(title, 0, 0, 1, 1, 0.1, 0.1,
           GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
           5, 5, 5, 5);
        
//        addc(instructions, 0, 1, 1, 1, 0.1, 0.1,
//           GridBagConstraints.NORTHWEST, GridBagConstraints.VERTICAL,
//           5, 5, 5, 5);
        
        addc(qOne, 0,2, 1,1, 0.0,0.0,
	     GridBagConstraints.LINE_START,  GridBagConstraints.NONE,
	     5,5,5,5);	
        
        addc(qTwo, 0,3, 1,1, 0.0,0.0,
	     GridBagConstraints.LINE_START,  GridBagConstraints.NONE,
	     5,5,5,5);	
        
        addc(qThree, 0,4, 1,1, 0.0,0.0,
	     GridBagConstraints.LINE_START,  GridBagConstraints.NONE,
	     5,5,5,5);	
        
        addc(qFour, 0,5, 1,1, 0.0,0.0,
	     GridBagConstraints.LINE_START,  GridBagConstraints.NONE,
	     5,5,5,5);	

        addc(qFive, 0,6, 1,1, 0.0,0.0,
	     GridBagConstraints.LINE_START,  GridBagConstraints.NONE,
	     5,5,5,5);	

        addc(qSix, 0,7, 1,1, 0.0,0.0,
	     GridBagConstraints.LINE_START,  GridBagConstraints.NONE,
	     5,5,5,5);	
        
        addc(qOneAnswer, 1,2, 1,1, 1.0,0.0,
	     GridBagConstraints.LINE_START,  GridBagConstraints.HORIZONTAL,
	     5,5,5,5);
        
        addc(qTwoAnswer, 1,3, 1,1, 1.0,0.0,
	     GridBagConstraints.LINE_START,  GridBagConstraints.HORIZONTAL,
	     5,5,5,5);

        addc(qThreeAnswer, 1,4, 1,1, 1.0,0.0,
	     GridBagConstraints.LINE_START,  GridBagConstraints.HORIZONTAL,
	     5,5,5,5);
       
        addc(qFourAnswer, 1,5, 1,1, 1.0,0.0,
	     GridBagConstraints.LINE_START,  GridBagConstraints.HORIZONTAL,
	     5,5,5,5);
        
        addc(qFiveAnswer, 1,6, 1,1, 1.0,0.0,
	     GridBagConstraints.LINE_START,  GridBagConstraints.HORIZONTAL,
	     5,5,5,5);
        
        addc(qSixAnswer, 1,7, 1,1, 1.0,0.0,
	     GridBagConstraints.LINE_START,  GridBagConstraints.HORIZONTAL,
	     5,5,5,5);
        
        addc(reminderButt, 0, 8, 1, 3, 0.0, 0.0,
           GridBagConstraints.LINE_START, GridBagConstraints.NONE,
           5, 5, 5, 5);  
        
        addc(hintButt, 1, 8, 1, 3, 0.0, 0.0,
           GridBagConstraints.LINE_START, GridBagConstraints.NONE,
           5, 5, 5, 5);  
        
        addc(submitButt, 2, 8, 1, 3, 1.0, 0.0,
           GridBagConstraints.LINE_START, GridBagConstraints.NONE,
           5, 5, 5, 5);
    }    
    
    public void reminderView() {
        closeButt = new JButton("Close");
        closeButt.addActionListener(this);
        
        JFrame frame = new JFrame("Logic Equivalences Reminder");
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        String[][] equivalences = {
            { "Commutative properties", "p∨q≡q∨p", "p∧q≡q∧p" },
            { "Associative properties", "(p∨q)∨r≡p∨(q∨r)", "(p∧q)∧r≡p∧(q∧r)" },
            { "Distributive laws", "p∨(q∧r)≡(p∨q)∧(p∨r)", "p∧(q∨r)≡(p∧q)∨(p∧r)" },
            { "Idempotent laws", "p∨p≡p", "p∧p≡p" },
            { "De Morgan’s laws", "p∨q≡p∧q", "p∧q≡p∨q" },
            { "inverse laws", "p∨p≡T", "p∧p≡F" },
            { "Identity laws", "p∨F≡p", "p∧T≡p" },
            { "Domination laws", "p∨T≡T", "p∧F≡F" }
        };
        String[] header = { "Property Name", "Property 1", "Property 2" };
        JTable table = new JTable(equivalences, header);
        
        JScrollPane tableContainer = new JScrollPane(table);
        
        panel.add(tableContainer, BorderLayout.CENTER);
        panel.add(closeButt, BorderLayout.SOUTH);
        frame.getContentPane().add(panel);
        
        frame.pack();
        frame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reminderButt) {
            System.out.println("------Button clicked to view Logic Equivalences "
                    + "laws------");
            reminderView();
        }
        
        if (e.getSource() == closeButt) {
            System.out.println("------Button clicked to close reminder view------");
            JComponent comp = (JComponent) e.getSource();
            Window win = SwingUtilities.getWindowAncestor(comp);
            win.dispose();
         }
        
        if (e.getSource() == submitButt) {
            System.out.println("------Submit button pressed for logic equivalences------");
            
            qOneAnswer.setBackground(Color.red);
            qTwoAnswer.setBackground(Color.green);
            
            if (qThreeAnswer.getText().equals("True")) {
                qThreeAnswer.setBackground(Color.green);
            }
            else {
                qThreeAnswer.setBackground(Color.red);
            }
            
            if (qFourAnswer.getText().equals("True")) {
                qFourAnswer.setBackground(Color.green);
            }
            else {
                qFourAnswer.setBackground(Color.red);
            }
            
            if (qFiveAnswer.getText().equals("False")) {
                qFiveAnswer.setBackground(Color.green);
            }
            else {
                qFiveAnswer.setBackground(Color.red);
            }
            
            if (qSixAnswer.getText().equals("False")) {
                qSixAnswer.setBackground(Color.green);
            }
            else {
                qSixAnswer.setBackground(Color.red);
            }
        }
        
        if (e.getSource() == hintButt) {
            qOneAnswer.setText("Try using Demorgan's Law");
            qTwoAnswer.setText("Consider breaking the question down into parts");
            qThreeAnswer.setText("T stands for tautology & F stands for contradiction");
            qFourAnswer.setText("T stands for tautology & F stands for contradiction");
            qFiveAnswer.setText("T stands for tautology & F stands for contradiction");
            qSixAnswer.setText("T stands for tautology & F stands for contradiction");
            
            System.out.println("------Hint Button selected for logic equivalences------");
        }
    }
}
