package world.ucode.Controller;

import javafx.fxml.FXML;
import world.ucode.Interface.Main;

public class EndController {

    @FXML
    public void menuGame() throws Exception {
        Main main = new Main();
        Main.scene = Main.SceneLoad.MainScreen;
        main.start(Main.currentStage);
    }

    @FXML
    public void exitGame() {
        System.exit(0);
    }
}
