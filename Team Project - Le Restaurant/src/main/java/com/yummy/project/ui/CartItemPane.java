package com.yummy.project.ui;

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

// Pane for items in cart
public class CartItemPane extends BorderPane {
    private State state = State.getInstance();
    private AccountManager accountManager = state.getAccountManager();

    private int itemLength = 150, itemHeight = 75;
    private Item item;

    public CartItemPane(Item _item) {
        this.item = _item;

        // ----MAIN COLOR----
        Color mainColor = Color.MISTYROSE;

        Text itemName = new Text(item.getName());
        itemName.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 15));
        itemName.setFill(Color.BLACK);
        itemName.setTextAlignment(TextAlignment.CENTER);
        itemName.relocate(5, 5);

        Text itemPrice = new Text("$" + item.getPrice().toString());
        itemPrice.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 15));
        itemPrice.setFill(Color.BLACK);
        itemPrice.setTextAlignment(TextAlignment.CENTER);
        itemPrice.relocate(5, 20);

        Pane rootPane = new Pane();

        Rectangle background = new Rectangle(itemLength, itemHeight);
        background.setFill(Color.WHITE);
        background.setStrokeWidth(5.0);
        background.setStroke(Color.LIGHTGREY);

        Rectangle rectangle = new Rectangle(itemLength, itemHeight / 3 * 2);
        rectangle.setFill(mainColor);
        rectangle.setStrokeWidth(5.0);
        rectangle.setStroke(Color.LIGHTGREY);

        Button remove = new Button("-");
        remove.relocate(itemLength - 34, 0);
        remove.setStyle("-fx-background-color: #F08080");
        remove.setFont(new Font("SimSun", 20));
        remove.setOnAction(actionEvent -> {
            accountManager.getCurrentAccount().getCart().removeItem(this.item);
        });

        rootPane.getChildren().addAll(rectangle, remove, itemName, itemPrice);

        this.setTop(rootPane);
    }
}
