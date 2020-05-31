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

    // Додати масив моделей
    public void addRange(GpsModel[] models) {
        Connection connection = this.connect();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                for (int i = 0; i < models.length; i++) {
                    String query = "INSERT INTO `library`.`trackers` (id, name, coordinates) \n" +
                            " VALUES (" + null + ", '" + models[i].id + "', '" + models[i].coordinates + "');";
                    stmt.executeUpdate(query);
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Видалити запис
    public void deleteRow(int id) {
        Connection connection = this.connect();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = "Delete from `library`.`trackers`  where id=" + id + "; ";
                stmt.executeUpdate(query);
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void getInRange(float x1, float x2, float y1, float y2) {
        Connection connection = this.connect();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String query = String.format("select * from trackers where (coordinatex between %.2f and %.2f ) and (coordinatey between %.2f and %.2f);", x1, x2, y1, y2).replace(',', '.');
                ResultSet res = stmt.executeQuery(query);
                while (res.next()) {
                    System.out.println(res.getInt(1) + " " + res.getString(2) + " " + res.getString(3) + " " + res.getString(4));
                }
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateRow(int id, String name, float x, float y) {
        Connection connection = this.connect();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                String coordX = String.format("coordinatex = %.2f", x).replace(',', '.');
                String coordy = String.format("coordinatey = %.2f", y).replace(',', '.');
                String query = String.format("update trackers set name= " + "'%s'" + "," + coordX + "," + coordy + " where id=%d;", name, id);
                stmt.executeUpdate(query);
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

