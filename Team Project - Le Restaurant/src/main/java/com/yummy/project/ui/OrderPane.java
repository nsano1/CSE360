package com.yummy.project.ui;

import com.yummy.project.account.AccountManager;
import com.yummy.project.menu.Item;
import com.yummy.project.menu.Order;
import com.yummy.project.state.State;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.concurrent.atomic.AtomicReference;

// Shows details for a single order
public class OrderPane extends BorderPane {
    private Order order;
    private State state = State.getInstance();
    private AccountManager accountManager = state.getAccountManager();

    public OrderPane(Order _order, Double _total) {
        this.order = _order;

        Pane rootPane = new Pane();

        //-----DISPLAY OF ORDER----
        TextArea orderList = new TextArea();
        orderList.setPrefSize(350, 130);
        orderList.setMinWidth(350);
        orderList.setMaxWidth(350);
        orderList.setMinHeight(130);
        orderList.setEditable(false);
        orderList.setWrapText(true);
        orderList.setFont(new Font("SimSun", 17));
        orderList.appendText(String.format("%-15s %15s %n", "Item Name", "Price\n"));
        orderList.appendText("----------------------------------\n");

        // Add cart items to table view
        AtomicReference<Double> totalPriceDisplay = new AtomicReference<>((double) 0);
        ObservableList<Item> items = FXCollections.observableArrayList();
        _order.getItemList().forEach(item -> {
            items.add(item);
            orderList.appendText(String.format("%-15s %15s %n", item.getName(), item.getPrice()) +"\n");
            totalPriceDisplay.set(totalPriceDisplay.get() + item.getPrice());
        });

        orderList.appendText("\nTotal: ");
        orderList.appendText(String.format("%10s", totalPriceDisplay + "\n"));
        orderList.setScrollTop(0);

        // add items to orderList table
        //orderList.setItems(items);
        orderList.relocate(100, 250);

        //----VBOX FOR ALL ITEMS----
        VBox vbox = new VBox();
        vbox.getChildren().addAll(orderList);

        rootPane.getChildren().add(vbox);
        this.setTop(rootPane);
    }
}
