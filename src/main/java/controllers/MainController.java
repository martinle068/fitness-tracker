package controllers;

import models.UserProfile;
import views.EditUserView;
import views.ExerciseCreationView;
import views.ExercisesView;
import views.MainView;
import views.UserCreationView;
import views.UsersView;
import views.WorkoutsView;
import models.Exercise;
import views.EditExerciseView;

/*
 * Main Controller class that manages the main application flow and view transitions.
 */
public class MainController {
    private final MainView mainView;
    public final UsersView usersView;
    public final UserCreationView userCreationView;
    public final EditUserView editUserView;
    public final UsersController usersController;
    public final ExercisesView exercisesView;
    public final ExercisesController exercisesController;
    public final ExerciseCreationView exerciseCreationView;
    public final EditExerciseView editExerciseView;
    public final WorkoutsView workoutsView;
    // Selected user profile and exercise for editing
    public UserProfile selectedUserProfile = null;
    // Selected exercise for editing
    public Exercise selectedExercise = null;

    public MainController(MainView mainView) {
        this.mainView = mainView;
        this.usersController = new UsersController();
        this.usersView = new UsersView(this, usersController); // Initialize UsersView
        this.userCreationView = new UserCreationView(this, usersController); // Initialize UserCreationView
        this.editUserView = new EditUserView(this, usersController); // Initialize EditUserView
        this.exercisesController = new ExercisesController(); // Initialize ExercisesController
        this.exercisesView = new ExercisesView(this, exercisesController); // Initialize ExercisesView
        this.exerciseCreationView = new ExerciseCreationView(this, exercisesController); // Initialize
        this.editExerciseView = new EditExerciseView(this, exercisesController); // Initialize EditExerciseView
        this.workoutsView = new WorkoutsView(this, usersController); // Initialize WorkoutsView
    }

    // Show the main menu
    public void showMainMenu() {
        mainView.showView("MainMenu");
    }

    // Show the users view
    public void showUsersView() {
        mainView.showView("UsersView");
    }

    // Show the user creation view
    public void showUserCreationView() {
        mainView.showView("UserCreationView"); // Show the UserCreationView
    }

    // Show the edit user view with the selected user profile
    public void showEditUserView(UserProfile userProfile) {
        this.selectedUserProfile = userProfile; // Store the selected user profile
        editUserView.setUserProfileFields(userProfile); // Set the user profile in the EditUserView
        mainView.showView("EditUserView");
    }

    // Show the exercises view
    public void showExercisesView() {
        mainView.showView("ExercisesView");
    }

    // Show the exercise creation view
    public void showExerciseCreationView() {
        mainView.showView("ExerciseCreationView");
    }

    // Show the edit exercise view with the selected exercise
    public void showEditExerciseView(Exercise exercise) {
        this.selectedExercise = exercise; // Store the selected exercise
        editExerciseView.setExerciseFields(exercise); // Set the exercise in the EditExerciseView
        mainView.showView("EditExerciseView");
    }

    // Show the workouts view
    public void showWorkoutsView() {
        // Load data into the workouts view
        workoutsView.loadData();
        mainView.showView("WorkoutsView");
    }
}