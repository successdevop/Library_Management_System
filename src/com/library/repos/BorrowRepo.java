package com.library.repos;

import com.library.database.Database;
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
            if(record != null && record.getBookId().equals(id))
                return record;
        }
        return null;
    }

    @Override
    public void addBorrowRecord(BorrowRecord record){
        Database.appendBorrowRecordToFile(BORROW_RECORD_STORAGE_FILE, record);

        for (BorrowRecord recordObj : records){
            if(recordObj == null){
                recordObj = record;
                break;
            }
        }
    }

    @Override
    public void editBorrowRecord(BorrowRecord record, String newValues){
        Database.updateBorrowRecordFile(BORROW_RECORD_STORAGE_FILE, record, newValues);

        for(BorrowRecord recordObj : records){
            if(recordObj != null && recordObj.getTransactionId().equals(record.getTransactionId())){
                recordObj = record;
                break;
            }
        }
    }

    @Override
    public void deleteBorrowRecord(String id){
        Database.deleteFromBorrowRecordFile(BORROW_RECORD_STORAGE_FILE, id);

        for(BorrowRecord recordObj : records){
            if(recordObj != null && recordObj.getTransactionId().equals(id)){
                recordObj = null;
                break;
            }
        }
    }

    @Override
    public int countUserActiveBorrowings(String id){
        int count = 0;

        for(BorrowRecord record : records){
            if(record != null && record.getUserId().equals(id) &&
                    record.getBorrowStatus().toString().equalsIgnoreCase("BORROWED")){
                count++;
            }
        }

        return count;
    }

    @Override
    public BorrowRecord[] getAllBorrowRecords() {
        return records;
    }
}