package world.ucode.Controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import world.ucode.Interface.Database;
import world.ucode.Interface.Main;
import world.ucode.Interface.Timer;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GamePlayController implements Initializable {
    public static double upProgress = 0.05d;
    public static double maxProgress = 1.0d;

    public static String namePet;
    public static double maxHP;
    public static int imageID;
    public static double health;
    public static double happiness;
    public static double hunger;
    public static double thirst;
    public static double cleanliness;

    public static Image[] imagesArr = new Image[3];

    private AnimationTimer timer = new Timer();

    @FXML
    public ImageView imagePet;

    @FXML
    public ProgressBar healthBar;
    @FXML
    public ProgressBar happinessBar;
    @FXML
    public ProgressBar hungerBar;
    @FXML
    public ProgressBar thirstBar;
    @FXML
    public ProgressBar cleanlinessBar;

    @FXML
    public void medBaton() {
        upProgressStat(healthBar);
    }
    @FXML
    public void playBaton() {
        upProgressStat(happinessBar);
    }
    @FXML
    public void feedBaton() {
        upProgressStat(hungerBar);
    }
    @FXML
    public void waterBaton() {
        upProgressStat(thirstBar);
    }
    @FXML
    public void cleanBaton() {
        upProgressStat(cleanlinessBar);
    }

    private void SaveGame() throws SQLException, ClassNotFoundException {
        timer.stop();

        Database db = new Database();
        db.savePet(namePet, health, happiness, hunger, thirst, cleanliness);
    }

    @FXML
    public void SnEBaton() throws Exception {
        SaveGame();

        Main main = new Main();
        Main.scene = Main.SceneLoad.MainScreen;
        main.start(Main.currentStage);

        //System.exit(0);
    }

    public void upProgressStat(ProgressBar bar) {
        if(bar == healthBar) {
            health += upProgress * 100;
            if (health > maxHP) {
                health = maxHP;
            }
            bar.setProgress((health / (maxHP / 100d)) / 100d);
            System.out.println((health / (maxHP / 100d)) / 100d);
        }
        if(bar == happinessBar) {
            happiness += upProgress;
            if(happiness > maxProgress) {
                happiness = maxProgress;
            }
            bar.setProgress(happiness);
        }
        if(bar == hungerBar) {
            hunger += upProgress;
            if(hunger > maxProgress) {
                hunger = maxProgress;
            }
            bar.setProgress(hunger);
        }
        if(bar == thirstBar) {
            thirst += upProgress;
            if(thirst > maxProgress) {
                thirst = maxProgress;
            }
            bar.setProgress(thirst);
        }
        if(bar == cleanlinessBar) {
            cleanliness += upProgress;
            if(cleanliness > maxProgress) {
                cleanliness = maxProgress;
            }
            bar.setProgress(cleanliness);
        }
    }

    public void setProgressBars(double health, double maxHP, double happiness, double hunger, double thirst, double cleanliness) {
        healthBar.setProgress((health / (maxHP / 100d)) / 100d);
        happinessBar.setProgress(happiness);
        hungerBar.setProgress(hunger);
        thirstBar.setProgress(thirst);
        cleanlinessBar.setProgress(cleanliness);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imagesArr[0] = new Image("/images/1.jpg");
        imagesArr[1] = new Image("/images/2.jpg");
        imagesArr[2] = new Image("/images/3.jpg");

        /*healthBar.setProgress((health / (maxHP / 100d)) / 100d);
        happinessBar.setProgress(happiness);
        hungerBar.setProgress(hunger);
        thirstBar.setProgress(thirst);
        cleanlinessBar.setProgress(cleanliness);*/

        timer.start();
    }
}
