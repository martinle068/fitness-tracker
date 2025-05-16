package controllers;
import javax.swing.JOptionPane;
import views.EditUserView;
import views.Utils;

/**
 * EditUserController is responsible for handling the logic of editing a user profile.
 * It interacts with the EditUserView and the MainController to save the edited user profile.
 */
public class EditUserController {
    private final MainController mainController;
    private final EditUserView editUserView;

    public EditUserController(MainController mainController, EditUserView editUserView) {
        this.mainController = mainController;
        this.editUserView = editUserView;
    }

    /**
     * Saves the edited user profile by updating the CSV file and refreshing the users view.
     */
    public void saveEditedUser() {
        try {
            Utils.updateUserInCSV(Utils.USER_PROFILES_PATH, this.mainController.selectedUserProfile,
                    editUserView.getUserProfileFromFields());
            mainController.usersController.loadUsersFromCSV(mainController.usersView.getPanel(),
                    Utils.USER_PROFILES_PATH);
            mainController.showUsersView();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(editUserView.getPanel(), "Error saving exercise!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
