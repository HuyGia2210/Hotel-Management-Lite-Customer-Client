package iuh.fit.hotelmanagementliteclient.controller;

import iuh.fit.dao.daointerface.PosDAO;
import iuh.fit.models.Account;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author Le Tran Gia Huy
 * @created 17/04/2025 - 9:40 PM
 * @project Hotel-Management-Lite-Client
 * @package iuh.fit.hotelmanagementliteclient.controller
 */

public class MainController {

    @FXML
    private AnchorPane searchForm;
    @FXML
    private SideMenuController menuBarController;

    public void initialize(Stage mainStage, PosDAO dao) {
        loadDefault("/iuh/fit/hotelmanagementliteclient/ui/RoomSearchingForm.fxml", dao);
        menuBarController.getCustomerSearchBtn().setOnAction(event -> loadPanel("/iuh/fit/hotelmanagementliteclient/ui/CustomerSearchingForm.fxml", dao));
        menuBarController.getRoomSearchBtn().setOnAction(event -> loadPanel("/iuh/fit/hotelmanagementliteclient/ui/RoomSearchingForm.fxml", dao));
    }

    public void loadDefault(String fxmlPath, PosDAO dao){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane layout = loader.load();

            Object controller = loader.getController();
            ((RoomSearchingFormController) controller).initialize(dao);

            searchForm.getChildren().clear();
            searchForm.getChildren().addAll(layout.getChildren());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPanel(String fxmlPath, PosDAO dao) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane layout = loader.load();

            Object controller = loader.getController();
            if (controller instanceof CustomerSearchingFormController) {
                ((CustomerSearchingFormController) controller).initialize(dao);
            } else if (controller instanceof RoomSearchingFormController) {
                ((RoomSearchingFormController) controller).initialize(dao);
            }

            searchForm.getChildren().clear();
            searchForm.getChildren().addAll(layout.getChildren());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
