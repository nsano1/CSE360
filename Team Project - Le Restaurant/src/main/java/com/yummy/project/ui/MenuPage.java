package com.yummy.project.ui;

import com.yummy.project.Main;
import com.yummy.project.account.AccountManager;
import com.yummy.project.menu.Item;
import com.yummy.project.menu.MenuManager;
import com.yummy.project.state.State;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MenuPage implements Page {
    private State state = State.getInstance();
    private MenuManager menuManager = state.getMenuManager();
    private AccountManager accountManager = state.getAccountManager();

    // Item type for choosing which part of the menu to display
    private Item.Type itemType = null;
    private Comparator<? super Item> filter = MenuManager.nameSort; // Sorting filter

    private TilePane items;
    private ScrollPane itemSection, cartSection;
    private Label totalPrice;

    public Scene getScene() {
        // pane to put all items on
        Pane rootPane = new Pane();

        int windowLength = 1100;
        int windowHeight = 630;

        Scene scene = new Scene(rootPane, windowLength, windowHeight);
        scene.setFill(Color.WHITE);

        // ----MAIN COLOR----
        Color mainColor = Color.MISTYROSE;

        // ----TITLE----
        Text title = new Text(230, 30, "Menu");
        title.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 30));
        title.setFill(Color.BLACK);

        // ----RECTANGLE FOR BACKGROUND----
        Rectangle rectangle = new Rectangle(windowLength, windowHeight);
        rectangle.setFill(Color.WHITE);
        rectangle.setStrokeWidth(5.0);
        rectangle.setStroke(Color.LIGHTGREY);

        Rectangle rectangle1 = new Rectangle(200, windowHeight);
        rectangle1.setFill(mainColor);
        rectangle1.setStrokeWidth(5.0);
        rectangle1.setStroke(Color.LIGHTGREY);

        Rectangle rectangle2 = new Rectangle(200, windowHeight);
        rectangle2.relocate(windowLength - 200, 0);
        rectangle2.setFill(mainColor);
        rectangle2.setStrokeWidth(5.0);
        rectangle2.setStroke(Color.LIGHTGREY);

        // ----PROFILE BUTTON----
        Button accountProfile = new Button("Account");
        accountProfile.setFont(new Font("SimSun", 15));
        accountProfile.setShape(new Circle(10));
        accountProfile.setStyle(
                "-fx-background-radius: 5em; " +
                        "-fx-min-width: 75px; " +
                        "-fx-min-height: 75px; " +
                        "-fx-background-color: #F08080; ");
        accountProfile.relocate(60, 50);
        accountProfile.setMaxSize(3, 3);
        // when profile clicked, go to account page
        accountProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.setPage(new AccountPage());
            }
        });

        // ----VIEW ORDERS BUTTON----
        Button orders = new Button("My Orders");
        orders.relocate(50, 150);
        orders.setStyle("-fx-background-color: #F08080");
        orders.setFont(new Font("SimSun", 15));
        orders.setMinWidth(100);
        orders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.setPage(new OrderPage());
            }
        });

        // ----BUTTONS TO SORT ITEMS----
        // all
        Button all = new Button("All");
        all.relocate(50, 200);
        all.setStyle("-fx-background-color: #F08080");
        all.setFont(new Font("SimSun", 15));
        all.setMinWidth(100);
        all.setOnAction(actionEvent -> {
            setItemType(null);
        });
        // appetizers
        Button appetizers = new Button("Appetizers");
        appetizers.relocate(50, 240);
        appetizers.setStyle("-fx-background-color: #F08080");
        appetizers.setFont(new Font("SimSun", 15));
        appetizers.setMinWidth(100);
        appetizers.setOnAction(actionEvent -> {
            setItemType(Item.Type.appetizer);
        });
        // dishes
        Button dishes = new Button("Dishes");
        dishes.relocate(50, 280);
        dishes.setStyle("-fx-background-color: #F08080");
        dishes.setFont(new Font("SimSun", 15));
        dishes.setMinWidth(100);
        dishes.setOnAction(actionEvent -> {
            setItemType(Item.Type.dish);
        });
        // beverages
        Button beverages = new Button("Beverages");
        beverages.relocate(50, 320);
        beverages.setStyle("-fx-background-color: #F08080");
        beverages.setFont(new Font("SimSun", 15));
        beverages.setMinWidth(100);
        beverages.setOnAction(actionEvent -> {
            setItemType(Item.Type.beverage);
        });

        // ----COMBO BOX FOR FILTER MENU----
        ObservableList<String> options = FXCollections.observableArrayList(
                "Price $$$ -> $",
                "Price $ -> $$$",
                "Name A -> Z",
                "Name Z -> A");
        ComboBox<String> filterList = new ComboBox<String>(options);
        filterList.relocate(40, 380);
        filterList.setStyle("-fx-background-color: #F08080");
        filterList.setValue("Name A -> Z");
        filterList.setOnAction(actionEvent -> {
            // Changes the filter based off the combo box value
            // Also updates the menu
            if (filterList.getValue().equals("Price $$$ -> $")) {
                filter = MenuManager.priceSort.reversed();
            } else if (filterList.getValue().equals("Price $ -> $$$")) {
                filter = MenuManager.priceSort;
            } else if (filterList.getValue().equals("Name A -> Z")) {
                filter = MenuManager.nameSort;
            } else {
                filter = MenuManager.nameSort.reversed();
            }
            update();
        });

        // ----TEXT FIELD FOR SEARCH----
        Text searchLabel = new Text(230, 70, "Search:");
        searchLabel.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 20));
        searchLabel.setFill(Color.BLACK);

        TextField search = new TextField();
        search.setPrefColumnCount(20);
        search.relocate(310, 50);
        search.setStyle("-fx-background-color: #D3D3D3");
        search.setPromptText("Search for Item");
        search.setAlignment(Pos.CENTER);
        search.setPrefHeight(30);

        Button searchEnter = new Button("Enter");
        searchEnter.relocate(550, 50);
        searchEnter.setStyle("-fx-background-color: #faef95; ");
        searchEnter.setFont(new Font("SimSun", 15));
        searchEnter.setMinWidth(80);
        searchEnter.setMinHeight(30);
        // when enter is pressed, update menu items to show item with name
        searchEnter.setOnAction(event -> {
            // display the results that match search
            List<Item> itemList = menuManager.getItemsByName(search.getText(), filter);
            displayItems(itemList);
        });

        // ----ITEMS ON MENU----
        // create scroll pane for all the items
        itemSection = new ScrollPane();
        itemSection.relocate(230, 100);
        itemSection.setStyle("-fx-background-color: #FFFFFF");
        itemSection.setPrefWidth(windowLength - 450);
        itemSection.setPrefHeight(windowHeight - 115);
        itemSection.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        itemSection.setPannable(true);

        // ----RIGHT SIDE FOR CUSTOMER----
        // ----TEXTS FOR RIGHT SIDE----
        Text yummyItems1 = new Text(windowLength - 160, 70, "Yummy Items");
        yummyItems1.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 20));
        yummyItems1.setFill(Color.BLACK);
        Text yummyItems2 = new Text(windowLength - 160, 100, "in your cart!");
        yummyItems2.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 20));
        yummyItems2.setFill(Color.BLACK);

        // ----CART ITEMS----
        cartSection = new ScrollPane();
        cartSection.relocate(windowLength - 196, 140);
        cartSection.setStyle("-fx-background-color: #FFFFFF");
        cartSection.setPrefWidth(196);
        cartSection.setPrefHeight(windowHeight - 200);
        cartSection.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        cartSection.setPannable(true);

        // ----CHECKOUT BUTTON----
        Button checkout = new Button("Checkout");
        checkout.relocate(windowLength - 150, windowHeight - 50);
        checkout.setStyle("-fx-background-color: #F08080");
        checkout.setFont(new Font("SimSun", 15));
        checkout.setMinWidth(100);

        checkout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.setPage(new Checkout());
            }
        });

        // ----TOTAL PRICE----
        totalPrice = new Label("Total: ");
        totalPrice.relocate(windowLength - 150, windowHeight - 100);
        totalPrice.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 15));

        // ----RIGHT SIDE FOR RESTAURANT OWNER----
        Popup addItemPopup = new Popup();
        ItemEditPane editPane = new ItemEditPane(new Item(), actionEvent -> addItemPopup.hide(), true);

        addItemPopup.getContent().add(editPane);

        // ----END OF POPUP----

        // ----ADD ITEM FOR OWNER----
        Button addItemOwner = new Button("Add Item");
        addItemOwner.relocate(windowLength - 195, 240);
        addItemOwner.setStyle("-fx-background-color: #F08080");
        addItemOwner.setFont(new Font("SimSun", 15));
        addItemOwner.setMinWidth(190);
        addItemOwner.setOnAction(actionEvent -> {
            if (!addItemPopup.isShowing()) {
                editPane.setItem(new Item());
                addItemPopup.show(Main.getStage());
            }
        });

        // ----POPUP FOR REMOVING ITEM----
        Popup removeItemPopup = new Popup();

        Rectangle removeItemBackground = new Rectangle();
        removeItemBackground.setX(0.0f);
        removeItemBackground.setY(0.0f);
        removeItemBackground.setWidth(500);
        removeItemBackground.setHeight(400);
        removeItemBackground.setArcWidth(10.0);
        removeItemBackground.setArcHeight(10.0);
        removeItemBackground.setFill(Color.WHITE);
        removeItemBackground.setStrokeWidth(5.0);
        removeItemBackground.setStroke(Color.LIGHTGREY);

        Text removeItemText = new Text("Please enter name of item");
        removeItemText.relocate(110, 100);
        removeItemText.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 20));
        removeItemText.setFill(Color.BLACK);

        Text removeItemText2 = new Text("that you want to remove:");
        removeItemText2.relocate(130, 130);
        removeItemText2.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 20));
        removeItemText2.setFill(Color.BLACK);

        TextField itemToRemove = new TextField();
        itemToRemove.setPrefColumnCount(15);
        itemToRemove.relocate(100, 200);
        itemToRemove.setStyle("-fx-background-color: #D3D3D3");
        itemToRemove.setPromptText("Item Name");
        itemToRemove.setAlignment(Pos.CENTER);
        itemToRemove.setPrefHeight(30);
        itemToRemove.setPrefColumnCount(25);

        Button closeRemovePopup = new Button("Close");
        closeRemovePopup.relocate(10, 10);
        closeRemovePopup.setMinHeight(30);
        closeRemovePopup.setMinWidth(70);
        closeRemovePopup.setFont(new Font("SimSun", 15));
        closeRemovePopup.setTextAlignment(TextAlignment.CENTER);
        closeRemovePopup.setStyle("-fx-background-color: #F08080; ");
        closeRemovePopup.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                removeItemPopup.hide();
            }
        });

        Button confirmRemove = new Button("Confirm");
        confirmRemove.relocate(170, 300);
        confirmRemove.setPrefHeight(50.0);
        confirmRemove.setPrefWidth(150.0);
        confirmRemove.setFont(new Font("SimSun", 20));
        confirmRemove.setStyle("-fx-background-color: #faef95; ");
        confirmRemove.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {

                // actually remove item here
                String save = itemToRemove.getText();
                menuManager.delete(save);
                removeItemPopup.hide();
            }
        });

        removeItemPopup.getContent().addAll(removeItemBackground,
                removeItemText, removeItemText2, itemToRemove,
                confirmRemove, closeRemovePopup);

        // ----END OF REMOVE ITEM POPUP----

        // ----REMOVE ITEM FOR OWNER----

        Button removeItemOwner = new Button("Remove Item");
        removeItemOwner.relocate(windowLength - 195, 290);
        removeItemOwner.setStyle("-fx-background-color: #F08080");
        removeItemOwner.setFont(new Font("SimSun", 15));
        removeItemOwner.setMinWidth(190);
        removeItemOwner.setOnAction(actionEvent -> {
            if (!removeItemPopup.isShowing()) {
                removeItemPopup.show(Main.getStage());
            }
        });

        // ----ADD TO PANE----
        rootPane.getChildren().addAll(rectangle, rectangle1, rectangle2);
        rootPane.getChildren().addAll(accountProfile, searchLabel, search,
                title, orders, all, appetizers, dishes, beverages,
                filterList, itemSection, searchEnter);

        // Owner account conditional rendering
        if (state.getAccountManager().getCurrentAccount().isOwner()) {
            rootPane.getChildren().addAll(addItemOwner, removeItemOwner);
        } else {
            rootPane.getChildren().addAll(yummyItems1, yummyItems2, checkout, cartSection, totalPrice);
        }

        return scene;
    }

    public String getTitle() {
        return "Menu Page!";
    }

    public void update() {
        createItems();
        createCart();

        // Set total label since cart may have changed.
        double total = accountManager.getCurrentAccount().getCart().getTotal();
        totalPrice.setText("Total: $" + total);
    }

    public void remove(String name) {
        menuManager.delete(name);
    }

    private void createItems() {
        // create a new list of Item components
        List<Item> itemList = menuManager.getItemsByType(itemType, filter);
        displayItems(itemList);
    }

    // Create an Item component for each Item
    private void displayItems(List<Item> itemList) {
        items = new TilePane();
        items.setHgap(60);
        items.setVgap(15);
        items.setPrefColumns(4);
        items.setPadding(new Insets(10, 10, 10, 10));
        // add items
        items.setTileAlignment(Pos.CENTER_LEFT);

        // remove all Item Panes
        items.getChildren().removeAll();

        itemList.forEach(item -> {
            items.getChildren().add(new ItemPane(item));
        });

        // add items to scrollpane
        itemSection.setContent(items);
    }

    private void createCart() {
        TilePane cartPane = new TilePane();
        cartPane.setVgap(15);
        cartPane.setPrefColumns(1);
        cartPane.setPadding(new Insets(10, 10, 10, 10));
        cartPane.setTileAlignment(Pos.CENTER_LEFT);

        List<Item> itemList = accountManager.getCurrentAccount().getCart().getItemList();

        itemList.forEach(item -> {
            cartPane.getChildren().add(new CartItemPane(item));
        });

        cartSection.setContent(cartPane);
    }

    // Changes itemSection, refreshes item list if it is changed
    private void setItemType(Item.Type newType) {
        boolean needsChange = newType != itemType;
        itemType = newType;
        if (needsChange) {
            createItems();
        }
    }
}
