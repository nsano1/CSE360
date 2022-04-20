package com.yummy.project.ui;

import com.yummy.project.Main;
import com.yummy.project.account.Account;
import com.yummy.project.account.AccountManager;
import com.yummy.project.account.CreditCard;
import com.yummy.project.state.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;


public class CreateAccountPage implements Page {
    AccountManager accountManager = State.getInstance().getAccountManager();

    private Button confirm,confirmPOP,cancelPOP;
    private TextField fullName, email, phone, username, password, confirm_password, fullNamePayment,cardNumber,expirationDate,CVV;
    private CheckBox ownerCheckBox;

    public Scene getScene() {
        //pane to put all items on
        Pane rootPane = new Pane();

        int windowLength = 1100;
        int windowHeight = 630;

        Scene scene = new Scene(rootPane, windowLength, windowHeight);
        scene.setFill(Color.WHITE);

        //----MAIN COLOR----
        Color mainColor = Color.MISTYROSE;

        //----PAGE TITLE----
        Text title = new Text(windowLength / 2 - 360,100, "Create Account");
        title.setFont(Font.font ("SimSun", FontWeight.EXTRA_BOLD, 50));
        title.setFill(Color.BLACK);
        
        //----RECTANGLE FOR BACKGROUND----
        Rectangle rectangle = new Rectangle(windowLength, windowHeight);
        rectangle.setFill(Color.WHITE);
        rectangle.setStrokeWidth(5.0);
        rectangle.setStroke(Color.LIGHTGREY);
        
        Rectangle rectangle1 = new Rectangle(windowLength-5, 145);
        rectangle1.relocate(5,5);
        rectangle1.setFill(mainColor);
        rectangle1.setStrokeWidth(5.0);
        rectangle1.setStroke(Color.LIGHTGREY);

        //----CONFIRM BUTTON----
        confirm = new Button("Confirm");
        confirm.relocate(800, 520);
        confirm.setStyle("-fx-background-color: #F08080");
        confirm.setFont(new Font("SimSun", 20));
        // Go back to FrontPage when button is clicked
        confirm.setOnAction(actionEvent -> confirmPressed());

        //----PAYMENT BUTTON----
        Button payment = new Button("Payment Information");
        payment.relocate(270, 520);
        payment.setStyle("-fx-background-color: #F08080");
        payment.setFont(new Font("SimSun", 20));


        //---- Payment Popup ----
        Popup paymentPop = new Popup();
        payment.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!paymentPop.isShowing()) {
                    Main.getStage().show();
                    paymentPop.show(Main.getStage());

                } else {
                    paymentPop.hide();
                }
            }
        });

        //---- Payment INFORMATION ----
        Rectangle paymentRec = new Rectangle();
        paymentRec.setX(0.0f);
        paymentRec.setY(0.0f);
        paymentRec.setWidth(400.0f);
        paymentRec.setHeight(300.0f);
        paymentRec.setArcWidth(10.0);
        paymentRec.setArcHeight(10.0);
        paymentRec.setFill(Color.WHITE);
        paymentRec.setStrokeWidth(5.0);
        paymentRec.setStroke(Color.LIGHTGREY);

        Text titlePayment = new Text(35,50, "Payment Information");
        titlePayment.setFont(Font.font ("SimSun", FontWeight.EXTRA_BOLD, 35));
        titlePayment.setFill(Color.BLACK);
        //---- Text  Boxes ----
        fullNamePayment = new TextField ();
        fullNamePayment.setPrefColumnCount(25);
        fullNamePayment.relocate(50,85);
        fullNamePayment.setStyle("-fx-background-color: #D3D3D3");
        fullNamePayment.setPromptText("Full Name");
        fullNamePayment.setAlignment(Pos.CENTER);
        fullNamePayment.setPrefHeight(35);

        cardNumber = new TextField ();
        cardNumber.setPrefColumnCount(25);
        cardNumber.relocate(50,140);
        cardNumber.setStyle("-fx-background-color: #D3D3D3");
        cardNumber.setPromptText("Credit Card Number");
        cardNumber.setAlignment(Pos.CENTER);
        cardNumber.setPrefHeight(35);

        expirationDate = new TextField ();
        expirationDate.setPrefColumnCount(25);
        expirationDate.relocate(50,180);
        expirationDate.setStyle("-fx-background-color: #D3D3D3");
        expirationDate.setPromptText("Expiration Date");
        expirationDate.setAlignment(Pos.CENTER);
        expirationDate.setPrefHeight(35);
        expirationDate.setPrefWidth(145);

        CVV = new TextField ();
        CVV.setPrefColumnCount(25);
        CVV.relocate(200,180);
        CVV.setStyle("-fx-background-color: #D3D3D3");
        CVV.setPromptText("CVV");
        CVV.setAlignment(Pos.CENTER);
        CVV.setPrefHeight(35);
        CVV.setPrefWidth(145);

        //---- Cancel Button ----
        cancelPOP = new Button("Cancel");
        cancelPOP.relocate(265, 225);
        cancelPOP.setStyle("-fx-background-color: #F08080");
        cancelPOP.setFont(new Font("SimSun", 20));
        cancelPOP.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent actionEvent) {
                paymentPop.hide();
            }
        });
        //---- Confrim Button ----
        confirmPOP = new Button("Confirm");
        confirmPOP.relocate(45, 225);
        confirmPOP.setStyle("-fx-background-color: #faef95;");
        confirmPOP.setFont(new Font("SimSun", 20));
        confirmPOP.setOnAction(actionEvent -> paymentConfirmation());
        confirmPOP.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent actionEvent) {
                paymentPop.hide();
            }
        });


        //---- Popup -----,CVV,expirationDate,cardNumber,fullNamePayment
        paymentPop.getContent().addAll(paymentRec,confirmPOP,cancelPOP,titlePayment,fullNamePayment,cardNumber,expirationDate,CVV);

        //----PERSONAL INFORMATION TEXT BOXES----
        fullName = new TextField ();
        fullName.setPrefColumnCount(25);
        fullName.relocate(700,200);
        fullName.setStyle("-fx-background-color: #D3D3D3");
        fullName.setPromptText("Full Name*");
        fullName.setAlignment(Pos.CENTER);
        fullName.setPrefHeight(30);

        email = new TextField ();
        email.setPrefColumnCount(25);
        email.relocate(700,250);
        email.setStyle("-fx-background-color: #D3D3D3");
        email.setPromptText("Email*");
        email.setAlignment(Pos.CENTER);
        email.setPrefHeight(30);

        phone = new TextField ();
        phone.setPrefColumnCount(25);
        phone.relocate(700,300);
        phone.setStyle("-fx-background-color: #D3D3D3");
        phone.setPromptText("Phone Number*");
        phone.setAlignment(Pos.CENTER);
        phone.setPrefHeight(30);

        username = new TextField ();
        username.setPrefColumnCount(25);
        username.relocate(700,350);
        username.setStyle("-fx-background-color: #D3D3D3");
        username.setPromptText("Username*");
        username.setAlignment(Pos.CENTER);
        username.setPrefHeight(30);

        password = new TextField ();
        password.setPrefColumnCount(25);
        password.relocate(700,400);
        password.setStyle("-fx-background-color: #D3D3D3");
        password.setPromptText("Password*");
        password.setAlignment(Pos.CENTER);
        password.setPrefHeight(30);

        confirm_password = new TextField ();
        confirm_password.setPrefColumnCount(25);
        confirm_password.relocate(700,450);
        confirm_password.setStyle("-fx-background-color: #D3D3D3");
        confirm_password.setPromptText("Confirm Password*");
        confirm_password.setAlignment(Pos.CENTER);
        confirm_password.setPrefHeight(30);

        ownerCheckBox = new CheckBox("Owner Account?");
        ownerCheckBox.relocate(700, 500);

        //----BACK BUTTON----
        Button back = new Button("Back");
        back.relocate(20, 20);
        back.setStyle("-fx-background-color: #778899");
        back.setFont(new Font("SimSun", 15));

        // Go back to FrontPage when button is clicked
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.setPage(new FrontPage());
            }
        });




        rootPane.getChildren().addAll(rectangle1, title, confirm, payment, back);
        rootPane.getChildren().addAll(fullName, email, phone, username, password, confirm_password, ownerCheckBox);

        return scene;
    }

    public String getTitle() {
        return "Create Account!";
    }

    public void update() {
        // Switch to MenuPage if logged in
        if (accountManager.getLoggedIn()) {
            Main.setPage(new MenuPage());
        }
    }
    public void paymentConfirmation(){
        String cardHolderName = fullNamePayment.getText();
        String cardNum = cardNumber.getText();
        String expDate = expirationDate.getText();
        String cvvNum = CVV.getText();
            CreditCard creditCard = new CreditCard(cardHolderName,cardNum,expDate,cvvNum
            );

    }
    // Called when confirm button is pressed
    // Checks if the conditions are valid to create the account
    private void confirmPressed() {
        String usernameText = username.getText();
        String passwordText = password.getText();
        String confirmPasswordText = confirm_password.getText();
        String phoneText = phone.getText();
        String emailText = email.getText();
        boolean isOwner = ownerCheckBox.isSelected();

        if (!passwordText.equals(confirmPasswordText)) {

            return; // Return if password != confirm password
        }

        // Create account object from values in text boxes
        Account account = new Account(usernameText,
                passwordText,
                phoneText,
                emailText,
                isOwner);

        accountManager.add(account);
        accountManager.login(account);

    }
}
