import javax.swing.*;

public class Main {
    public static void main(String... args) {
        JFrame f = new JFrame(); // Creating instance of JFrame
        JButton b = new JButton("Click"); // creating instance of JButton
        b.setBounds(130, 100, 100, 40); // x-axis, y-axis, width, height
        f.add(b); //adding button in JFrame
        f.setSize(400, 500);
        f.setLayout(null); // Using no layout managers
        f.setVisible(true); // making the frame visible
    }
}