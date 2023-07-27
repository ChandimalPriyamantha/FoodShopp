package com.example.foodshope_j;

import com.example.foodshope_j.ConnectionDB.ConnectionShopp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

public class CardProductController implements Initializable {


    @FXML
    private Button card_addBtn;

    @FXML
    private AnchorPane card_form;

    @FXML
    private ImageView card_product_image;

    @FXML
    private Label card_product_name;

    @FXML
    private Label card_product_price;

    @FXML
    private Spinner<Integer> card_spiner;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private ProductData productData;
    private Image image;

    private  String product_image;

    private SpinnerValueFactory<Integer> spin;


    private int qty;

    private  String prodID;
    private  String type;
    private String prod_image;
    private String prod_date;

    private double totalP;
    private double pr;


    public void setData(ProductData productData){

        this.productData = productData;

        prod_image = productData.getImage();
        prod_date = String.valueOf(productData.getDate());
        type = productData.getType();
        prodID = productData.getProductId();
        card_product_name.setText(productData.getProductName());
        card_product_price.setText("LKR" + String.valueOf(productData.getPrice()));
        String path = "File:" + productData.getImage();
        image = new Image(path, 245, 91, false, true);
        card_product_image.setImage(image);
        pr = productData.getPrice();

    }

    public  void setQuantity() {

        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        card_spiner.setValueFactory(spin);

    }

    public  void addBtn(){

       MainForm mainForm = new MainForm();
       mainForm.customerID();

        qty = card_spiner.getValue();
        String check = "";
        String checkAvailable = "SELECT status FROM product WHERE prod_id = '"+
                prodID+"'";
        connect = ConnectionShopp.ConnectionDB();

        try {

            int checkStockNumber = 0;
            String checkStock = "SELECT stock FROM product WHERE prod_id = '"+prodID+"'";

            prepare = connect.prepareStatement(checkStock);
            result = prepare.executeQuery();

            if(result.next()){
                checkStockNumber = result.getInt("stock");
            }

            if(checkStockNumber == 0){

                String updateStock = "UPDATE product SET prod_name = '"
                        +card_product_name.getText()+"', type= '"
                        + type +"',stock= 0,price = " + pr
                        +",status='Unavailable', image='"
                        + prod_image+"',date='"+prod_date+"' WHERE prod_id = '"+prodID+"' ";

                prepare = connect.prepareStatement(updateStock);
                prepare.executeUpdate();
            }

            prepare = connect.prepareStatement(checkAvailable);
            result = prepare.executeQuery();

            if(result.next()){
                check = result.getString("status");
            }
            if(!check.equals("Available") || qty == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Something Wrong");
                alert.showAndWait();
            }else{



               if( checkStockNumber < qty){

                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Error Message");
                   alert.setHeaderText(null);
                   alert.setContentText("Out of stock");
                   alert.showAndWait();

               }else{

                   //prod_image = prod_image.replace("\\","\\\\");

                   String insertData = "INSERT INTO customer " +
                           "(customer_id,prod_id,type,prod_name,quantity,price,date,image,em_username)" +
                           "VALUES(?,?,?,?,?,?,?,?,?)";

                   prepare = connect.prepareStatement(insertData);
                   prepare.setString(1,String.valueOf(UserData.cID));
                   prepare.setString(2,prodID);
                   prepare.setString(3,type);
                   prepare.setString(4,card_product_name.getText());
                   prepare.setString(5,String.valueOf(qty));
                   totalP = (qty * pr);
                   prepare.setString(6,String.valueOf(totalP));

                   Date date = new Date();
                   java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                   prepare.setString(7,String.valueOf(sqlDate));
                   prepare.setString(8,"null");
                   prepare.setString(9,UserData.username);

                   prepare.executeUpdate();

                   int upStock = checkStockNumber - qty;

                   String updateStock = "UPDATE product SET prod_name = '"
                           +card_product_name.getText()+"', type= '"
                           + type +"',stock= " +upStock+",price = " + pr
                           +",status='"
                           + check+"', image='"
                           + prod_image+"',date='"+prod_date+"' WHERE prod_id = '"+prodID+"' ";

                   prepare = connect.prepareStatement(updateStock);
                   prepare.executeUpdate();


                   Alert alert = new Alert(Alert.AlertType.INFORMATION);
                   alert.setTitle("Information Message");
                   alert.setHeaderText(null);
                   alert.setContentText("Successfully Added!");
                   alert.showAndWait();


                   mainForm.menuGetTotal();


               }




            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(qty == 0){

        }
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setQuantity();
    }
}
