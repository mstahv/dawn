package com.vaadin.theme.valo.test;

import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class TestUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout root = new VerticalLayout();
        setContent(root);

        TextField textfield = new TextField();
        textfield.setIcon(new ThemeResource("../runo/icons/16/document.png"));
        root.addComponent(textfield);

        Table table = new Table();
        table.setIcon(new ThemeResource("../runo/icons/16/document.png"));
        root.addComponent(table);
        table.addContainerProperty("Foo", String.class, null);
        for (int i = 0; i < 1000; i++) {
            table.addItem(new Object[] { "Name" + i }, i);
        }

        textfield = new TextField();
        textfield.setIcon(new ThemeResource("../runo/icons/16/document.png"));
        root.addComponent(textfield);

        Label label = new Label("Label");
        label.setIcon(new ThemeResource("../runo/icons/16/document.png"));
        root.addComponent(label);
    }

}
