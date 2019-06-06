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
    private double x1, y1, x2, y2; //Coordinates of the end points of the graphed line

    public static void main (String args[]) {
        new Main().setUpGUI();
    }

    private void setUpGUI() {
        //Frame
        frame = new JFrame (" Line Graphing Tool ");
        frame.setSize(475, 590);
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

        slopeInput = new JTextField ("slope");
        slopeInput.setMargin(new Insets(4, 2, 4, 2));
        slopeInput.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    slopeInput.setText("");
                }
            });

        JLabel secondLabel = new JLabel (" x + ");
        secondLabel.setFont(new Font("Helvetica", Font.BOLD, 15));

        interceptInput = new JTextField("intercept");
        interceptInput.setMargin(new Insets(4, 2, 4, 2));
        interceptInput.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    interceptInput.setText("");
                }
            });

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
        
        frame.setVisible(true);//Setting it visible at the last line so that the app doesn't open with a blank window and require refresh
    }

    double[] changeCoords (double tempX1, double tempY1,double tempX2,double tempY2) {
        double width = graph.getWidth(), height = graph.getHeight();

        double[] moddedCoordinates = {tempX1 + (width/2), (height/2) - tempY1, tempX2 + (width/2), (height/2) - tempY2};

        return moddedCoordinates;
    }

    class ButtonListener implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            try {
                double m = Double.parseDouble( slopeInput.getText() );
                double c = Double.parseDouble( interceptInput.getText() );

                c *= 10;// So that if c = 10, the graph will plot it at 100 making it look good

                //Coordinates for the end point in normal way
                double tempY1 = graph.getHeight()/2, tempY2 = - tempY1;

                //If slope is non-zero 
                double tempX1 = (tempY1 - c)/m;
                double tempX2 = (tempY2 - c)/m;
                
                if (m == 0) {//For the y = c form
                    tempX1 = -graph.getWidth()/2;
                    tempY1 = c;
                    tempX2 = graph.getWidth()/2;
                    tempY2 = c;
                }

                double[] newCoord = Main.this.changeCoords (tempX1, tempY1, tempX2, tempY2);//Change the coordinates for the graph panel

                //Assigning the new coordinates
                x1 = newCoord[0];
                y1 = newCoord[1];
                x2 = newCoord[2];
                y2 = newCoord[3];

                graph.repaint();
            } catch (Exception er) {
                er.printStackTrace();
            }
        }
    }

    //Custom Panel
    class MyPanel extends JPanel {
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            double width = this.getWidth(), height = this.getHeight();
            int intWidth = (int)width, intHeight = (int) height; //Integer counterparts of double width and height to save time
            double originX = width/2, originY = height/2;

            //Set background to white
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, intWidth, intHeight);

            //Draw Axes

            //X-Axis
            g2.setColor(new Color(0, 0, 0));
            g2.drawLine(0, intHeight/2, intWidth, intHeight/2);

            //Markings on the x-axis
            for (int i = 0; i <= (width/2); i++) {
                double[] coords = Main.this.changeCoords(i*10, 2, i*10, -2);
                g2.drawLine((int)coords[0],(int) coords[1],(int) coords[2],(int) coords[3]);
            }

            // Negative x-axis markings
            for (int i = 0; i >= - (width/2); i--) {
                double[] coords = Main.this.changeCoords(i*10, 2, i*10, -2);
                g2.drawLine((int)coords[0],(int) coords[1],(int) coords[2],(int) coords[3]);
            }

            //Y-Axis
            g2.drawLine(intWidth/2, 0,intWidth/2, intHeight);

            //Markings on the y-axis
            for (int i = 0; i <= (height/2); i++) {
                double[] coords = Main.this.changeCoords(2, i*10, -2, i*10);
                g2.drawLine((int)coords[0],(int) coords[1],(int) coords[2],(int) coords[3]);
            }

            //Negative y-axis markings
            for (int i = 0; i >= -(height/2); i--) {
                double[] coords = Main.this.changeCoords(2, i*10, -2, i*10);
                g2.drawLine((int)coords[0],(int) coords[1],(int) coords[2],(int) coords[3]);
            }

            //Draw the required line
            g2.setStroke (new BasicStroke(2));
            g2.setColor(Color.GREEN);
            g2.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
        }
    }
}