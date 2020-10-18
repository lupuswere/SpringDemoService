package net.lilifei.SpringDemoService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.lilifei.SpringDemoService.model.Record;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CRUDController {

    @RequestMapping(value = "/api/records", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRecords() {
        final Record record = Record.builder()
                .key("foo")
                .value("bar")
                .build();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String response;
        try {
            response = objectMapper.writeValueAsString(record);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
