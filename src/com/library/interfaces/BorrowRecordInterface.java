package com.library.interfaces;

import com.library.models.BorrowRecord;

public interface BorrowRecordInterface {
    public BorrowRecord getRecordByTransactionId(String id);
    public BorrowRecord getRecordByUserId(String id);
    public BorrowRecord getRecordByBookId(String id);
    public void addBorrowRecord(BorrowRecord record);
    public void editBorrowRecord(BorrowRecord record, String newValues);
    public void deleteBorrowRecord(String id);
    public BorrowRecord[] getAllBorrowRecords();
}
