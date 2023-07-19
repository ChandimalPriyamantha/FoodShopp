package com.example.foodshope_j;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

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