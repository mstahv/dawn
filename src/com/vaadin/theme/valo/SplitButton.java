package com.vaadin.theme.valo;

import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

public class SplitButton extends CssLayout {

    private static final long serialVersionUID = 1L;
    
    protected Button primaryButton = new Button();
    protected MenuBar secondaryButton = new MenuBar();
    protected MenuItem menuRoot;

    public SplitButton(String caption) {
        primaryButton.setCaption(caption);
        menuRoot = secondaryButton.addItem("", null);

        addComponent(primaryButton);
        addComponent(secondaryButton);

        setPrimaryStyleName("jk-splitbutton");
        secondaryButton.setPrimaryStyleName("jk-splitbutton-secondary");
    }

    public void addMenuItem(String caption, Command command) {
        menuRoot.addItem(caption, command);
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        primaryButton.setEnabled(enabled);
        secondaryButton.setEnabled(enabled);
    }

}
