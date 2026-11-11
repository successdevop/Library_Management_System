package com.library.api.borrow_records;

import com.library.services.BorrowService;

public class BorrowRecordController{
    private final BorrowService borrowService;

    public BorrowRecordController(){
        this.borrowService = new BorrowService();
    }

    // BORROW BOOK
    public boolean borrowBook(String userId, String bookId){
        return borrowService.borrowBook(userId, bookId);
    }

    // RETURN BOOK
    public boolean returnBook(String bookId){
        return borrowService.returnBook(bookId);
    }

    // PAY FINE
    public void payFine(String userId, double amount){
        borrowService.payFine(userId, amount);
    }

    // VIEW PERSONAL RECORDS
    public void viewPersonalBorrowingRecords(String id){
        borrowService.viewPersonalBorrowingRecords(id);
    }

    // VIEW ALL RECORDS
    public void viewAllBorrowRecords(String id){
        borrowService.viewAllBorrowRecords(id);
    }

    // DELETE RECORDS
    public void deleteBorrowRecords(String recordId){
        borrowService.deleteBorrowRecord(recordId);
    }

    // VIEW LIBRARY STATISTICS
    public String viewLibraryStatistics(){
        return borrowService.libraryStatistics();
    }
}