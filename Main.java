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
        //Frame
        frame = new JFrame (" Line Graphing Tool ");
        frame.setSize(475, 590);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        //Panel for graph
        graph = new MyPanel();

        //Making the input part
        inputPanel = new JPanel ();
        inputPanel.setLayout( new BoxLayout(inputPanel, BoxLayout.Y_AXIS) );
        inputPanel.setBackground( new Color(255,255,200));

        //Separating the graph from the input panel
        inputPanel.add(new JSeparator());
        inputPanel.add(Box.createVerticalStrut(10));

        //Text input area
        JLabel firstLabel = new JLabel ("y = ");
        firstLabel.setFont(new Font("Helvetica", Font.BOLD, 15));

        slopeInput = new JTextField ();
        slopeInput.setMargin(new Insets(2, 2, 2, 2));

        JLabel secondLabel = new JLabel (" x + ");
        secondLabel.setFont(new Font("Helvetica", Font.BOLD, 15));

        interceptInput = new JTextField();
        interceptInput.setMargin(new Insets(2, 2, 2, 2));

        //Box to hold the input area alongwith the paddings
        Box inputBox = new Box(BoxLayout.X_AXIS);
        inputBox.add(Box.createHorizontalStrut(20));
        inputBox.add(firstLabel);
        inputBox.add(slopeInput);
        inputBox.add(secondLabel);
        inputBox.add(interceptInput);
        inputBox.add(Box.createHorizontalStrut(20));

        //Button for input
        JButton graphButton = new JButton ("Graph");
        graphButton.addActionListener (new ButtonListener ());
        graphButton.setBackground(new Color(224,224,255));
        graphButton.setFocusPainted(false);

        //Adding to the input panel
        inputPanel.add(inputBox);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(graphButton);
        inputPanel.add(Box.createVerticalStrut(10));

        //Adding everything to the frame
        frame.add(BorderLayout.CENTER, graph);
        frame.add(BorderLayout.SOUTH, inputPanel);
    }

    void changeCoords (int tempX1, int tempY1, int tempX2, int tempY2) {
        int width = graph.getWidth(), height = graph.getHeight();

        x1 = tempX1 + (width/2);
        y1 = (height/2) - tempY1;
        x2 = tempX2 + (width/2);
        y2 = (height/2) - tempY2;
    }

    class ButtonListener implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            try {
                int m = Integer.parseInt( slopeInput.getText() );
                int c = Integer.parseInt( interceptInput.getText() );

                c *= 10;// So that if c = 10, the graph will plot it at 100 making it look good

                //Coordinates for the end point in normal way
                int tempY1 = graph.getHeight()/2, tempY2 = - tempY1;
                int tempX1 = (tempY1 - c)/m;
                int tempX2 = (tempY2 - c)/m;

                Main.this.changeCoords (tempX1, tempY1, tempX2, tempY2);//Change the coordinates for the graph panel
                graph.repaint();
            } catch (Exception er) {}
        }
    }

    //Custom Panel
    class MyPanel extends JPanel {
        public void paintComponent(Graphics g) {
            int width = this.getWidth(), height = this.getHeight();
            int originX = width/2, originY = height/2;

            //Set background to white
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            //Draw Axes

            //X-Axis
            g.setColor(new Color(0, 0, 0));
            g.drawLine(0, height/2, width, height/2);

            //Markings on the x-axis
            for (int currentMark = 1; currentMark < (width/10); currentMark++) {
                int x = (currentMark * 10) + originX;
                g.drawLine(x, originY - 2, x, originY + 2);//For positive x-axis
            }
            /*         LOGICAL ERROR HERE - It doesn't plot at the correct place (error - approx. 2px)
            //Markings on the -x-axis
            for (int currentMark = 1; currentMark < (width/20); currentMark++) {
                int x = (currentMark * 10);
                g.drawLine(x, originY - 2, x, originY + 2);//For negative x-axis
            }*/

            //Y-Axis
            g.drawLine(width/2, 0, width/2, height);

            //Draw the required line
            g.drawLine(x1, y1, x2, y2);
        }
    }
}