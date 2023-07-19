package com.example.foodshope_j;

import java.sql.Connection;

import com.example.foodshope_j.ConnectionDB.ConnectionShopp;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.nio.channels.ConnectionPendingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginRegistration {
    @FXML
    private Button l_button;

    @FXML
    private Button l_createAccount;

    @FXML
    private Hyperlink l_forgotPassword;

    @FXML
    private AnchorPane l_login_form;

    @FXML
    private PasswordField l_passWord;

    @FXML
    private TextField l_userName;

    @FXML
    private TextField r_answer;

    @FXML
    private PasswordField r_password;

    @FXML
    private ComboBox<?> r_question;

    @FXML
    private TextField r_userName;

    @FXML
    private Button sing_up_button;

    @FXML
    private AnchorPane sing_up_form;

    @FXML
    private Button l_alreadyButton;

    @FXML
    private AnchorPane l_side_form;

    private Connection connect;
    private PreparedStatement pts;
    private ResultSet st;



    private Alert alert;

    public void userRegistration(){
        LocalDateTime currentTime = LocalDateTime.now();
        if(r_userName.getText().isEmpty() || r_password.getText().isEmpty() ||
                r_question.getSelectionModel().getSelectedItem() == null
            || r_answer.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank field.");
            alert.showAndWait();
        }else{
            String rgQuery = "INSERT INTO employee (username, password, question, answer, date) VALUES(?,?,?,?,?)";

            connect = ConnectionShopp.ConnectionDB();

            try {
                pts = connect.prepareStatement(rgQuery);
                pts.setString(1, r_userName.getText());
                pts.setString(2, r_password.getText());
                pts.setString(3, (String)r_question.getSelectionModel().getSelectedItem());
                pts.setString(4, r_answer.getText());
                pts.setString(5, String.valueOf(currentTime));

                pts.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully registered Account!");
                alert.showAndWait();

                r_answer.setText("");
                r_userName.setText("");
                r_password.setText("");
                r_question.getSelectionModel().clearSelection();

                TranslateTransition slider = new TranslateTransition();

                slider.setNode(l_side_form);
                slider.setToX(0 );
                slider.setDuration(Duration.seconds(.5));

                slider.setOnFinished((ActionEvent e) ->{
                    l_alreadyButton.setVisible(false);
                    l_createAccount.setVisible(true);
                });
                slider.play();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String[] questionList = {"What is your favorite color?", "What is your favorite foods?", "What is your birthdate?"};

    public void userQuestionList(){
        List<String> listQ = new ArrayList<>();

        for(String data : questionList){
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);
        r_question.setItems(listData);
    }

    //private Connection con;

    public void switchForm(ActionEvent event){
        TranslateTransition slider = new TranslateTransition();

        if(event.getSource() == l_createAccount){
            slider.setNode(l_side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) ->{
                l_alreadyButton.setVisible(true);
                l_createAccount.setVisible(false);

                userQuestionList();
            });

            slider.play();
        }else if(event.getSource() == l_alreadyButton){
            slider.setNode(l_side_form);
            slider.setToX(0 );
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) ->{
                l_alreadyButton.setVisible(false);
                l_createAccount.setVisible(true);
            });
            slider.play();
        }
    }
}