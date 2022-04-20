module com.example.cse360teamproject {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.yummy.project.ui;
    opens com.yummy.project.ui to javafx.fxml;
    exports com.yummy.project.state;
    opens com.yummy.project.state to javafx.fxml;
    exports com.yummy.project.account;
    opens com.yummy.project.account to javafx.fxml;
    exports com.yummy.project.menu;
    opens com.yummy.project.menu to javafx.fxml;
    exports com.yummy.project;
    opens com.yummy.project to javafx.fxml;
}