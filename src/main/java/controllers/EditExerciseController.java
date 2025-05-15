package controllers;

import views.Utils;

import javax.swing.JOptionPane;

import views.EditExerciseView;

public class EditExerciseController {
    private final MainController mainController;
    private final EditExerciseView editExerciseView;

    public EditExerciseController(MainController mainController, EditExerciseView editExerciseView) {
        this.mainController = mainController;
        this.editExerciseView = editExerciseView;
    }

    public void saveEditedExercise() {
        try {
            Utils.updateExerciseInCSV(Utils.EXERCISES_PATH, this.mainController.selectedExercise,
                    editExerciseView.getExerciseFromFields());
            mainController.exercisesController.loadExercisesFromCSV(mainController.exercisesView.getPanel(),
                    Utils.EXERCISES_PATH);
            mainController.showExercisesView();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(editExerciseView.getPanel(), "Error saving exercise!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
