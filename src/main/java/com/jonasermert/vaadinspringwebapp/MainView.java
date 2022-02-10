package com.jonasermert.vaadinspringwebapp;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    public MainView(){

        // add(new H1("Hello World"));
        var button = new Button("Click me");
        var textField = new TextField();

        add(textField, button);
        button.addClickListener(e -> {
            add(new Paragraph("Hello, " + textField.getValue()));
            textField.clear();
        });
    }
}
