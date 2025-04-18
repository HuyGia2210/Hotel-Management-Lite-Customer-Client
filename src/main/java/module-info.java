module iuh.fit.hotelmanagementliteclient {
    requires iuh.fit;

    // JavaFX
    requires javafx.fxml;
    requires javafx.web;

    // JPA
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    // Khác
    requires static lombok;
    requires net.datafaker;
    requires com.dlsc.gemsfx;
    requires com.dlsc.unitfx;
    requires com.calendarfx.view;


    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires java.rmi;
    requires java.naming;
    requires org.checkerframework.checker.qual;
    requires itextpdf;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;

    opens iuh.fit.hotelmanagementliteclient to javafx.fxml;
    opens iuh.fit.hotelmanagementliteclient.controller to javafx.fxml;
    exports iuh.fit.hotelmanagementliteclient;
    exports iuh.fit.hotelmanagementliteclient.controller;
}