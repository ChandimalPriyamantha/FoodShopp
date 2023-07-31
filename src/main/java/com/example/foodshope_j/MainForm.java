package com.example.foodshope_j;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
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
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.PublicKey;
import java.sql.*;
import java.util.Date;


import java.util.*;



public class MainForm implements Initializable {


    @FXML
    private Label dasbord_NFC;

    @FXML
    private Label dashbord_SP;

    @FXML
    private Label dashbord_TI;

    @FXML
    private Label dashbord_TLI;

    @FXML
    private Button dashbord_btn;

    @FXML
    private BarChart<?, ?> dashbord_customer_chart;

    @FXML
    private AnchorPane dashbord_form;

    @FXML
    private AreaChart<?, ?> dashbord_incomeChart;

    @FXML
    private AnchorPane customer_form;

    @FXML
    private TableView<CustomerData> customer_table;

    @FXML
    private TableColumn<CustomerData, String> customer_table_chashir;

    @FXML
    private TableColumn<CustomerData, String> customer_table_customer_id;

    @FXML
    private TableColumn<CustomerData, String> customer_table_date;



    @FXML
    private TableColumn<CustomerData, String> customer_table_total;


    @FXML
    private Button customer_btn;



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
    private TableColumn<ProductData, String> inventory_category;

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
    private ComboBox<?> category_item;

    @FXML
    private TextField stoke;

    @FXML
    private ComboBox<?> type;

    @FXML
    private TextField menu_amount;

    @FXML
    private Button filter_buttn;

    @FXML
    private ComboBox<?> filter_list;

    @FXML
    private Label menu_change;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private Button menu_pay;

    @FXML
    private TableColumn<ProductData, String> menu_price;

    @FXML
    private TableColumn<ProductData, String> menu_quantity;

    @FXML
    private Button menu_receipt;

    @FXML
    private Button menu_remove;

    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private Label menu_total;

    @FXML
    private TableColumn<ProductData, String> munu_product_name;

    @FXML
    private TableView<ProductData> menu_view_table;


    @FXML
    private Button add_catogory_btn;

    @FXML
    private TextField category_id;

    @FXML
    private TextField category_name;

    @FXML
    private Button clear_catogory_btn;


    @FXML
    private Button delete_category_btn;

    @FXML
    private Button update_catogory;

    @FXML
    private TableColumn<CategoryData, String> table_category_id;

    @FXML
    private TableColumn<CategoryData, String> table_category_name;

    @FXML
    private TableView<CategoryData> category_table_view;


    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Alert alert;




