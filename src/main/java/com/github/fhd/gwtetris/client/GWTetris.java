package com.github.fhd.gwtetris.client;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

/**
 * The entry point of GWTetris.
 */
public class GWTetris implements EntryPoint {
    /**
     * The entry point method.
     */
    public void onModuleLoad() {
        GWTetrisResources resources = GWT.create(GWTetrisResources.class);
        GWTetrisCss css = resources.css();
        css.ensureInjected();
        
        Button resumeButton = new Button("Start");
        resumeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent arg0) {
                DecoratedPopupPanel popup = new DecoratedPopupPanel(true);
                //popup.setWidth("150px");
                popup.setWidget(new Label("Not implemented yet."));
                popup.center();
                popup.show();
            }
        });
        RootPanel.get("buttons").add(resumeButton);
    }
}
