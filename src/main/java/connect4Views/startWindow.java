package connect4Views;

import connect4Controllers.connect4Controller;
import connect4Models.gameMechanics;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class startWindow extends Application {
    /**
     * Field variable to represent the stage of this whole application
     */
    private Stage stage;

    /**
     * Setter method that sets the scene of the stage for this connect4 game
     * @param scene The scene that will be set as the stage's scene
     */
    public void setScene(Scene scene){
        stage.setScene(scene);
    }

    @Override
    public void start(Stage arg0) throws Exception {
        stage = arg0;
        connect4View connect4view = new connect4View();
        gameMechanics gamemechanics = new gameMechanics();
        connect4Controller connect4controller = new connect4Controller(connect4view, gamemechanics);

        setScene(connect4controller.getConnect4view().getGameScene());
        arg0.setTitle("");
        arg0.setResizable(false);
        arg0.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}