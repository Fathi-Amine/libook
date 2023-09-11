
package com.libook.models;
import com.libook.models.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Livres_empruntes {
    private Member member;
    private Book book;
    private Date date_emprunt;
    private Date date_retour;
    private Boolean lost;

    private Status status;

    public Livres_empruntes(Member member, Book book, Date date_emprunt, Date date_retour, Boolean lost) {
        this.member = member;
        this.book = book;
        this.date_emprunt = date_emprunt;
        this.date_retour = date_retour;
        this.lost = lost;
        this.status = Status.BORROWED;
    }


    public Livres_empruntes() {
        this.member = null;
        this.book = null;
        this.date_emprunt = null;
        this.date_retour = null;
        this.lost = null;
        this.status = null;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }


    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getBorrowingDate() {
        return date_emprunt;
    }

    public void setBorrowingDate(Date date_emprunt) {
        this.date_emprunt = date_emprunt;
    }


    public Date getReturnDate() {
        return date_retour;
    }

    public void setReturnDate(Date date_retour) {
        this.date_retour = date_retour;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void emprunter_livre(Connection connection, Member member, Book book, Date date_emprunt, Date date_retour){
        try {

            int availableQuantity = book.getQuantiteDisponibleForBook(connection, book.getIsbn());

            if (availableQuantity <= 0) {
                System.out.println("This book is not available for borrowing as there are no copies left.");
                return;
            }

            if (hasBorrowedBook(connection, member.getSerialNumber())) {
                System.out.println("You already have a borrowed book. Return it before borrowing another one.");
                return;
            }

            String insertQuery = "INSERT INTO livres_empruntes (date_emprunt, date_retour, livre_isbn,member_serial_number, status, lost) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setDate(1, new java.sql.Date(date_emprunt.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(date_retour.getTime()));
            preparedStatement.setString(3, book.getIsbn());
            preparedStatement.setInt(4, member.getSerialNumber());
            preparedStatement.setString(5, Status.BORROWED.name());
            preparedStatement.setBoolean(6, false);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("The book has been borrowed successfully.");
            } else {
                System.out.println("Unable to borrow the book. Please try again later.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while processing your request.");
        }
    }

    private boolean isBookBorrowedByMember(Connection connection, String bookIsbn, String memberSerialNumber) {
        try {
            String query = "SELECT COUNT(*) FROM livres_empruntes WHERE livre_isbn = ? AND member_serial_number = ? AND status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, bookIsbn);
            preparedStatement.setString(2, memberSerialNumber);
            preparedStatement.setString(3, Status.BORROWED.name());

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

    private boolean hasBorrowedBook(Connection connection, int memberSerialNumber) {
        try {
            String query = "SELECT COUNT(*) FROM livres_empruntes WHERE member_serial_number = ? AND status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, memberSerialNumber);
            preparedStatement.setString(2, Status.BORROWED.name());

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

    public void returnBook(Connection connection, String bookIsbn, String memberSerialNumber) {
        try {
            if (!isBookBorrowedByMember(connection, bookIsbn, memberSerialNumber)) {
                System.out.println("The book is not currently borrowed by the member.");
                return;
            }

            String updateQuery = "UPDATE livres_empruntes SET status = ?, date_retour = ? WHERE livre_isbn = ? AND member_serial_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.setString(1, Status.RETURNED.name());
            preparedStatement.setDate(2, new java.sql.Date(new Date().getTime()));
            preparedStatement.setString(3, bookIsbn);
            preparedStatement.setString(4, memberSerialNumber);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("The book has been successfully marked as returned.");
            } else {
                System.out.println("Unable to mark the book as returned. Please try again later.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while processing your request.");
        }
    }

    public void declareBookAsLost(Connection connection, String bookIsbn, String memberSerialNumber) {
        try {
            if (!isBookBorrowedByMember(connection, bookIsbn, memberSerialNumber)) {
                System.out.println("The book is not currently borrowed by the member.");
                return;
            }

            String updateQuery = "UPDATE livres_empruntes SET lost = ? WHERE livre_isbn = ? AND member_serial_number = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, bookIsbn);
            preparedStatement.setString(3, memberSerialNumber);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("The book has been declared as lost.");
            } else {
                System.out.println("Unable to declare the book as lost. Please try again later.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while processing your request.");
        }
    }

    //Statistics Part

    public int getNumberOfLostBooks(Connection connection) {
        try {
            String query = "SELECT COUNT(*) FROM livres_empruntes WHERE lost = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, true);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getNumberOfBorrowedBooks(Connection connection) {
        try {
            String query = "SELECT COUNT(*) FROM livres_empruntes WHERE status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "BORROWED");

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getNumberOfReturnedBooks(Connection connection) {
        try {
            String query = "SELECT COUNT(*) FROM livres_empruntes WHERE status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "RETURNED");

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }




}



 /*   public Livres_empruntes afficher_livre_emprunte(Member member, Book book){}
    public void modifier_livre_emprunte(Member member, Book book, Date date_emprunt, Date date_retour){}
}*/


