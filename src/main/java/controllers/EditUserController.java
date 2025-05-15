package controllers;

import java.io.IOException;

import javax.swing.JOptionPane;

import views.EditUserView;
import views.Utils;

public class EditUserController {
    private final MainController mainController;
    private final EditUserView editUserView;
    private final UsersController usersController;

    public EditUserController(MainController mainController, EditUserView editUserView,
            UsersController usersController) {
        this.mainController = mainController;
        this.editUserView = editUserView;
        this.usersController = usersController;
    }

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
