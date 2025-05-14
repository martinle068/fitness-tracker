package controllers;
import views.EditUserView;
import views.Utils;

public class EditUserController {
    private final MainController mainController;
    private final EditUserView editUserView;
    private final UsersController usersController;

    public EditUserController(MainController mainController, EditUserView editUserView, UsersController usersController) {
        this.mainController = mainController;
        this.editUserView = editUserView;
        this.usersController = usersController;
    }

    public void saveEditedUser() {
        Utils.updateUserInCSV(Utils.USER_PROFILES_PATH, this.mainController.selectedUserProfile, editUserView.getUserProfileFromFields());
        mainController.usersController.loadUsersFromCSV(mainController.usersView.getPanel(), Utils.USER_PROFILES_PATH);
        mainController.showUsersView();
    }

    public void showUsersView() {
        // Implement the logic to switch back to the Users View
        // You can use the main controller to switch views
    }
}
