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

import edu.regis.frisbee.model.TutoringSession;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * Displays a tutoring session (the top-level GUI view for the application).
 *
 * Various aspects of the tutoring session are displayed in the child components
 * of this view.
 *
 * @author rickb
 */
public class TutoringSessionView extends GPanel  {
   
    /**
     * The tutoring session model displayed in this view.
     */
    private TutoringSession model;
   
    private JTabbedPane tabPanel;
    
    private TruthTableViewDB diegoView;
    private RicksView rickView;

    /**
     * Initialize this view including creating and laying out its child components.
     */
    public TutoringSessionView() {
        initializeComponents();
        layoutComponents();
    }

    /**
     * Return the model currently displayed in this view.
     *
     * @return a TutoringSession
     */
   public TutoringSession getModel() {
        return model;
    }

    /**
     * Display the given model in this view.
     *
     * @param model a TutoringSession.
     */
    public void setModel(TutoringSession model) {
        this.model = model;
    }


    /**
     * Create the child GUI components appearing in this frame.
     */
    private void initializeComponents() {
        tabPanel = new JTabbedPane();
        
        
        diegoView = new TruthTableViewDB();
        rickView = new RicksView();
        
        tabPanel.add("Truth Table View", diegoView);
        tabPanel.add("Rick View", rickView);
        
        
        tabPanel.addComponentListener(new ComponentListener() {
        
        @Override
        public void componentResized(ComponentEvent e){
            //tablePanelSize = tabPanel.getHeight();
            //table.set
        }
        
        @Override 
        public void componentMoved(ComponentEvent e){    
        }
        @Override
        public void componentShown(ComponentEvent e ){   
        }
        @Override
        public void componentHidden(ComponentEvent e){   
        }
        });
        
        
    }

    /**
     * Layout the child components in this view
     */
    private void layoutComponents() {
 
        addc(tabPanel, 0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                5, 5, 5, 5);
    }

}

