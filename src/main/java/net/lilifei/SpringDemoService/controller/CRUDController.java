package net.lilifei.SpringDemoService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import net.lilifei.SpringDemoService.model.CreateRecordRequest;
import net.lilifei.SpringDemoService.model.CreateRecordResponse;
import net.lilifei.SpringDemoService.model.Record;
import net.lilifei.SpringDemoService.storage.RecordStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class CRUDController {

    private RecordStorage recordStorage;

    private ObjectMapper objectMapper;

    @Autowired
    public CRUDController(final RecordStorage recordStorage,
                          final ObjectMapper objectMapper) {
        this.recordStorage = recordStorage;
        this.objectMapper = objectMapper;
    }

    @RequestMapping(value = "/api/empty", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEmptyJson() {
        // Response can be anything as long as it is a JSON String.
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @RequestMapping(value = "/api/records", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRecords() {
        final List<Record> records = recordStorage.getRecords();
        return new ResponseEntity<>(writeValueAsString(records), HttpStatus.OK);
    }

    @RequestMapping(value = "/api/records/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRecordById(@PathVariable("id") final String id) {
        final Record record = recordStorage.getRecordById(id);
        return new ResponseEntity<>(record == null ? "{}" : writeValueAsString(record), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/records",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createRecord(@RequestBody final CreateRecordRequest createRecordRequest) {
        final String id = recordStorage.createRecord(Record.builder()
                .someProperty(createRecordRequest.getSomeProperty())
                .build());
        final CreateRecordResponse createRecordResponse = CreateRecordResponse.builder()
                .recordId(id)
                .build();
        return new ResponseEntity<>(writeValueAsString(createRecordResponse), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/records/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateRecord(@PathVariable("id") final String id,
                                          @RequestBody final CreateRecordRequest createRecordRequest) {
        recordStorage.updateRecordById(Record.builder()
                .someProperty(createRecordRequest.getSomeProperty())
                .build(), id);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/records/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteRecord(@PathVariable("id") final String id) {
        recordStorage.deleteRecordById(id);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    private String writeValueAsString(final Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
