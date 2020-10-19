package net.lilifei.SpringDemoService.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class HeaderController {

    @RequestMapping(value = "/api/getWithHeader", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRecords(final HttpServletRequest httpServletRequest) {
        final String header = httpServletRequest.getHeader("X-DEMO");
        log.info("Header X-DEMO: {}", header);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
