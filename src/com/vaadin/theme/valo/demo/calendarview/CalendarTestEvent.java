package com.vaadin.theme.valo.demo.calendarview;

import com.vaadin.ui.components.calendar.event.BasicEvent;

public class CalendarTestEvent extends BasicEvent {

    private static final long serialVersionUID = 1L;

    private boolean done;

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
        fireEventChange();
    }

}