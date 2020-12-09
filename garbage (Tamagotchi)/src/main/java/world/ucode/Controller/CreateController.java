package world.ucode.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import world.ucode.Interface.Database;
import world.ucode.Interface.Main;

public class CreateController {
    public int intImages = 0;

    public Image img1 = new Image("/images/1.jpg");
    public Image img2 = new Image("/images/2.jpg");
    public Image img3 = new Image("/images/3.jpg");

    @FXML
    public TextField fieldName;
    @FXML
    public TextField fieldPass;
    @FXML
    public TextField fieldMaxHP;
    @FXML
    public ImageView petImage;
    @FXML
    public Text errText;

    @FXML
    public void createBaton() throws Exception {
        /*Database db = new Database();
        int status = db.createPet(fieldName.getText(), fieldPass.getText(),
                                    Double.parseDouble(fieldMaxHP.getText()), intImages);
        printStatus(status);*/

        /*if(status == 1) {
            Main main = new Main();
            Main.scene = Main.SceneLoad.GamePlayScreen;
            main.start(Main.currentStage);
            db.loadPet(fieldName.getText());
        }*/
    }

    @FXML
    public void leftBaton() throws Exception {
        intImages--;
        if(intImages < 0) {
            intImages = 2;
        }
        if(intImages == 0) {
            petImage.setImage(img1);
        }
        if(intImages == 1) {
            petImage.setImage(img2);
        }
        if(intImages == 2) {
            petImage.setImage(img3);
        }
    }

    @FXML
    public void rightBaton() throws Exception {
        intImages++;
        if(intImages > 2) {
            intImages = 0;
        }
        if(intImages == 0) {
            petImage.setImage(img1);
        }
        if(intImages == 1) {
            petImage.setImage(img2);
        }
        if(intImages == 2) {
            petImage.setImage(img3);
        }
    }

    @FXML
    public void backBaton() throws Exception {
        Main main = new Main();
        Main.scene = Main.SceneLoad.MainScreen;
        main.start(Main.currentStage);
    }

    public void printStatus(int status) {
        if(status == 1) {
            errText.setText("Pet created.");
        }
        if(status == 2) {
            errText.setText("This name already exists.");
        }
    }
}
