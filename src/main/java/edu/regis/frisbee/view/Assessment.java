package edu.regis.frisbee.view;

import javax.swing.*;
import javax.swing.table.*;
import edu.regis.frisbee.model.User;
import java.awt.*;


/**
 * 
 * @author Everett Seavy
 * Assessment class that creates an assessment view for the user and provides the course topics, 
 * their descriptions, the number of exposures and successes, and the success rate.
 * (Hardcoded data currently, no database or actual exposure/success data to dynamically use)
 * 
 */
public class Assessment extends JPanel {

    private JLabel user; // Label to display the user's name or ID
    private JTable assessmentTable; // Table to display topics, descriptions, and performance data
    private static User currentUser; // Reference to the currently logged-in user

    // Static method to set the current user for the assessment view
    public static void setUser(User user) {
        currentUser = user;
    }

    // Constructor to initialize and layout the components
    public Assessment() {
        initializeComponents();
        layoutComponents();
        styleComponents();
    }

    // Initialize all components in the view
    private void initializeComponents() {
        // Set up the user label with the current user's ID or default text.
        // (App does not have user/account database functionality. Can not resolve any names or ids for display.)
        if (currentUser != null) {
            String userID = currentUser.getUserId();
            user = new JLabel("User: " + userID);
        } else {
            user = new JLabel("User: Unknown");
        }

        // Column names for the table
        String[] columnNames = {"Topic", "Description", "Exposures", "Successes", "Percentage"};
        // Data for the table (can be dynamically replaced with actual user data once database functionality has been added.)
        Object[][] data = {
            {"Logical Equivalency", "Understanding how two logical expressions can be considered equivalent based on their truth values in all possible scenarios.", 2, 1, 50.0},
            {"Syntax", "The formal set of rules and structures used to write expressions, statements, or programs correctly in a given language.", 4, 2, 50.0},
            {"Truth Tables", "A systematic way to determine the truth value of logical expressions by listing all possible truth values of their components.", 6, 3, 50.0}
        };

        // Create a table model with the data and column names
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        // Create the table and override specific behaviors
        assessmentTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table non-editable
            }

            @Override
            public void doLayout() {
                super.doLayout();
                adjustRowHeights(this, data, 1); // Dynamically adjust row heights after layout
            }
        };

        // Set custom renderer for the "Description" column to enable text wrapping
        assessmentTable.getColumnModel().getColumn(1).setCellRenderer(new MultiLineCellRenderer());

        // Create a center alignment renderer for all other columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setVerticalAlignment(SwingConstants.CENTER);

        // Apply the center renderer to all columns except "Description"
        for (int i = 0; i < assessmentTable.getColumnCount(); i++) {
            if (i != 1) { // Skip "Description" column
                assessmentTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        // Perform the initial adjustment of row heights
        adjustRowHeights(assessmentTable, data, 1);

        // Enable full height filling for the table in the viewport
        assessmentTable.setFillsViewportHeight(true);
    }

    // Layout components in the view
    private void layoutComponents() {
        // Use BorderLayout with spacing
        setLayout(new BorderLayout(10, 10));

        // Panel to add padding and set a background color
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(245, 245, 245));

        // User label at the top in a dedicated panel
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userPanel.add(user);
        userPanel.setBackground(new Color(220, 220, 220));

        // Add the table inside a scroll pane
        JScrollPane tableScrollPane = new JScrollPane(assessmentTable);
        tableScrollPane.setBackground(Color.WHITE);
        tableScrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // Add components to the content panel
        contentPanel.add(userPanel, BorderLayout.NORTH);
        contentPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Add the content panel to the main view
        add(contentPanel, BorderLayout.CENTER);
    }

    // Style components for a professional look
    private void styleComponents() {
        // Style the user label with bold font and dark color
        user.setFont(new Font("SansSerif", Font.BOLD, 16));
        user.setForeground(new Color(50, 50, 50));

        // Style the table headers
        JTableHeader header = assessmentTable.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setBackground(new Color(200, 200, 200));
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false); // Prevent column reordering

        // Add alternating row colors for better readability
        assessmentTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? new Color(240, 240, 240) : Color.WHITE);
                } else {
                    c.setBackground(new Color(173, 216, 230)); // Light blue for selected rows
                }
                return c;
            }
        });
    }

    // Dynamically adjust row heights based on text content
    private void adjustRowHeights(JTable table, Object[][] data, int descriptionColumnIndex) {
        FontMetrics metrics = table.getFontMetrics(table.getFont());
        int columnWidth = table.getColumnModel().getColumn(descriptionColumnIndex).getWidth();

        for (int row = 0; row < data.length; row++) {
            String description = data[row][descriptionColumnIndex].toString();

            // Calculate the number of lines required for the text
            int charsPerLine = columnWidth / metrics.charWidth('W'); // Estimate using average char width
            int numLines = (int) Math.ceil((double) description.length() / charsPerLine);

            // Calculate the row height based on the number of lines
            int rowHeight = metrics.getHeight() * numLines;

            // Apply the calculated row height to the table
            table.setRowHeight(row, rowHeight + 5); // Add padding
        }
    }

    // Custom renderer to wrap text in the "Description" column
    static class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {
        public MultiLineCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "");
            setFont(table.getFont());
            setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
            return this;
        }
    }
}
