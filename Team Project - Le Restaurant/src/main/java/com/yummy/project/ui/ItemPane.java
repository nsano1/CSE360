package com.yummy.project.ui;

import java.io.File;
import java.io.FileNotFoundException;

import com.yummy.project.Main;
import com.yummy.project.account.AccountManager;
import com.yummy.project.menu.Item;
import com.yummy.project.state.State;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class ItemPane extends BorderPane {
    private State state = State.getInstance();
    private AccountManager accountManager = state.getAccountManager();

    private int itemLength = 100, itemHeight = 150;
    private Item item;

    // we will make multiple of these objects and put it on the menu
    public ItemPane(Item item) {
        this.item = item;

        // ----MAIN COLOR----
        Color mainColor = Color.MISTYROSE;

        // ----POPUP FOR ITEM DETAILS----
        Popup itemDetailsPopup = new Popup();
        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(630);
        stage.toBack();

        Rectangle addItemBackground = new Rectangle();
        addItemBackground.setX(0.0f);
        addItemBackground.setY(0.0f);
        addItemBackground.setWidth(500);
        addItemBackground.setHeight(630);
        addItemBackground.setArcWidth(10.0);
        addItemBackground.setArcHeight(10.0);
        addItemBackground.setFill(Color.WHITE);
        addItemBackground.setStrokeWidth(5.0);
        addItemBackground.setStroke(Color.LIGHTGREY);

        ImageView imageViewDetailed = new ImageView();
        if (item.getImageName() != null) {
            File file = new File(this.item.getImageName());
            Image image = new Image(file.toURI().toString());
            imageViewDetailed.setImage(image);
            imageViewDetailed.setFitHeight(630 / 3);
            imageViewDetailed.setFitWidth(500);
            imageViewDetailed.setPreserveRatio(false);
        }

        Rectangle rectangle1 = new Rectangle(500, 630 / 3);
        rectangle1.setFill(mainColor);
        rectangle1.setStrokeWidth(5.0);
        rectangle1.setStroke(Color.LIGHTGREY);

        Text itemName = new Text(item.getName());
        itemName.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 45));
        itemName.setFill(Color.BLACK);
        itemName.setTextAlignment(TextAlignment.CENTER);
        itemName.relocate(50, 220);

        Text itemPrice = new Text("$" + item.getPrice().toString());
        itemPrice.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 20));
        itemPrice.setFill(Color.BLACK);
        itemPrice.setTextAlignment(TextAlignment.CENTER);
        itemPrice.relocate(350, 220);

        Text itemDescription = new Text(item.getDescription());
        itemDescription.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 20));
        itemDescription.setFill(Color.BLACK);
        itemDescription.relocate(50, 300);

        Text itemCalories = new Text(String.valueOf(item.getCalories()) + " Calories");
        itemCalories.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 20));
        itemCalories.setFill(Color.BLACK);
        itemCalories.relocate(50, 400);

        ObservableList<String> ingredients = FXCollections.observableArrayList(
                item.getIngredients());
        ComboBox<String> itemIngredients = new ComboBox<String>(ingredients);
        itemIngredients.relocate(50, 450);
        itemIngredients.setStyle("-fx-background-color: #faef95; ");
        itemIngredients.setMinHeight(50);
        itemIngredients.setMinWidth(200);
        itemIngredients.setValue("Item Ingredients");

        // ----IF OWNER, SHOW EDIT PRICE BUTTON----
        // still doesnt work

        Button editInfo = new Button("Edit Info");
        editInfo.relocate(150, 530);
        editInfo.setMinHeight(50);
        editInfo.setMinWidth(200);
        editInfo.setFont(new Font("SimSun", 15));
        editInfo.setTextAlignment(TextAlignment.CENTER);
        editInfo.setStyle("-fx-background-color: #F08080; ");
        Popup addItemPopup = new Popup();
        ItemEditPane editPane = new ItemEditPane(item, actionEvent -> addItemPopup.hide(), false);
        addItemPopup.getContent().add(editPane);
        editInfo.setOnAction(actionEvent -> {
            stage.close();
            editPane.setItem(item);
            addItemPopup.show(Main.getStage());
        });

        Button close = new Button("Close");
        close.relocate(10, 10);
        close.setMinHeight(30);
        close.setMinWidth(70);
        close.setFont(new Font("SimSun", 15));
        close.setTextAlignment(TextAlignment.CENTER);
        close.setStyle("-fx-background-color: #F08080; ");
        close.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                itemDetailsPopup.hide();
                stage.close();
            }
        });

        itemDetailsPopup.getContent().addAll(addItemBackground, rectangle1);
        if (item.getImageName() != null) {
            itemDetailsPopup.getContent().addAll(imageViewDetailed);
        }
        itemDetailsPopup.getContent().addAll(itemName, itemPrice, itemDescription, itemCalories, itemIngredients,
                close);

        // if owner, show edit button
        itemDetailsPopup.getContent().add(editInfo);

        // ----END OF POPUP----

        // ----ITEM OBJECT----

        Pane rootPane = new Pane();

        Rectangle background = new Rectangle(itemLength, itemHeight);
        background.setFill(Color.WHITE);
        background.setStrokeWidth(5.0);
        background.setStroke(Color.LIGHTGREY);

        Rectangle rectangle = new Rectangle(itemLength, itemHeight / 3 * 2);
        rectangle.setFill(mainColor);
        rectangle.setStrokeWidth(5.0);
        rectangle.setStroke(Color.LIGHTGREY);

        ImageView imageView = new ImageView();
        if (item.getImageName() != null) {
            File file = new File(this.item.getImageName());
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            imageView.setFitHeight(itemHeight / 3 * 2);
            imageView.setFitWidth(itemLength);
            imageView.setPreserveRatio(false);
        }
        Button name = new Button(item.getName());
        name.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 15));
        name.setStyle("-fx-background-color: #FFFFFF");
        name.setPrefHeight(itemHeight / 3);
        name.setPrefWidth(itemLength);
        name.relocate(0, itemHeight / 3 * 2);
        name.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!itemDetailsPopup.isShowing()) {
                    stage.show();
                    itemDetailsPopup.show(stage);
                }
            }
        });

        Button add = new Button("+");
        add.relocate(itemLength - 34, 0);
        add.setStyle("-fx-background-color: #F08080");
        add.setFont(new Font("SimSun", 20));

        // Add item to current user's cart
        add.setOnAction(actionEvent -> {
            if (accountManager.getLoggedIn())
                accountManager.getCurrentAccount().getCart().addItem(item);
        });

        rootPane.getChildren().addAll(background, rectangle);
        if (item.getImageName() != null) {
            rootPane.getChildren().addAll(imageView);
        }
        rootPane.getChildren().addAll(name, add);
        this.setTop(rootPane);
    }

    public Item getItem() {
        return item;
    }
}
