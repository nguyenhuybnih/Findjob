package doan.timkiemvieclam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class HomeController {
    @RequestMapping("admin/home")
    public String index(){
            return "admin/index";
    }

    @RequestMapping("")
    public String index1(){
        return "user/index";
    }
    @RequestMapping("/JobList")
    public String JobList(){
        return "user/fe/job";
    }
    @RequestMapping("/Blog")
    public String Blog(){
        return "user/fe/blog";
    }
}
