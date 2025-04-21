package iuh.fit.hotelmanagementliteclient;

import iuh.fit.dao.daointerface.PosDAO;
import iuh.fit.hotelmanagementliteclient.controller.MainController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * @author Le Tran Gia Huy
 * @created 17/04/2025 - 6:01 PM
 * @project Hotel-Management-Lite-Client
 * @package iuh.fit
 */
public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws Exception {
        Context context = new InitialContext();
        PosDAO dao = (PosDAO) context.lookup("rmi://localhost:8701/pos");
        dao.searchCustomer("0912345682").forEach(System.out::println);

        startApp(stage, dao);
    }

    public void startApp(Stage primaryStage, PosDAO dao) {
        loadUI(primaryStage, dao);
    }

    private void loadUI(Stage mainStage, PosDAO dao){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/iuh/fit/hotelmanagementliteclient/ui/MainMenu.fxml"));

            AnchorPane root = loader.load();

            MainController mainController = loader.getController();
            mainController.initialize(mainStage, dao);

            Scene scene = new Scene(root);

            mainStage.setTitle("Tra cứu thông tin khách Sạn");
            mainStage.setScene(scene);
            mainStage.setResizable(true);
            mainStage.setWidth(1500);
            mainStage.setHeight(800);
            mainStage.setFullScreen(true);
            mainStage.setMaximized(false);
            mainStage.centerOnScreen();

            mainStage.show();

            mainStage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
