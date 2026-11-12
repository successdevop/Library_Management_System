package com.library.repos;

import com.library.database.Database;
import com.library.enums.BorrowStatus;
import com.library.interfaces.BorrowRecordInterface;
import com.library.models.BorrowRecord;

import static com.library.app_files.Config.BORROW_RECORD_STORAGE_FILE;


public class BorrowRepo implements BorrowRecordInterface {
    private final BorrowRecord[] records = Database.getBorrowRecordDatabase();

    @Override
    public BorrowRecord getRecordByTransactionId(String id){
        for(BorrowRecord record : records){
            if(record != null && record.getTransactionId().equals(id))
                return record;
        }
        return null;
    }

    @Override
    public BorrowRecord getRecordByUserId(String id){
        for(BorrowRecord record : records){
            if(record != null && record.getUserId().equals(id))
                return record;
        }
        return null;
    }

    @Override
    public BorrowRecord getRecordByBookId(String id){
        for(BorrowRecord record : records){
            if(record != null && record.getBookId().equals(id) && record.getBorrowStatus() == BorrowStatus.BORROWED)
                return record;
        }
        return null;
    }

    @Override
    public void addBorrowRecord(BorrowRecord record){
        Database.appendBorrowRecordToFile(BORROW_RECORD_STORAGE_FILE, record);

        for(int i = 0; i < records.length; i++){
            if (records[i] == null){
                records[i] = record;
                break;
            }
        }
    }

    @Override
    public void editBorrowRecord(BorrowRecord record, String newValues){
        Database.updateBorrowRecordFile(BORROW_RECORD_STORAGE_FILE, record, newValues);

        for(int i = 0; i < records.length; i++){
            if (records[i] != null && records[i].getTransactionId().equals(record.getTransactionId())){
                records[i] = record;
                break;
            }
        }
    }

    @Override
    public void deleteBorrowRecord(String id){
        Database.deleteFromBorrowRecordFile(BORROW_RECORD_STORAGE_FILE, id);

        for(int i = 0; i < records.length; i++){
            if (records[i] != null && records[i].getTransactionId().equals(id)){
                records[i] = null;
                break;
            }
        }
    }

    @Override
    public BorrowRecord[] getAllBorrowRecords() {
        return records;
    }
}