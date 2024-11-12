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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Sophie Holland
 */
public class LogicEquivalence extends GPanel implements ActionListener {
    
    private JTextField fooField;
    
    private JButton enterBut;
    
    public LogicEquivalence() {
        initializeComponents();
        layoutComponents();
        
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Logical Equivalences", TitledBorder.CENTER, TitledBorder.TOP));
        
        String[][] equivalences = {
            {"Commutative Properties", "p∨q≡q∨p", "p∧q≡q∧p"},
            {"Associative Properties", "(p∨q)∨r≡p∨(q∨r)", "(p∧q)∧r≡p∧(q∧r)"},
        };
        String[] header = {"Property Name", "Property 1", "Property 2"};
        JTable table = new JTable(equivalences, header);
        panel.add(new JScrollPane(table));
        frame.add(panel);
        frame.setVisible(true);
    }
    
//    public static void view(String[] args) {
//        JFrame frame = new JFrame();
//        JPanel panel = new JPanel();
//        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Logical Equivalences", TitledBorder.CENTER, TitledBorder.TOP));
//        
//        String[][] equivalences = {
//            {"Commutative Properties", "p∨q≡q∨p", "p∧q≡q∧p"},
//            {"Associative Properties", "(p∨q)∨r≡p∨(q∨r)", "(p∧q)∧r≡p∧(q∧r)"},
//        };
//        String[] header = {"Property Name", "Property 1", "Property 2"};
//        JTable table = new JTable(equivalences, header);
//        panel.add(new JScrollPane(table));
//        frame.add(panel);
//        frame.setVisible(true);
//    }
    
    private void initializeComponents() {
        fooField = new JTextField();
        fooField.setText("Enter Logic Equivalence");
        
        enterBut = new JButton("Enter");
        enterBut.addActionListener(this);
    }
    
    private void layoutComponents() {
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enterBut) {
            System.out.println("Enter Button on LogicEquivalence Clicked");
        }
    }
}
