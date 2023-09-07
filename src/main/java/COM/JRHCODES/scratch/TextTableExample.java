package com.jrhcodes.scratch;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TextTableExample {
    public static void main(String[] args) {
        // Create a JFrame
        JFrame frame = new JFrame("Text Table Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sample data for the table
        String[][] data = {
                {"John", "Doe", "30"},
                {"Jane", "Smith", "25"},
                {"Bob", "Johnson", "35"}
        };

        // Column names
        String[] columnNames = {"First Name", "Last Name", "Age"};

        // Create a table model and set the data and column names
        DefaultTableModel model = new DefaultTableModel(data, columnNames);


        // Create a JTable with the model
        JTable table = new JTable(model);

        // Create a JScrollPane to hold the table
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the JScrollPane to the JFrame
        frame.add(scrollPane);

        // Set JFrame size and make it visible
        frame.setSize(400, 200);
        frame.setVisible(true);
    }
}


