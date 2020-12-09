package world.ucode.Interface;

import javafx.animation.AnimationTimer;
import world.ucode.Controller.GamePlayController;

import java.util.Random;

public class Timer extends AnimationTimer {

    private int millisecond = 0;

    private int moveToX = 0;
    private int moveToY = 0;
    private double stepToX = 0d;
    private double stepToY = 0d;
    private boolean moving = false;
    private boolean movingWay = false; // false - left, true - right

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

            contr.health -= 0.0002;
            contr.hunger -= 0.0005;
            contr.happiness -= 0.0003;
            contr.thirst -= 0.0005;
            contr.cleanliness -= 0.0003;
            contr.setProgressBars(contr.health, contr.maxHP, contr.happiness, contr.hunger, contr.thirst, contr.cleanliness);
        }
    }

    private void MovePet() {
        System.out.println(millisecond);
        if(moving) {
            GamePlayController contr = Main.loaderPlay.getController();

            System.out.println("Get X: " + contr.imagePet.getX());
            System.out.println("moveToX: " + moveToX);
            System.out.println("stepToX: " + stepToX);
            System.out.println();
            System.out.println("Get Y: " + contr.imagePet.getY());
            System.out.println("moveToY: " + moveToY);
            System.out.println("stepToY: " + stepToY);
            System.out.println();

            if(contr.imagePet.getX() < moveToX) {
                contr.imagePet.setX(contr.imagePet.getX() + stepToX);
                if(contr.imagePet.getX() > moveToX) {
                    contr.imagePet.setX(moveToX);
                    moving = false;
                }
            } else if(contr.imagePet.getX() > moveToX) {
                contr.imagePet.setX(contr.imagePet.getX() - stepToX);
                if(contr.imagePet.getX() < moveToX) {
                    contr.imagePet.setX(moveToX);
                    moving = false;
                }
            } else if(contr.imagePet.getY() < moveToY) {
                contr.imagePet.setY(contr.imagePet.getY() + stepToY);
                if(contr.imagePet.getY() > moveToY) {
                    contr.imagePet.setY(moveToY);
                    moving = false;
                }
            } else if(contr.imagePet.getY() > moveToY) {
                contr.imagePet.setY(contr.imagePet.getY() - stepToY);
                if(contr.imagePet.getY() < moveToY) {
                    contr.imagePet.setY(moveToY);
                    moving = false;
                }
            } else {
                moving = false;
            }
        } else if(millisecond == 1500) {
            GamePlayController contr = Main.loaderPlay.getController();

            moveToX = getRandomNumberIntsX();
            moveToY = getRandomNumberIntsY();

            if(contr.imagePet.getX() < moveToX) {
                int x = contr.imagePet.getX()
            }

            stepToX = moveToX / 1000;
            stepToY = moveToY / 1000;

            millisecond = 0;
            moving = true;
        } else {
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
}
