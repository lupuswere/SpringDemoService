package net.lilifei.SpringDemoService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.lilifei.SpringDemoService.model.Record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CRUDController {

    private ObjectMapper objectMapper;

    @Autowired
    public CRUDController(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RequestMapping(value = "/api/records", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRecords() {
        // Response can be anything as long as it is a String.
        final String response = getResponseJsonString();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private String getResponseJsonString() {
        final Record record = Record.builder()
                .someProperty("foobar")
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
