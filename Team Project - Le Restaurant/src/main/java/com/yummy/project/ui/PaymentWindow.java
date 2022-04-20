package com.yummy.project.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PaymentWindow {
    public PaymentWindow() {

    }

    public void open() {
        Stage secondStage = new Stage();
        Pane rootPane = new Pane();

        int windowLength = 275;
        int windowHeight = 220;

        Scene scene = new Scene(rootPane, windowLength, windowHeight);
        scene.setFill(Color.LIGHTGREY);

        Color mainColor = Color.MISTYROSE;
        
         //----TITLE PAGE----
        Text title = new Text(30,40, "Payment Information");
        title.setFont(Font.font ("SimSun", FontWeight.EXTRA_BOLD, 30));
        title.setFill(Color.BLACK);

        //----CONFIRM BUTTON----
        Button confirm = new Button("Confirm");
        confirm.relocate(windowLength/2  + 10, windowHeight - 50);
        confirm.setStyle("-fx-background-color: #F08080");
        confirm.setFont(new Font("SimSun", 15));

        //----BACK BUTTON----
        Button back = new Button("Back");
        back.relocate(windowLength/2  - 70, windowHeight - 50);
        back.setStyle("-fx-background-color: #778899");
        back.setFont(new Font("SimSun", 15));

        //----FULL NAME----
        TextField fullName = new TextField ();
        fullName.setPrefColumnCount(20);
        fullName.relocate(60, 80);
        fullName.setStyle("-fx-background-color: #D3D3D3");
        fullName.setPromptText("Full Name");
        fullName.setAlignment(Pos.CENTER);
        fullName.setPrefHeight(30);

        //----CREDIT CARD NUMBER----
        TextField creditCardNumber = new TextField ();
        creditCardNumber.setPrefColumnCount(20);
        creditCardNumber.relocate(60, 120);
        creditCardNumber.setStyle("-fx-background-color: #D3D3D3");
        creditCardNumber.setPromptText("Credit Card Number");
        creditCardNumber.setAlignment(Pos.CENTER);
        creditCardNumber.setPrefHeight(30);

        //----EXPIRATION DATE----
        TextField expDate = new TextField ();
        expDate.setPrefColumnCount(10);
        expDate.relocate(60, 160);
        expDate.setStyle("-fx-background-color: #D3D3D3");
        expDate.setPromptText("Expiration Date");
        expDate.setAlignment(Pos.CENTER);
        expDate.setPrefHeight(30);

        //----CVV----
        TextField cVV = new TextField ();
        cVV.setPrefColumnCount(10);
        cVV.relocate(170, 160);
        cVV.setStyle("-fx-background-color: #D3D3D3");
        cVV.setPromptText("CVV");
        cVV.setAlignment(Pos.CENTER);
        cVV.setPrefHeight(30);

        rootPane.getChildren().addAll(title, confirm, back,
                fullName, creditCardNumber, expDate, cVV);

        secondStage.setTitle("Payment Info");
        secondStage.setScene(scene);
        secondStage.show();
    }

}
