package views;

import models.Exercise;
import models.UserProfile;
import javax.swing.*;
import controllers.MainController;
import controllers.UsersController;
import java.awt.*;
import java.time.*;
import java.util.*;
import java.util.List;

/**
 * WorkoutsView is responsible for displaying the UI for managing workouts.
 * It allows the user to select a user, navigate through months, and manage exercises for each day.
 */
public class WorkoutsView {
    private final MainController mainController;
    private final UsersController usersController;
    private final JPanel panel;
    private final JLabel monthLabel;
    private final JPanel calendarPanel;
    private YearMonth currentMonth;
    private final JComboBox<UserProfile> userComboBox;

    private final Map<UserProfile, Map<LocalDate, List<Exercise>>> workoutsPerUser = new HashMap<>();
    private UserProfile currentUser;

    public WorkoutsView(MainController controller, UsersController usersController) {
        this.mainController = controller;
        this.usersController = usersController;

        panel = new JPanel(new BorderLayout());
        currentMonth = YearMonth.now();

        // Setup user selection dropdown
        userComboBox = new JComboBox<>();

        userComboBox.addActionListener(e -> {
            currentUser = (UserProfile) userComboBox.getSelectedItem();
            updateCalendar();
        });

        JPanel topUserPanel = new JPanel(new BorderLayout());
        topUserPanel.add(new JLabel("Select User: "), BorderLayout.WEST);
        topUserPanel.add(userComboBox, BorderLayout.CENTER);

        // Month navigation
        monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JButton prevButton = new JButton("<");
        prevButton.addActionListener(e -> {
            currentMonth = currentMonth.minusMonths(1);
            updateCalendar();
        });

        JButton nextButton = new JButton(">");
        nextButton.addActionListener(e -> {
            currentMonth = currentMonth.plusMonths(1);
            updateCalendar();
        });

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(prevButton, BorderLayout.WEST);
        headerPanel.add(monthLabel, BorderLayout.CENTER);
        headerPanel.add(nextButton, BorderLayout.EAST);

        calendarPanel = new JPanel(new GridLayout(0, 7, 5, 5));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(topUserPanel, BorderLayout.NORTH);
        topPanel.add(headerPanel, BorderLayout.SOUTH);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(calendarPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.addActionListener(e -> this.mainController.showMainMenu());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(backButton);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        if (userComboBox.getItemCount() > 0) {
            currentUser = (UserProfile) userComboBox.getSelectedItem();
        }

        loadData();
        updateCalendar();
    }

    /**
     * Updates the calendar display based on the current month and selected user.
     */
    private void updateCalendar() {
        calendarPanel.removeAll();
        monthLabel.setText(currentMonth.getMonth().toString() + " " + currentMonth.getYear());

        String[] weekdays = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };
        for (String day : weekdays) {
            JLabel label = new JLabel(day, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            calendarPanel.add(label);
        }

        LocalDate firstDay = currentMonth.atDay(1);
        int dayOfWeekValue = firstDay.getDayOfWeek().getValue() - 1;

        for (int i = 0; i < dayOfWeekValue; i++) {
            calendarPanel.add(new JLabel());
        }

        Map<LocalDate, List<Exercise>> workouts = workoutsPerUser.getOrDefault(currentUser, new HashMap<>());
        int daysInMonth = currentMonth.lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            final LocalDate date = currentMonth.atDay(day);
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.setBackground(workouts.containsKey(date) ? Color.CYAN : Color.WHITE);
            dayButton.addActionListener(e -> openDayEditor(date));
            calendarPanel.add(dayButton);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    /**
     * Opens a dialog to edit exercises for a specific date.
     *
     * @param date the date for which to edit exercises
     */
    private void openDayEditor(LocalDate date) {
        Map<LocalDate, List<Exercise>> workouts = workoutsPerUser.computeIfAbsent(currentUser, k -> new HashMap<>());
        JDialog dialog = new JDialog((Frame) null, "Workout on " + date, true);
        dialog.setLayout(new BorderLayout());

        DefaultListModel<Exercise> listModel = new DefaultListModel<>();
        List<Exercise> exercises = workouts.getOrDefault(date, new ArrayList<>());
        exercises.forEach(listModel::addElement);

        JList<Exercise> exerciseList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(exerciseList);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            Exercise exercise = promptForExercise();
            if (exercise != null)
                listModel.addElement(exercise);
        });

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> {
            Exercise selected = exerciseList.getSelectedValue();
            if (selected != null) {
                Exercise edited = promptForExercise(selected);
                if (edited != null) {
                    listModel.setElementAt(edited, exerciseList.getSelectedIndex());
                }
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            int idx = exerciseList.getSelectedIndex();
            if (idx >= 0 && JOptionPane.showConfirmDialog(dialog,
                    "Are you sure you want to delete this exercise?", "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                listModel.remove(idx);
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            List<Exercise> updated = Collections.list(listModel.elements());
            if (updated.isEmpty()) {
                workouts.remove(date);
            } else {
                workouts.put(date, updated);
            }
            dialog.dispose();
            updateCalendar();
        });

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(addButton);
        buttons.add(editButton);
        buttons.add(deleteButton);
        buttons.add(saveButton);

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(buttons, BorderLayout.SOUTH);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(panel);
        dialog.setVisible(true);

        Utils.saveWorkoutsToFile(workoutsPerUser);
    }

    /**
     * Prompts the user to select or create an exercise.
     *
     * @return the selected or created exercise
     */
    private Exercise promptForExercise() {
        Exercise exercise = (Exercise) JOptionPane.showInputDialog(
                panel,
                "Choose an exercise:",
                "Exercise",
                JOptionPane.PLAIN_MESSAGE,
                null,
                this.mainController.exercisesController.exercisesList.toArray(),
                this.mainController.exercisesController.exercisesList.get(0));
        return exercise;
    }

    /**
     * Prompts the user to edit an existing exercise.
     *
     * @param existing the existing exercise to edit
     * @return the edited exercise
     */
    private Exercise promptForExercise(Exercise existing) {
        JTextField nameField = new JTextField(existing.getName());
        JTextField typeField = new JTextField(existing.getType());
        JTextField muscleGroupField = new JTextField(existing.getMuscleGroup());
        JTextField repsField = new JTextField(String.valueOf(existing.getRepetitions()));
        JTextField setsField = new JTextField(String.valueOf(existing.getSets()));

        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Type:"));
        inputPanel.add(typeField);
        inputPanel.add(new JLabel("Muscle Group:"));
        inputPanel.add(muscleGroupField);
        inputPanel.add(new JLabel("Repetitions:"));
        inputPanel.add(repsField);
        inputPanel.add(new JLabel("Sets:"));
        inputPanel.add(setsField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, "Edit Exercise", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                return new Exercise(
                        nameField.getText(),
                        typeField.getText(),
                        muscleGroupField.getText(),
                        Integer.parseInt(repsField.getText()),
                        Integer.parseInt(setsField.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    /**
     * Loads user data and workouts from the file.
     */
    public void loadData() {
        userComboBox.removeAllItems();
        workoutsPerUser.clear();

        for (Enumeration<UserProfile> e = usersController.usersList.elements(); e.hasMoreElements();) {
            UserProfile user = e.nextElement();
            userComboBox.addItem(user);
            workoutsPerUser.putIfAbsent(user, new HashMap<>());
        }
        Utils.loadWorkoutsFromFile(workoutsPerUser);
    }

    public JPanel getPanel() {
        return panel;
    }
}
