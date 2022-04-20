package com.yummy.project.ui;

import javafx.scene.Scene;

// Interface for each UI page, allows for each
// page to have the same "structure" so that pages can
// easily be switched
public interface Page {
    // Returns the JavaFX scene that the page generates
    public Scene getScene();

    // Returns the title of the window that should be set
    public String getTitle();

    // This method will be called whenever State is updated,
    // allowing the UI page to react to any changes.
    public void update();
}
