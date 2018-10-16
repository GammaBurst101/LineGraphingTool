/*
 * This is the main class which will have the main method.
 */
import javax.swing.*;
import java.awt.*;
class Main {
    private JFrame frame;
    private MyPanel graph;
    public static void main (String args[]) {
        new Main().setUpGUI();
    }
    
    private void setUpGUI() {
        frame = new JFrame (" Line Graphing Tool ");
        frame.setSize(475, 590);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        graph = new MyPanel();
        frame.getContentPane().add(BorderLayout.CENTER, graph);
    }
    
    //Custom Panel
    class MyPanel extends JPanel {
        public void paintComponent(Graphics g) {
            //Set background to white
            g.setColor(new Color (255, 255, 255));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            
            //Draw Axes
            int length = this.getWidth(), height = this.getHeight();
            //X-Axis
            g.setColor(new Color(0, 0, 0));
            g.drawLine(0, height/2, length, height/2);
            
            //Y-Axis
            g.drawLine(length/2, 0, length/2, height);
        }
    }
}