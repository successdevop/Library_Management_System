package com.library.api.borrow_records;

import com.library.services.BorrowService;

public class BorrowRecordController{
    private final BorrowService borrowService;

    public BorrowRecordController(){
        this.borrowService = new BorrowService();
    }

    public void borrowBook(String title, String author){
        borrowService.borrowBook(title, author);
    }
}