open module org.group.game {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires transitive com.almasb.fxgl.entity;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires transitive com.almasb.fxgl.all;
    requires com.almasb.fxgl.core;
    requires javafx.graphics;

    exports org.group1.GamePackage;
    exports org.group1.GamePackage.Factory;
    exports org.group1.GamePackage.Handlers;
    exports org.group1.GamePackage.Components.Player;
    exports org.group1.GamePackage.Components.Enemy;
    exports org.group1.GamePackage.Components.Projectiles;
    exports org.group1.GamePackage.Components.UI;
}
