package world.ucode.Interface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {
    private Parent root;

    public static enum SceneLoad {
        PreLoad,
        MainScreen,
        CreateScreen,
        LoadScreen,
        GamePlayScreen,
        EndScreen
    };

    public static SceneLoad scene = SceneLoad.PreLoad;

    public static Stage currentStage;

    public static FXMLLoader loaderStart;
    public static FXMLLoader loaderLoad;
    public static FXMLLoader loaderCreate;
    public static FXMLLoader loaderPlay;
    public static FXMLLoader loaderEnd;

    @Override
    public void start(Stage primaryStage) throws Exception {
        if(scene == SceneLoad.PreLoad) {
            Database db = new Database();
            db.firstStart();

            scene = SceneLoad.MainScreen;
        }

        LoadScene();
        showCurStage(primaryStage);
    }

    public static void main(String args[]) {
        Application.launch(args);
    }

    private void LoadScene() throws Exception {
        if(scene == SceneLoad.MainScreen) {
            loaderStart = new FXMLLoader(getClass().getResource("/fxml/MainScene.fxml"));
            root = loaderStart.load();
        }
        if(scene == SceneLoad.LoadScreen) {
            loaderLoad = new FXMLLoader(getClass().getResource("/fxml/Load.fxml"));
            root = loaderLoad.load();
        }
        if(scene == SceneLoad.CreateScreen) {
            loaderCreate = new FXMLLoader(getClass().getResource("/fxml/Create.fxml"));
            root = loaderCreate.load();
        }
        if(scene == SceneLoad.GamePlayScreen) {
            loaderPlay = new FXMLLoader(getClass().getResource("/fxml/GamePlay.fxml"));
            root = loaderPlay.load();
        }
        if(scene == SceneLoad.EndScreen) {
            loaderEnd = new FXMLLoader(getClass().getResource("/fxml/Dead.fxml"));
            root = loaderEnd.load();
        }
    }

    private void showCurStage(Stage primaryStage) {
        currentStage = primaryStage;
        currentStage.setScene(new Scene(root));
        currentStage.setMinWidth(600);
        currentStage.setMinHeight(400);
        currentStage.setResizable(false);
        currentStage.setTitle("Tamagotchi");
        currentStage.show();
    }
}