    public void dashboardDisplayNC() {

        String sql = "SELECT COUNT(id) FROM receipt";
        connect = ConnectionShopp.ConnectionDB();

        try {
            int nc = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                nc = result.getInt("COUNT(id)");
            }
            dasbord_NFC.setText(String.valueOf(nc));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dashboardDisplayTI() {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) FROM receipt WHERE date = '"
                + sqlDate + "'";

        connect = ConnectionShopp.ConnectionDB();

        try {
            double ti = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }

            dashbord_TI.setText("LKR " + ti);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardTotalI() {
        String sql = "SELECT SUM(total) FROM receipt";

        connect =  ConnectionShopp.ConnectionDB();

        try {
            float ti = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                ti = result.getFloat("SUM(total)");
            }
            dashbord_TLI.setText("LKR " + ti);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardNSP() {

        String sql = "SELECT COUNT(quantity) FROM customer";

        connect = ConnectionShopp.ConnectionDB();

        try {
            int q = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                q = result.getInt("COUNT(quantity)");
            }
            dashbord_SP.setText(String.valueOf(q));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardIncomeChart() {
        dashbord_incomeChart.getData().clear();

        String sql = "SELECT date, SUM(total) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
        connect = ConnectionShopp.ConnectionDB();
        XYChart.Series chart = new XYChart.Series();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getFloat(2)));
            }

            dashbord_incomeChart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboardCustomerChart(){
        dashbord_customer_chart.getData().clear();

        String sql = "SELECT date, COUNT(id) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
        connect = ConnectionShopp.ConnectionDB();
        XYChart.Series chart = new XYChart.Series();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
            }

            dashbord_customer_chart.getData().add(chart);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableList<ProductData> cardListData = FXCollections.observableArrayList();

    public void inventoryAddBtn(){

        if(product_id.getText().isEmpty() || product_name.getText().isEmpty() || type.getSelectionModel().getSelectedItem() == null
        || stoke.getText().isEmpty() || price.getText().isEmpty() || status.getSelectionModel().getSelectedItem() == null||
            UserData.path == null || category_item.getSelectionModel().getSelectedItem() == null){

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
                            "(prod_id, prod_name, type, stock, price, status, image,date,category_id)" +
                            "VALUES(?,?,?,?,?,?,?,?,?)";
                    getCategoryID();
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
                    prepare.setString(9,UserData.itemID);

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

    public void addCategory(){

        if(category_id.getText().isEmpty() || category_name.getText().isEmpty()){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        }else{
            String checkProdID = "SELECT category_id FROM category WHERE category_id ='"
                    +category_id.getText()+"'";

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

                    String insertData = "INSERT INTO category" +
                            "(category_id, category_name,date)" +
                            "VALUES(?,?,?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, category_id.getText());
                    prepare.setString(2, category_name.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(3, String.valueOf((sqlDate)));

                    prepare.executeUpdate();



                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    categoryShowData();
                    categoryClear();
                    showCategoryOnInventory();

                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public void categoryClear(){

        category_id.setText("");
        category_name.setText("");
    }

    public  void InventoryUpdate(){

        if(product_id.getText().isEmpty() || product_name.getText().isEmpty() || type.getSelectionModel().getSelectedItem() == null
                || stoke.getText().isEmpty() || price.getText().isEmpty() || status.getSelectionModel().getSelectedItem() == null||
                UserData.path == null || UserData.id == 0 || category_item.getSelectionModel().getSelectedItem() == null){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        }else{

            String path = UserData.path;
            path = path.replace("\\","\\\\");
            getCategoryID();
            String updateDate = "UPDATE product SET "
                    +"prod_id = '"+ product_id.getText()+"',prod_name ='"
                    + product_name.getText()+"', type='"
                    +type.getSelectionModel().getSelectedItem()+"',stock='"
                    +stoke.getText()+"',price='"
                    +price.getText()+"',status='"
                    +status.getSelectionModel().getSelectedItem()+"',image='"
                    +path+"', date ='"
                    +UserData.date+"', category_id='"+UserData.itemID+"' WHERE id = " + UserData.id;

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

    public void categoryUpdate(){
        if(category_id.getText().isEmpty() || category_name.getText().isEmpty()){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        }else{



            String updateDate = "UPDATE category SET "
                    +"category_id = '"+ category_id.getText()+"',category_name ='"
                    + category_name.getText()+"' WHERE id = " + UserData.CatID;

            connect = ConnectionShopp.ConnectionDB();

            try {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE product ID:" + category_id.getText()+"?");
                Optional<ButtonType> optional = alert.showAndWait();

                if(optional.get().equals(ButtonType.OK)){

                    prepare = connect.prepareStatement(updateDate);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    categoryShowData();
                    categoryClear();
                    showCategoryOnInventory();
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

    public  void categoryDelete(){
        if(UserData.CatID == 0){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        }else{

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE product ID:" + category_id.getText()+"?");
            Optional<ButtonType> optional = alert.showAndWait();

            if(optional.get().equals(ButtonType.OK)){

                String deleteData = "DELETE FROM category WHERE id = " + UserData.CatID;

                try {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted");


                   categoryShowData();
                   categoryClear();
                   showCategoryOnInventory();

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

    public void categorySelectData(){
        CategoryData categoryData = category_table_view.getSelectionModel().getSelectedItem();
        int num = category_table_view.getSelectionModel().getSelectedIndex();

        if(num-1<-1) return;;

        category_id.setText(categoryData.getCategory_id());
        category_name.setText(categoryData.getCategory_name());

        UserData.CatID = categoryData.getId();
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
                        result.getDate("date"),
                        result.getString("category_id"));
                listData.add(prodData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
      return  listData;
    }

    public ObservableList<CategoryData> categoryDataList(){

        ObservableList<CategoryData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM category";

        connect = ConnectionShopp.ConnectionDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            CategoryData categoryData;

            while (result.next()){

                categoryData = new CategoryData(result.getInt("id"),
                        result.getString("category_id"),
                        result.getString("category_name"));
                listData.add(categoryData);
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
        inventory_category.setCellValueFactory(new PropertyValueFactory<>("category_id"));


        inventory_table_view.setItems(inventoryListData);
    }

    private  ObservableList<CategoryData> categoryListData;
    public void categoryShowData(){
        categoryListData = categoryDataList();

        table_category_id.setCellValueFactory(new PropertyValueFactory<>("category_id"));
        table_category_name.setCellValueFactory(new PropertyValueFactory<>("category_name"));

        category_table_view.setItems(categoryListData);



    }


    public void categoriesList(){

        List<String> itemList = new ArrayList<>();

        String sql = "SELECT * FROM category";

        connect = ConnectionShopp.ConnectionDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            CategoryData categoryData;

            while (result.next()){

                String itemName = result.getString("category_name");
                itemList.add(itemName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




        ObservableList listData = FXCollections.observableArrayList(itemList);
        filter_list.setItems(listData);
    }

    public void showCategoryOnInventory(){

       List<String> itemList = new ArrayList<>();

        String sql = "SELECT * FROM category";

        connect = ConnectionShopp.ConnectionDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            CategoryData categoryData;

            while (result.next()){

                String itemName = result.getString("category_name");
                itemList.add(itemName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObservableList listData = FXCollections.observableArrayList(itemList);
        category_item.setItems(listData);


    }

    public void getCategoryID(){

        String itemID = null;

        String sql = "SELECT category_id FROM category where category_name = '"+category_item.getSelectionModel().getSelectedItem()+"'";

        connect = ConnectionShopp.ConnectionDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();



            while (result.next()){

                itemID = result.getString("category_id");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        UserData.itemID = itemID;
        //System.out.println(UserData.itemID);

    }

    public void getCategoryFilterId(){
        String itemID = null;

        String sql = "SELECT category_id FROM category where category_name = '"+filter_list.getSelectionModel().getSelectedItem()+"'";

        connect = ConnectionShopp.ConnectionDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();



            while (result.next()){

                itemID = result.getString("category_id");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        UserData.itemID = itemID;
        //System.out.println(UserData.itemID);
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
                        result.getString("type"),result.getInt("stock")
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

    public ObservableList<CustomerData> customersDataList() {

        ObservableList<CustomerData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM receipt";
        connect = ConnectionShopp.ConnectionDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            CustomerData cData;

            while (result.next()) {
                cData = new CustomerData(result.getInt("id"),
                        result.getInt("customer_id"),
                        result.getDouble("total"),
                        result.getDate("date"),
                        result.getString("em_username"));

                listData.add(cData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<CustomerData> customersListData;

    public void customersShowData() {
        customersListData = customersDataList();

        customer_table_customer_id.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customer_table_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        customer_table_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        customer_table_chashir.setCellValueFactory(new PropertyValueFactory<>("emUsername"));

        customer_table.setItems(customersListData);
    }

    public void switchForm(ActionEvent event){

        if(event.getSource() == dashbord_btn){
            dashbord_form.setVisible(true);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customer_form.setVisible(false);

            dashboardDisplayNC();
            dashboardDisplayTI();
            dashboardTotalI();
            dashboardNSP();

            dashboardCustomerChart();
            dashboardIncomeChart();


        }else if(event.getSource() == inventory_btn){
            dashbord_form.setVisible(false);
            inventory_form.setVisible(true);
            menu_form.setVisible(false);
            customer_form.setVisible(false);

            inventoryTypeList();
            inventoryStatusList();
            inventoryShowData();
            showCategoryOnInventory();



        } else if (event.getSource() == menu_btn) {
            dashbord_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(true);
            customer_form.setVisible(false);
            DisplayMenuCard();
            menuDisplayTotal();
            menuShowOrderData();
            categoriesList();

        }else if(event.getSource() == customer_btn){
            dashbord_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customer_form.setVisible(true);
            customersShowData();
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

                stage.setTitle("ARACHCHI (J) MANAGEMENT SYSTEM");

                Image image = new Image(getClass().getResourceAsStream("/appIcon.png"));
                stage.getIcons().add(image);

                stage.setScene(scene);
                stage.show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<ProductData> menuGetOrder(){
          customerID();
          ObservableList<ProductData> listData = FXCollections.observableArrayList();

          String sql = "SELECT * FROM customer WHERE customer_id = " + cID;

          connect = ConnectionShopp.ConnectionDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ProductData prod;
            while(result.next()){
                prod = new ProductData(result.getInt("id"),
                                       result.getString("prod_id"),
                                       result.getString("prod_name"),
                                       result.getString("type"),
                                       result.getInt("quantity"),
                                       result.getString("image"),
                                       result.getDouble("price"),
                                       result.getDate("date")

                                       );
                listData.add(prod);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
          return listData;
    }

    private  ObservableList<ProductData> menuOrderListData;
    public void menuShowOrderData(){
        menuOrderListData = menuGetOrder();

        munu_product_name.setCellValueFactory(new PropertyValueFactory<>("productName"));
        menu_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        menu_view_table.setItems(menuOrderListData);


    }

    private int getID;
    public void menuSelectOrder(){

        ProductData prod = menu_view_table.getSelectionModel().getSelectedItem();
        int num = menu_view_table.getSelectionModel().getSelectedIndex();

        if((num-1)<-1) return;

        getID = prod.getId();


    }

    private double totalPayment;
    public void menuGetTotal(){
        customerID();
        String total = "SELECT SUM(price) FROM customer WHERE customer_id =" + cID;

        connect = ConnectionShopp.ConnectionDB();

        try {
            prepare = connect.prepareStatement(total);
            result = prepare.executeQuery();

            if(result.next()){
                totalPayment = result.getDouble("SUM(price)");
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void menuDisplayTotal(){

        menuGetTotal();
        menu_total.setText("LKR " + totalPayment);
    }



    private double amount;
    private double change;
    public void menuAmount(){
        menuGetTotal();

        if(menu_amount.getText().isEmpty() || totalPayment == 0){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid");
            alert.showAndWait();
        }else{

            amount = Double.parseDouble(menu_amount.getText());


                change = (amount - totalPayment);
                menu_change.setText("LKR " + change);
                System.out.println(change);

        }
    }

    public void menuPayBtn(){
        if(totalPayment ==0){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid");
            alert.showAndWait();
        }else{
            menuGetTotal();
            String insertPay = "INSERT INTO receipt (customer_id, total, date,em_username)" +
                    "VALUES(?,?,?,?)";

            connect = ConnectionShopp.ConnectionDB();

            try{
                if(amount == 0){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Something Wrong.");
                    alert.showAndWait();
                }else{

                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure?");
                    Optional<ButtonType> optional = alert.showAndWait();


                    if(optional.get().equals(ButtonType.OK)){

                        customerID();
                        menuGetTotal();
                        prepare = connect.prepareStatement(insertPay);
                        prepare.setString(1,String.valueOf(cID));
                        prepare.setString(2,String.valueOf(totalPayment));

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                        prepare.setString(3,String.valueOf(sqlDate));
                        prepare.setString(4,UserData.username);

                        prepare.executeUpdate();
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Order is successful.");
                        alert.showAndWait();

                        menuShowOrderData();


                    }else {
                        alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Cancelled");
                        alert.showAndWait();
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
public void menuReceiptBtn(){

        if(totalPayment == 0 || menu_amount.getText().isEmpty()){

            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Order first.");
            alert.showAndWait();
        }else {

            HashMap map = new HashMap();
            map.put("getReceipt",(cID-1));
            try {
                JasperDesign jasperDesign = JRXmlLoader.load("E:\\Real World Project\\Cafe Managemant System'\\FoodShope_J\\report\\report.jrxml");
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,map,connect);

                JasperViewer.viewReport(jasperPrint,false);
                //JasperPrintManager.printReport(jasperPrint, true);
                menuRestart();
            } catch (JRException e) {
                throw new RuntimeException(e);
            }
        }

}
    public void menuRemoveBtn(){

        if(getID == 0){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select tht item you want to remove.");
            alert.showAndWait();
        }

        String deleteData = "DELETE FROM customer WHERE id = " + getID;
        connect = ConnectionShopp.ConnectionDB();

        try{

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this order?");
            Optional<ButtonType> optional = alert.showAndWait();

            if(optional.get().equals(ButtonType.OK)){

                prepare = connect.prepareStatement(deleteData);
                prepare.executeUpdate();
            }


            menuShowOrderData();

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void menuRestart(){

        totalPayment = 0;
        change = 0;
        amount = 0;
        menu_total.setText("LKR 0.0");
        menu_amount.setText("");
        menu_change.setText("LKR 0.0");
    }

    public ObservableList<ProductData> menuFilter(){

        getCategoryFilterId();

        String sql = "SELECT * FROM product WHERE category_id = '"+UserData.itemID+"'";


        ObservableList<ProductData> filterData = FXCollections.observableArrayList();
        connect = ConnectionShopp.ConnectionDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ProductData productData;

            while (result.next()){

                productData = new ProductData(result.getInt("id"),
                        result.getString("prod_id"),result.getString("prod_name"),
                        result.getString("type"),result.getInt("stock")
                        ,result.getString("image"),result.getDouble("price"),
                        result.getDate("date"));

                filterData.add(productData);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return  filterData;

    }

    public void DisplayFilterMenuCard(){
        cardListData.clear();
        cardListData.addAll(menuFilter());

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
        categoryShowData();
        DisplayMenuCard();

        menuGetOrder();
        menuDisplayTotal();
        menuShowOrderData();
        customersShowData();


        // DASHBORD DATA
        dashboardDisplayNC();
        dashboardDisplayTI();
        dashboardTotalI();
        dashboardNSP();
        dashboardCustomerChart();
        dashboardIncomeChart();

        categoriesList();






    }
}
