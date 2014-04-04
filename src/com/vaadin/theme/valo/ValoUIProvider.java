package com.vaadin.theme.valo;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.theme.valo.demo.ValoDemoUI;
import com.vaadin.theme.valo.test.CalendarTest;
import com.vaadin.theme.valo.test.TestUI;
import com.vaadin.theme.valo.test.ValoThemeUI;
import com.vaadin.ui.UI;

public class ValoUIProvider extends UIProvider {

    @WebServlet(value = "/*", asyncSupported = true, initParams = @WebInitParam(name = "UIProvider", value = "com.vaadin.theme.valo.ValoUIProvider"))
    // @VaadinServletConfiguration(productionMode = false, ui =
    // ValoThemeUI.class)
    public static class Servlet extends ThemeServlet {
    }

    @Override
    public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
        String path = event.getRequest().getPathInfo();
        if (path.startsWith("/theme")) {
            return ValoThemeUI.class;
        } else if (path.startsWith("/calendar")) {
            return CalendarTest.class;
        } else if (path.startsWith("/test")) {
            return TestUI.class;
        }
        return ValoDemoUI.class;
    }

}
