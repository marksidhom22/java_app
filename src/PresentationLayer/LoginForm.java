package PresentationLayer;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JComboBox<String> userTypeDropdown;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JCheckBox securityCheck_checkbox; // Checkbox for skipping security

    public LoginForm() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        add(panel);
        placeComponents(panel);

        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("User Type:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        String[] userTypes = {"Staff", "Customer"};
        userTypeDropdown = new JComboBox<>(userTypes);
        userTypeDropdown.setBounds(100, 20, 160, 25);
        panel.add(userTypeDropdown);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 50, 160, 25);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 80, 80, 25);
        panel.add(loginButton);

        securityCheck_checkbox = new JCheckBox("Security Check");
        securityCheck_checkbox.setBounds(100, 110, 120, 25);
        panel.add(securityCheck_checkbox);
        securityCheck_checkbox.setSelected(false);

    // ActionListener for login button remains the same
    loginButton.addActionListener(e -> {
        if (authenticate()) {
            String selectedUserType = (String) userTypeDropdown.getSelectedItem();
            if( securityCheck_checkbox.isSelected() &&  selectedUserType != "Customer")
            {
                selectedUserType=selectedUserType+"-SecurityCheck";
            }
            MainFrame mainFrame = new MainFrame(selectedUserType);
            dispose(); // Close login frame
            mainFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
        }
    });

        // ActionListener for user type dropdown
        userTypeDropdown.addActionListener(e -> {
            String selectedUserType = (String) userTypeDropdown.getSelectedItem();
            if (selectedUserType.equals("Customer")) {
                passwordField.setEnabled(false);
                passwordField.setBackground(Color.LIGHT_GRAY);
                passwordField.setText("");
                securityCheck_checkbox.setSelected(false);
                securityCheck_checkbox.setEnabled(false);
                securityCheck_checkbox.setBackground(Color.LIGHT_GRAY);
            } else {
                passwordField.setEnabled(true);
                                passwordField.setBackground(getBackground());

                                securityCheck_checkbox.setEnabled(true);
securityCheck_checkbox.setBackground(getForeground());
            }
        });

    }

    public String getUsername() {
        return (String) userTypeDropdown.getSelectedItem();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public boolean authenticate() {
        String username = getUsername();
        String password = getPassword();

        if (username.equals("Staff") && password.equals("cs430@SIUC")) {
        // if (username.equals("Staff") ) {
            return true;
        } else if (username.equals("Customer")) {
            // No password required for customers
            return true;
        }
        return false;
    }

    
}
