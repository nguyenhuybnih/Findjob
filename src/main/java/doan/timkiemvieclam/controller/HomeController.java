package doan.timkiemvieclam.controller;

import doan.timkiemvieclam.entity.Accounts;
import doan.timkiemvieclam.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Slf4j
@Controller

public class HomeController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private JobService JobService;
    @Autowired
    private EmployerService EmployerService;
    @Autowired
    private BranchService BranchService;
    @Autowired
    private UserService UserService;
    @Autowired
    private BlogService BlogService;

    @RequestMapping("admin/home")
    public String index(HttpSession session, Model model){
        Accounts account = (Accounts) session.getAttribute("account");
        if (account != null) {
            model.addAttribute("username", account.getAccountName()); // Truyền username vào model
        }
        // Lấy thông tin tài khoản từ session
        if (account == null) {
            log.info("Account is null in session.");
            return "redirect:/login"; // Nếu chưa đăng nhập, chuyển hướng về trang login
        }
        if (!accountService.isAdmin(account)) {
            log.info("Account is not admin: " + account.getAccountName());
            return "redirect:/login"; // Nếu không phải admin, chuyển hướng về trang login
        }
        model.addAttribute("account", account);
        return "admin/index"; // Trả về trang admin/index nếu đã đăng nhập và là admin
    }
    //    @GetMapping("/login")
//    public String login(){
//        return "admin/login";
//    }
    @GetMapping("employer/home")
    public String index2(HttpSession session, Model model){
        Accounts account = (Accounts) session.getAttribute("account");
        if (account != null) {
            model.addAttribute("username", account.getAccountName()); // Truyền username vào model
        }
        // Lấy thông tin tài khoản từ session
        if (account == null) {
            log.info("Account is null in session.");
            return "redirect:/login"; // Nếu chưa đăng nhập, chuyển hướng về trang login
        }
        if (!accountService.isEmployer(account)) {
            log.info("Account is not employer: " + account.getAccountName());
            return "redirect:/login"; // Nếu không phải admin, chuyển hướng về trang login
        }
        model.addAttribute("account", account);
        return "employer/index";
    }



    @GetMapping
    public String index1(Model model){
        int totalJobs = JobService.countActiveJobs();  // Đếm tổng số công việc
        model.addAttribute("totalJobs", totalJobs);    // Truyền giá trị vào model
        int totalEmployer = EmployerService.countActiveEmployer();
        model.addAttribute("totalEm", totalEmployer);
        int totalBranch = BranchService.countActiveBranch();
        model.addAttribute("totalBranch", totalBranch);
        return "user/index";
    }

    @GetMapping("Create/Cv")
    public String createCv(){
        return "user/Cv/Create";
    }

    @GetMapping("/admin/statistical")
    public String getEmployerApplications(HttpSession session, Model model) {
        Accounts account = (Accounts) session.getAttribute("account"); // Lấy accountId từ session
        if (account == null) {
            // Xử lý nếu session không có accountId (người dùng chưa đăng nhập)
            return "redirect:/login"; // Ví dụ: redirect đến trang đăng nhập
        }
        int totalJobs = JobService.countActiveJobs();  // Đếm tổng số công việc
        model.addAttribute("totalJobs", totalJobs);
        int totalEmployer = EmployerService.countActiveEmployer();
        model.addAttribute("totalEm", totalEmployer);
        int totalUser = UserService.countbyUserId();
        model.addAttribute("totalUser", totalUser);
        int totalBlog = BlogService.countByBlogId();
        model.addAttribute("totalBlog", totalBlog);

        return "admin/statistical"; // Trả về trang hiển thị số lượng ứng tuyển
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

    @GetMapping("/Job-detail/{id}")
    public String jobDetail(@PathVariable("id") Integer jobId){
        return "user/Job/detail";
    }
}
