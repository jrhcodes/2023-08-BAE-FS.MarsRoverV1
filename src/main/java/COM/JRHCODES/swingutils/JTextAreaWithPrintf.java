package com.jrhcodes.swingutils;

import javax.swing.*;
import javax.swing.text.Document;

public class JTextAreaWithPrintf extends JTextArea {
    public JTextAreaWithPrintf() {
    }

    public JTextAreaWithPrintf(String text) {
        super(text);
    }

    public JTextAreaWithPrintf(int rows, int columns) {
        super(rows, columns);
    }

    public JTextAreaWithPrintf(String text, int rows, int columns) {
        super(text, rows, columns);
    }

    public JTextAreaWithPrintf(Document doc) {
        super(doc);
    }

    public JTextAreaWithPrintf(Document doc, String text, int rows, int columns) {
        super(doc, text, rows, columns);
    }

    public void printf(String formatString, Object... args) {
        String formattedText = String.format(formatString, args);
        super.append(formattedText);
    }

    public void clear() {
        super.setText(null);
    }

    public void newLine() {
        this.append("\n");
    }
}
