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
    private TextField fp_answer;

    @FXML
    private Button fp_back;

    @FXML
    private AnchorPane fp_newPasswordForm;

    @FXML
    private Button fp_proceedBtn;

    @FXML
    private ComboBox<?> fp_question;

    @FXML
    private AnchorPane fp_question_form;

    @FXML
    private Button l_alreadyButton;

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
    private AnchorPane l_side_form;

    @FXML
    private TextField l_userName;

    @FXML
    private Button pf_back;

    @FXML
    private Button pf_changedPassword;

    @FXML
    private PasswordField pf_confPassword;

    @FXML
    private PasswordField pf_newPassword;

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
    private TextField fp_usernameProceed;

    private Connection connect;
    private PreparedStatement pts;
    private ResultSet st;



    private Alert alert;


    public void loginBtn(){

        if(l_userName.getText().isEmpty() || l_passWord.getText().isEmpty()){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Username/Password");
            alert.showAndWait();

        }else{

            String selctData = "SELECT username, password FROM employee WHERE username=? and password=?";

            connect = ConnectionShopp.ConnectionDB();

            try{

                pts = connect.prepareStatement(selctData);
                pts.setString(1,l_userName.getText());
                pts.setString(2,l_passWord.getText());

                st = pts.executeQuery();

                if(st.next()){

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();

                    l_passWord.setText("");
                    l_userName.setText("");
                }else{

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/Password");
                    alert.showAndWait();

                }

            }catch (Exception e){
                   e.printStackTrace();
            }
        }
    }

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

                String checkUserName = "SELECT username FROM employee WHERE username = '"
                        + r_userName.getText() + "'";

                pts = connect.prepareStatement(checkUserName);
                st = pts.executeQuery();

                if (st.next()) {

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(r_userName.getText() + "is already taken");
                    alert.showAndWait();


                } else if (r_password.getText().length() < 8) {

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Password should be more than 8 character");
                    alert.showAndWait();

                } else {




                pts = connect.prepareStatement(rgQuery);
                pts.setString(1, r_userName.getText());
                pts.setString(2, r_password.getText());
                pts.setString(3, (String) r_question.getSelectionModel().getSelectedItem());
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
                slider.setToX(0);
                slider.setDuration(Duration.seconds(.5));

                slider.setOnFinished((ActionEvent e) -> {
                    l_alreadyButton.setVisible(false);
                    l_createAccount.setVisible(true);
                });
                slider.play();
            }
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

    public void switchForgotPass(){

        fp_question_form.setVisible(true);
        l_login_form.setVisible(false);

        forgotPassQuestionList();
    }

    public void proceedBtn(){

            if(fp_usernameProceed.getText().isEmpty() || fp_question.getSelectionModel().getSelectedItem() == null ||
               fp_answer.getText().isEmpty()){

                alert  =  new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{

                String selectData = "SELECT username, question, answer FROM employee WHERE username = ? AND question = ? AND answer = ? ";

                connect = ConnectionShopp.ConnectionDB();

                try {
                    pts = connect.prepareStatement(selectData);
                    pts.setString(1, fp_usernameProceed.getText());
                    pts.setString(2, (String)fp_question.getSelectionModel().getSelectedItem());
                    pts.setString(3, fp_answer.getText());

                    st = pts.executeQuery();

                    if(st.next()){

                        fp_newPasswordForm.setVisible(true);
                        fp_question_form.setVisible(false);


                    }else{

                        alert  =  new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Incorrect Information");
                        alert.showAndWait();

                    }


                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }

    }

    public void changePassword(){

        if(pf_newPassword.getText().isEmpty() || pf_confPassword.getText().isEmpty()){

            alert  =  new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect Information");
            alert.showAndWait();
        }else{

            if(pf_newPassword.getText().equals(pf_confPassword.getText())){

                String getDate = "SELECT date FROM employee WHERE username = '"+fp_usernameProceed.getText()+"'";

                connect = ConnectionShopp.ConnectionDB();

                try {
                    pts = connect.prepareStatement(getDate);
                    st = pts.executeQuery();

                    String date = null;
                    if(st.next()){

                        date = st.getString("date");
                    }


                    String updatePass = "UPDATE employee SET password = '"
                            + pf_newPassword.getText()+"', question = '"+ fp_question.getSelectionModel().getSelectedItem() +"'," +
                            "answer = '"+ fp_answer.getText()+"', date = '"+date+"' WHERE username = '"+fp_usernameProceed.getText()+"'" ;

                    pts = connect.prepareStatement(updatePass);
                    pts.executeUpdate();

                    alert  =  new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully changed Password!");
                    alert.showAndWait();

                    l_login_form.setVisible(true);
                    fp_newPasswordForm.setVisible(false);

                    //TO CLEAR FIELDS
                    pf_newPassword.setText("");
                    pf_confPassword.setText("");
                    fp_usernameProceed.setText("");
                    fp_answer.setText("");
                    fp_question.getSelectionModel().clearSelection();


                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


            }else{

                alert  =  new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Password is not matched");
                alert.showAndWait();


            }



        }
    }

    public  void backToLoginForm(){
           l_login_form.setVisible(true);
           fp_question_form.setVisible(false);

           fp_question.getSelectionModel().clearSelection();
           fp_answer.setText("");
           fp_usernameProceed.setText("");
    }

    public  void backToQuestionForm(){

        fp_question_form.setVisible(true);
        fp_newPasswordForm.setVisible(false);
    }
    public void forgotPassQuestionList(){
        List<String> listQ = new ArrayList<>();

        for(String data : questionList){
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);
        fp_question.setItems(listData);
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

                fp_question_form.setVisible(false);
                l_login_form.setVisible(true);
                fp_newPasswordForm.setVisible(false);
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

                fp_question_form.setVisible(false);
                l_login_form.setVisible(true);
                fp_newPasswordForm.setVisible(false);

                fp_question.getSelectionModel().clearSelection();
                fp_answer.setText("");
                fp_usernameProceed.setText("");
            });
            slider.play();
        }
    }
}