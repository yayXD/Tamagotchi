package world.ucode.Interface;

import javafx.animation.AnimationTimer;
import javafx.geometry.NodeOrientation;
import world.ucode.Controller.GamePlayController;

import java.util.Random;

public class Timer extends AnimationTimer {

    private int millisecond = 0;
    private int XYStep = 0;

    private int moveToX = 0;
    private int moveToY = 0;
    private double stepToX = 0d;
    private double stepToY = 0d;
    private boolean moving = false;
    private boolean movingXSide = false; // false - left, true - right
    private boolean movingYSide = false; // false - down, true - up
    private boolean movingXEnd = false;
    private boolean movingYEnd = false;

    @Override
    public void handle(long l) {
        try {
            doHandle();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    private void doHandle() throws Exception {
        GamePlayController contr = Main.loaderPlay.getController();

        if(contr.health <= 0 || contr.happiness <= 0 || contr.hunger <= 0 || contr.thirst <= 0 || contr.cleanliness <= 0) {
            stop();

            Database db = new Database();
            db.deadPet(contr.namePet);

            Main main = new Main();
            Main.scene = Main.SceneLoad.EndScreen;
            main.start(Main.currentStage);
        } else {
            MovePet();

            contr.health -= contr.health / 10000;
            contr.hunger -= 0.00004;
            contr.happiness -= 0.00003;
            contr.thirst -= 0.00005;
            contr.cleanliness -= 0.00003;
            contr.setProgressBars(contr.health, contr.maxHP, contr.happiness, contr.hunger, contr.thirst, contr.cleanliness);
        }
    }

    private void MovePet() {
        GamePlayController contr = Main.loaderPlay.getController();

        if(moving) {
            XYStep++;

            /*System.out.println("Get X: " + contr.imagePet.getLayoutX());
            System.out.println("moveToX: " + moveToX);
            System.out.println("stepToX: " + stepToX);
            System.out.println();
            System.out.println("Get Y: " + contr.imagePet.getLayoutY());
            System.out.println("moveToY: " + moveToY);
            System.out.println("stepToY: " + stepToY);
            System.out.println();*/

            if(movingXSide && XYStep % 2 == 0 && !movingXEnd) {
                contr.imagePet.setLayoutX(contr.imagePet.getLayoutX() + stepToX);
                if(contr.imagePet.getLayoutX() > moveToX) {
                    contr.imagePet.setLayoutX(moveToX);
                    movingXEnd = true;
                }
            } else if(!movingXSide && XYStep % 2 == 0 && !movingXEnd) {
                contr.imagePet.setLayoutX(contr.imagePet.getLayoutX() - stepToX);
                if(contr.imagePet.getLayoutX() < moveToX) {
                    contr.imagePet.setLayoutX(moveToX);
                    movingXEnd = true;
                }
            } else if(!movingYSide && XYStep % 2 != 0 && !movingYEnd) {
                contr.imagePet.setLayoutY(contr.imagePet.getLayoutY() + stepToY);
                if(contr.imagePet.getLayoutY() > moveToY) {
                    contr.imagePet.setLayoutY(moveToY);
                    movingYEnd = true;
                }
            } else if(movingYSide && XYStep % 2 != 0 && !movingYEnd) {
                contr.imagePet.setLayoutY(contr.imagePet.getLayoutY() - stepToY);
                if(contr.imagePet.getLayoutY() < moveToY) {
                    contr.imagePet.setLayoutY(moveToY);
                    movingYEnd = true;
                }
            } else if(movingXEnd && movingYEnd) {
                moving = false;
                XYStep = 0;
            }
        } else if(millisecond > getRandomNumberIntsMilliS()) {

            moveToX = getRandomNumberIntsX();
            moveToY = getRandomNumberIntsY();

            if(contr.imagePet.getLayoutX() < moveToX) {
                stepToX = (moveToX - contr.imagePet.getLayoutX()) / 100d;
                contr.imagePet.nodeOrientationProperty().setValue(NodeOrientation.RIGHT_TO_LEFT);
                movingXSide = true;
            } else if(contr.imagePet.getLayoutX() > moveToX) {
                stepToX = (contr.imagePet.getLayoutX() - moveToX) / 100d;
                contr.imagePet.nodeOrientationProperty().setValue(NodeOrientation.LEFT_TO_RIGHT);
                movingXSide = false;
            } else {
                moveToX += 1d;
                stepToX += 1d;
                movingXSide = true;
            }

            if(contr.imagePet.getLayoutY() < moveToY) {
                stepToY = (moveToY - contr.imagePet.getLayoutY()) / 100d;
                movingYSide = false;
            } else if(contr.imagePet.getLayoutY() > moveToY) {
                stepToY = (contr.imagePet.getLayoutY() - moveToY) / 100d;
                movingYSide = true;
            } else {
                moveToY += 1d;
                stepToY += 1d;
                movingYSide = false;
            }

            millisecond = 0;
            movingXEnd = false;
            movingYEnd = false;
            moving = true;
        } else {
            //System.out.println(millisecond);
            millisecond++;
        }
    }

    public static int getRandomNumberIntsX() {
        Random rand = new Random();
        return rand.ints(20, (430 + 1)).findFirst().getAsInt();
    }

    public static int getRandomNumberIntsY() {
        Random rand = new Random();
        return rand.ints(105, (200 + 1)).findFirst().getAsInt();
    }

    public static int getRandomNumberIntsMilliS() {
        Random rand = new Random();
        return rand.ints(500, (1000 + 1)).findFirst().getAsInt();
    }
}
