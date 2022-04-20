package com.yummy.project.ui;

import com.yummy.project.Main;
import com.yummy.project.account.AccountManager;
import com.yummy.project.state.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class FrontPage implements Page {

    private AccountManager accountManager = State.getInstance().getAccountManager();

    private TextField textFieldUsername;
    private TextField textFieldPassword;

    public Scene getScene() {
        //pane to put all items on
        Pane rootPane = new Pane();

        int windowLength = 1100;
        int windowHeight = 630;

        Scene scene = new Scene(rootPane, windowLength, windowHeight);
        scene.setFill(Color.WHITE);

        //----MAIN COLOR----
        Color mainColor = Color.MISTYROSE;
        Rectangle rectangle = new Rectangle(windowLength, windowHeight);
		rectangle.setFill(Color.WHITE);
		rectangle.setStrokeWidth(5.0);
		rectangle.setStroke(Color.LIGHTGREY);

        //----CIRCLES FOR BACKGROUND SHAPE----
        //1
        Circle circle1 = new Circle(550, 400, 120);
        circle1.setStroke(mainColor);
        circle1.setStrokeWidth(1);
        circle1.setFill(mainColor);
        //2
        Circle circle2 = new Circle(540, 200, 150);
        circle2.setStroke(mainColor);
        circle2.setStrokeWidth(1);
        circle2.setFill(mainColor);
        //3
        Circle circle3 = new Circle(380, 260, 110);
        circle3.setStroke(mainColor);
        circle3.setStrokeWidth(1);
        circle3.setFill(mainColor);
        //4
        Circle circle4 = new Circle(750, 310, 120);
        circle4.setStroke(mainColor);
        circle4.setStrokeWidth(1);
        circle4.setFill(mainColor);
        //5
        Circle circle5 = new Circle(410, 380, 90);
        circle5.setStroke(mainColor);
        circle5.setStrokeWidth(1);
        circle5.setFill(mainColor);
        //6
        Circle circle6 = new Circle(700, 440, 80);
        circle6.setStroke(mainColor);
        circle6.setStrokeWidth(1);
        circle6.setFill(mainColor);
        //7
        Circle circle7 = new Circle(690, 180, 80);
        circle7.setStroke(mainColor);
        circle7.setStrokeWidth(1);
        circle7.setFill(mainColor);


        //----TITLE----
        Text title = new Text(windowLength / 2 - 270,150, "LE' RESTAURANT");
        title.setFont(Font.font ("SimSun", FontWeight.EXTRA_BOLD, 80));
        title.setFill(Color.BLACK);

        Text loginTo = new Text(windowLength / 2 - 220,190, "Log in/Sign up to your Le' Restaurant account!");
        loginTo.setFont(Font.font ("SimSun", 20));
        loginTo.setFill(Color.BLACK);


        //----TEXT BOXES AND LABELS----
        Label username = new Label("Username:");
        username.relocate(windowLength / 2 - 170,300);
        username.setFont(new Font("SimSun", 20));

        Label password = new Label("Password:");
        password.relocate(windowLength / 2 - 170,350);
        password.setFont(new Font("SimSun", 20));

        textFieldUsername = new TextField ();
        textFieldUsername.setPrefColumnCount(15);
        textFieldUsername.relocate(windowLength / 2 - 80,300);
        textFieldUsername.setStyle("-fx-background-color: #D3D3D3");
        textFieldUsername.setPromptText("Enter your username here");

        textFieldPassword = new TextField ();
        textFieldPassword.setPrefColumnCount(15);
        textFieldPassword.relocate(windowLength / 2 - 80,350);
        textFieldPassword.setStyle("-fx-background-color: #D3D3D3");
        textFieldPassword.setPromptText("Enter your password here");


        //----LOGIN BUTTON----
        Button logIn = new Button("Log in");
        logIn.relocate(windowLength / 2 - 70, 400);
        logIn.setStyle("-fx-background-color: #F08080");
        logIn.setFont(new Font("SimSun", 15));
        
        //----Sign Up Button----
        Button signUp = new Button("Sign Up");
        signUp.relocate(windowLength/ 2 + 20, 400);
        signUp.setStyle("-fx-background-color: #F08080");
        signUp.setFont(new Font("SimSun", 15));
    
        // Change to CreateAccountPage when clicked.
        signUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.setPage(new CreateAccountPage());
            }
        });
        // Change to MenuPage when clicked.
        /*TODO: need to incorporate some type of authentication handler, before allowing
        login*/
        logIn.setOnAction(actionEvent -> loginPressed());

        //----ADDING TO PANE----
        rootPane.getChildren().addAll(rectangle, circle1, circle2, circle3, circle4, circle5, circle6, circle7);
        rootPane.getChildren().addAll(title, loginTo);
        rootPane.getChildren().addAll(username, password, textFieldUsername, textFieldPassword, logIn, signUp);

        return scene;
    }

    public String getTitle() {
        return "Le Restaurant!";
    }

    public void update() {
        // Switch to MenuPage when logged in
        if (accountManager.getLoggedIn()) {
            Main.setPage(new MenuPage());
        }
    }

    private void loginPressed() {
        accountManager.login(textFieldUsername.getText(),
                textFieldPassword.getText());
    }
}
