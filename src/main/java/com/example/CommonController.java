package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController {

    @GetMapping("/login")
    public String viewLoginPage() {
        // custom logic before showing login page...

        return "login";
    }
}
