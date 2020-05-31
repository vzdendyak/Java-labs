package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBaseConnection {
    // Метод для встановлення зєднання
    public Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useUnicode=false&characterEncoding=utf8&serverTimezone=UTC", "root", "");
        } catch (SQLException ex) {
            System.out.println("Connection failed!!!");
            ex.printStackTrace();
            return null;
        }
        if (connection == null) {
            System.out.println("Failed");
        }
        return connection;
    }

    // Додавання 1 рядка
    public void addRow(TrackerEntity track) {
        Connection connection = this.connect();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "INSERT INTO `library`.`trackers` (id, name, Coordinates) \n" +
                        " VALUES (" + null + ", '" + track.name + "', '" + track.coordinates + "');";
                stmt.executeUpdate(query);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public TrackerEntity[] selectAll() {
        Connection connection = this.connect();
        if (connection != null) {
            try {
                ArrayList<TrackerEntity> trackers = new ArrayList<TrackerEntity>();
                Statement stmt = connection.createStatement();
                ResultSet res = stmt.executeQuery("select * from trackers");
                //TrackerEntity[] trackers2 = new TrackerEntity[];
                while (res.next()) {
                    TrackerEntity tracker = new TrackerEntity(res.getInt(1),res.getString(2),res.getString(3));
                    trackers.add(tracker);
                    //System.out.println(res.getInt(1) + " " + res.getString(2) + " " + res.getString(3));
                }
                connection.close();
                return  trackers.toArray(new TrackerEntity[0]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public TrackerEntity selectById(int id) {
        Connection connection = this.connect();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                ResultSet res = stmt.executeQuery("select * from trackers where id ="+id+";");
                TrackerEntity track=null;
                while (res.next()) {
                     track = new TrackerEntity(res.getInt(1),res.getString(2),res.getString(3));
                    //System.out.println(res.getInt(1) + " " + res.getString(2) + " " + res.getString(3));
                }
                connection.close();
                return track;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void clean() {
        Connection connection = this.connect();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "truncate table trackers;";
                stmt.executeUpdate(query);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

