package com.library.models;

import com.library.enums.BorrowStatus;

import java.time.LocalDate;


public class BorrowRecord{
    private String transactionId;
    private String bookId;
    private String userId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private BorrowStatus status;

    public BorrowRecord(String tId, String bId, String uId, LocalDate borrowDate, LocalDate dueDate, LocalDate returnDate, BorrowStatus status){
        this.transactionId = tId;
        this.bookId = bId;
        this.userId = uId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public void setTransactionId(String id){
        this.transactionId = id;
    }

    public String getTransactionId(){
        return this.transactionId;
    }

    public void setUserId(String id){
        this.userId = id;
    }

    public String getUserId(){
        return this.userId;
    }

    public void setBookId(String id){
        this.bookId = id;
    }

    public String getBookId(){
        return this.bookId;
    }

    public void setBorrowDate(LocalDate date){
        this.borrowDate = date;
    }

    public LocalDate getBorrowDate(){
        return this.borrowDate;
    }

    public void setDueDate(LocalDate date){
        this.dueDate = date;
    }

    public LocalDate getDueDate(){
        return this.dueDate;
    }

    public void setReturnDate(LocalDate date){
        this.returnDate = date;
    }

    public LocalDate getReturnDate(){
        return this.returnDate;
    }

    public void setBorrowStatus(BorrowStatus status){
        this.status = status;
    }

    public BorrowStatus getBorrowStatus(){
        return this.status;
    }

    @Override
    public String toString(){
        return "<BorrowRecord(TransactionId: "+this.transactionId+", MemberId: "+this.userId+", BookId: "+this.bookId
                +", BorrowedDate: "+this.borrowDate+", DueDate: "+this.dueDate+", status: "+this.status+")>";
    }
}