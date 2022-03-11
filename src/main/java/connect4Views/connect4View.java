package connect4Views;

import connect4Controllers.connect4Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;


public class connect4View {
    /**
     * Field variable to represent the connect4 controller
     */
    private connect4Controller connect4controller;
    /**
     * Field variable to show to the user the current game state.
     */
    private Text currentGameStateText;
    /**
     * Field variable to hold the currentGameStateText field variable and align the text horizontally center
     */
    private HBox currentGameStateTextContainer;
    /**
     * Field variable to show to the user whose turn it is currently.
     */
    private Text currentPlayerText;
    /**
     * Field variable to hold the currentPlayerText field variable and align the text horizontally center
     */
    private HBox currentPlayerTextContainer;
    /**
     * Field variable to represent the reset button
     */
    private Button resetButton;
    /**
     * Field variable to represent the connect4 board as a JavaFX grid pane
     */
    private GridPane gridPane;
    /**
     * Field variable to represent the number of rows for the connect4 game
     */
    private final int noOfRows=6;
    /**
     * Field variable to represent the number of columns for the connect4 game
     */
    private final int noOfColumns=7;
    /**
     * Field variable to represent the scene of the connect4 game
     */
    private Scene gameScene;
    /**
     * Field variable to hold the colors of the two counter used for the connect4 game
     */
    private final Color[] counterColors = {Color.RED,Color.YELLOW};
    /**
     * Field variable that points to one of the colors within the counterColors array above.
     */
    private int currentCounterColorIndex;

    /**
     * Constructor for the connect4View class.
     */
    public connect4View(){
        currentCounterColorIndex=0;
    }

    /**
     * Calls the "createGridPane()" and "createResetButton()" method
     * Creates a vbox object. aligns the reset button and the grid pane in a vertical order respectively.
     * vbox object is passed to a group object called layoutWrapper. layoutWrapper ensures that the size of the scene automatically adjusts to the preferred-size of its children nodes,
     * i.e. the vbox, the reset button and the grid pane.
     * The "gameScene" field variable is initialised with a scene object. The scene object takes in the "layoutWrapper" field variable as it's argument
     * The colour the "gameScene" is set to blue
     **/
    public void createScene(){
        createResetButton();
        createCurrentGameStateTextContainer();
        createCurrentPlayerTextContainer();
        createGridPane();

        VBox vbox = new VBox();
        vbox.getChildren().addAll(resetButton,currentGameStateTextContainer,currentPlayerTextContainer,gridPane);

        Group layoutWrapper = new Group();
        layoutWrapper.getChildren().add(vbox);

        gameScene = new Scene(layoutWrapper);
        gameScene.setFill(Color.BLUE);
    }

    /**
     * Creates a circle with pixel radius 50, color white-smoke and adds an event to the circle.
     * The event-handler comes from the controller class called "connect4controller"
     * @return Circle A circle node
     */
    public Circle createCircle(){
        Circle circle = new Circle();
        circle.setRadius(50);
        circle.setFill(Color.WHITESMOKE);
        circle.addEventFilter(MouseEvent.MOUSE_CLICKED, connect4controller.getHandleEvent());
        return circle;
    }

