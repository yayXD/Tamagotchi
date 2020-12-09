package world.ucode.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import world.ucode.Interface.Database;
import world.ucode.Interface.Main;

public class LoadController {

    @FXML
    public TextField fieldName;
    @FXML
    public TextField fieldPass;
    @FXML
    public Text errText;


    @FXML
    public void loadBaton() throws Exception {
        Database db = new Database();
        int status = db.checkPet(fieldName.getText(), fieldPass.getText());
        printStatus(status);

        if(status == 3) {
            Main main = new Main();
            Main.scene = Main.SceneLoad.GamePlayScreen;
            main.start(Main.currentStage);
            db.loadPet(fieldName.getText());
        }
    }

    @FXML
    public void backBaton() throws Exception {
        Main main = new Main();
        Main.scene = Main.SceneLoad.MainScreen;
        main.start(Main.currentStage);
    }

    public void printStatus(int status) {
        if(status == 3) {
            errText.setText("Pet loaded.");
        }
        if(status == 4) {
            errText.setText("There is no pet with that name.");
        }
        if(status == 5) {
            errText.setText("Invalid password.");
        }
    }
}
