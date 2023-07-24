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

    private SpinnerValueFactory<Integer> spin;


    private int qty;

    private  String prodID;
    public  void setQuantity() {

        spin = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        card_spiner.setValueFactory(spin);

    }

    public  void addBtn(){

        qty = card_spiner.getValue();
        String check = "";
        String checkAvailable = "SELECT status FROM product WHERE product_name = '"+
                card_product_name+"'";
        connect = ConnectionShopp.ConnectionDB();

        try {
            prepare = connect.prepareStatement(checkAvailable);
            result = prepare.executeQuery();

            if(result.next()){
                check = result.getString("status");
            }

            if(check.equals("Available") || qty == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Something Wrong");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(qty == 0){

        }
    }

    public void setData(ProductData productData){

        this.productData = productData;

        card_product_name.setText(productData.getProductName());
        card_product_price.setText("LKR" + String.valueOf(productData.getPrice()));
        String path = "File:" + productData.getImage();
        image = new Image(path, 245, 91, false, true);
        card_product_image.setImage(image);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
