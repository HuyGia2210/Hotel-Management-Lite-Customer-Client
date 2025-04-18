package iuh.fit.hotelmanagementliteclient.controller;

import iuh.fit.dao.PosDAO;
import iuh.fit.dao.RoomCategoryDAO;
import iuh.fit.models.Room;
import iuh.fit.models.RoomCategory;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

/**
 * @author Le Tran Gia Huy
 * @created 18/04/2025 - 11:07 AM
 * @project Hotel-Management-Lite-Client
 * @package iuh.fit.hotelmanagementliteclient.controller
 */
public class RoomSearchingFormController {
    @FXML
    private ComboBox<RoomCategory> roomCategoryCbx;
    @FXML
    private TextField roomIdTextField;
    @FXML
    private TextField roomCategoryTextField;
    @FXML
    private TextField dayNumbTextField;
    @FXML
    private TableView<Room> dataTable;
    @FXML
    private TableColumn<Room, String> roomIdColumn;
    @FXML
    private TableColumn<Room, String> roomCategoryColumn;
    @FXML
    private TableColumn<Room, Integer> numbOfBedColumn;
    @FXML
    private TableColumn<Room, Double> hourlyPriceColumn;
    @FXML
    private TableColumn<Room, Double> dailyPriceColumn;
    @FXML
    private Text priceText;
    @FXML
    private Text dayNumbText;
    @FXML
    private Text totalDueText;
    @FXML
    private Button zero;
    @FXML
    private Button one;
    @FXML
    private Button two;
    @FXML
    private Button three;
    @FXML
    private Button four;
    @FXML
    private Button five;
    @FXML
    private Button six;
    @FXML
    private Button seven;
    @FXML
    private Button eight;
    @FXML
    private Button nine;
    @FXML
    private Button backspace;
    @FXML
    private Text resultNumbText;

    private ObservableList<Room> dataList;

    private Room selectedRoom;


