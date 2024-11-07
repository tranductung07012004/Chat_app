package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    private MainFrameGUI mainFrame;

    public SettingsPanel(MainFrameGUI mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Settings", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Panel for updating user information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(7, 2, 10, 10)); // Updated to accommodate more fields
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create labels and input fields for user information
        JLabel fullNameLabel = new JLabel("Full Name: ");
        JTextField fullNameField = new JTextField();
        
        JLabel genderLabel = new JLabel("Gender: ");
        String[] genders = {"Male", "Female", "Other"};
        JComboBox<String> genderComboBox = new JComboBox<>(genders);
        
        JLabel addressLabel = new JLabel("Address: ");
        JTextField addressField = new JTextField();
        
        JLabel dobLabel = new JLabel("Date of Birth: ");
        JTextField dobField = new JTextField(); // Use a text field for simplicity, can replace with DatePicker
        
        JLabel emailLabel = new JLabel("Email: ");
        JTextField emailField = new JTextField();
        
        JLabel passwordLabel = new JLabel("New Password: ");
        JPasswordField passwordField = new JPasswordField();

        // Add components to the info panel
        infoPanel.add(fullNameLabel);
        infoPanel.add(fullNameField);
        infoPanel.add(genderLabel);
        infoPanel.add(genderComboBox);
        infoPanel.add(addressLabel);
        infoPanel.add(addressField);
        infoPanel.add(dobLabel);
        infoPanel.add(dobField);
        infoPanel.add(emailLabel);
        infoPanel.add(emailField);
        infoPanel.add(passwordLabel);
        infoPanel.add(passwordField);

        add(infoPanel, BorderLayout.CENTER);

        // Panel for buttons (Save and Back)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Save Changes Button
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> {
            // Gather data from the fields
            String fullName = fullNameField.getText().trim();
            String gender = (String) genderComboBox.getSelectedItem();
            String address = addressField.getText().trim();
            String dob = dobField.getText().trim();
            String email = emailField.getText().trim();
            String newPassword = new String(passwordField.getPassword()).trim();

            // Validation and logic to save data
            if (!fullName.isEmpty() && !address.isEmpty() && !dob.isEmpty() && !email.isEmpty() && !newPassword.isEmpty()) {
                // Update user information logic here (e.g., store it in a database)
                JOptionPane.showMessageDialog(this, "Changes saved successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all the fields.");
            }
        });

        // Back Button to navigate to the previous panel
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            // Navigate back to the previous panel (you can define the exact logic in MainFrameGUI)
            mainFrame.showChatPanel(); // You can define this method in MainFrameGUI to manage panel transitions
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
