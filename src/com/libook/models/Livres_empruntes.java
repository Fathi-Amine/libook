
package com.libook.models;
import com.libook.models.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

            String insertQuery = "INSERT INTO livres_empruntes (date_emprunt, date_retour, livre_isbn,member_serial_number, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setDate(1, new java.sql.Date(date_emprunt.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(date_retour.getTime()));
            preparedStatement.setString(3, book.getIsbn());
            preparedStatement.setInt(4, member.getSerialNumber());
            preparedStatement.setString(5, Status.BORROWED.name());

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
}

 /*   public Livres_empruntes afficher_livre_emprunte(Member member, Book book){}
    public void modifier_livre_emprunte(Member member, Book book, Date date_emprunt, Date date_retour){}
}*/