    public void initialize(PosDAO dao) throws RemoteException {
        loadData(dao);
        zero.setOnAction(e -> handelDayNumbTextField("0"));
        one.setOnAction(e -> handelDayNumbTextField("1"));
        two.setOnAction(e -> handelDayNumbTextField("2"));
        three.setOnAction(e -> handelDayNumbTextField("3"));
        four.setOnAction(e -> handelDayNumbTextField("4"));
        five.setOnAction(e -> handelDayNumbTextField("5"));
        six.setOnAction(e -> handelDayNumbTextField("6"));
        seven.setOnAction(e -> handelDayNumbTextField("7"));
        eight.setOnAction(e -> handelDayNumbTextField("8"));
        nine.setOnAction(e -> handelDayNumbTextField("9"));
        backspace.setOnAction(e -> handelBackspace());
        roomCategoryCbx.setOnAction(event -> {
            try {
                searchAvailableRoomByRoomCategory(dao);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
        dataTable.setOnMouseClicked(e ->{
            setUpForm();
        });
    }

    private void loadData(PosDAO dao) throws RemoteException {
        List<RoomCategory> roomCategoryList = dao.getCategoryForPOS();
        roomCategoryCbx.getItems().setAll(roomCategoryList);
        dayNumbTextField.setText("0");
        priceText.setText("- VND");
        dayNumbText.setText("-");
        totalDueText.setText("- VND");
        resultNumbText.setText("");
    }

    private void searchAvailableRoomByRoomCategory(PosDAO dao) throws RemoteException {
        dataTable.setItems(FXCollections.observableArrayList());
        RoomCategory category = roomCategoryCbx.getSelectionModel().getSelectedItem();
        List<Room> rooms = dao.getAvailableRoomByRoomCategoryForPOS(category);
        resultNumbText.setText(rooms.isEmpty() ? ("Có " + 0 + " phòng trống") : ("Có " + rooms.size() + " phòng trống như sau:"));
        dataList = FXCollections.observableArrayList(rooms);
        dataTable.setItems(dataList);
        setUpColumn();
    }

    public void setUpColumn(){
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        roomCategoryColumn.setCellValueFactory(cell->{
            Room room = cell.getValue();
            String categoryName = room.getRoomCategory() != null ? room.getRoomCategory().getRoomCategoryName() : "";
            return new SimpleStringProperty(categoryName);
        });
        numbOfBedColumn.setCellValueFactory(cell ->{
            Room room = cell.getValue();
            int numberOfBed = room.getRoomCategory() != null ? room.getRoomCategory().getNumberOfBed() : 0;
            return new SimpleIntegerProperty(numberOfBed).asObject();
        });
        hourlyPriceColumn.setCellValueFactory(cell->{
            Room room = cell.getValue();
            double hourlyPrice = room.getRoomCategory() != null ? room.getRoomCategory().getHourlyPrice() : 0.0;
            return new SimpleDoubleProperty(hourlyPrice).asObject();
        });
        dailyPriceColumn.setCellValueFactory(cell ->{
            Room room = cell.getValue();
            double hourlyPrice = room.getRoomCategory() != null ? room.getRoomCategory().getDailyPrice() : 0.0;
            return new SimpleDoubleProperty(hourlyPrice).asObject();
        });
    }

    private void setUpForm() {
        DecimalFormat formatter = new DecimalFormat("#,###");

        Room room = dataTable.getSelectionModel().getSelectedItem();
        selectedRoom = room;
        if (room != null) {
            roomIdTextField.setText(room.getRoomID());
            roomCategoryTextField.setText(room.getRoomCategory().getRoomCategoryName());
            priceText.setText(formatter.format(room.getRoomCategory().getDailyPrice()) + " VND");
            totalDueText.setText(
                    Objects.equals(dayNumbTextField.getText(), "") || Integer.parseInt(dayNumbTextField.getText()) == 0 ?
                            "- VND" : (formatter.format(selectedRoom.getRoomCategory().getDailyPrice() * Integer.parseInt(dayNumbTextField.getText())) + " VND")
            );
        } else {
            roomIdTextField.clear();
            roomCategoryTextField.clear();
        }
    }

    private void handelDayNumbTextField(String s){
        if(dayNumbTextField.getText().equalsIgnoreCase("0")){
            dayNumbTextField.setText("");
            dayNumbTextField.appendText(s);
        }else{
            dayNumbTextField.appendText(s);
        }
        dayNumbText.setText(dayNumbTextField.getText() == null ? "-" : dayNumbTextField.getText());
        DecimalFormat formatter = new DecimalFormat("#,###");
        totalDueText.setText(
                Objects.equals(dayNumbTextField.getText(), "") || Integer.parseInt(dayNumbTextField.getText()) == 0 ?
                        "- VND" : (formatter.format(selectedRoom.getRoomCategory().getDailyPrice() * Integer.parseInt(dayNumbTextField.getText())) + " VND")
        );
    }

    private void handelBackspace(){
        String text = dayNumbTextField.getText();
        if (!text.isEmpty() && !text.equalsIgnoreCase("0")) {
            String newText = text.substring(0, text.length() - 1);
            // Nếu sau khi xóa mà rỗng thì set về "0"
            dayNumbTextField.setText(newText.isEmpty() ? "0" : newText);
        }else{
            dayNumbTextField.setText("0");
        }
        dayNumbText.setText(dayNumbTextField.getText() == null ? "-" : dayNumbTextField.getText());
        DecimalFormat formatter = new DecimalFormat("#,###");
        totalDueText.setText(
                Objects.equals(dayNumbTextField.getText(), "") || Integer.parseInt(dayNumbTextField.getText()) == 0 ?
                        "- VND" : (formatter.format(selectedRoom.getRoomCategory().getDailyPrice() * Integer.parseInt(dayNumbTextField.getText())) + " VND")
        );
    }
}
