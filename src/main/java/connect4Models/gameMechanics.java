package connect4Models;

import Util.coordinates;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class gameMechanics {
    // 0 = game not finished
    // 1 = game is finished, a player has won
    // 2 = game is finished, drawn game
    private int gameStatus  = 0;

    private final int[] players = {1,2};
    private int currentPlayerIndex;

    private coordinates lastCounterLocation;

    private final int[][] boardState;
    private int totalCountersPlaced;

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Constructor for the gameMechanics class. Initialises the size of the board, current player index and the current total counters placed in the board
     */
    public gameMechanics(){
        this.boardState = new int[6][7];
        this.totalCountersPlaced=0;
        this.currentPlayerIndex=0;
    }
    /**
     * resetGame resets all the integer values in the board to 0, total counters placed to 0, current player index to 0.
     */
    public void resetGame(){
        for(int i=0;i<this.boardState.length;i++){
            for(int j=0;j<this.boardState[0].length;j++){
                this.boardState[i][j]=0;
            }
        }
        this.totalCountersPlaced=0;
        this.currentPlayerIndex=0;
        this.gameStatus=0;
    }
    /**
     * getCurrentPlayer returns the integer value that represents the current player
     * @return The integer value that represents the current player
     */
    public int getCurrentPlayer(){
        return this.players[this.currentPlayerIndex];
    }
    /**
     * getLastCounterLocation returns a coordinates object, which contains the row and column number of the latest counter that was placed into the board
     * @return Util.coordinates An object that has the x and y location of the most recent counter.
     */
    public coordinates getLastCounterLocation() {return this.lastCounterLocation;}

    /**
     * getGameStatus returns an integer value that indicates if the game can still go on (0), or if a player has won the game (1), or if the game ends in a draw (2)
     * @return gameStatus An integer value that represents the current status of the game
     */
    public int getGameStatus() {
        return gameStatus;
    }

    /**
     * setGameStatus sets a new integer value to the field variable "gameStatus"
     * @param gameStatus An integer value that represents the current status of the game
     */
    public void setGameStatus(int gameStatus){
        this.gameStatus = gameStatus;
    }
    /**
     * setCurrentPlayerIndex changes integer value of the field variable "currentPlayerIndex", using the integer value of the argument
     * @param currentPlayerIndex The new integer value that will be assigned to the field variable "currentPlayerIndex"
     */
    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }
    /**
     * Check if given column is inside the board
     * @param col The given column to be checked
     * @return boolean if the given column is within the board boundaries or not
     */
    public boolean isValidCol(int col){return col >= 0 && col < boardState[0].length;}
    /**
     * Check if given row is inside the board
     * @param row The given row to be checked
     * @return boolean if the given column is within the board boundaries or not
     */
    public boolean isValidRow(int row) {return row >= 0 && row < boardState.length;}
    /**
     * Checks if the chosen column is valid. This is done by checking if the "col" argument is actually within the board boundaries.
     * Also checks if the chosen column is full or not.
     * @param col The column chosen by the player
     * @return boolean if the chosen column is within the board or the chosen column is not full or the chosen column is full
     */
    public boolean isChosenColumnValid(int col) {
        if (!isValidCol(col)) {
            //Check is chosen column is valid
            System.out.println("invalid col ");
            return false;
        } else if (this.boardState[0][col]!=0){
            //If the top row of the given column is filled, then the column is full. Can't place anymore counters
            System.out.println("Chosen column is filled");
            return false;
         } else {
            return true;
        }

    }
    /**
     * slotCounter slots a counter into the chosen column.
     * @param col The column selected for the counter to be slotted into
     */
    public void slotCounter(int col){

            int currentRow = this.boardState.length-1;
            //Fix the chosen column. Using while loop, check if cell is valid and has a counter. If there is a counter, go one row up and repeat
            while(isValidRow(currentRow) && this.boardState[currentRow][col]!=0){
                currentRow=currentRow-1;
            }
            //Place a counter in the free valid cell with the integer value that represents the current player
            this.boardState[currentRow][col] = this.getCurrentPlayer();
            //Record the row and column of the last counter that was placed into the board
            lastCounterLocation = new coordinates(currentRow,col);
            //increment total counters placed inside the board
            totalCountersPlaced++;
            //this.displayState();
    }
    /**
     * switchPlayer switches who the current player is. This is done by updating the field variable "currentPlayerIndex"
     */
    public void switchPlayer(){
        currentPlayerIndex = (this.currentPlayerIndex + 1) % 2;
    }
    /**
     * checkHorizontalWinCondition checks the horizontal win condition when the counter is at a certain row
     * @param row The row where the most recent counter has been placed
     * @return boolean if the horizontal win condition has been satisfied
     */
    public boolean checkHorizontalWinCondition(int row, int col){
        int chosenPlayer = this.players[currentPlayerIndex];
        int counterAmount = 0;
        int tempCol = col;

        while(isValidRow(row) && isValidCol(tempCol) && this.boardState[row][tempCol] == chosenPlayer && counterAmount != 4){
            counterAmount++;
            tempCol--;
        }
        tempCol = col+1;
        while(isValidRow(row) && isValidCol(tempCol) && this.boardState[row][tempCol] == chosenPlayer && counterAmount != 4){
            counterAmount++;
            tempCol++;
        }

        return counterAmount==4;
    }
    /**
     * checkVerticalWinCondition checks the vertical win condition when the counter is at a certain column
     * @param col The column where the most recent counter has been placed
     * @return boolean if the vertical win condition has been satisfied
     */
    public boolean checkVerticalWinCondition(int row, int col){
        int chosenPlayer = this.players[currentPlayerIndex];
        int counterAmount = 0;
        int tempRow = row;

        while(isValidRow(tempRow) && isValidCol(col) && this.boardState[tempRow][col] == chosenPlayer && counterAmount !=4){
            counterAmount++;
            tempRow++;
        }

        return counterAmount==4;
    }
    /**
     * checkDiagonalNorthWestWinCondition checks the diagonal win condition when the counter is at a certain row and column
     * @param col The column where the most recent counter has been placed
     * @param row The row where the most recent counter has been placed
     * @return boolean if the diagonal North West win condition has been satisfied
     */
    public boolean checkDiagonalNorthWestWinCondition(int row, int col){
        int chosenPlayer = this.players[currentPlayerIndex];
        int counterAmount = 0;

        //Checking 1st diagonal in 2-parts
        //(3.a)check diagonal (North-West direction)
        int tempRow = row;
        int tempCol = col;
        while(isValidRow(tempRow) && isValidCol(tempCol) && this.boardState[tempRow][tempCol] == chosenPlayer && counterAmount != 4){
            counterAmount++;
            tempRow--;
            tempCol--;
        }

        //(3.b)check diagonal (South-East direction)
        tempRow = row+1;
        tempCol = col+1;
        while(isValidRow(tempRow) && isValidCol(tempCol) && this.boardState[tempRow][tempCol] == chosenPlayer && counterAmount != 4){
            counterAmount++;
            tempRow++;
            tempCol++;
        }

        return counterAmount==4;
    }
    /**
     * checkDiagonalNorthEastWinCondition checks the diagonal win condition when the counter is at a certain row and column
     * @param col The column where the most recent counter has been placed
     * @param row The row where the most recent counter has been placed
     * @return boolean if the diagonal North East win condition has been satisfied
     */
    public boolean checkDiagonalNorthEastWinCondition(int row, int col){
        int chosenPlayer = this.players[currentPlayerIndex];
        int counterAmount = 0;

        //Checking 2nd diagonal in 2-parts
        //(4.a)check diagonal (South-West direction)
        int tempRow = row;
        int tempCol = col;
        while(isValidRow(tempRow) && isValidCol(tempCol) && this.boardState[tempRow][tempCol] == chosenPlayer && counterAmount != 4){
            counterAmount++;
            tempRow++;
            tempCol--;
        }
        //(4.b)check diagonal (North-East direction)
        tempRow = row-1;
        tempCol = col+1;
        while(isValidRow(tempRow) && isValidCol(tempCol) && this.boardState[tempRow][tempCol] == chosenPlayer && counterAmount != 4){
            counterAmount++;
            tempRow--;
            tempCol++;
        }

        return counterAmount==4;
    }
    /**
     * isGameWon checks for a given counter at a certain row and column, whether win conditions are satisfied
     * @param row The row position of the counter that was just slotted into the board
     * @param col The column position of the counter that was just slotted into the board
     * @return boolean that indicates if any player has satisfied the win conditions for the game
     */
    public boolean isGameWon(int row, int col){
        return checkHorizontalWinCondition(row,col) || checkVerticalWinCondition(row,col) || checkDiagonalNorthWestWinCondition(row,col) || checkDiagonalNorthEastWinCondition(row,col);
    }
    /**
     * hasGameDrawn checks if the number of counters is equal to the size of the board
     * @return boolean If the number of counters is equal to the size of the board
     */
    public boolean isGameDrawn(){
        return totalCountersPlaced >= boardState.length * boardState[0].length;
    }
    /**
     * playGame ask for a column to slot counter in. Validates the chosen column. Slots a counter into chosen column.
     */
    public void playGame(){
        while(gameStatus==0){
            try {
                slotCounter(getInputColumn());
                if(isGameDrawn()){
                    System.out.println("Game ends in a draw");
                    System.out.println("Press 0 to reset game.");
                    System.out.println("Press other natural numbers to end game");
                    try {
                        int option = Integer.parseInt(reader.readLine());
                        if(option==0){
                            resetGame();
                        } else {
                            System.out.println("Thank you for playing");
                            gameStatus=2;
                        }
                        continue;
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                //Check if current player has/not satisfied the win condition
                if(!isGameWon(lastCounterLocation.getRow(), lastCounterLocation.getCol())){
                    switchPlayer();
                } else {
                    System.out.println("Player " + this.getCurrentPlayer() +  " has won");
                    System.out.println("Press 0 to reset game.");
                    System.out.println("Press other natural numbers to end game");
                    try {
                        int option = Integer.parseInt(reader.readLine());
                        if(option==0){
                            resetGame();
                        } else {
                            System.out.println("Thank you for playing");
                            gameStatus=1;
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * getInputColumn will keep asking for column number until it is valid
     * @return int The chosen column, after passing all validation checks.
     */
    public int getInputColumn() throws IOException {
        boolean isColAccepted = false;
        int chosenColumn;
            do {
                System.out.println("Please select a column");
                 chosenColumn = Integer.parseInt(reader.readLine());
                if (isChosenColumnValid(chosenColumn)) {
                    isColAccepted=true;
                }
            } while(!isColAccepted);

            return chosenColumn;
    }

    /**
     * Prints out the current board state onto the console log
     */
    public void displayState(){
        System.out.println();
        for (int[] eachRow : boardState) {
            for (int j = 0; j < boardState[0].length; j++) {
                System.out.print(eachRow[j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * main method starts the connect4 game. It is played in the console log.
     * @param args
    public static void main(String[] args){
        System.out.println("Welcome");
        gameMechanics testGame = new gameMechanics();
        try {
            testGame.playGame();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    */
}

