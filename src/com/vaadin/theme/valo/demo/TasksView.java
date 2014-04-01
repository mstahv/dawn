package com.vaadin.theme.valo.demo;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CssLayout;

public class TasksView extends CssLayout implements View {

    private static final long serialVersionUID = 1L;

    public static final String NAME = "Tasks";

    public TasksView() {
        addStyleName(NAME.toLowerCase());
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

}
