package com.libook.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.List;

public class Book {
        private String isbn;
        private String title;
        private Auteur auteur;
        private Date datePublication;
        private Integer quantite;

        private int quantite_disponible;

        public Book(String isbn, String title, Auteur auteur, Integer quantite, Date datePublication, int quantite_disponible) {
                this.isbn = isbn;
                this.title = title;
                this.auteur = auteur;
                this.quantite = quantite;
                this.datePublication = datePublication;
                this.quantite_disponible = quantite_disponible;
        }

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
            this.quantite_disponible = 0;
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

        public void setAvailableQuantity(int quantite_disponible){
            this.quantite_disponible = quantite_disponible;
        }

        public int getQuantite_disponible(){
            return quantite_disponible;
        }

        public void createBook(Connection connection,String isbn, String title, Auteur auteur, int quantite, Date datePublication) {
            String insertQuery = "INSERT INTO livre (isbn, title, auteur_serial_number, publication_date, quantity, quantite_disponible) VALUES (?, ?, ?, ?, ?,?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, isbn);
                preparedStatement.setString(2, title);
                preparedStatement.setInt(3, auteur.getSerialNumber());
                preparedStatement.setDate(4, new java.sql.Date(datePublication.getTime()));
                preparedStatement.setInt(5, quantite);
                preparedStatement.setInt(6, quantite);


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

    public List<Book> searchBooks(Connection connection, String searchCriteria) {
        List<Book> searchResults = new ArrayList<>();
        String selectQuery = "SELECT * FROM livre WHERE title LIKE ? OR auteur_serial_number IN (SELECT serial_number FROM auteur WHERE name LIKE ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            String searchTerm = "%" + searchCriteria + "%";
            preparedStatement.setString(1, searchTerm);
            preparedStatement.setString(2, searchTerm);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                int auteurSerialNumber = resultSet.getInt("auteur_serial_number");
                Date publicationDate = resultSet.getDate("publication_date");
                int quantity = resultSet.getInt("quantity");

                Auteur auteur = new Auteur();
                auteur = auteur.getAuteurBySerialNumber(connection, auteurSerialNumber);

                Book book = new Book(isbn, title, auteur, quantity, publicationDate);
                searchResults.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }

    public List<Book> displayBooks(Connection connection){
        List<Book> availableBooks = new ArrayList<>();
        String sqlQuery = "SELECT * FROM livre WHERE quantite_disponible > 0";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                    String isbn = resultSet.getString("isbn");
                    String title = resultSet.getString("title");
                    int auteurSerialNumber = resultSet.getInt("auteur_serial_number");
                    int quantity = resultSet.getInt("quantity");
                    java.sql.Date publicationDate = resultSet.getDate("publication_date");
                    Auteur auteur = new Auteur();
                    auteur = auteur.getAuteurBySerialNumber(connection, auteurSerialNumber);
                    Book book = new Book(isbn, title, auteur, quantity, publicationDate);
                    availableBooks.add(book);

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return availableBooks;
    }

    public int getQuantiteDisponibleForBook(Connection connection, String bookIsbn) {
        try {
            String sqlQuery = "SELECT quantite_disponible FROM livre WHERE isbn = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, bookIsbn);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("quantite_disponible");
            } else {
                System.out.println("Book you wanna borrow is not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int getQuantityOfBooksInLibrary(Connection connection, String isbn) {
        try {
            String query = "SELECT quantite_disponible FROM livre WHERE isbn = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, isbn);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("quantite_disponible");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getTotalNumberOfBooks(Connection connection) {
        try {
            String query = "SELECT SUM(quantite_disponible) FROM livre";
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
}

