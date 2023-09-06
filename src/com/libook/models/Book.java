package com.libook.models;

import java.util.Date;
import java.util.List;

public class Book {
        private int isbn;
        private String title;
        private Auteur auteur;
        private Integer quantite;
        private Boolean lost;
        private Date datePublication;

        public Book(int isbn, String title, Auteur auteur, Integer quantite, Boolean lost, Date datePublication) {
                this.isbn = isbn;
                this.title = title;
                this.auteur = auteur;
                this.quantite = quantite;
                this.lost = lost;
                this.datePublication = datePublication;
        }

        public int getIsbn() {
                return isbn;
        }

        public void setIsbn(Integer isbn) {
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

        public Boolean getLost() {
                return lost;
        }

        public void setLost(Boolean lost) {
                this.lost = lost;
        }

        public Date getDate_publication() {
                return datePublication;
        }

        public void setDate_publication(Date date_publication) {
                this.datePublication = date_publication;
        }


        public static Book createBook(Integer isbn, String title, Auteur auteur, int quantite, Boolean lost, Date datePublication) {

        }

        public void displayBookInfo() {

        }

        public List<Book>

        public void updateBookInfo(String newTitle, Auteur newAuteur, int newQuantite, Boolean newLost, Date newDatePublication) {

        }

        public void deleteBook() {

        }


}

