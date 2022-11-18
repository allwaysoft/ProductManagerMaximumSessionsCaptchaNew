package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/index2")
    public String index2() {
        return "index2";
    }

    @GetMapping("/index3")
    public String index3() {
        return "index3";
    }

    @GetMapping("/header")
    public String header() {
        return "fragments/header";
    }

    @GetMapping("/footer")
    public String footer() {
        return "fragments/footer";
    }

    @GetMapping("/sidebar")
    public String sidebar() {
        return "fragments/sidebar";
    }
}
