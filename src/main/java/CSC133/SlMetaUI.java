package CSC133;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SlMetaUI {

    private final static Font defaultFont = new Font("Arial", Font.PLAIN, 45);
    public static String getFileName() {

        JTextField textField = new JTextField(25);
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 45));
        UIManager.put("OptionPane.buttonFont", defaultFont);
        textField.setFont(new Font("Arial", Font.PLAIN, 45)); // Set the font size as needed

        Object[] message = {
                "Please enter a file name:", textField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Save to file", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return textField.getText();
        }
        return null;
    }
    public static File getFile() {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setPreferredSize(new Dimension(800, 600)); // This might need adjustment too

        // Set the current directory
        fileChooser.setCurrentDirectory(new File("/home/ben/Programming/CSC 133/Assignment"));

        // Show the open file dialog
        int result = fileChooser.showOpenDialog(null);

        // Check if the user selected a file
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }


    public static void printUsage() {
        String usageTemplate = "%-15s: %s%n"; // Left-align with a width of 15 characters for the key
        System.out.println("--Usage Instructions--");
        System.out.format(usageTemplate, "ESC", "Exit program");
        System.out.format(usageTemplate, "H", "Halt render");
        System.out.format(usageTemplate, "D", "Delay render");
        System.out.format(usageTemplate, "F", "Display FPS");
        System.out.format(usageTemplate, "SPACE", "Start render");
        System.out.format(usageTemplate, "R", "Reset render");
        System.out.format(usageTemplate, "S", "Save to file");
        System.out.format(usageTemplate, "L", "Load from file");
        System.out.println("--End of Usage--");
    }

    public static void fps(long start, long end) {
        long fps = 1000 / (end - start + 1);
        System.out.println("fps: " + fps);
    }
}
