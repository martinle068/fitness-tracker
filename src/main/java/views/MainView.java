package views;

import controllers.MainController;
import javax.swing.*;
import java.awt.*;

public class MainView {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JButton userProfileButton;
    private JButton exercisesButton;
    private JButton exitButton;
    private final MainController mainController;

    public MainView() {
        this.mainController = new MainController(this);
        initialize();
    }

    private void initialize() {
        Utils.setupModernUI();
        setupFrame();
        setupCardLayout();
        assert frame != null;
        frame.add(mainPanel);
        centerFrame();
        addListeners();
    }

    private void setupFrame() {
        frame = new JFrame("Fitness Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());
    }

    private void setupCardLayout() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create menu panel (Main Menu)
        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, "MainMenu");

        // Create users panel (User Profiles View)
        JPanel usersPanel = this.mainController.usersView.getPanel();
        mainPanel.add(usersPanel, "UsersView");

        // Add UserCreationView panel
        mainPanel.add(this.mainController.userCreationView.getPanel(), "UserCreationView");
        mainPanel.add(this.mainController.editUserView.getPanel(), "EditUserView");
        mainPanel.add(this.mainController.exercisesView.getPanel(), "ExercisesView");
        mainPanel.add(this.mainController.exerciseCreationView.getPanel(), "ExerciseCreationView");
        mainPanel.add(this.mainController.editExerciseView.getPanel(), "EditExerciseView");
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(230, 230, 230));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel titleLabel = new JLabel("Main Menu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        userProfileButton = createButton("User Profiles");
        exercisesButton = createButton("Exercises");
        exitButton = createButton("Exit");

        panel.add(titleLabel, gbc);
        panel.add(userProfileButton, gbc);
        panel.add(exercisesButton, gbc);
        panel.add(exitButton, gbc);

        return panel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        return button;
    }

    private void centerFrame() {
        frame.setLocationRelativeTo(null);
    }

    private void addListeners() {
        userProfileButton.addActionListener(e -> mainController.showUsersView());
        exercisesButton.addActionListener(e -> mainController.showExercisesView());
        exitButton.addActionListener(e -> System.exit(0));
    }

    public void showView(String viewName) {
        cardLayout.show(mainPanel, viewName);
    }

    public void start() {
        frame.setVisible(true);
    }

    public void display() {
        frame.setVisible(true);
    }
}