package com.libook.models;

import java.util.Date;

public class Livres_empruntes {
    private Member member;
    private Book book;
    private Date date_emprunt;
    private Date date_retour;

    public Livres_empruntes(Member member, Book book, Date date_emprunt, Date date_retour) {
        this.member = member;
        this.book = book;
        this.date_emprunt = date_emprunt;
        this.date_retour = date_retour;
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

    public void emprunter_livre(Member member, Book book, Date date_emprunt, Date date_retour){

    }

    public Livres_empruntes afficher_livre_emprunte(Member member, Book book){}
    public void modifier_livre_emprunte(Member member, Book book, Date date_emprunt, Date date_retour){}
}

