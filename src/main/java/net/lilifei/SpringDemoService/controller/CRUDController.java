package net.lilifei.SpringDemoService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import net.lilifei.SpringDemoService.model.CreateRecordRequest;
import net.lilifei.SpringDemoService.model.Record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CRUDController {

    private ObjectMapper objectMapper;

    @Autowired
    public CRUDController(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RequestMapping(value = "/api/records", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRecords() {
        // Response can be anything as long as it is a JSON String.
        final String response = getResponseJsonString("foobar");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/records/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRecordById(@PathVariable("id") final String id) {
        // Response can be anything as long as it is a JSON String.
        final String response = getResponseJsonString(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/records",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createRecord(@RequestBody final CreateRecordRequest createRecordRequest) {
        log.info("{}", createRecordRequest.toString());
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    private String getResponseJsonString(final String value) {
        final Record record = Record.builder()
                .someProperty(value)
                .build();
        final String response;
        try {
            response = objectMapper.writeValueAsString(record);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
