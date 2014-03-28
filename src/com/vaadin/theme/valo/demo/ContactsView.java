package com.vaadin.theme.valo.demo;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ContactsView extends HorizontalSplitPanel implements View {

    private static final long serialVersionUID = 1L;

    public static final String NAME = "Contacts";

    public ContactsView() {
        setSplitPosition(20, Unit.PERCENTAGE);

        VerticalLayout sidebar = new VerticalLayout();
        sidebar.setSizeFull();
        setFirstComponent(sidebar);

        TextField search = new TextField();
        search.setWidth("100%");
        search.setInputPrompt("Search contacts...");
        sidebar.addComponent(search);

        Table contactList = new Table();
        contactList.setSizeFull();
        sidebar.addComponent(contactList);
        sidebar.setExpandRatio(contactList, 1);

    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

}
