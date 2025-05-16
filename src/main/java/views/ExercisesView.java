package views;

import controllers.ExercisesController;
import controllers.MainController;
import models.Exercise;
import javax.swing.*;
import java.awt.*;

/**
 * ExercisesView is responsible for displaying the list of exercises.
 * It allows the user to view, edit, and delete exercises.
 */
public class ExercisesView {
    private final JPanel panel;
    private final MainController mainController;
    private final ExercisesController exercisesController;
    private final JList<Exercise> exerciseList;

    public ExercisesView(MainController controller, ExercisesController exercisesController) {
        this.mainController = controller;
        this.exercisesController = exercisesController;

        exerciseList = new JList<>(this.exercisesController.exercisesList);
        exerciseList.setFont(new Font("Arial", Font.PLAIN, 16));
        exerciseList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel label = new JLabel(String.format(
                    "<html><div style='text-align: center;'><b>%s</b><br>Type: %s, Muscle: %s, %d reps × %d sets</div></html>",
                    value.getName(), value.getType(), value.getMuscleGroup(),
                    value.getRepetitions(), value.getSets()));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setOpaque(true);
            label.setBackground(isSelected ? new Color(100, 149, 237) : Color.WHITE);
            label.setForeground(Color.BLACK);
            return label;
        });

        JScrollPane scrollPane = new JScrollPane(exerciseList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Context menu for Edit and Delete
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");
        contextMenu.add(editItem);
        contextMenu.add(deleteItem);

        exerciseList.setComponentPopupMenu(contextMenu);

        exerciseList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = exerciseList.locationToIndex(e.getPoint());
                    exerciseList.setSelectedIndex(index);
                }
            }
        });

        editItem.addActionListener(e -> {
            Exercise selectedExercise = exerciseList.getSelectedValue();
            if (selectedExercise != null) {
                mainController.showEditExerciseView(selectedExercise);
            }
        });

        panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 230, 230));

        deleteItem.addActionListener(e -> {
            Exercise selectedExercise = exerciseList.getSelectedValue();
            if (selectedExercise != null) {
                int confirm = JOptionPane.showConfirmDialog(panel,
                        "Are you sure you want to delete this exercise?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    exercisesController.deleteExercise(selectedExercise);
                    exerciseList.updateUI();
                }
            }
        });

        // Load exercises from CSV
        exercisesController.loadExercisesFromCSV(panel, Utils.EXERCISES_PATH);

        JLabel titleLabel = new JLabel("Exercises", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.addActionListener(e -> mainController.showMainMenu());

        JButton createExerciseButton = new JButton("Create Exercise");
        createExerciseButton.setFont(new Font("Arial", Font.BOLD, 18));
        createExerciseButton.addActionListener(e -> mainController.showExerciseCreationView());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(createExerciseButton);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Returns the main panel of the ExercisesView.
     */
    public JPanel getPanel() {
        return panel;
    }
}
