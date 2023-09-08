package com.libook;
import com.libook.db.DbConnection;
import com.libook.models.Auteur;
import com.libook.models.Book;
import com.libook.models.Livres_empruntes;
import com.libook.models.Member;

import java.sql.*;
import java.text.ParseException;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DbConnection.getConnection();

        // Check if the connection is not null (indicating a successful connection)
        if (connection != null) {
            System.out.println("Connected to the database.");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                // Display the menu
                displayMenu();

                // Read the user's choice
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        createAuthor();
                        break;
                    case 2:
                        viewAllAuthors();
                        break;
                    case 3:
                        updateAuthor();
                        break;
                    case 4:
                        deleteAuthor();
                        break;
                    case 5:
                        addBook();
                        break;
                    case 6:
                        updateBook();
                        break;
                    case 7:
                        deleteBook();
                    case 8:
                        getAuteurBySn();
                    case 9:
                        searchBooks();
                        break;
                    case 10:
                        showAvailableBooks();
                        break;
                    case 11:
                        createMember();
                        break;
                    case 12:
                        updateMember();
                        break;
                    case 13:
                        deleteMember();
                        break;
                    case 14:
                        borrow_book();
                        break;
                    case 15:
                        exitProgram();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

        } else {
            System.err.println("Failed to establish a database connection.");
        }
    }
    private static void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Create an author");
        System.out.println("2. View all authors");
        System.out.println("3. Update author information");
        System.out.println("4. Delete author information");
        System.out.println("5. Add a book");
        System.out.println("6. Update a book");
        System.out.println("7. delete a book");
        System.out.println("8. get an author by serial number");
        System.out.println("9. search books");
        System.out.println("10. Display books");
        System.out.println("11. Create Member");
        System.out.println("12. Update a Member");
        System.out.println("14. Borrow");
        System.out.println("15. Exit");
        System.out.print("Enter your choice (1/2/3/4/5/6/7/8/9/10/11/12/13): ");
    }
    private static void printAuthorsGrid(List<Auteur> authors) {
        System.out.println("Authors:");
        System.out.println("---------------------------------------------------");
        System.out.println("|  Name           |  Serial Number  |");
        System.out.println("---------------------------------------------------");

        for (Auteur a : authors) {
            System.out.printf("|  %-15s  |  %-13d  |\n", a.getName(), a.getSerialNumber());
        }

        System.out.println("---------------------------------------------------");
    }

    private static void createAuthor(){
        Connection connection = DbConnection.getConnection();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the author serial number");
        int serial_number = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the author name");
        String nom_auteur = scanner.nextLine();

        Auteur nouveau_auteur = new Auteur(nom_auteur, serial_number);

        nouveau_auteur.createAuteur(connection, nouveau_auteur);
        System.out.println("Database connection closed.");
    }

    private static void viewAllAuthors() {
        Connection connection = DbConnection.getConnection();

        Auteur author = new Auteur();

        List<Auteur> authors = author.getAuteurs(connection);

        if (!authors.isEmpty()) {
            System.out.println("\nList of Authors:");
            for (Auteur auteur : authors) {
                System.out.println("Name: " + auteur.getName() + ", Serial Number: " + auteur.getSerialNumber());
            }
        } else {
            System.out.println("No authors found in the database.");
        }
    }

    private static void updateAuthor() {
        Connection connection = DbConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the author's serial number: ");
        int serialNumber = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the new author's name: ");
        String newName = scanner.nextLine();
        Auteur author = new Auteur();
        author.updateAuteur(connection, serialNumber, newName);
    }

    private static void deleteAuthor() {
        Connection connection = DbConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the author's serial number to delete: ");
        int serialNumber = scanner.nextInt();
        scanner.nextLine();
        Auteur author = new Auteur();

        author.deleteAuteur(connection, serialNumber);
    }


    private static void addBook() {
        Connection connection = DbConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Enter Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Author's Serial Number: ");
        int authorSerialNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Quantity: ");
        int quantite = scanner.nextInt();
        scanner.nextLine();


        System.out.print("Enter Date of Publication (YYYY-MM-DD): ");
        String datePublicationStr = scanner.nextLine();

        Date datePublication = null;
        try {
            datePublication = new SimpleDateFormat("yyyy-MM-dd").parse(datePublicationStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Use YYYY-MM-DD.");
            return;
        }

        Auteur auteur = new Auteur(null, authorSerialNumber);

        Book book = new Book();

        book.createBook(connection, isbn, title, auteur, quantite, datePublication);

    }

    private static void updateBook() {
        Connection connection = DbConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ISBN of the book you want to update: ");
        String isbnToUpdate = scanner.nextLine();

        System.out.print("Enter new title: ");
        String newTitle = scanner.nextLine();

        System.out.print("Enter new author's serial number: ");
        int newAuthorSerialNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new quantity: ");
        int newQuantite = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new Date of Publication (YYYY-MM-DD): ");
        String newDatePublicationStr = scanner.nextLine();

        Date newDatePublication = null;
        try {
            newDatePublication = new SimpleDateFormat("yyyy-MM-dd").parse(newDatePublicationStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Use YYYY-MM-DD.");
            return;
        }

        Book book = new Book();
        book.setIsbn(isbnToUpdate);

        Auteur newAuteur = new Auteur(null, newAuthorSerialNumber);

        book.updateBookInfo(connection, newTitle, newAuteur, newQuantite, newDatePublication);
    }

    private static void deleteBook() {
        Connection connection = DbConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ISBN of the book you want to delete: ");
        String isbnToDelete = scanner.nextLine();

        Book book = new Book();
        book.setIsbn(isbnToDelete);

        book.deleteBook(connection);
    }

    private static void getAuteurBySn(){
        Connection connection = DbConnection.getConnection();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the author serial number");
        int serial_number = scanner.nextInt();
        scanner.nextLine();
        Auteur auteur = new Auteur();
        auteur = auteur.getAuteurBySerialNumber(connection, serial_number);
        if (auteur != null) {
            System.out.println("Author Details:");
            System.out.println("Serial Number: " + auteur.getSerialNumber());
            System.out.println("Name: " + auteur.getName());
            // Add more details as needed
        } else {
            System.out.println("Author not found for serial number: " + serial_number);
        }

    }

    private static void searchBooks() {
        Connection connection = DbConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter search criteria (title or author name): ");
        String searchCriteria = scanner.nextLine();

        Book book = new Book();
        List<Book> searchResults = book.searchBooks(connection, searchCriteria);

        if (!searchResults.isEmpty()) {
            System.out.println("\nSearch Results:");
            for (Book result : searchResults) {
                System.out.println("ISBN: " + result.getIsbn());
                System.out.println("Title: " + result.getTitle());
                System.out.println("Author: " + result.getAuteur().getName());
                System.out.println("Publication Date: " + result.getDate_publication());
                System.out.println("Quantity: " + result.getQuantité());
                System.out.println();
            }
        } else {
            System.out.println("No matching books found.");
        }
    }

    private static void showAvailableBooks() {
        Connection connection = DbConnection.getConnection();
        Book book = new Book();

        List<Book> availableBooks = book.displayBooks(connection);
        if (!availableBooks.isEmpty()) {
            System.out.println("\nAvailable Books:");
            for (Book availableBook : availableBooks) {
                System.out.println("ISBN: " + availableBook.getIsbn());
                System.out.println("Title: " + availableBook.getTitle());
                System.out.println("Author: " + availableBook.getAuteur().getName());
                System.out.println("Publication Date: " + availableBook.getDate_publication());
                System.out.println("Quantity: " + availableBook.getQuantité());
                System.out.println();
            }
        } else {
            System.out.println("No available books found.");
        }
    }

    private static void createMember(){
        Connection connection = DbConnection.getConnection();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Member serial number");
        int serial_number = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the member name");
        String nom_membre = scanner.nextLine();

        Member nouveau_membre = new Member(nom_membre, serial_number);

        nouveau_membre.createMember(connection, nouveau_membre);
        System.out.println("Database connection closed.");
    }

    private static void updateMember() {
        Connection connection = DbConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the member's serial number: ");
        int serialNumber = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the new member's name: ");
        String newName = scanner.nextLine();
        Member member = new Member();
        member.updateMember(connection, serialNumber, newName);
    }
    private static void deleteMember() {
        Connection connection = DbConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the member's serial number to delete: ");
        int serialNumber = scanner.nextInt();
        scanner.nextLine();
        Member member = new Member();

        member.deleteMember(connection, serialNumber);
    }

    private static void borrow_book(){
        Connection connection = DbConnection.getConnection();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the ISBN of the book: ");
        String isbn = scanner.nextLine();

        System.out.print("Enter the member's serial number: ");
        int memberSerialNumber = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the borrow date (yyyy-MM-dd): ");
        String borrowDateStr = scanner.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date borrowDate;
        try {
            borrowDate = dateFormat.parse(borrowDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            scanner.close();
            return;
        }


        System.out.print("Enter the return date (yyyy-MM-dd): ");
        String returnDateStr = scanner.nextLine();

        Date returnDate;
        try {
            returnDate = dateFormat.parse(returnDateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            scanner.close();
            return;
        }

        Member member = new Member(null, memberSerialNumber);
        Book book = new Book(isbn,null,null,0,null,0);

        Livres_empruntes livre_emprunte = new Livres_empruntes(member, book, borrowDate, returnDate,Boolean.FALSE);
        // Call the borrowBook function
        livre_emprunte.emprunter_livre(connection,member, book, borrowDate, returnDate);
    }
    private static void exitProgram() {
        DbConnection.closeConnection();
        System.out.println("Exiting the program.");
        System.exit(0);
    }
}