/*
 * This is the main class which will have the main method.
 */
import javax.swing.*;
class Main {
    private JFrame frame;
    
    public static void main (String args[]) {
        new Main().setUpGUI();
    }
    
    private void setUpGUI() {
        frame = new JFrame (" Line Graphing Tool ");
        frame.setSize(475, 590);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}