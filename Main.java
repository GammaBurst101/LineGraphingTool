/*
 * This is the main class which will have the main method.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
class Main {
    private JFrame frame;
    private MyPanel graph;
    private JPanel inputPanel;
    private JTextField slopeInput, interceptInput;
    private int x1, y1, x2, y2; //Coordinates of the end points of the graphed line
    
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
        
        //Making the input side
        inputPanel = new JPanel ();
        inputPanel.setLayout( new BoxLayout(inputPanel, BoxLayout.Y_AXIS) );
        
        JLabel firstLabel = new JLabel ("y = ");
        slopeInput = new JTextField ();
        JLabel secondLabel = new JLabel ("x + ");
        interceptInput = new JTextField();
        
        Box inputBox = new Box(BoxLayout.X_AXIS);//To hold the input area
        inputBox.add(firstLabel);
        inputBox.add(slopeInput);
        inputBox.add(secondLabel);
        inputBox.add(interceptInput);
        
        JButton graphButton = new JButton ("Graph");
        graphButton.addActionListener (new ButtonListener ());
        
        inputPanel.add(inputBox);
        inputPanel.add(graphButton);
        
        //Adding everything to the frame
        frame.add(BorderLayout.CENTER, graph);
        frame.add(BorderLayout.SOUTH, inputPanel);
    }
    
    void changeCoords (int tempX1, int tempY1, int tempX2, int tempY2) {
        x1 = tempX1 + (graph.getWidth()/2);
        y1 = (graph.getHeight()/2) - tempY1;
        x2 = tempX2 + (graph.getWidth()/2);
        y2 = (graph.getHeight()/2) - tempY2;
    }
    
    class ButtonListener implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            int m = Integer.parseInt( slopeInput.getText() );
            int c = Integer.parseInt( interceptInput.getText() );
            
            //Coordinates for the end point in normal way
            int tempY1 = graph.getHeight()/2, tempY2 = - tempY1;
            int tempX1 = (tempY1 - c)/m;
            int tempX2 = (tempY2 - c)/m;
            
            Main.this.changeCoords (tempX1, tempY1, tempX2, tempY2);
            graph.repaint();
        }
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
            
            //Draw the required line
            g.drawLine(x1, y1, x2, y2);
        }
    }
}