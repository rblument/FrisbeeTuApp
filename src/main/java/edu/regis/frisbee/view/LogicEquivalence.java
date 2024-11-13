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

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
/**
 *
 * @author Sophie Holland
 */
public class LogicEquivalence extends GPanel implements ActionListener {
    
    private JLabel title;
    
    private JButton enterBut;
    
    public LogicEquivalence() {
        initializeComponents();
        display();
    }
    
    private void initializeComponents() {
        title = new JLabel();
        title.setText("Practice: Logic Equivalences");
        
        enterBut = new JButton("Enter");
        enterBut.addActionListener(this);
    }
    
    private void display() {   
        title.setFont(new Font("Serif", Font.PLAIN, 24));

        addc(title, 0, 0, 1, 1, 0.1, 0.1,
           GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
           5, 5, 5, 5);

    }    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enterBut) {
            System.out.println("Enter Button on LogicEquivalence Clicked");
        }
    }
}
