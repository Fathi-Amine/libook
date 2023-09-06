
package com.libook.models;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Auteur {
    private String name;
    private int serialNumber;

    public Auteur(String name, int serialNumber) {
        this.name = name;
        this.serialNumber = serialNumber;
    }

    public Auteur() {
        this.name = null;
        this.serialNumber = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void createAuteur(Connection connection, Auteur auteur) {
        String insertQuery = "INSERT INTO auteur (name, serial_number) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, auteur.getName());
            statement.setInt(2, auteur.getSerialNumber());
            statement.executeUpdate();
            System.out.println("Author data inserted into the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*public Auteur getAuteurByName(String name) {
    }*/

    public void updateAuteur(Connection connection, int serialNumber, String newName) {
        if (!authorExists(connection, serialNumber)) {
            System.out.println("No author with the provided serial number found.");
            return; // Exit the method if the author doesn't exist
        }

        // If the author exists, proceed with the update
        String updateQuery = "UPDATE auteur SET name = ? WHERE serial_number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, serialNumber);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Author information updated successfully.");
            } else {
                System.out.println("Failed to update author information.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database-related exceptions here
        }
    }


    /*public void deleteAuteur(String name) {

    }*/

    public List<Auteur> getAuteurs(Connection connection) {
        List<Auteur> authors = new ArrayList<>();
        String selectQuery = "SELECT name, serial_number FROM auteur";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int serialNumber = resultSet.getInt("serial_number");

                Auteur author = new Auteur(name, serialNumber);
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public void deleteAuteur(Connection connection, int serialNumber) {
        if (!authorExists(connection, serialNumber)) {
            System.out.println("No author with the provided serial number found.");
            return;
        }

        String deleteQuery = "DELETE FROM auteur WHERE serial_number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, serialNumber);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Author deleted successfully.");
            } else {
                System.out.println("Failed to delete author.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private boolean authorExists(Connection connection, int serialNumber) {
        String selectQuery = "SELECT COUNT(*) FROM auteur WHERE serial_number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, serialNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}


