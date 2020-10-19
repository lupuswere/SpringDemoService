package net.lilifei.SpringDemoService.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class CookieController {

    @RequestMapping(value = "/api/getWithCookie", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getWithCookie(final HttpServletRequest httpServletRequest) {
        final Cookie[] cookies = httpServletRequest.getCookies();
        printCookies(cookies);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @RequestMapping(value = "/api/writeCookie", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> writeCookie(final HttpServletRequest httpServletRequest,
                                         final HttpServletResponse httpServletResponse) {
        final Cookie cookie = new Cookie("DEMO", "helloworld!");
        cookie.setMaxAge(60 * 60 * 24 * 365 * 10);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    private void printCookies(final Cookie[] cookies) {
        for (final Cookie cookie : cookies) {
            log.info("Cookie name {} value {}", cookie.getName(), cookie.getValue());
        }
    }
}
