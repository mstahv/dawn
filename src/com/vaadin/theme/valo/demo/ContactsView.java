package com.vaadin.theme.valo.demo;

import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class ContactsView extends HorizontalSplitPanel implements View,
        ValueChangeListener {

    private static final long serialVersionUID = 1L;

    public static final String NAME = "Contacts";

    private static final String PROPERTY_FIRST_NAME = "First Name";
    private static final String PROPERTY_LAST_NAME = "Last Name";
    private static final String PROPERTY_COMPANY = "Company";

    private Table contactList;

    public ContactsView() {
        addStyleName(NAME.toLowerCase());
        setSplitPosition(300, Unit.PIXELS);

        VerticalLayout sidebar = new VerticalLayout();
        sidebar.setSizeFull();
        setFirstComponent(sidebar);

        TextField search = new TextField();
        search.setWidth("100%");
        search.setInputPrompt("Search contacts...");
        search.setIcon(FontAwesome.SEARCH);
        search.addStyleName("inline-icon");
        sidebar.addComponent(search);

        contactList = new Table();
        contactList.setSizeFull();
        sidebar.addComponent(contactList);
        sidebar.setExpandRatio(contactList, 1);
        contactList.addContainerProperty(PROPERTY_FIRST_NAME, String.class,
                null);
        contactList
                .addContainerProperty(PROPERTY_LAST_NAME, String.class, null);
        contactList.addContainerProperty(PROPERTY_COMPANY, String.class, null);
        contactList.setSelectable(true);

        generateData();

        contactList.setVisibleColumns(new Object[] { PROPERTY_FIRST_NAME,
                PROPERTY_LAST_NAME });

        contactList.addValueChangeListener(this);
        contactList.select(0);
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

    private void generateData() {
        for (int i = 0; i < 100; i++) {
            contactList.addItem(
                    new Object[] { Generator.randomFirstName(),
                            Generator.randomLastName(),
                            Generator.randomCompanyName() }, i);
        }
    }

    @Override
    public void valueChange(ValueChangeEvent event) {
        setSecondComponent(createContactCard(contactList.getItem(event
                .getProperty().getValue())));
    }

    private VerticalLayout createContactCard(Item item) {
        VerticalLayout root = new VerticalLayout();
        root.setSpacing(true);
        root.setMargin(true);

        if (item == null) {
            root.setSizeFull();
            Label hint = new Label("Select a contact from the list");
            hint.setSizeUndefined();
            root.addComponent(hint);
            root.setComponentAlignment(hint, Alignment.MIDDLE_CENTER);
        } else {

            Label name = new Label(item.getItemProperty(PROPERTY_FIRST_NAME)
                    .getValue()
                    + " "
                    + item.getItemProperty(PROPERTY_LAST_NAME).getValue());
            name.addStyleName("h1");
            root.addComponent(name);

            final FieldGroup fieldGroup = new FieldGroup();
            fieldGroup.setItemDataSource(item);

            final FormLayout form = new FormLayout();
            form.setMargin(false);
            form.addStyleName("light");
            root.addComponent(form);

            for (final Object propertyId : fieldGroup.getUnboundPropertyIds()) {
                form.addComponent(fieldGroup.buildAndBind(propertyId));
            }

            fieldGroup.setReadOnly(true);

            Button edit = new Button("Edit", new ClickListener() {
                @Override
                public void buttonClick(ClickEvent event) {
                    boolean readOnly = fieldGroup.isReadOnly();
                    if (readOnly) {
                        fieldGroup.setReadOnly(false);
                        form.removeStyleName("light");
                        event.getButton().setCaption("Save");
                        event.getButton().addStyleName("primary");
                    } else {
                        fieldGroup.setReadOnly(true);
                        form.addStyleName("light");
                        event.getButton().setCaption("Edit");
                        event.getButton().removeStyleName("primary");
                    }
                }
            });

            root.addComponent(edit);
        }

        return root;
    }

}
