package connect4Controllers;

import Util.coordinates;
import connect4Models.gameMechanics;
import connect4Views.connect4View;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class connect4Controller {
    /**
     * Field variable to represent the view of the connect4 game
     */
    private final connect4View connect4view;
    /**
     * Field variable to represent the model of the connect4 game (game logic and state)
     */
    private final gameMechanics connect4model;

    /**
     * Constructor of the connect4Controller. Initialises the connect4view and connect4model field variables.
     * Passes a reference of itself to the connect4view. So that the connect4view can retrieve the event handlers in this class and add them to nodes in the view.
     * Tells the connect4view field variable to createScene.
     * @param connect4view The parameter that will represent the view of the connect4 game
     * @param connect4model The parameter that will represent the model of the connect4 game
     */
    public connect4Controller(connect4View connect4view, gameMechanics connect4model){
        this.connect4model = connect4model;
        this.connect4view = connect4view;

        this.connect4view.setConnect4Controller(this);
        this.connect4view.createScene();
    }

    /**
     * Getter method to get the connect4view field variable in this class
     * @return connect4View
     */
    public connect4View getConnect4view(){return connect4view;}

    /**
     * Handles a mouse-pressed event within the connect4 board
     * If it receives a mouse-pressed event from within the grid pane, it will call the "handle" method
     */
    private final EventHandler<MouseEvent> handleEvent = new EventHandler<>() {
        @Override
        public void handle(MouseEvent event) {
            if(connect4model.getGameStatus()!=0){
                System.out.println("The game is over");
            } else {
                int column = connect4view.getGridPane().getColumnIndex((Node) event.getSource());
                boolean checkValidColumn = connect4model.isChosenColumnValid(column);
                if (checkValidColumn) {
                    connect4model.slotCounter(column);
                    coordinates lastCounterPosition = connect4model.getLastCounterLocation();
                    int latestRow = lastCounterPosition.getRow();
                    int latestCol = lastCounterPosition.getCol();

                    Circle selectedCircle = connect4view.getCircleByCoordinate(latestRow, latestCol);
                    //SETS THE COLOR OF A SELECTED CIRCLE
                    connect4view.setCircleColor(selectedCircle, connect4view.getCounterColors()[connect4view.getCurrentCounterColorIndex()]);

                    if (connect4model.isGameWon(latestRow, latestCol)) {
                        int currentPlayer = connect4model.getCurrentPlayer();
                        System.out.println("Player " + currentPlayer + " has won. ");
                        Text gameStateText = connect4view.getCurrentGameStateText();
                        gameStateText.setText("Game over!!!!!! A player has won");
                        connect4model.setGameStatus(1);

                    } else if (connect4model.isGameDrawn()) {
                        System.out.println("Game has ended in a draw");
                        Text gameStateText = connect4view.getCurrentGameStateText();
                        gameStateText.setText("Game has ended in a draw");
                        connect4model.setGameStatus(2);
                    } else {
                        //CHANGE COUNTER COLOUR INDEX
                        connect4view.setCurrentCounterColorIndex((connect4view.getCurrentCounterColorIndex() + 1) % 2);
                        //CHANGE WHO CURRENT PLAYER IS
                        connect4model.switchPlayer();
                        //CHANGE CURRENT PLAYER TEXT
                        Text currentPlayersTextBox = connect4view.getCurrentPlayerText();
                        int currentColorIndex = connect4view.getCurrentCounterColorIndex();
                        if(currentColorIndex == 0 ){
                            currentPlayersTextBox.setText("Red's turn");
                        } else {
                            currentPlayersTextBox.setText("Yellow's turn");
                        }
                        currentPlayersTextBox.setStroke(connect4view.getCounterColors()[currentColorIndex]);
                    }
                } else {
                    System.out.println("Please select a valid column");
                }
            }
        }
    };

    /**
     * Getter method to get the event handler responsible handling a mouse clicked event within the connect4 board
     * @return EventHandler<MouseEvent> An event handler that handles a mouse clicked event came from within the connect4 board
     */
    public EventHandler<MouseEvent> getHandleEvent(){
        return handleEvent;
    }

    /**
     * Handles a mouse-pressed event of the reset button
     * If it receives a mouse-pressed event from the reset button, it will call the "handle" method
     */
    private final EventHandler<MouseEvent> handleResetEvent = new EventHandler<>() {
        @Override
        public void handle(MouseEvent event) {
            connect4model.resetGame();

            connect4view.resetCircleColors();
            connect4view.resetText();
        }
    };

    /**
     * Getter method to get the event handler responsible for handling a mouse clicked event upon the reset button
     * @return EventHandler<MouseEvent> An event handler that handles a mouse clicked event that came from the reset button
     */
    public EventHandler<MouseEvent> getHandleResetEvent() { return handleResetEvent; }

}
