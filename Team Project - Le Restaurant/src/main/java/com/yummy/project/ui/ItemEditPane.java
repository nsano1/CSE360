package com.yummy.project.ui;

import com.yummy.project.Main;
import com.yummy.project.menu.Item;
import com.yummy.project.menu.MenuManager;
import com.yummy.project.menu.Item.Type;
import com.yummy.project.state.State;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ItemEditPane extends BorderPane {
    private State state = State.getInstance();
    private MenuManager menuManager = state.getMenuManager();

    private Item item;
    private EventHandler<ActionEvent> closeHandler;
    private TextField itemName, itemDescription, itemPrice, itemCalories, ingredients, cookTime;
    private ComboBox type;
    private Button save, close, selectImage;
    private FileChooser fileChooser;

    public ItemEditPane(Item item, EventHandler<ActionEvent> closeHandler, boolean add) {
        this.item = item;
        this.closeHandler = closeHandler;

        int windowLength = 1100;
        int windowHeight = 630;

        Pane rootPane = new Pane();

        Rectangle addItemBackground = new Rectangle();
        addItemBackground.setX(0.0f);
        addItemBackground.setY(0.0f);
        addItemBackground.setWidth(500);
        addItemBackground.setHeight(windowHeight);
        addItemBackground.setArcWidth(10.0);
        addItemBackground.setArcHeight(10.0);
        addItemBackground.setFill(Color.WHITE);
        addItemBackground.setStrokeWidth(5.0);
        addItemBackground.setStroke(Color.LIGHTGREY);

        itemName = new TextField();
        itemName.setPrefColumnCount(25);
        itemName.relocate(100, 150);
        itemName.setStyle("-fx-background-color: #D3D3D3");
        itemName.setPromptText("Item Name");
        itemName.setAlignment(Pos.CENTER);
        itemName.setPrefHeight(30);

        itemDescription = new TextField();
        itemDescription.setPrefColumnCount(25);
        itemDescription.relocate(100, 200);
        itemDescription.setStyle("-fx-background-color: #D3D3D3");
        itemDescription.setPromptText("Description");
        itemDescription.setAlignment(Pos.CENTER);
        itemDescription.setPrefHeight(50);

        itemPrice = new TextField();
        itemPrice.setPrefColumnCount(10);
        itemPrice.relocate(100, 270);
        itemPrice.setStyle("-fx-background-color: #D3D3D3");
        itemPrice.setPromptText("Price");
        itemPrice.setAlignment(Pos.CENTER);
        itemPrice.setPrefHeight(30);
        // force the field to be decimal format
        itemPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^\\d{0,7}([\\.]\\d{0,2})?")) {
                itemPrice.setText(oldValue);
            }
        });

        itemCalories = new TextField();
        itemCalories.setPrefColumnCount(10);
        itemCalories.relocate(270, 270);
        itemCalories.setStyle("-fx-background-color: #D3D3D3");
        itemCalories.setPromptText("Calories");
        itemCalories.setAlignment(Pos.CENTER);
        itemCalories.setPrefHeight(30);
        // force the field to be numeric only
        itemCalories.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                itemCalories.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        ingredients = new TextField();
        ingredients.setPrefColumnCount(15);
        ingredients.relocate(100, 340);
        ingredients.setStyle("-fx-background-color: #D3D3D3");
        ingredients.setPromptText("Ingredients");
        ingredients.setAlignment(Pos.CENTER);
        ingredients.setPrefHeight(30);
        ingredients.setPrefColumnCount(25);

        cookTime = new TextField();
        cookTime.setPrefColumnCount(15);
        cookTime.relocate(100, 410);
        cookTime.setStyle("-fx-background-color: #D3D3D3");
        cookTime.setPromptText("Average Cook Time");
        cookTime.setAlignment(Pos.CENTER);
        cookTime.setPrefHeight(30);
        cookTime.setPrefColumnCount(25);

        // force the field to be numeric only
        cookTime.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                cookTime.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        ObservableList<String> filters = FXCollections.observableArrayList(
                "Appetizer",
                "Dish",
                "Beverage");
        type = new ComboBox(filters);
        type.relocate(100, 480);
        type.setStyle("-fx-background-color: #faef95; ");
        type.setValue("Item Type");
        type.setMinWidth(200);
        type.setMinHeight(40);

        selectImage = new Button("Select Image");
        selectImage.setStyle("-fx-background-color: #faef95; ");
        selectImage.relocate(150, 80);
        selectImage.setMinHeight(40);
        selectImage.setMinWidth(200);
        selectImage.setTextAlignment(TextAlignment.CENTER);
        selectImage.setOnAction(actionEvent -> {
            fileChooser = new FileChooser();
            fileChooser.setTitle("Select Item Image");
            File file = fileChooser.showOpenDialog(Main.getStage());
            this.item.setImageName(file.getAbsolutePath());
        });

        close = new Button("Close");
        close.relocate(10, 10);
        close.setMinHeight(30);
        close.setMinWidth(70);
        close.setFont(new Font("SimSun", 15));
        close.setTextAlignment(TextAlignment.CENTER);
        close.setStyle("-fx-background-color: #F08080; ");
        close.setOnAction(closeHandler);

        save = new Button("Save");
        save.relocate(180, windowHeight - 80);
        save.setPrefHeight(50.0);
        save.setPrefWidth(150.0);
        save.setFont(new Font("SimSun", 20));
        save.setStyle("-fx-background-color: #faef95; ");

        save.setOnAction(actionEvent -> {
            saveParameters();

            if (add)
                menuManager.add(this.item);
            else
                state.update();

            closeHandler.handle(actionEvent);
        });

        rootPane.getChildren().addAll(addItemBackground, itemName, itemDescription,
                itemPrice, itemCalories, ingredients, cookTime, type,
                save, close, selectImage);

        this.setTop(rootPane);

        loadParameters();
    }

    public void setItem(Item item) {
        this.item = item;
        loadParameters();
    }

    // Save parameters to item
    private void saveParameters() {
        item.setName(itemName.getText());
        item.setPrice(Double.parseDouble(itemPrice.getText()));
        item.setCalories(Integer.parseInt(itemCalories.getText()));
        item.setDescription(itemDescription.getText());
        item.setIngredients(ingredients.getText());
        item.setCookTime(Integer.parseInt(cookTime.getText()));
        switch ((String) type.getValue()) {
            case "Appetizer":
                item.setType(Item.Type.appetizer);
                break;
            case "Beverage":
                item.setType(Item.Type.beverage);
                break;
            case "Dish":
                item.setType(Item.Type.dish);
                break;
        }
    }

    // Load the parameters from this.item into input fields
    private void loadParameters() {
        itemName.setText(item.getName());
        itemDescription.setText(item.getDescription());
        itemPrice.setText(item.getPrice().toString());
        itemCalories.setText(Integer.toString(item.getCalories()));
        ingredients.setText(item.getIngredients());

        switch (item.getType()) {
            case appetizer:
                type.setValue("Appetizer");
                break;
            case beverage:
                type.setValue("Beverage");
                break;
            case dish:
                type.setValue("Dish");
                break;
        }
    }
}
