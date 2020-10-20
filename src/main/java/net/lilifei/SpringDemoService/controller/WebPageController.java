package net.lilifei.SpringDemoService.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class WebPageController {

    @GetMapping("/")
    public String index(final HttpServletResponse httpServletResponse) {
        // Set standard HTTP/1.1 no-cache headers.
        httpServletResponse.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        // Set standard HTTP/1.0 no-cache header.
        httpServletResponse.setHeader("Pragma", "no-cache");
        return "index.html";
    }
}
