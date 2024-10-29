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

/**
 *
 * @author rickb
 */
public class RicksView extends GPanel implements ActionListener {
    
    private JTextField fooField;
    
    private JButton submitBut;
    
    public RicksView() {
        initializeComponents();
        layoutComponents();
    }
    
    private void initializeComponents() {
        fooField = new JTextField();
        fooField.setText("Enter your foo");
        
        submitBut = new JButton("Submit");
        submitBut.addActionListener(this);
    }
    
    private void layoutComponents() {
        JLabel label = new JLabel("Foo");
        label.setLabelFor(fooField);
        addc(label, 0,0, 1,1, 0.0,0.0,
	     GridBagConstraints.NORTHWEST,  GridBagConstraints.NONE,
	     5,5,5,5);	
        
        addc(fooField, 1,0, 1,1, 1.0,0.0,
	     GridBagConstraints.NORTHWEST,  GridBagConstraints.HORIZONTAL,
	     5,5,5,5);	
        
         addc(submitBut, 0,1, 2,1, 0.0,0.0,
	     GridBagConstraints.CENTER,  GridBagConstraints.NONE,
	     5,5,5,5);	
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitBut) {
            System.out.println("**** HERE ******");
        }

    }
}
