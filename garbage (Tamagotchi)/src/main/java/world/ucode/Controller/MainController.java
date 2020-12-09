package world.ucode.Controller;

import javafx.fxml.FXML;
import world.ucode.Interface.Main;

public class MainController {

    @FXML
    public void newGame() throws Exception {
        Main main = new Main();
        Main.scene = Main.SceneLoad.CreateScreen;
        main.start(Main.currentStage);
    }

    @FXML
    public void loadGame() throws Exception {
        Main main = new Main();
        Main.scene = Main.SceneLoad.LoadScreen;
        main.start(Main.currentStage);
    }

    @FXML
    public void exitGame() {
        System.exit(0);
    }
}
