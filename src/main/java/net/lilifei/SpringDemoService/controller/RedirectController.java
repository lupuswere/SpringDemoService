package net.lilifei.SpringDemoService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class RedirectController {

    @GetMapping("/api/redirect/v1")
    public void redirectV1(final HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect("/");
    }
}
