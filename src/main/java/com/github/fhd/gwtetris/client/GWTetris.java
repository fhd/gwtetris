package com.github.fhd.gwtetris.client;

import com.google.gwt.core.client.*;
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
        resources.css().ensureInjected();
        
        Label grid = new HTML("Nothing");
        grid.setStyleName(resources.css().grid());
        Label preview = new HTML("Here");
        preview.setStyleName(resources.css().preview());
        
        FlowPanel p = new FlowPanel();
        p.add(grid);
        p.add(preview);

        RootLayoutPanel.get().add(p);
    }
}
