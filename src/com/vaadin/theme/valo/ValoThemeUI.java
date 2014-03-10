package com.vaadin.theme.valo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;

import javax.servlet.annotation.WebServlet;

import org.vaadin.risto.mockupcontainer.BaconDataSet;
import org.vaadin.risto.mockupcontainer.MockupContainer;
import org.vaadin.risto.mockupcontainer.MockupDataSet;
import org.vaadin.risto.mockupcontainer.MockupFactory;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.Action;
import com.vaadin.event.Action.Handler;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Slider;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
@Theme("valo-demo")
@Title("Valo Theme")
public class ValoThemeUI extends UI implements Handler {

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
        getPage().setTitle("Valo Theme");
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
        root.addStyleName("color-context" + num);

        /**
         * Labels
         */
        addSection(root, "Labels", Category.Basic_Components, null);

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
         * Loading incidator
         */
        HorizontalLayout row = addSection(
                root,
                "Loading Indicator",
                Category.Common,
                "You can test the loading indicator by pressing the buttons. The theme also provides a handy Sass mixin that you can use to include a spinner anywhere in your application.");

        Button loading = new Button("Loading…");
        loading.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                }
            }
        });
        row.addComponent(loading);

        Button delay = new Button("Task Delayed…");
        delay.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }
            }
        });
        row.addComponent(delay);

        Button wait = new Button("Please Wait…");
        wait.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                }
            }
        });
        row.addComponent(wait);

        /**
         * Notifications
         */

        row = addSection(
                root,
                "Notifications",
                Category.Common,
                "There are the built-in notification styles, but you can easily create more variations with the theme mixins.");

        Button notify = new Button("Humanized", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Humanized");
            }
        });
        notify.addStyleName("small");
        row.addComponent(notify);

        notify = new Button("w/ Description", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Humanized",
                        "A more informative message about what has happened.",
                        Type.HUMANIZED_MESSAGE);
            }
        });
        notify.addStyleName("small");
        row.addComponent(notify);

        notify = new Button("Tray", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Tray notification", null,
                        Type.TRAY_NOTIFICATION);
            }
        });
        notify.addStyleName("small");
        row.addComponent(notify);

        notify = new Button("w/ Description", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Tray notification",
                        "A more informative message about what has happened.",
                        Type.TRAY_NOTIFICATION);
            }
        });
        notify.addStyleName("small");
        row.addComponent(notify);

        notify = new Button("Warning", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Warning", Type.WARNING_MESSAGE);
            }
        });
        notify.addStyleName("small");
        row.addComponent(notify);

        notify = new Button("w/ Description", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Warning",
                        "A more informative message about what has happened.",
                        Type.WARNING_MESSAGE);
            }
        });
        notify.addStyleName("small");
        row.addComponent(notify);

        notify = new Button("Error", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Error", Type.ERROR_MESSAGE);
            }
        });
        notify.addStyleName("small");
        row.addComponent(notify);

        notify = new Button("w/ Description", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification
                        .show("Error",
                                "A more informative message about what has happened. Duis aliquet egestas purus in blandit. Curabitur vulputate, ligula lacinia scelerisque tempor, lacus lacus ornare ante, ac egestas est urna.",
                                Type.ERROR_MESSAGE);
            }
        });
        notify.addStyleName("small");
        row.addComponent(notify);

        /**
         * Buttons
         */
        row = addSection(root, "Buttons", Category.Basic_Components, null);

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

        button = new Button("Photos");
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

        Link link = new Link("Link to vaadin.com", new ExternalResource(
                "https://vaadin.com"));
        row.addComponent(link);

        link = new Link("Custom color", new ExternalResource(
                "https://vaadin.com"));
        link.addStyleName("color3");
        link.setIcon(FontAwesome.CHEVRON_CIRCLE_RIGHT);
        row.addComponent(link);

        link = new Link("Small", new ExternalResource("https://vaadin.com"));
        link.addStyleName("small");
        row.addComponent(link);

        link = new Link("Large", new ExternalResource("https://vaadin.com"));
        link.addStyleName("large");
        row.addComponent(link);

        /**
         * Text fields
         */
        row = addSection(root, "Text Fields", Category.Inputs, null);

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

        /**
         * Text areas
         */
        row = addSection(root, "Text Areas", Category.Inputs, null);

        TextArea ta = new TextArea("Normal");
        ta.setInputPrompt("Write your comment…");
        row.addComponent(ta);

        ta = new TextArea("Focused");
        ta.setInputPrompt("Write your comment…");
        ta.addStyleName("focused");
        row.addComponent(ta);

        ta = new TextArea("Custom color");
        ta.addStyleName("color1");
        ta.setInputPrompt("Write your comment…");
        row.addComponent(ta);

        ta = new TextArea("Custom color");
        ta.addStyleName("color2");
        ta.setInputPrompt("Write your comment…");
        row.addComponent(ta);

        ta = new TextArea("Custom color");
        ta.addStyleName("color3");
        ta.setInputPrompt("Write your comment…");
        row.addComponent(ta);

        ta = new TextArea("Small");
        ta.addStyleName("small");
        ta.setInputPrompt("Write your comment…");
        row.addComponent(ta);

        ta = new TextArea("Large");
        ta.addStyleName("large");
        ta.setInputPrompt("Write your comment…");
        row.addComponent(ta);

        /**
         * Combo boxes
         */
        row = addSection(root, "Combo Boxes", Category.Selection_Components,
                null);

        ComboBox combo = new ComboBox("Normal");
        combo.setInputPrompt("You can type here");
        for (int i = 1; i <= 200; i++) {
            combo.addItem("Option " + i);
        }
        row.addComponent(combo);

        combo = new ComboBox("Disabled");
        combo.setInputPrompt("You can type here");
        combo.addItem("Option One");
        combo.addItem("Option Two");
        combo.addItem("Option Three");
        combo.setEnabled(false);
        row.addComponent(combo);

        combo = new ComboBox("Custom color");
        combo.setInputPrompt("You can type here");
        for (int i = 1; i <= 200; i++) {
            combo.addItem("Option " + i);
        }
        combo.addStyleName("color1");
        row.addComponent(combo);

        combo = new ComboBox("Custom color");
        combo.setInputPrompt("You can type here");
        for (int i = 1; i <= 200; i++) {
            combo.addItem("Option " + i);
        }
        combo.addStyleName("color2");
        row.addComponent(combo);

        combo = new ComboBox("Custom color");
        combo.setInputPrompt("You can type here");
        for (int i = 1; i <= 200; i++) {
            combo.addItem("Option " + i);
        }
        combo.addStyleName("color3");
        row.addComponent(combo);

        combo = new ComboBox("Small");
        combo.setInputPrompt("You can type here");
        combo.addItem("Option One");
        combo.addItem("Option Two");
        combo.addItem("Option Three");
        combo.addStyleName("small");
        row.addComponent(combo);

        combo = new ComboBox("Large");
        combo.setInputPrompt("You can type here");
        combo.addItem("Option One");
        combo.addItem("Option Two");
        combo.addItem("Option Three");
        combo.addStyleName("large");
        row.addComponent(combo);

        /**
         * Split button
         */
        // row = addSection(
        // root,
        // "SplitButton",
        // Category.Other,
        // "This is a custom composite component, extending CssLayout and containing a Button and a MenuBar. Theme mixins are used to style the MenuBar to look like a Button.");
        //
        // SplitButton split = new SplitButton("Main Option");
        // split.addMenuItem("Alternative Option", null);
        // split.addMenuItem("Second Alternative Option", null);
        // split.addMenuItem("Option Three", null);
        // row.addComponent(split);

        /**
         * Check boxes
         */
        row = addSection(root, "Check Boxes", Category.Inputs, null);

        CheckBox check = new CheckBox("Checked", true);
        row.addComponent(check);

        check = new CheckBox("Not checked");
        row.addComponent(check);

        check = new CheckBox(null, true);
        check.setDescription("No caption");
        row.addComponent(check);

        check = new CheckBox("Custom color", true);
        check.addStyleName("color1");
        row.addComponent(check);

        check = new CheckBox("Custom color", true);
        check.addStyleName("color2");
        check.setIcon(FontAwesome.DESKTOP);
        row.addComponent(check);

        check = new CheckBox("With Icon", true);
        check.setIcon(FontAwesome.KEYBOARD_O);
        row.addComponent(check);

        check = new CheckBox();
        check.setIcon(FontAwesome.GAMEPAD);
        row.addComponent(check);

        check = new CheckBox("Small", true);
        check.addStyleName("small");
        row.addComponent(check);

        check = new CheckBox("Large", true);
        check.addStyleName("large");
        row.addComponent(check);

        /**
         * Option groups
         */
        row = addSection(root, "Option Groups", Category.Selection_Components,
                null);

        OptionGroup options = new OptionGroup("Choose one");
        options.addItem("Option One");
        options.addItem("Option Two");
        options.addItem("Option Three");
        options.select("Option One");
        options.setItemIcon("Option One", FontAwesome.DESKTOP);
        options.setItemIcon("Option Two", FontAwesome.KEYBOARD_O);
        options.setItemIcon("Option Three", FontAwesome.GAMEPAD);
        row.addComponent(options);

        options = new OptionGroup("Choose many");
        options.setMultiSelect(true);
        options.addItem("Option One");
        options.addItem("Option Two");
        options.addItem("Option Three");
        options.select("Option One");
        options.setItemIcon("Option One", FontAwesome.DESKTOP);
        options.setItemIcon("Option Two", FontAwesome.KEYBOARD_O);
        options.setItemIcon("Option Three", FontAwesome.GAMEPAD);
        row.addComponent(options);

        /**
         * Date fields
         */
        row = addSection(root, "Date Fields", Category.Inputs, null);
        DateField date = new DateField("Second resolution");
        date.setValue(new Date());
        date.setResolution(Resolution.SECOND);
        row.addComponent(date);

        date = new DateField("Minute resolution");
        date.setValue(new Date());
        date.setResolution(Resolution.MINUTE);
        row.addComponent(date);

        date = new DateField("Hour resolution");
        date.setValue(new Date());
        date.setResolution(Resolution.HOUR);
        row.addComponent(date);

        date = new DateField("Disabled");
        date.setValue(new Date());
        date.setResolution(Resolution.HOUR);
        date.setEnabled(false);
        row.addComponent(date);

        date = new DateField("Day resolution");
        date.setValue(new Date());
        date.setResolution(Resolution.DAY);
        row.addComponent(date);

        date = new DateField("Month resolution");
        date.setValue(new Date());
        date.setResolution(Resolution.MONTH);
        row.addComponent(date);

        date = new DateField("Year resolution");
        date.setValue(new Date());
        date.setResolution(Resolution.YEAR);
        row.addComponent(date);

        date = new DateField("Small");
        date.setValue(new Date());
        date.setResolution(Resolution.DAY);
        date.addStyleName("small");
        row.addComponent(date);

        date = new DateField("Primary color");
        date.setValue(new Date());
        date.setResolution(Resolution.DAY);
        date.addStyleName("primary");
        row.addComponent(date);

        date = new DateField("Primary disabled");
        date.setValue(new Date());
        date.setResolution(Resolution.DAY);
        date.addStyleName("primary");
        date.setEnabled(false);
        row.addComponent(date);

        date = new DateField("Week numbers");
        date.setValue(new Date());
        date.setResolution(Resolution.DAY);
        date.setLocale(new Locale("fi", "fi"));
        date.setShowISOWeekNumbers(true);
        row.addComponent(date);

        date = new DateField("US locale");
        date.setValue(new Date());
        date.setResolution(Resolution.SECOND);
        date.setLocale(new Locale("en", "US"));
        row.addComponent(date);

        /**
         * Windows
         */
        row = addSection(root, "Windows", Category.Component_Containers, null);

        final Window win = new Window("Normal Window");
        win.setWidth("30%");
        win.setHeight("30%");
        win.setContent(windowContents());

        button = new Button("Open Window");
        button.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                win.close();
                UI.getCurrent().addWindow(win);
                win.setPositionX(Page.getCurrent().getBrowserWindowWidth() / 2);
                win.setPositionY(Page.getCurrent().getBrowserWindowHeight() / 2);
            }
        });
        row.addComponent(button);

        /**
         * Panels and layout panels
         */
        row = addSection(root, "Panels & Layout Panels",
                Category.Component_Containers, null);
        Panel panel = new Panel("Normal");
        panel.setContent(panelContent());
        row.addComponent(panel);

        panel = new Panel("Sized");
        panel.setWidth("10em");
        panel.setHeight("8em");
        panel.setContent(panelContent());
        row.addComponent(panel);

        panel = new Panel("Custom Color");
        panel.addStyleName("important");
        panel.setContent(panelContent());
        row.addComponent(panel);

        panel = new Panel("Testing for IE");
        panel.addStyleName("custom");
        panel.setWidth("10em");
        panel.setHeight("8em");
        VerticalLayout l = new VerticalLayout();
        l.setSizeFull();
        Button test1 = new Button("Test 1");
        test1.setSizeFull();
        Button test2 = new Button("Test 2");
        l.addComponent(test1);
        l.addComponent(test2);
        l.setExpandRatio(test1, 1.0f);
        panel.setContent(l);
        row.addComponent(panel);

        MockupDataSet baconDataSet = new BaconDataSet();
        MockupFactory.setDefaultDataSet(baconDataSet);
        MockupContainer container = new MockupContainer();
        container.setDataSet(baconDataSet);
        container.setItemCount(20);
        container.setPropertyCount(4);
        container.setNumberOfChildren(4);
        container.setItemDelay(0);

        /**
         * Trees
         */
        row = addSection(root, "Tree", Category.Selection_Components, null);
        Tree tree = new Tree();
        tree.setSelectable(true);
        tree.setContainerDataSource(container);
        row.addComponent(tree);
        Iterator<String> propertyIterator = container.getContainerPropertyIds()
                .iterator();
        if (propertyIterator.hasNext()) {
            Object captionId = propertyIterator.next();
            tree.setItemCaptionPropertyId(captionId);
        }
        Collection<Integer> itemIds = container.getItemIds();
        for (Integer integer : itemIds) {
            tree.expandItem(integer);
        }

        // Add actions (context menu)
        tree.addActionHandler(this);

        /**
         * Tables
         */
        row = addSection(root, "Table/TreeTable/Grid",
                Category.Selection_Components, null);

        Table table = getTable("Normal");
        table.setContainerDataSource(container);
        row.addComponent(table);

        table = getTable("With footer");
        table.setFooterVisible(true);
        table.setContainerDataSource(container);
        if (propertyIterator.hasNext()) {
            Object id = propertyIterator.next();
            table.setColumnFooter(id, "Footer for " + id);
        }
        row.addComponent(table);

        /**
         * Sliders
         */
        row = addSection(root, "Slider", Category.Basic_Components, null);

        Slider slider = new Slider("Horizontal");
        row.addComponent(slider);

        slider = new Slider("Horizontal, sized");
        slider.setWidth("200px");
        row.addComponent(slider);

        slider = new Slider("Vertical");
        slider.setOrientation(SliderOrientation.VERTICAL);
        row.addComponent(slider);

        slider = new Slider("Vertical, sized");
        slider.setOrientation(SliderOrientation.VERTICAL);
        slider.setHeight("200px");
        row.addComponent(slider);

        slider = new Slider("Disabled");
        slider.setEnabled(false);
        row.addComponent(slider);

        return root;
    }

    HorizontalLayout addSection(VerticalLayout root, String title,
            Category category, String description) {
        String id = title.toLowerCase().replace(" ", "-");

        Label h1 = new Label(title);
        h1.addStyleName("h1");
        h1.setId("" + id);
        root.addComponent(h1);

        if (description != null) {
            Label desc = new Label(description + "<br><br>", ContentMode.HTML);
            root.addComponent(desc);
        }

        Link link = new Link(title, new ExternalResource("#" + id));

        ArrayList<Link> items = menuItems.get(category);
        if (items == null) {
            items = new ArrayList<Link>();
            menuItems.put(category, items);
        }
        items.add(link);

        HorizontalLayout row = new HorizontalLayout();
        row.addStyleName("wrapping");
        row.setSpacing(true);
        root.addComponent(row);
        return row;
    }

    private Component windowContents() {
        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        content.addComponent(new Label(
                "<h2>Lorem Ipsum Dolor Sit Amet, Consectetur</h2><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam in dui mauris. Vivamus hendrerit arcu sed erat molestie vehicula. Sed auctor neque eu tellus rhoncus ut eleifend nibh porttitor. <h3>Ut in nulla enim</h3> Phasellus molestie magna non est.</p><p>Bibendum non venenatis nisl tempor. Suspendisse dictum feugiat nisl ut dapibus. Mauris iaculis porttitor posuere. Praesent id metus massa, ut blandit odio. Proin quis tortor orci. Etiam at risus et justo dignissim congue. Donec congue lacinia dui, a porttitor lectus condimentum laoreet. Nunc eu ullamcorper orci. Quisque eget odio ac lectus vestibulum faucibus eget in metus. In pellentesque faucibus vestibulum. Nulla at nulla justo, eget luctus tortor. Nulla facilisi. Duis aliquet egestas purus in blandit. Curabitur vulputate, ligula lacinia scelerisque.</p>",
                ContentMode.HTML));
        return content;
    }

    Component panelContent() {
        // return new Button("Panel content");
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        Label content = new Label(
                "Suspendisse dictum feugiat nisl ut dapibus. Mauris iaculis porttitor posuere. Praesent id metus massa, ut blandit odio.");
        content.setWidth("10em");
        layout.addComponent(content);
        layout.addComponent(new Button("Button"));
        return layout;
    }

    Table getTable(String caption) {
        Table table = new Table(caption);
        table.setSelectable(true);
        table.setSortEnabled(true);
        table.setColumnCollapsingAllowed(true);
        table.setColumnReorderingAllowed(true);
        table.setPageLength(0);
        return table;
    }

    // Actions for the context menu
    private static final Action ACTION_ONE = new Action("Action One");
    private static final Action ACTION_TWO = new Action("Action Two");
    private static final Action ACTION_THREE = new Action("Action Three");
    private static final Action[] ACTIONS = new Action[] { ACTION_ONE,
            ACTION_TWO, ACTION_THREE };

    @Override
    public Action[] getActions(Object target, Object sender) {
        return ACTIONS;
    }

    @Override
    public void handleAction(Action action, Object sender, Object target) {
        Notification.show(action.getCaption());
    }

}