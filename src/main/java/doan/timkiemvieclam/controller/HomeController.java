package doan.timkiemvieclam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

public class HomeController {
    @RequestMapping("admin/home")
    public String index(){
            return "admin/index";
    }

    @GetMapping
    public String index1(){
        return "user/index";
    }
    @RequestMapping("/JobList")
    public String jobList(){
        return "user/Job/index";
    }
    @RequestMapping("/Blog")
    public String blogIndex(){
        return "user/Blog/index";
    }

    @GetMapping("/Blog-detail/{id}")
    public String blogDetail(@PathVariable("id") Integer blogId){
        return "user/Blog/detail";
    }

    @GetMapping("/Blog-detail")
    public String blogDetail(){
        return "user/Blog/detail";
    }

    @GetMapping("/Job-detail")
    public String jobDetail(){
        return "user/Job/detail";
    }

//    @GetMapping("/Job-detail/{id}")
//    public String jobDetail(@PathVariable("id") Integer jobId){
//        return "user/Job/detail";
//    }
}
