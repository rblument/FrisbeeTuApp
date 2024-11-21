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

import java.awt.*;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author sofia
 */
public class MultilineCellRenderer extends JTextArea implements TableCellRenderer {

    public MultilineCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        setText(value != null ? value.toString() : "");

        // Adjust row height to fit the content
        int newHeight = getPreferredSize().height;
        if (table.getRowHeight(row) != newHeight) {
            table.setRowHeight(row, newHeight);
        }

        return this;
    }

}
