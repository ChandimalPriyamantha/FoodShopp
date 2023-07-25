package com.example.foodshope_j;

import com.example.foodshope_j.ConnectionDB.ConnectionShopp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainForm implements Initializable {

    @FXML
    private Button customer_btn;

    @FXML
    private Button dashbord_btn;

    @FXML
    private Button inventory_adding;

    @FXML
    private Button inventory_btn;

    @FXML
    private Button inventory_clear;

    @FXML
    private Button inventory_deleting;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private TableColumn<ProductData, String> inventory_id_product;

    @FXML
    private ImageView inventory_imageview;

    @FXML
    private TableColumn<ProductData, String> inventory_price;

    @FXML
    private TableColumn<ProductData, String> inventory_product_name;

    @FXML
    private TableColumn<ProductData, String> inventory_satus;

    @FXML
    private TableColumn<ProductData, String> inventory_stock;

    @FXML
    private TableColumn<ProductData, String> inventory_date;

    @FXML
    private TableView<ProductData> inventory_table_view;

    @FXML
    private TableColumn<ProductData, String> inventory_type;

    @FXML
    private Button inventory_updating;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button menu_btn;

    @FXML
    private Button sign_out_btn;

    @FXML
    private Label username;

    @FXML
    private TextField price;

    @FXML
    private TextField product_id;

    @FXML
    private TextField product_name;

    @FXML
    private ComboBox<?> status;

    @FXML
    private TextField stoke;

    @FXML
    private ComboBox<?> type;

    @FXML
    private TextField menu_amount;



    @FXML
    private Label menu_change;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private Button menu_pay;

    @FXML
    private TableColumn<?, ?> menu_price;

    @FXML
    private TableColumn<?, ?> menu_quantity;

    @FXML
    private Button menu_receipt;

    @FXML
    private Button menu_remove;

    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private Label menu_total;

    @FXML
    private TableColumn<?, ?> munu_product_name;




    @FXML
    private AnchorPane dashbord_form;




    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Alert alert;

    private ObservableList<ProductData> cardListData = FXCollections.observableArrayList();

    public void inventoryAddBtn(){

        if(product_id.getText().isEmpty() || product_name.getText().isEmpty() || type.getSelectionModel().getSelectedItem() == null
        || stoke.getText().isEmpty() || price.getText().isEmpty() || status.getSelectionModel().getSelectedItem() == null||
            UserData.path == null){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        }else{
            String checkProdID = "SELECT prod_id FROM product WHERE prod_id ='"
                    +product_id.getText()+"'";

            connect = ConnectionShopp.ConnectionDB();


            try {
                statement = connect.createStatement();
                result = statement.executeQuery(checkProdID);

                if(result.next()){

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(product_id.getText() + "is already taken");
                    alert.showAndWait();

                }else{

                    String insertData = "INSERT INTO product" +
                            "(prod_id, prod_name, type, stock, price, status, image,date)" +
                            "VALUES(?,?,?,?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, product_id.getText());
                    prepare.setString(2, product_name.getText());
                    prepare.setString(3, (String)type.getSelectionModel().getSelectedItem());
                    prepare.setString(4, stoke.getText());
                    prepare.setString(5, price.getText());
                    prepare.setString(6, (String)status.getSelectionModel().getSelectedItem());

                    String path = UserData.path;
                    path = path.replace("\\","\\\\");

                    prepare.setString(7,path);

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(8, String.valueOf((sqlDate)));

                    prepare.executeUpdate();



                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    inventoryShowData();
                    inventoryClearBtn();


                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public  void InventoryUpdate(){

        if(product_id.getText().isEmpty() || product_name.getText().isEmpty() || type.getSelectionModel().getSelectedItem() == null
                || stoke.getText().isEmpty() || price.getText().isEmpty() || status.getSelectionModel().getSelectedItem() == null||
                UserData.path == null || UserData.id == 0){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        }else{

            String path = UserData.path;
            path = path.replace("\\","\\\\");

            String updateDate = "UPDATE product SET "
                    +"prod_id = '"+ product_id.getText()+"',prod_name ='"
                    + product_name.getText()+"', type='"
                    +type.getSelectionModel().getSelectedItem()+"',stock='"
                    +stoke.getText()+"',price='"
                    +price.getText()+"',status='"
                    +status.getSelectionModel().getSelectedItem()+"',image='"
                    +path+"', date ='"
                    +UserData.date+"' WHERE id = " + UserData.id;

            connect = ConnectionShopp.ConnectionDB();

            try {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE product ID:" + product_id.getText()+"?");
                Optional<ButtonType> optional = alert.showAndWait();

                if(optional.get().equals(ButtonType.OK)){

                    prepare = connect.prepareStatement(updateDate);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    inventoryShowData();
                    inventoryClearBtn();
                }else{

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled");


                }

                prepare = connect.prepareStatement(updateDate);


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public void inventoryDeleteBtn(){

        if(UserData.id == 0){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        }else{

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE product ID:" + product_id.getText()+"?");
            Optional<ButtonType> optional = alert.showAndWait();

            if(optional.get().equals(ButtonType.OK)){

                String deleteData = "DELETE FROM product WHERE id = " + UserData.id;

                try {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted");


                    inventoryShowData();
                    inventoryClearBtn();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }else {


                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();

            }



        }

    }

    public  void inventorySelectData(){

        ProductData productData = inventory_table_view.getSelectionModel().getSelectedItem();
        int num = inventory_table_view.getSelectionModel().getSelectedIndex();

        if((num-1)<-1) return;

        product_id.setText(productData.getProductId());
        product_name.setText(productData.getProductName());
        stoke.setText(String.valueOf(productData.getStock()));
        price.setText(String.valueOf(productData.getPrice()));

        UserData.path = productData.getImage();

        String path  = "File:" + productData.getImage();
        UserData.date = String.valueOf(productData.getDate());
        UserData.id = productData.getId();


        Image image = new Image( path,151,141,false,true );
        inventory_imageview.setImage(image);


    }

    public  void inventoryClearBtn(){

        product_id.setText("");
        product_name.setText("");
        price.setText("");
        stoke.setText("");
        status.getSelectionModel().clearSelection();
        type.getSelectionModel().clearSelection();
        UserData.path = "";
        UserData.id  = 0;
        inventory_imageview.setImage(null);

    }

    public void inventoryImportBtn(){

        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*png","*jpg"));

        File file = openFile.showOpenDialog(main_form.getScene().getWindow());

        if(file != null){

            UserData.path = file.getAbsolutePath();
            Image image = new Image(file.toURI().toString(), 151, 144, false, true);
            inventory_imageview.setImage(image);
        }

    }


    public ObservableList<ProductData> inventoryDataList(){
        ObservableList<ProductData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM product";

        connect = ConnectionShopp.ConnectionDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ProductData prodData;

            while (result.next()){

                prodData = new ProductData(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDouble("price"),
                        result.getDate("date"));
                listData.add(prodData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
      return  listData;
    }

    private ObservableList<ProductData> inventoryListData;

    public  void inventoryShowData(){

        inventoryListData = inventoryDataList();

        inventory_id_product.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_product_name.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventory_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_satus.setCellValueFactory(new PropertyValueFactory<>("status"));
        inventory_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        inventory_table_view.setItems(inventoryListData);
    }

    private String[] typeList = {"Meals", "Drinks"};
    public void inventoryTypeList(){

        List<String> typeL = new ArrayList<>();

        for(String data: typeList){
            typeL.add(data);

        }

        ObservableList listData = FXCollections.observableArrayList(typeList);
        type.setItems(listData);
    }
    private String[] statusList = {"Available", "Unavailable"};
    private void inventoryStatusList(){

        List<String> statustL = new ArrayList<>();

        for(String data : statusList){
            statustL.add(data);
        }

        ObservableList listDataS = FXCollections.observableArrayList(statustL);
        status.setItems(listDataS);
    }

    public ObservableList<ProductData> menuGetData(){

        String sql = "SELECT * FROM product";


        ObservableList<ProductData> listData = FXCollections.observableArrayList();
        connect = ConnectionShopp.ConnectionDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ProductData productData;

            while (result.next()){

                productData = new ProductData(result.getInt("id"),
                        result.getString("prod_id"),result.getString("prod_name"),
                        result.getString("type")
                        ,result.getString("image"),result.getDouble("price"),
                        result.getDate("date"));

                listData.add(productData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return  listData;
    }

    public void DisplayMenuCard(){
        cardListData.clear();
        cardListData.addAll(menuGetData());

        int row = 0;
        int column = 0;

        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();
        for (int q = 0; q < cardListData.size(); q++){


            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("CardProduct.fxml"));
                AnchorPane pane = load.load();
                CardProductController cardC = load.getController();
                cardC.setData(cardListData.get(q));

                if(column == 3){
                    column = 0;
                    row += 1;
                }

                menu_gridPane.add(pane , column++, row);
               GridPane.setMargin(pane, new Insets(10));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void switchForm(ActionEvent event){

        if(event.getSource() == dashbord_btn){
            dashbord_form.setVisible(true);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
        }else if(event.getSource() == inventory_btn){
            dashbord_form.setVisible(false);
            inventory_form.setVisible(true);
            menu_form.setVisible(false);

            inventoryTypeList();
            inventoryStatusList();
            inventoryShowData();
        } else if (event.getSource() == menu_btn) {
            dashbord_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(true);
            DisplayMenuCard();
        }
    }

    public void logout(){

        try{

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)){

                sign_out_btn.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("Login&Registration.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("NILAME J Management System");

                stage.setScene(scene);
                stage.show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private  int cID;
    public void customerID(){
        String sql = "SELECT MAX(customer_id) FROM customer";
        connect = ConnectionShopp.ConnectionDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                cID = result.getInt("MAX(customer_id)");

            }
            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            prepare = connect.prepareStatement(checkCID);
            result = prepare.executeQuery();
            int checkID =0;
            if(result.next()){
                checkID = result.getInt("MAX(customer_id)");
            }
            if(cID == 0){
                cID += 1;
            }else if(cID == checkID){
                cID += 1;
            }

            UserData.cID = cID;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void displayUsername(){

        String user = UserData.username;
        user = user.substring(0,1).toUpperCase() + user.substring(1);
        username.setText(user);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        inventoryTypeList();
        inventoryStatusList();
        inventoryShowData();
        DisplayMenuCard();
    }
}
