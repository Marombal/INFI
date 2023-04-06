module t.test {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens t.Logic to javafx.fxml;
    exports t.Logic;
}