package views;

import controllers.MainController;
import controllers.UsersController;
import models.UserProfile;

import javax.swing.*;
import java.awt.*;

public class UsersView {
    private final JPanel panel;
    private final MainController mainController;
    private final UsersController usersController;
    private final JList<UserProfile> userList;

    public UsersView(MainController controller, UsersController usersController) {
        this.mainController = controller;
        this.usersController = usersController;

        userList = new JList<>(this.usersController.usersList);
        userList.setFont(new Font("Arial", Font.PLAIN, 16));
        userList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(String.format(
                    "<html><div style='text-align: center;'><b>%s %s</b><br>Age: %d, Weight: %.1fkg, Height: %.1fcm</div></html>",
                    value.getName(), value.getSurname(), value.getAge(),
                    value.getWeight(), value.getHeight()));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            label.setOpaque(true);
            label.setBackground(isSelected ? new Color(100, 149, 237) : Color.WHITE);
            label.setForeground(Color.BLACK);
            return label;
        });

        JScrollPane scrollPane = new JScrollPane(userList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Context menu for Edit and Delete
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");

        contextMenu.add(editItem);
        contextMenu.add(deleteItem);

        userList.setComponentPopupMenu(contextMenu);

        userList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = userList.locationToIndex(e.getPoint());
                    userList.setSelectedIndex(index);
                }
            }
        });

        editItem.addActionListener(e -> {
            UserProfile selectedUser = userList.getSelectedValue();
            if (selectedUser != null) {
                mainController.showEditUserView(selectedUser);
            }
        });

        panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 230, 230));

        deleteItem.addActionListener(e -> {
            UserProfile selectedUser = userList.getSelectedValue();
            if (selectedUser != null) {
                int confirm = JOptionPane.showConfirmDialog(panel,
                        "Are you sure you want to delete this user?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    usersController.deleteUser(selectedUser);
                    userList.updateUI(); // reflect deletion
                }
            }
        });

        this.usersController.loadUsersFromCSV(panel, Utils.USER_PROFILES_PATH);

        JLabel titleLabel = new JLabel("User Profiles", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.addActionListener(e -> this.mainController.showMainMenu());

        JButton createUserButton = new JButton("Create User");
        createUserButton.setFont(new Font("Arial", Font.BOLD, 18));
        createUserButton.addActionListener(e -> this.mainController.showUserCreationView());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(createUserButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return panel;
    }
}
