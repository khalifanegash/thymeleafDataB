package se.utb.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class IndexController {
    @GetMapping("/index")
   public String home(){
       return "index";
   }
}
