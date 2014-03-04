package com.vaadin.theme.valo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("valo-demo")
@Title("Valo Theme")
public class ValoThemeUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = ValoThemeUI.class)
    public static class Servlet extends ThemeServlet {
    }

    CssLayout root = new CssLayout();
    CssLayout components = new CssLayout();
    CssLayout menu = new CssLayout();

    HashMap<Category, ArrayList<Link>> menuItems = new HashMap<Category, ArrayList<Link>>();

    enum Category {
        Common, Basic_Components, Component_Containers, Selection_Components, Inputs, Feedback, Other
    }

    @Override
    protected void init(VaadinRequest request) {
        setContent(root);
        root.addComponent(components);

        components.addComponents(components(1), components(2), components(3),
                components(4), components(5));
        components.addStyleName("components-root");

        root.addComponentAsFirst(buildMenu());

    }

    CssLayout buildMenu() {
        menu.setPrimaryStyleName("valo-menu");

        Label title = new Label("Valo Theme");
        title.setSizeUndefined();
        title.setPrimaryStyleName("valo-menu-title");
        menu.addComponent(title);

        final CheckBox enabled = new CheckBox("Enabled", true);
        enabled.setPrimaryStyleName("valo-menu-enabled");
        menu.addComponent(enabled);
        enabled.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                components.setEnabled(enabled.getValue());
            }
        });

        for (Entry<Category, ArrayList<Link>> entry : menuItems.entrySet()) {
            Label caption = new Label(entry.getKey().toString()
                    .replaceAll("_", " "));
            caption.setSizeUndefined();
            caption.setPrimaryStyleName("valo-menu-category");
            menu.addComponent(caption);

            for (Link link : entry.getValue()) {
                menu.addComponent(link);
                link.setPrimaryStyleName("valo-menu-item");
            }
        }

        return menu;
    }

    VerticalLayout components(int num) {
        menuItems.clear();

        final VerticalLayout root = new VerticalLayout();
        root.setWidth("33.333%");
        root.setMargin(true);
        root.addStyleName("components");
        root.addStyleName("color" + num);

        /**
         * Labels
         */
        addSection(root, "Labels", Category.Basic_Components);

        Label h1 = new Label("Main Title");
        h1.addStyleName("h1");
        root.addComponent(h1);

        Label large = new Label(
                "Large type for introductory text. Etiam at risus et justo dignissim congue. Donec congue lacinia dui, a porttitor lectus condimentum laoreet. Nunc eu.");
        large.addStyleName("large");
        root.addComponent(large);

        Label h2 = new Label("Subtitle");
        h2.addStyleName("h2");
        root.addComponent(h2);

        Label normal = new Label(
                "Normal type for plain text. Etiam at risus et justo dignissim congue. Donec congue lacinia dui, a porttitor lectus condimentum laoreet. Nunc eu.");
        root.addComponent(normal);

        Label h3 = new Label("Small Title");
        h3.addStyleName("h3");
        root.addComponent(h3);

        Label small = new Label(
                "Small type for additional text. Etiam at risus et justo dignissim congue. Donec congue lacinia dui, a porttitor lectus condimentum laoreet. Nunc eu.");
        small.addStyleName("small");
        root.addComponent(small);

        Label h4 = new Label("Section Title");
        h4.addStyleName("h4");
        root.addComponent(h4);

        normal = new Label(
                "Normal type for plain text. Etiam at risus et justo dignissim congue. Donec congue lacinia dui, a porttitor lectus condimentum laoreet. Nunc eu.");
        root.addComponent(normal);

        /**
         * Buttons
         */
        addSection(root, "Buttons", Category.Basic_Components);

        HorizontalLayout row = new HorizontalLayout();
        row.addStyleName("wrapping");
        row.setSpacing(true);
        root.addComponent(row);

        Button button = new Button("Normal");
        row.addComponent(button);

        button = new Button("Focused");
        button.addStyleName("focus");
        row.addComponent(button);

        button = new Button("Pressed");
        button.addStyleName("active");
        button.addStyleName("focus");
        row.addComponent(button);

        button = new Button("Disabled");
        button.setEnabled(false);
        row.addComponent(button);

        button = new Button("Custom");
        button.addStyleName("color2");
        row.addComponent(button);

        button = new Button("User Color");
        button.addStyleName("color3");
        row.addComponent(button);

        button = new Button("Themed");
        button.addStyleName("color4");
        row.addComponent(button);

        button = new Button("Alternate");
        button.addStyleName("color5");
        row.addComponent(button);

        button = new Button("Other");
        button.addStyleName("color6");
        row.addComponent(button);

        button = new Button("Small");
        button.addStyleName("small");
        row.addComponent(button);

        button = new Button("Large");
        button.addStyleName("large");
        row.addComponent(button);

        button = new Button("Icon");
        button.setIcon(FontAwesome.CAMERA);
        row.addComponent(button);

        button = new Button();
        button.setIcon(FontAwesome.MICROPHONE);
        button.addStyleName("icon");
        row.addComponent(button);

        button = new Button("Frameless");
        button.setIcon(FontAwesome.CHEVRON_RIGHT);
        button.addStyleName("frameless");
        row.addComponent(button);

        /**
         * Text fields
         */
        addSection(root, "Text Fields", Category.Basic_Components);

        row = new HorizontalLayout();
        row.addStyleName("wrapping");
        row.setSpacing(true);
        root.addComponent(row);

        TextField tf = new TextField("Normal");
        tf.setInputPrompt("First name");
        row.addComponent(tf);

        tf = new TextField("Focused");
        tf.setInputPrompt("Last name");
        tf.addStyleName("focused");
        row.addComponent(tf);

        tf = new TextField("Custom color");
        tf.setInputPrompt("Email");
        tf.addStyleName("color1");
        row.addComponent(tf);

        tf = new TextField("User Color");
        tf.setInputPrompt("Gender");
        tf.addStyleName("color2");
        row.addComponent(tf);

        tf = new TextField("Themed");
        tf.setInputPrompt("Age");
        tf.addStyleName("color3");
        row.addComponent(tf);

        tf = new TextField("Read-only");
        tf.setInputPrompt("Nationality");
        tf.setValue("Finnish");
        tf.setReadOnly(true);
        row.addComponent(tf);

        tf = new TextField("Small");
        tf.setValue("Field value");
        tf.addStyleName("small");
        row.addComponent(tf);

        tf = new TextField("Large");
        tf.setValue("Field value");
        tf.addStyleName("large");
        row.addComponent(tf);

        addSection(root, "SplitButton (custom)", Category.Other);

        row = new HorizontalLayout();
        row.addStyleName("wrapping");
        row.setSpacing(true);
        root.addComponent(row);

        SplitButton split = new SplitButton("Main Option");
        split.addMenuItem("Alternative Option", null);
        split.addMenuItem("Second Alternative Option", null);
        split.addMenuItem("Option Three", null);
        row.addComponent(split);

        return root;
    }

    void addSection(VerticalLayout root, String title, Category category) {
        String id = title.toLowerCase().replace(" ", "-");

        Label h1 = new Label(title);
        h1.addStyleName("h1");
        h1.setId("" + id);
        root.addComponent(h1);

        Link link = new Link(title, new ExternalResource("#" + id));

        ArrayList<Link> items = menuItems.get(category);
        if (items == null) {
            items = new ArrayList<Link>();
            menuItems.put(category, items);
        }
        items.add(link);
    }

}