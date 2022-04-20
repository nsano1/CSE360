package com.yummy.project.ui;

import com.yummy.project.Main;
import com.yummy.project.account.AccountManager;
import com.yummy.project.menu.Item;
import com.yummy.project.state.State;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.Random;

public class Checkout implements Page {
	private State state = State.getInstance();
	private AccountManager accountManager = state.getAccountManager();

	public Scene getScene() {
		Pane rootPane = new Pane();

		int windowLength = 1100;
		int windowHeight = 630;

		Scene scene = new Scene(rootPane, windowLength, windowHeight);
		scene.setFill(Color.WHITE);

		// ----MAIN COLOR ----
		Color mainColor = Color.MISTYROSE;

		// ----PAGE TITLE ----
		Text title = new Text(windowLength / 2 - 360, 100, "Checkout");
		title.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 50));
		title.setFill(Color.BLACK);

		// ----RECTANGLE FOR BACKGROUND----
		Rectangle rectangle = new Rectangle(windowLength, windowHeight);
		rectangle.setFill(Color.WHITE);
		rectangle.setStrokeWidth(5.0);
		rectangle.setStroke(Color.LIGHTGREY);

		Rectangle rectangle1 = new Rectangle(windowLength - 5, 145);
		rectangle1.relocate(5, 5);
		rectangle1.setFill(mainColor);
		rectangle1.setStrokeWidth(5.0);
		rectangle1.setStroke(Color.LIGHTGREY);

		// ----EDIT ORDERS----
		Button editOrder = new Button("Edit Order");
		editOrder.relocate(100, 180);
		editOrder.setStyle("-fx-background-color: #F08080");
		editOrder.setFont(new Font("SimSun", 20));

		/*
		 * TODO: figure out what to do, right now. right now it just
		 * links back to the menu page
		 */
		editOrder.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				Main.setPage(new MenuPage());
			}
		});

		// -----DISPLAY OF ORDER----
		TableView orderList = new TableView();
		TableColumn nameOfItem = new TableColumn("Item Name");
		nameOfItem.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameOfItem.setMinWidth(300);
		TableColumn priceOfItem = new TableColumn("Price");
		priceOfItem.setCellValueFactory(new PropertyValueFactory<>("price"));
		priceOfItem.setMinWidth(100);
		orderList.getColumns().addAll(nameOfItem, priceOfItem);
		
		// ----TOTAL PRICE----
		double total = accountManager.getCurrentAccount().getCart().getTotal();
		Label totalPrice = new Label("Total: " + total);
		totalPrice.relocate(150, windowHeight - 50);
		totalPrice.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 20));

		// Add cart items to table view
		ObservableList<Item> items = FXCollections.observableArrayList();
		accountManager.getCurrentAccount().getCart().getItemList().forEach(item -> {
			items.add(item);
		});

		orderList.setItems(items);
		orderList.relocate(100, 250);
		orderList.setPrefWidth(400);
		orderList.setPrefHeight(300);

		// ----USER INFO----
		Button userInfo = new Button("User Info");
		userInfo.relocate(720, 300);
		userInfo.setStyle("-fx-background-color: #F08080");
		userInfo.setFont(new Font("SimSun", 20));
		userInfo.setMinWidth(170);
		userInfo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				Main.setPage(new AccountPage());
			}
		});

		// ----PAYMENT INFO----
		/*
		 * TODO: need to figure out what to do with payment info button
		 * currently just sending back to account page.
		 */
		Button paymentInfo = new Button("Payment Info");
		paymentInfo.relocate(720, 400);
		paymentInfo.setStyle("-fx-background-color: #F08080");
		paymentInfo.setFont(new Font("SimSun", 20));
		paymentInfo.setMinWidth(170);
		paymentInfo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				Main.setPage(new AccountPage());
			}
		});

		// ---EXPECTED WAIT----
		// int randomNumberOfMinutes = new Random().nextInt(10) * 5;
		int waitOfOrder = 0;
		for (Item i : accountManager.getCurrentAccount().getCart().getItemList()) {
			waitOfOrder += i.getCookTime();
		}
		int newWaitOrder = waitOfOrder;
		Text expectedWait = new Text(windowLength / 2 + 90, 180,
				"Expected wait: " + waitOfOrder + " minutes");
		expectedWait.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 20));
		expectedWait.setFill(Color.BLACK);

		// ----CONFIRM ORDER----
		Button confirmOrder = new Button("Confirm Order");
		confirmOrder.relocate(720, 500);
		confirmOrder.setStyle("-fx-background-color: #F08080");
		confirmOrder.setFont(new Font("SimSun", 20));
		confirmOrder.setMinWidth(170);
		confirmOrder.setOnAction(actionEvent -> {
			// Submit the order and switch to OrderPage
			accountManager.getCurrentAccount().submitOrder(newWaitOrder);
			Main.setPage(new OrderPage());
		});

		// --- COUPON ---
		Text coupon = new Text(windowLength / 2 + 90, 230, "Coupon code:");
		coupon.setFont(Font.font("SimSun", FontWeight.EXTRA_BOLD, 20));
		coupon.setFill(Color.BLACK);

		TextField couponCode = new TextField();
		couponCode.setPrefColumnCount(15);
		couponCode.relocate(windowLength / 2 + 90 + 130, 210);
		couponCode.setStyle("-fx-background-color: #D3D3D3");
		couponCode.setPromptText("Enter code here");
		couponCode.setAlignment(Pos.CENTER);
		couponCode.setPrefHeight(30);

		// ----BACK BUTTON----
		Button back = new Button("Back");
		back.relocate(20, 20);
		back.setStyle("-fx-background-color: #778899");
		back.setFont(new Font("SimSun", 15));
		// Go back to FrontPage when button is clicked
		back.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				Main.setPage(new MenuPage());
			}
		});

		rootPane.getChildren().addAll(rectangle, rectangle1, title, editOrder, userInfo, paymentInfo);
		rootPane.getChildren().addAll(orderList, confirmOrder, expectedWait, coupon, couponCode, totalPrice);
		return scene;
	}

	public String getTitle() {
		return "Checkout";
	}

	public void update() {

	}
}
