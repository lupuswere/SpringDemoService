package net.lilifei.SpringDemoService.controller;

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
        return new ResponseEntity<>("{\"key\": \"foo\", \"value\":\"bar\"}", HttpStatus.OK);
    }
}
