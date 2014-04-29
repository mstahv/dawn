package com.vaadin.theme.valo.test;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

@Theme("valo")
public class TestUI extends UI {

    @Override
    protected void init(VaadinRequest request) {

        HorizontalSplitPanel sp = new HorizontalSplitPanel();
        setContent(sp);

        final HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        sp.addComponent(layout);

        // layout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);

        Label label = new Label(
                "Me non paenitet nullum festiviorem excogitasse ad hoc.");
        label.setWidth("200px");
        layout.addComponent(label);

        Button button = new Button("Button");
        layout.addComponent(button);
        layout.setComponentAlignment(button, Alignment.MIDDLE_CENTER);

        TextField tf = new TextField("TextField");
        layout.addComponent(tf);
        layout.setComponentAlignment(tf, Alignment.BOTTOM_RIGHT);

    }
}
