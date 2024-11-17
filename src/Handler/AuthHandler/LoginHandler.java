package Handler.AuthHandler;

import GUI.Auth.*;
import GUI.MainFrameGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginHandler implements ActionListener {
    private final LoginGUI loginScreen;
    private final MainFrameGUI mainFrame;

    public LoginHandler(LoginGUI inputLoginScreen, MainFrameGUI inputFrame) {
        this.loginScreen = inputLoginScreen;
        this.mainFrame = inputFrame;


        loginScreen.getAdminBtn().addActionListener(this);
        loginScreen.getRegisterBtn().addActionListener(this);
        loginScreen.getForgotPasswordLink().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openForgotPasswordDialog(inputFrame);
            }
        });
        loginScreen.getLoginBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginScreen.getAdminBtn()) {
            mainFrame.showVerifyAdminGUIPanel();
        }
        else if (e.getSource() == loginScreen.getRegisterBtn()) {
            mainFrame.showRegisterPanel();
        }
        else if (e.getSource() == loginScreen.getLoginBtn()) {
            mainFrame.showChatPanel();
        }
    }
    private void openForgotPasswordDialog(JFrame parentFrame) {
        // Tạo dialog
        JDialog dialog = new JDialog(parentFrame, "Quên mật khẩu", true);
        dialog.setSize(300, 150);
        dialog.setLayout(new FlowLayout());

        JLabel emailLabel = new JLabel("Nhập email của bạn:");
        JTextField emailField = new JTextField(20);

        // Tạo nút OK
        JButton okButton = new JButton("OK");
        okButton.addActionListener(event -> {
            String email = emailField.getText();
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập email!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialog, "Yêu cầu đặt lại mật khẩu đã được gửi đến: " + email, "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            }
        });

        dialog.add(emailLabel);
        dialog.add(emailField);
        dialog.add(okButton);

        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }


}
