package com.yummy.project;

import com.yummy.project.state.Listener;
import com.yummy.project.state.State;
import com.yummy.project.ui.FrontPage;
import com.yummy.project.ui.Page;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage stage;
    private static Page currentPage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Subscribe to State changes
        State.getInstance().addListener(new ChangeListener());

        // Setup JavaFX stage with FrontPage.
        stage = primaryStage;
        Page frontPage = new FrontPage();//change back to front page when done
        setPage(frontPage);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    // Allow other UI classes to change scenes
    public static void setPage(Page page) {
        currentPage = page; // Set new page as current page

        // Set stage to display page's scene and title
        stage.setScene(page.getScene());
        stage.setTitle(page.getTitle());

        // Call the page's update method so that it can display correct data.
        currentPage.update();
    }

    public static class ChangeListener implements Listener {
        // When state is changed, notify the current page by calling
        // its update method
        public void onChanged() {
            currentPage.update();
        }
    }
}
