package com.yummy.project.ui;

import com.yummy.project.Main;
import com.yummy.project.account.AccountManager;
import com.yummy.project.menu.Order;
import com.yummy.project.menu.OrderManager;
import com.yummy.project.state.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.text.FontWeight;
import javafx.scene.control.ScrollPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.util.List;

public class OrderPage implements Page {
    private State state = State.getInstance();
    private AccountManager accountManager = state.getAccountManager();
    private OrderManager orderManager = state.getOrderManager();

    public Scene getScene() {

        // canvas to put the items on
        StackPane layout = new StackPane();
        // creates a new popup
        Popup popup = new Popup();

        // ----CANCEL BUTTON----
        Button cancel = new Button("Cancel");
        cancel.relocate(800.0, 340.0);
        cancel.setMaxHeight(200.0);
        cancel.setMaxWidth(200.0);
        cancel.setStyle("-fx-background-color: #F08080");
        cancel.setFont(new Font("SimSun", 20));
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!popup.isShowing()) {
                    Main.getStage().show();
                    popup.show(Main.getStage());
                } else {
                    popup.hide();

                }
            }
        });
        // ---- YES BUTTON ----
        Button yes = new Button("YES");
        yes.relocate(70, 200.0);
        yes.setPrefHeight(50.0);
        yes.setPrefWidth(150.0);
        yes.setFont(new Font("SimSun", 20));
        yes.setStyle("-fx-background-color: #faef95; ");
        yes.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Main.setPage(new MenuPage());

                // Most current order is removed by the order manager if some exist
                int size = orderManager.getOrdersByUser(accountManager.getCurrentAccount().getId()).size();
                if (size > 0) {
                    orderManager.removeOrder(
                            orderManager.getOrdersByUser(accountManager.getCurrentAccount().getId()).get(size - 1));
                }
                popup.hide();
            }
        });

        // ----no BUTTON----
        Button no = new Button("NO");
        no.relocate(300.0, 200.0);
        no.setPrefHeight(50.0);
        no.setPrefWidth(150.0);
        no.setFont(new Font("SimSun", 20));
        no.setStyle("-fx-background-color: #F08080; ");
        no.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                Main.setPage(new OrderPage());
                popup.hide();
            }
        });

        // ----RECTANGLE 1----
        Rectangle cancelRec = new Rectangle();
        cancelRec.setX(0.0f);
        cancelRec.setY(0.0f);
        cancelRec.setWidth(500.0f);
        cancelRec.setHeight(300.0f);
        cancelRec.setArcWidth(10.0);
        cancelRec.setArcHeight(10.0);
        cancelRec.setFill(Color.WHITE);
        cancelRec.setStrokeWidth(5.0);
        cancelRec.setStroke(Color.LIGHTGREY);

        // --- Order Header ---
        Text canTitle = new Text(100, 100, "Cancel Order");
        canTitle.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 50));
        canTitle.setFill(Color.BLACK);
        // --- confirmation1 Header ---
        Text confirmation = new Text(60, 150, "Are you sure you want to cancel?");
        confirmation.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 25));
        confirmation.setFill(Color.BLACK);

        // ----BACK BUTTON----
        Button back = new Button("Back");
        back.relocate(15.0, 10.0);
        back.setMaxHeight(200.0);
        back.setMaxWidth(200.0);
        back.setFont(new Font("SimSun", 20));
        back.setStyle("-fx-background-color: #F08080; ");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.setPage(new MenuPage());
                popup.hide();
            }
        });

        // ----RECTANGLE 1----
        Rectangle rec = new Rectangle();
        rec.setX(130.0f);
        rec.setY(2.0f);
        rec.setWidth(830.0f);
        rec.setHeight(625.0f);
        rec.setArcWidth(10.0);
        rec.setArcHeight(10.0);
        rec.setFill(Color.WHITE);
        rec.setStrokeWidth(5.0);
        rec.setStroke(Color.LIGHTGREY);

        // ----RECTANGLE 2----
        Rectangle tRec = new Rectangle();
        tRec.setX(130.0f);
        tRec.setY(2.0f);
        tRec.setWidth(830.0f);
        tRec.setHeight(100.0f);
        tRec.setArcWidth(10.0);
        tRec.setArcHeight(10.0);
        tRec.setFill(Color.WHITE);
        tRec.setStrokeWidth(5.0);
        tRec.setStroke(Color.LIGHTGREY);

        // --- Title Header ---
        Text title = new Text(315, 75, "Yummy Orders");
        title.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 75));
        title.setFill(Color.BLACK);

        // ----RECTANGLE 3----
        Rectangle cRec = new Rectangle();
        cRec.setX(180.0f);
        cRec.setY(180.0f);
        cRec.setWidth(730.0f);
        cRec.setHeight(150.0f);
        cRec.setArcWidth(10.0);
        cRec.setArcHeight(10.0);
        cRec.setFill(Color.WHITE);
        cRec.setStrokeWidth(11.0);
        cRec.setStroke(Color.LIGHTGREY);

        // --- Scroll rec ---
        // Rectangle scRec = new Rectangle(730, 140, Color.WHITE);
        // scRec.setArcWidth(10.0);
        // scRec.setArcHeight(10.0);
        TilePane curOrders = new TilePane();
        curOrders.setHgap(20);
        curOrders.setVgap(20);
        curOrders.setPrefColumns(2);
        curOrders.setPadding(new Insets(1, 1, 1, 1));
        curOrders.setTileAlignment(Pos.CENTER_LEFT);

        ScrollPane curScroll = new ScrollPane();
        curScroll.setPrefSize(730, 150);
        curScroll.relocate(180.0, 180.0);

        // --- Current Header ---
        Text curTitle = new Text(170, 170, "Current Orders");
        curTitle.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 40));
        curTitle.setFill(Color.BLACK);

        // ----RECTANGLE 3----
        Rectangle pRec = new Rectangle();
        pRec.setX(180.0f);
        pRec.setY(430.0f);
        pRec.setWidth(730.0f);
        pRec.setHeight(150.0f);
        pRec.setArcWidth(10.0);
        pRec.setArcHeight(10.0);
        pRec.setFill(Color.WHITE);
        pRec.setStrokeWidth(11.0);
        pRec.setStroke(Color.LIGHTGREY);

        // // --- Scroll rec ---
        // Rectangle pasRec = new Rectangle(730, 150, Color.WHITE);
        // pasRec.setArcWidth(10.0);
        // pasRec.setArcHeight(10.0);

        TilePane pastOrders = new TilePane();
        pastOrders.setHgap(20);
        pastOrders.setVgap(20);
        pastOrders.setPrefColumns(2);
        pastOrders.setPadding(new Insets(1, 1, 1, 1));
        pastOrders.setTileAlignment(Pos.CENTER_LEFT);

        ScrollPane pasScroll = new ScrollPane();
        pasScroll.setPrefSize(730, 150);
        pasScroll.relocate(180, 430.0);

        // Add account orders to scroll areas
        List<Order> accountOrders = orderManager.getOrdersByUser(accountManager.getCurrentAccount().getId());
        accountOrders.forEach(order -> {
            if (order.isCurrent()) {
                curOrders.getChildren().add(new OrderPane(order, accountManager.getCurrentAccount().getCart().getTotal()));
            } else {
                pastOrders.getChildren().add(new OrderPane(order, accountManager.getCurrentAccount().getCart().getTotal()));
            }
        });

        // Set contents of scroll views
        curScroll.setContent(curOrders);
        pasScroll.setContent(pastOrders);

        // --- Previous Header ---
        Text prevTitle = new Text(170, 420, "Past Orders");
        prevTitle.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 40));
        prevTitle.setFill(Color.BLACK);

        popup.getContent().add(cancelRec);
        popup.getContent().add(canTitle);
        popup.getContent().add(confirmation);
        popup.getContent().add(yes);
        popup.getContent().add(no);

        // setting the Scene
        Group root = new Group(layout, back, rec, tRec, title, cRec, 
                               curScroll, curTitle, pRec, pasScroll, prevTitle,
                               cancel);
        Scene scene = new Scene(root, 1100, 630, Color.MISTYROSE);

        return scene;
    }

    public String getTitle() {
        return "Orders!";
    }

    public void update() {

    }
}
