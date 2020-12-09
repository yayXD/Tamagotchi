package world.ucode.Interface;

import world.ucode.Controller.GamePlayController;

import java.sql.*;

public class Database {
    private static Connection connect;
    private static Statement stmt;
    private static ResultSet res;

    private void connectDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connect = DriverManager.getConnection("jdbc:sqlite:Tamagotchi.s3db");
        stmt = connect.createStatement();
    }

    private void closeDB() throws SQLException {
        stmt.close();
        connect.close();
    }

    public void firstStart() throws SQLException, ClassNotFoundException {
        connectDB();
        String sql = "CREATE TABLE IF NOT EXISTS Pets " +
                "(name             TEXT PRIMARY KEY NOT NULL, " +
                " password         char(50)     NOT NULL, " +
                " max_hp           double       NOT NULL, " +
                " image_id         int          NOT NULL, " +
                " health_stat      double       NOT NULL, " +
                " happiness_stat   double       NOT NULL, " +
                " hunger_stat      double       NOT NULL, " +
                " thirst_stat      double       NOT NULL, " +
                " cleanliness_stat double       NOT NULL);";
        stmt.executeUpdate(sql);
        closeDB();
    }

    public int createPet(String name, String pass, double maxHP, int image_ID) throws SQLException, ClassNotFoundException {
        int status = 1;
        connectDB();
        String sql = "INSERT INTO Pets(name,password,max_hp,image_id," +
                "health_stat,happiness_stat,hunger_stat,thirst_stat,cleanliness_stat) VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = connect.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, pass);
        pstmt.setDouble(3, maxHP);
        pstmt.setInt(4, image_ID);
        pstmt.setDouble(5, maxHP);
        pstmt.setDouble(6, 1);
        pstmt.setDouble(7, 1);
        pstmt.setDouble(8, 1);
        pstmt.setDouble(9, 1);
        try {
            pstmt.executeUpdate();
        } catch(Exception err) {
            if(err.getMessage().contains("(UNIQUE constraint failed: Pets.name)")) {
                status = 2;
            }
            System.out.println(err);
        }
        closeDB();
        return status;
    }

    public int checkPet(String name, String pass) throws SQLException, ClassNotFoundException {
        int status = 3;
        connectDB();

        System.out.println("the loadPet (Console)");

        String sql = "SELECT * FROM Pets WHERE name = \'" + name + "\';";

        res = stmt.executeQuery(sql);

        String password = "";
        try {
            password = res.getString("password");
        } catch(Exception err) {
            if(err.getMessage().contains("ResultSet closed")) {
                status = 4;
                closeDB();
                return status;
            }
            System.out.println(err);
        }

        /*if(password != pass) {
            System.out.println("invalid password \'" + password + "\' -- \'" + pass + "\' (Console)");
            System.out.println();
            status = 5;
            closeDB();
            return status;
        }*/

        closeDB();
        return status;
    }

    public void loadPet(String name) throws SQLException, ClassNotFoundException {
        connectDB();

        System.out.println("the loadPet (Console)");

        String sql = "SELECT * FROM Pets WHERE name = \'" + name + "\';";

        res = stmt.executeQuery(sql);

        GamePlayController.namePet = res.getString("name");
        GamePlayController.maxHP = res.getDouble("max_hp");
        GamePlayController.imageID = res.getInt("image_id");
        GamePlayController.health = res.getDouble("health_stat");
        GamePlayController.happiness = res.getDouble("happiness_stat");
        GamePlayController.hunger = res.getDouble("hunger_stat");
        GamePlayController.thirst = res.getDouble("thirst_stat");
        GamePlayController.cleanliness = res.getDouble("cleanliness_stat");

        GamePlayController contr = Main.loaderPlay.getController();
        contr.setProgressBars(GamePlayController.health, GamePlayController.maxHP, GamePlayController.happiness,
                                GamePlayController.hunger, GamePlayController.thirst, GamePlayController.cleanliness);
        contr.imagePet.setImage(GamePlayController.imagesArr[res.getInt("image_id")]);

        closeDB();
    }

    public void savePet(String name, double health, double happiness, double hunger, double thirst, double cleanliness) throws SQLException, ClassNotFoundException {
        connectDB();

        String sql = "UPDATE Pets SET health_stat = ?, happiness_stat = ?, hunger_stat = ?, thirst_stat = ?, cleanliness_stat = ? WHERE name = \'" + name + "\';";
        PreparedStatement pstmt = connect.prepareStatement(sql);

        pstmt.setDouble(1, health);
        pstmt.setDouble(2, happiness);
        pstmt.setDouble(3, hunger);
        pstmt.setDouble(4, thirst);
        pstmt.setDouble(5, cleanliness);

        pstmt.executeUpdate();

        closeDB();
    }

    public void deadPet(String name) throws SQLException, ClassNotFoundException {
        connectDB();

        String sql = "DELETE FROM Pets WHERE name = \'" + name + "\';";
        PreparedStatement pstmt = connect.prepareStatement(sql);

        pstmt.setString(1, name);

        pstmt.executeUpdate();

        closeDB();
    }

    /*public void selectNames() throws SQLException, ClassNotFoundException {
        connectDB();
        String sql = "SELECT name FROM Pets;";
        res = stmt.executeQuery(sql);
        while(res.next()) {
            System.out.println(res.getString("name"));
        }
        closeDB();
    }*/

}