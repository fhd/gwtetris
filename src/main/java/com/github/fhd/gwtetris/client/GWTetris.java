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
        RootPanel.get().add(new Label("Hello, World!"));
    }
}
