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

import org.json.*;
import java.nio.file.*;
import java.io.*;

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
        for (Enumeration<UserProfile> e = usersController.usersList.elements(); e.hasMoreElements();) {
            UserProfile user = e.nextElement();
            userComboBox.addItem(user);
            workoutsPerUser.putIfAbsent(user, new HashMap<>());
        }

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
        loadWorkoutsFromFile();

        updateCalendar();
    }

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

        saveWorkoutsToFile();
    }

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

    private UserProfile findUserByUsername(String name, String surname) {
        for (UserProfile user : workoutsPerUser.keySet()) {
            if (user.getName().equals(name) && user.getSurname().equals(surname)) {
                return user;
            }
        }
        return null;

    }

    private void loadWorkoutsFromFile() {
        Path path = Path.of(Utils.WORKOUTS_PATH);
        if (!Files.exists(path))
            return;

        try {
            String content = Files.readString(path);
            JSONArray usersArray = new JSONArray(content);

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userObject = usersArray.getJSONObject(i);
                String name;
                String surname;
                String[] nameParts = userObject.getString("user").split(" ");
                if (nameParts.length == 2) {
                    name = nameParts[0];
                    surname = nameParts[1];
                } else {
                    continue; // Skip if the format is unexpected
                }

                UserProfile user = findUserByUsername(name, surname);
                if (user == null)
                    continue;

                Map<LocalDate, List<Exercise>> userWorkouts = workoutsPerUser.computeIfAbsent(user,
                        k -> new HashMap<>());

                JSONArray datesArray = userObject.getJSONArray("workouts");
                for (int j = 0; j < datesArray.length(); j++) {
                    JSONObject dateObject = datesArray.getJSONObject(j);
                    LocalDate date = LocalDate.parse(dateObject.getString("date"));

                    List<Exercise> exercises = new ArrayList<>();
                    JSONArray exArray = dateObject.getJSONArray("exercises");
                    for (int k = 0; k < exArray.length(); k++) {
                        JSONObject exJson = exArray.getJSONObject(k);
                        Exercise ex = new Exercise(
                                exJson.getString("name"),
                                exJson.getString("type"),
                                exJson.getString("muscleGroup"),
                                exJson.getInt("repetitions"),
                                exJson.getInt("sets"));
                        exercises.add(ex);
                    }

                    userWorkouts.put(date, exercises);
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveWorkoutsToFile() {
        JSONArray usersArray = new JSONArray();

        for (Map.Entry<UserProfile, Map<LocalDate, List<Exercise>>> entry : workoutsPerUser.entrySet()) {
            JSONObject userObject = new JSONObject();
            userObject.put("user", entry.getKey().getName() + " " + entry.getKey().getSurname()); // or getId()

            JSONArray datesArray = new JSONArray();
            for (Map.Entry<LocalDate, List<Exercise>> dateEntry : entry.getValue().entrySet()) {
                JSONObject dateObject = new JSONObject();
                dateObject.put("date", dateEntry.getKey().toString());

                JSONArray exercisesArray = new JSONArray();
                for (Exercise ex : dateEntry.getValue()) {
                    JSONObject exJson = new JSONObject();
                    exJson.put("name", ex.getName());
                    exJson.put("type", ex.getType());
                    exJson.put("muscleGroup", ex.getMuscleGroup());
                    exJson.put("repetitions", ex.getRepetitions());
                    exJson.put("sets", ex.getSets());
                    exercisesArray.put(exJson);
                }

                dateObject.put("exercises", exercisesArray);
                datesArray.put(dateObject);
            }

            userObject.put("workouts", datesArray);
            usersArray.put(userObject);
        }

        try {
            Files.writeString(Path.of(Utils.WORKOUTS_PATH), usersArray.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
