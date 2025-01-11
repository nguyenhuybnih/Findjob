package doan.timkiemvieclam.controller;

import doan.timkiemvieclam.entity.Accounts;
import doan.timkiemvieclam.service.AccountService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j

@Controller
public class AuthController {
    @Autowired
    private AccountService AccountService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }
    @PostMapping("/register")
    public String registerAccount(@RequestParam String accountName, @RequestParam String password, Model model) {
        if (AccountService.register(accountName, password)) {
            model.addAttribute("message", "Đăng ký thành công!");
            return "admin/login";
        } else {
            model.addAttribute("message", "Tên tài khoản đã tồn tại!");
            return "admin/register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "admin/login";
    }
    @PostMapping("/login")
    public String loginAccount(@RequestParam String accname,  @RequestParam String password, Model model,HttpSession session) {
        if ((accname == null || accname.trim().isEmpty()) && (password == null || password.trim().isEmpty())) {
            model.addAttribute("errorMessage", "Vui lòng nhập tài khoản và mật khẩu.");
            return "admin/login";  // Quay lại form đăng nhập và hiển thị thông báo lỗi
        }

        if (accname == null || accname.trim().isEmpty()) {
            model.addAttribute("errorMessage", "Vui lòng nhập tài khoản.");
            model.addAttribute("accname", accname); // Giữ lại tài khoản đã nhập
            return "admin/login";
        }
        if (password == null || password.trim().isEmpty()) {
            model.addAttribute("errorMessage", "Vui lòng nhập mật khẩu.");
            model.addAttribute("accname", accname); // Giữ lại tài khoản đã nhập
            return "admin/login";
        }

        try {
            if (AccountService.login(accname, password)) {
                Accounts account = AccountService.findByAccountName(accname);
                session.setAttribute("account", account);
                if (AccountService.isAdmin(account)) {
                    return "redirect:/admin/home";
                }
                if(AccountService.isEmployer(account))
                {
                    return "redirect:/employer/home";
                }
                else if (AccountService.isUser(account)) {
                    return "redirect:/";
                }
            } else {
                model.addAttribute("errorMessage", "Sai tài khoản hoặc mật khẩu.");
            }
        } catch (RuntimeException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        model.addAttribute("accname", accname); // Giữ lại tài khoản đã nhập
        return "admin/login";
    }
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Hủy session khi đăng xuất
        return "redirect:/";
    }

}
