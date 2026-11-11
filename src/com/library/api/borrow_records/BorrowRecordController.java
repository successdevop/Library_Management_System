package com.library.api.borrow_records;

import com.library.services.BorrowService;

public class BorrowRecordController{
    private final BorrowService borrowService;

    public BorrowRecordController(){
        this.borrowService = new BorrowService();
    }

    public boolean borrowBook(String userId, String bookId){
        return borrowService.borrowBook(userId, bookId);
    }

    public boolean returnBook(String bookId){
        return borrowService.returnBook(bookId);
    }

    public void payFine(String userId, double amount){
        borrowService.payFine(userId, amount);
    }

    public void viewPersonalBorrowingRecords(String id){
        borrowService.viewPersonalBorrowingRecords(id);
    }

    public void viewAllBorrowRecords(String id){
        borrowService.viewAllBorrowRecords(id);
    }

    public void deleteBorrowRecords(String recordId){
        borrowService.deleteBorrowRecord(recordId);
    }

    public String viewLibraryStatistics(){
        return borrowService.libraryStatistics();
    }
}