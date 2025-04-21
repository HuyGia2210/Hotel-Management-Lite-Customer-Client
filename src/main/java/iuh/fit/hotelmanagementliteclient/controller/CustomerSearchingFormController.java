package iuh.fit.hotelmanagementliteclient.controller;

//import iuh.fit.controller.features.DashboardController;
import iuh.fit.dao.daointerface.PosDAO;
import iuh.fit.dto.InvoiceInfoDTO;
import iuh.fit.hotelmanagementliteclient.utils.MaskUtil;
import iuh.fit.models.Customer;
import iuh.fit.models.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Le Tran Gia Huy
 * @created 17/04/2025 - 9:48 PM
 * @project Hotel-Management-Lite-Client
 * @package iuh.fit.hotelmanagementliteclient.controller
 */
public class CustomerSearchingFormController {

    @FXML
    private PasswordField inputTextField;
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
    private Button enter;
    @FXML
    private Button delete;
    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField phoneNumbTextField;
    @FXML
    private TextField idNumbTextField;
    @FXML
    private TableView<InvoiceInfoDTO> dataTable;
    @FXML
    private TableColumn<InvoiceInfoDTO, String> invoiceIdColumn;
    @FXML
    private TableColumn<InvoiceInfoDTO, String> roomIdColumn;
    @FXML
    private TableColumn<InvoiceInfoDTO, String> roomCategoryColumn;
    @FXML
    private TableColumn<InvoiceInfoDTO, Double> totalColumn;
    @FXML
    private TableColumn<InvoiceInfoDTO, LocalDate> dateColumn;

    private List<InvoiceInfoDTO> invoiceList = new ArrayList<>();

    private ObservableList<InvoiceInfoDTO> items;

    public void initialize(PosDAO dao) {
        zero.setOnAction(e -> inputTextField.appendText("0"));
        one.setOnAction(e -> inputTextField.appendText("1"));
        two.setOnAction(e -> inputTextField.appendText("2"));
        three.setOnAction(e -> inputTextField.appendText("3"));
        four.setOnAction(e -> inputTextField.appendText("4"));
        five.setOnAction(e -> inputTextField.appendText("5"));
        six.setOnAction(e -> inputTextField.appendText("6"));
        seven.setOnAction(e -> inputTextField.appendText("7"));
        eight.setOnAction(e -> inputTextField.appendText("8"));
        nine.setOnAction(e -> inputTextField.appendText("9"));
        backspace.setOnAction(e -> {
            String text = inputTextField.getText();
            if (text.length() > 0) {
                inputTextField.setText(text.substring(0, text.length() - 1));
            }
        });
        enter.setOnAction(e -> {
            try {
                search(dao);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });
        delete.setOnAction(e->{
            inputTextField.clear();
            fullNameTextField.clear();
            phoneNumbTextField.clear();
            idNumbTextField.clear();
            clearTable();
        }
        );
    }

    private void clearTable(){
        invoiceList.clear();
        items = FXCollections.observableArrayList(invoiceList);
        dataTable.setItems(items);
        dataTable.refresh();
    }

    private void searchInvoice(PosDAO dao, String info) throws RemoteException {
        List<InvoiceInfoDTO> dtos = dao.searchCustomerInvoice(info);
        if (dtos != null && !dtos.isEmpty()) {
            invoiceList = dtos;
            items = FXCollections.observableArrayList(invoiceList);
            dataTable.setItems(items);
            dataTable.refresh();
            setupTable();
        } else {
        }
    }

    private void search(PosDAO dao) throws RemoteException {
        String info = inputTextField.getText();
        List<Customer> customers = dao.searchCustomer(info);
        if (customers != null && !customers.isEmpty()) {
            Customer customer = customers.getFirst();
            fullNameTextField.setText(MaskUtil.mask(customer.getFullName(), "name"));
            phoneNumbTextField.setText(MaskUtil.mask(customer.getPhoneNumber(), "phone"));
            idNumbTextField.setText(MaskUtil.mask(customer.getIdCardNumber(), "id"));

            searchInvoice(dao, info);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Không tìm thấy thông tin khách hàng!");
            alert.showAndWait();
        }
    }


    private void setupTable() {
        invoiceIdColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceID"));
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        roomCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("roomCategory"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalDue"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceDate"));

        dataTable.setItems(items);
    }
}