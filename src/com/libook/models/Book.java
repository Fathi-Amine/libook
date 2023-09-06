package com.libook.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
//import java.util.List;

public class Book {
        private String isbn;
        private String title;
        private Auteur auteur;
        private Integer quantite;
        private Date datePublication;

        public Book(String isbn, String title, Auteur auteur, Integer quantite, Date datePublication) {
                this.isbn = isbn;
                this.title = title;
                this.auteur = auteur;
                this.quantite = quantite;
                this.datePublication = datePublication;
        }

        public Book() {
            this.isbn = null;
            this.title = null;
            this.auteur = null;
            this.quantite = 0;
            this.datePublication = null;
        }

        public String getIsbn() {
                return isbn;
        }

        public void setIsbn(String isbn) {
                this.isbn = isbn;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public Auteur getAuteur() {
                return auteur;
        }

        public void setAuteur(Auteur auteur) {
                this.auteur = auteur;
        }

        public int getQuantité() {
                return quantite;
        }

        public void setQuantité(Integer quantité) {
                this.quantite = quantité;
        }


        public Date getDate_publication() {
                return datePublication;
        }

        public void setDate_publication(Date date_publication) {
                this.datePublication = date_publication;
        }


        public void createBook(Connection connection,String isbn, String title, Auteur auteur, int quantite, Date datePublication) {
            String insertQuery = "INSERT INTO livre (isbn, title, auteur_serial_number, publication_date, quantity) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, isbn);
                preparedStatement.setString(2, title);
                preparedStatement.setInt(3, auteur.getSerialNumber());
                preparedStatement.setDate(4, new java.sql.Date(datePublication.getTime()));
                preparedStatement.setInt(5, quantite);


                int rowsInserted = preparedStatement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Book added successfully.");
                } else {
                    System.out.println("Failed to add book.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

       /* public void displayBookInfo() {
                return 0;
        }*/


       public void updateBookInfo(Connection connection, String newTitle, Auteur newAuteur, int newQuantite, Date newDatePublication) {
        String updateQuery = "UPDATE livre SET title = ?, auteur_serial_number = ?, publication_date = ?, quantity = ? WHERE isbn = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, newTitle);
            preparedStatement.setInt(2, newAuteur.getSerialNumber());
            preparedStatement.setDate(3, new java.sql.Date(newDatePublication.getTime()));
            preparedStatement.setInt(4, newQuantite);
            preparedStatement.setString(5, this.isbn);
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Book updated successfully.");
                this.title = newTitle;
                this.auteur = newAuteur;
                this.quantite = newQuantite;
                this.datePublication = newDatePublication;
            } else {
                System.out.println("No book found with ISBN: " + this.isbn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(Connection connection) {
        String deleteQuery = "DELETE FROM livre WHERE isbn = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, this.isbn);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Book deleted successfully.");
            } else {
                System.out.println("No book found with ISBN: " + this.isbn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

