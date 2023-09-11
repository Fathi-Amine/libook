
package com.libook.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Member {
    private String name;
    private int serialNumber;

    public Member(String name, int serialNumber) {
        this.name = name;
        this.serialNumber = serialNumber;
    }

    public Member() {
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

    public void createMember(Connection connection, Member member) {
        String sqlQuery = "INSERT INTO member (name, serial_number) VALUES (?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)){
            statement.setString(1, member.getName());
            statement.setInt(2, member.getSerialNumber());
            statement.executeUpdate();
            System.out.println("Author data inserted into the database.");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    public void updateMember(Connection connection, int serialNumber, String name) {
        if (!memberExists(connection, serialNumber)){
            System.out.println("No member with the provided serial number found.");
            return;
        }

        String sqlQuery = "UPDATE member SET name = ? WHERE serial_number = ? ";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)){
            statement.setString(1, name);
            statement.setInt(2, serialNumber);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0){
                System.out.println("Member information updated successfully.");
            } else {
                System.out.println("Failed to update member information.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    private boolean memberExists(Connection connection, int serialNumber) {
        String selectQuery = "SELECT COUNT(*) FROM member WHERE serial_number = ?";

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

    public void deleteMember(Connection connection, int serialNumber) {
        if(!memberExists(connection, serialNumber)){
            System.out.println("No member with the provided serial number found.");
            return;
        }

        String sqlQuery = "DELETE FROM member WHERE serial_number = ?";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)){
            statement.setInt(1, serialNumber);
            int rowsDeleted = statement.executeUpdate();

            if(rowsDeleted > 0){
                System.out.println("Member deleted succefully");
            }else {
                System.out.println("Failed to delete member.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        };
    }

    public int getNumberOfMembers(Connection connection) {
        try {
            String query = "SELECT COUNT(*) FROM member";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
/*
    public static List<Member> getAllMembers() {}*/
}


