package com.jonasermert.vaadinspringwebapp;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    private PersonRepository repository;


    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private EmailField email = new EmailField("First Name");
    private Binder<Person> binder = new Binder<>(Person.class);
    private Grid<Person> grid = new Grid<>(Person.class);


    public MainView(PersonRepository repository){
        this.repository = repository;

        grid.setColumns("firstName", "lastName", "email");
        add(getForm(), grid);
        refreshGrid();

        /*
        // add(new H1("Hello World"));
        var button = new Button("Click me");
        var textField = new TextField();

        add(textField, button);
        button.addClickListener(e -> {
            add(new Paragraph("Hello, " + textField.getValue()));
            textField.clear();
        });
        */
    }

    private Component getForm() {
        var layout = new HorizontalLayout();
        layout.setAlignItems(Alignment.BASELINE);

        var addButton = new Button("Add");
        addButton.addClickShortcut(Key.ENTER);
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layout.add(firstName, lastName, email, addButton);

        binder.bindInstanceFields(this);

        addButton.addClickListener(click -> {
            try {
                var person = new Person();
                binder.writeBean(person);
                repository.save(person);
                binder.readBean(new Person());
                refreshGrid();
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        });

        return layout;
    }

    private void refreshGrid() {
        grid.setItems(repository.findAll());
    }
}
