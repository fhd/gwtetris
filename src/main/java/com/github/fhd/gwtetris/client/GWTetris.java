package com.github.fhd.gwtetris.client;

import com.github.fhd.gwtetris.client.game.*;
import com.github.fhd.gwtetris.client.game.Grid;
import com.google.gwt.core.client.*;
import com.google.gwt.dom.client.Style.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.ui.*;

/**
 * The entry point and user interface of GWTetris.
 */
public class GWTetris extends UIObject implements EntryPoint {
    interface Binder extends UiBinder<Widget, GWTetris> {}
    private static Binder uiBinder = GWT.create(Binder.class);

    private Style style;
    private Game game;
    private Grid grid;
    @UiField LayoutPanel gridPanel;
    @UiField LayoutPanel previewPanel;
    @UiField Button resumeButton;

    /**
     * The entry point method.
     */
    public void onModuleLoad() {
        Resources resources = GWT.create(Resources.class);
        style = resources.style();
        style.ensureInjected();
        game = new Game();
        grid = new Grid(20, 30);
        RootPanel.get().add(uiBinder.createAndBindUi(this));
    }
    
    @UiHandler("resumeButton")
    void handleClick(ClickEvent e) {
        game.start();
        new Timer() {
            @Override
            public void run() {
                grid.nextStep();
                updateGrid(grid.getMatrix());
            }
        }.scheduleRepeating(Constants.STEP_TIME);
    }
    
    private void updateGrid(int[][] matrix) {
        gridPanel.clear();
        for (int yCell = 0; yCell < matrix.length; yCell++)
            for (int xCell = 0; xCell < matrix[yCell].length; xCell++)
                if (matrix[yCell][xCell] > 0) {
                    HTML piece = new HTML();
                    piece.setStyleName(style.block());
                    gridPanel.add(piece);
                    gridPanel.setWidgetLeftWidth(piece, 20 * xCell, Unit.PX,
                                                 20, Unit.PX);
                    gridPanel.setWidgetTopHeight(piece, 20 * yCell, Unit.PX,
                                                 20, Unit.PX);
                }
    }
}
