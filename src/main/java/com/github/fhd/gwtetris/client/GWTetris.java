package com.github.fhd.gwtetris.client;

import com.github.fhd.gwtetris.client.game.*;
import com.github.fhd.gwtetris.client.game.Grid;
import com.google.gwt.core.client.*;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.*;

/**
 * The entry point and user interface of GWTetris.
 */
class GWTetris extends UIObject implements EntryPoint {
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
        grid = new Grid(Constants.GRID_COLS, Constants.GRID_ROWS);
        RootPanel.get().add(uiBinder.createAndBindUi(this));
        resumeButton.setFocus(true);
    }
    
    @UiHandler("resumeButton")
    void handleClick(ClickEvent event) {
        game.start();
        new Timer() {
            @Override
            public void run() {
                grid.nextStep();
                drawMatrix(grid.getMatrix(), gridPanel, 0, 0);
                
                int[][] nextPieceMatrix = grid.getNextPieceMatrix();
                int previewXOffset = previewPanel.getOffsetWidth() / 2
                                     - (nextPieceMatrix[0].length
                                        * Constants.BLOCK_WIDTH) / 2;
                int previewYOffset = previewPanel.getOffsetHeight() / 2
                                     - (nextPieceMatrix.length
                                        * Constants.BLOCK_HEIGHT) / 2;
                drawMatrix(nextPieceMatrix, previewPanel, previewXOffset,
                           previewYOffset);
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
        
    private void drawMatrix(int[][] matrix, LayoutPanel panel,
                            int xoffset, int yoffset) {
        panel.clear();
        for (int yCell = 0; yCell < matrix.length; yCell++)
            for (int xCell = 0; xCell < matrix[yCell].length; xCell++)
                if (matrix[yCell][xCell] > 0) {
                    HTML piece = new HTML();
                    piece.setStyleName(style.block());
                    panel.add(piece);
                    panel.setWidgetLeftWidth(piece, 
                                             xCell * Constants.BLOCK_HEIGHT + xoffset,
                                             Unit.PX, Constants.BLOCK_WIDTH, Unit.PX);
                    panel.setWidgetTopHeight(piece,
                                             yCell * Constants.BLOCK_WIDTH + yoffset, 
                                             Unit.PX, Constants.BLOCK_HEIGHT, Unit.PX);
                }
    }
}
