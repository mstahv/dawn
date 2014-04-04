package com.vaadin.theme.valo.demo;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.theme.valo.demo.calendarview.CalendarView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("valo-demo")
@Title("Valo Theme Demo")
public class ValoDemoUI extends UI {

    private static final long serialVersionUID = 1L;

    VerticalLayout root = new VerticalLayout();
    CssLayout content = new CssLayout();
    private MenuBar nav = new MenuBar();
    private MenuBar userNav = new MenuBar();
    private Navigator navigator;

    @Override
    protected void init(VaadinRequest request) {
        Page.getCurrent().setTitle("Valo Theme Demo");

        initLayout();
        initNav();
        initUserNav();

        navigator = new Navigator(this, content);

        ContactsView contactsView = new ContactsView();
        navigator.addView("", contactsView);
        navigator.addView(ContactsView.NAME.toLowerCase(), contactsView);
        navigator.addView(CalendarView.NAME.toLowerCase(), new CalendarView());
        navigator.addView(TasksView.NAME.toLowerCase(), new TasksView());

        navigator.addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                // TODO Auto-generated method stub
                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {
                if (event.getViewName() == null
                        || event.getViewName().equals("")) {
                    updateNavSelection("contacts");
                } else {
                    updateNavSelection(event.getViewName());
                }
            }
        });

    }

    private void initLayout() {
        root.setSizeFull();
        setContent(root);

        HorizontalLayout topbar = new HorizontalLayout();
        topbar.addStyleName("topbar");
        topbar.setWidth("100%");
        topbar.setSpacing(true);
        root.addComponent(topbar);

        content.setSizeFull();
        root.addComponent(content);
        root.setExpandRatio(content, 1);

        Label title = new Label("Tasku");
        title.setSizeUndefined();
        title.addStyleName("h2");
        title.addStyleName("no-margin");
        topbar.addComponent(title);
        topbar.setComponentAlignment(title, Alignment.MIDDLE_LEFT);

        nav.addStyleName("nav");
        topbar.addComponent(nav);

        userNav.addStyleName("user-nav");
        topbar.addComponent(userNav);
        topbar.setComponentAlignment(userNav, Alignment.TOP_RIGHT);
    }

    private void initNav() {
        nav.addItem(ContactsView.NAME, navCommand).setCheckable(true);
        nav.addItem(CalendarView.NAME, navCommand).setCheckable(true);
        nav.addItem(TasksView.NAME, navCommand).setCheckable(true);
    }

    private void initUserNav() {
        MenuItem username = userNav.addItem("Jouni Koivuviita",
                FontAwesome.CHEVRON_DOWN, null);
        username.addItem("Edit Profile", null);
        username.addItem("Settings", null);
        username.addSeparator();
        username.addItem("Sign Out", null);
    }

    private Command navCommand = new Command() {
        @Override
        public void menuSelected(MenuItem selectedItem) {
            updateNavSelection(selectedItem.getText());
            navigator.navigateTo(selectedItem.getText().toLowerCase());
        }
    };

    private void updateNavSelection(String selectedItem) {
        for (MenuItem item : nav.getItems()) {
            item.setChecked(item.getText().toLowerCase()
                    .equals(selectedItem.toLowerCase()));
        }
    }

    void navigateTo(String menuItem) {
        for (MenuItem item : nav.getItems()) {
            if (item.getText().toLowerCase().equals(menuItem.toLowerCase())) {
                navCommand.menuSelected(item);
                return;
            }
        }
    }

}
