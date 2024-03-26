import java.io.FileInputStream;

import java.io.IOException;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class InsertImage {

    public static void main(String[] args) throws SQLException {

        String url = "jdbc:postgresql://localhost:5432/foodorderingsystem";

        String user = "postgres";

        String password = "1021";


        try (Connection connection = DriverManager.getConnection(url, user, password)) {

            String query = "INSERT INTO food_items (food_id, food_name, food_des, food_price, food_image) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);


            FileInputStream fileInputStream = new FileInputStream("/home/siddharth/Downloads/frombg1(2).jpg");

            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "chicken");
            preparedStatement.setString(3, "tasty");
            preparedStatement.setString(4, "250");

            preparedStatement.setBinaryStream(5, fileInputStream, (int) fileInputStream.available());


            preparedStatement.executeUpdate();

            preparedStatement.close();

            fileInputStream.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}