    /**
     * Initialises the "gridPane" field variable.
     * Adds margin and padding to the grid-pane
     * Adds 42 circle nodes to the 6x7 grid-pane via the "createCircle()" method
     */
    public void createGridPane(){
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10,10,10,10));

        for(int i=0;i<noOfRows;i++){
            for(int j=0;j<noOfColumns;j++){
                gridPane.add(createCircle(),j,i,1,1);
            }
        }
    }

    /**
     * createCurrentPlayerTextContainer creates a HBox. Also initialises the current player text node. Passes the current player text node to the HBox,
     * so that the current player text node can be aligned horizontally center
     */
    public void createCurrentPlayerTextContainer(){
        currentPlayerTextContainer = new HBox();
        createCurrentPlayerText();
        currentPlayerTextContainer.getChildren().add(currentPlayerText);
        currentPlayerTextContainer.setAlignment(Pos.BASELINE_CENTER);
    }
    /**
     * createCurrentPlayerText initialises the currentPlayerText field variable with a JavaFX Text object
     */
    public void createCurrentPlayerText(){
        currentPlayerText = new Text();
        currentPlayerText.setText("Red's turn");
        currentPlayerText.setStroke(counterColors[0]);
    }
    /**
     * createCurrentGameStateText initialises the currentGameStateText field variable with a JavaFX Text object
     */
    public void createCurrentGameStateText(){
        currentGameStateText = new Text();
        currentGameStateText.setText("In progress");
        currentGameStateText.setStroke(Color.WHITESMOKE);
    }
    /**
     * createCurrentGameStateTextContainer creates a HBox. Also initialises the current game state text node. Passes the current game state text node to the HBox,
     * so that the current game state text node can be aligned horizontally center
     */
    public void createCurrentGameStateTextContainer(){
        currentGameStateTextContainer = new HBox();
        createCurrentGameStateText();
        currentGameStateTextContainer.getChildren().add(currentGameStateText);
        currentGameStateTextContainer.setAlignment(Pos.BASELINE_CENTER);
    }
    /**
     * createResetButton method creates the reset button. The reset button text is "RESET GAME". An event filter is added to the button.
     * The event handler for this reset button comes from the connect4controller class
     */
    public void createResetButton(){
        resetButton = new Button("RESET GAME");
        resetButton.addEventFilter(MouseEvent.MOUSE_CLICKED, connect4controller.getHandleResetEvent());
    }


    /**
     * Gets a circle node from the grid-pane by its row and column number
     * @param row The row number of the circle node that we want
     * @param column The column number of the circle node that we want
     * @return Circle A circle node that is retrieved according to its row and column number
     */
    public Circle getCircleByCoordinate(int row, int column){
        for(Node circle : gridPane.getChildren()){
            if(gridPane.getRowIndex(circle) == row && gridPane.getColumnIndex(circle) == column){
                return (Circle) circle;
            }
        }
        return null;
    }

    //GETTER METHODS
    public Scene getGameScene(){
        return gameScene;
    }
    //GETTER METHODS
    public GridPane getGridPane(){
        return gridPane;
    }
    //GETTER METHODS
    public Color[] getCounterColors() { return counterColors;}
    //GETTER METHODS
    public int getCurrentCounterColorIndex(){ return currentCounterColorIndex;}
    //GETTER METHODS
    //public Button getResetButton(){ return resetButton;}
    //GETTER METHODS
    public Text getCurrentPlayerText() {return currentPlayerText;}
    //GETTER METHODS
    public Text getCurrentGameStateText() {return currentGameStateText;}

    //SETTER METHOD
    public void setCurrentCounterColorIndex(int newCounterColorIndex){
        this.currentCounterColorIndex = newCounterColorIndex;
    }
    //SETTER METHODS
    public void setConnect4Controller(connect4Controller connect4controller){
        this.connect4controller = connect4controller;
    }
    //SETTER METHODS
    public void setCircleColor(Circle circle, Color color){
        circle.setFill(color);
    }
    //SETTER METHODS
    //public void setCurrentPlayerText(String newCurrentPlayer){this.currentPlayerText.setText(newCurrentPlayer);}
    //SETTER METHODS
    //public void setCurrentGameStateText(String newGameState){this.currentGameStateText.setText(newGameState);}

    /**
     * resetText resets all the text within the view to their initial state such as their initial String message and font color.
     * It also resets the counter color index to its initial value (0)
     */
    public void resetText(){
        Text currentGameState = getCurrentGameStateText();
        currentGameState.setText("In progress");

        setCurrentCounterColorIndex(0);
        Text currentPlayer = getCurrentPlayerText();
        currentPlayer.setText("Red's turn");
        currentPlayer.setStroke(counterColors[0]);
    }
    /**
     * resetCircleColors retrieves each circle node from the grid pane and changes the circle node color to white-smoke
     */
    public void resetCircleColors(){
        for(int i=0;i<noOfRows;i++){
            for(int j=0;j<noOfColumns;j++){
                Circle circle = getCircleByCoordinate(i,j);
                setCircleColor(circle, Color.WHITESMOKE);
            }
        }
    }

}

