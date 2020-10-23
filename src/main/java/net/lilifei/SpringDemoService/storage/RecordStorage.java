package net.lilifei.SpringDemoService.storage;

import net.lilifei.SpringDemoService.model.Record;

import java.util.List;

public interface RecordStorage {
    List<Record> getRecords();

    Record getRecordById(String id);

    String createRecord(Record record);

    void deleteRecordById(String id);

    void updateRecordById(Record record, String id);
}
