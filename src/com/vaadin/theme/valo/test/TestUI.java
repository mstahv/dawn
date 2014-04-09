package com.vaadin.theme.valo.test;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class TestUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                TextField textField = new TextField("KEnttä");
                Window window = new Window();
                window.setContent(new VerticalLayout(textField));
                addWindow(window);
                textField.focus();
            }
        });
        layout.addComponent(button);
    }

}
