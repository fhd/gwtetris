package com.github.fhd.gwtetris.client;

import com.github.fhd.gwtetris.client.game.Game;
import com.github.fhd.gwtetris.client.game.Grid;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

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
    void handleClick(ClickEvent event) {
        game.start();
        new Timer() {
            @Override
            public void run() {
                grid.nextStep();
                updateGrid(grid.getMatrix());
            }
        }.scheduleRepeating(Constants.STEP_TIME);
        
        Event.addNativePreviewHandler(new NativePreviewHandler() {
            @Override
            public void onPreviewNativeEvent(NativePreviewEvent event) {
                if (event.getTypeInt() == Event.ONKEYPRESS) {
                    switch (event.getNativeEvent().getKeyCode()) {
                    case KeyCodes.KEY_LEFT:
                        grid.movePiece(-1, 0);
                        event.cancel();
                        break;
                    case KeyCodes.KEY_RIGHT:
                        grid.movePiece(1, 0);
                        event.cancel();
                        break;
                    case KeyCodes.KEY_DOWN:
                        grid.movePiece(0, 1);
                        event.cancel();
                        break;
                    case KeyCodes.KEY_UP:
                        grid.rotatePiece();
                        event.cancel();
                        break;
                    }
                }
            }
        });
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
