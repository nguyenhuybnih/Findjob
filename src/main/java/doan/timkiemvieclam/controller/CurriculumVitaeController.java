package doan.timkiemvieclam.controller;

import doan.timkiemvieclam.entity.Accounts;
import doan.timkiemvieclam.entity.Blogs;
import doan.timkiemvieclam.entity.CurriculumVitae;
import doan.timkiemvieclam.entity.Employersq;
import doan.timkiemvieclam.service.CurriculumVitaeService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/user/cv")
public class CurriculumVitaeController {

    @Autowired
    private CurriculumVitaeService cvService;

    @GetMapping("/Edit/{id}")
    public String cvDetail(@PathVariable("id") Integer id,HttpSession session, Model model ){
        Accounts account = (Accounts) session.getAttribute("account");
        if (account != null) {
            model.addAttribute("username12", account.getAccountName()); // Truyền username vào model
        }
        // Lấy thông tin tài khoản từ session
        if (account == null) {
            log.info("Account is null in session.");
            return "redirect:/login"; // Nếu chưa đăng nhập, chuyển hướng về trang login
        }
        if (!account.getRole().getRoleName().equals("Seeker")) { // Kiểm tra role của tài khoản
            log.info("User is not an nguoi tim viec.");
            return "redirect:/login"; //
        }
        model.addAttribute("account", account);
        return "user/Cv/Edit";
    }

    @GetMapping
    public String cvIndex(HttpSession session, Model model){
        Accounts account = (Accounts) session.getAttribute("account");
        if (account != null) {
            model.addAttribute("username12", account.getAccountName()); // Truyền username vào model
        }
        // Lấy thông tin tài khoản từ session
        if (account == null) {
            log.info("Account is null in session.");
            return "redirect:/login"; // Nếu chưa đăng nhập, chuyển hướng về trang login
        }
        if (!account.getRole().getRoleName().equals("Seeker")) { // Kiểm tra role của tài khoản
            log.info("User is not an nguoi tim viec.");
            return "redirect:/login"; //
        }
        model.addAttribute("account", account);
        return "user/Cv/Index";
    }

    @ResponseBody
    @PostMapping()
    public ResponseEntity<CurriculumVitae> addCv(@RequestBody CurriculumVitae cv, HttpSession session) {
        Accounts currentAccount = (Accounts) session.getAttribute("account");
        if (currentAccount != null) {
            cv.setAccount(currentAccount); // Gán đối tượng account
        }

        CurriculumVitae cr =cvService.saveCV(cv);

        return ResponseEntity.status(HttpStatus.CREATED).body(cr);
    }

    @GetMapping("/data")
    @ResponseBody
    public List<CurriculumVitae> getCvsBySession(HttpSession session) {
        Accounts account = (Accounts) session.getAttribute("account");

        if (account != null) {
            return cvService.getCvsByAccount(account);
        } else {
            throw new RuntimeException("Không có tài khoản đăng nhập");
        }
    }



    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<CurriculumVitae> getCvByCvId(@PathVariable Integer id) {
        Optional<CurriculumVitae> cVOptional = cvService.getCvByCvId(id);
        if (cVOptional.isPresent()) {
            return ResponseEntity.ok(cVOptional.get()); // Trả về 200 OK cùng với thông tin blog
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy
        }

    }


    @PutMapping("/{cvId}")
    public ResponseEntity<CurriculumVitae> updateCv(@PathVariable Integer cvId, @RequestBody CurriculumVitae updatedCv, HttpSession session) {
        Optional<CurriculumVitae> existingCv = cvService.getCvByCvId(cvId);
        if (existingCv.isPresent()) {
            updatedCv.setCvId(cvId);

            Accounts account = (Accounts) session.getAttribute("account");
            if (account == null) {
                log.error("Không tìm thấy thông tin tài khoản trong session.");
            }

            if (account != null) {
                updatedCv.setAccount(account); // Gán đối tượng account vào blog
            }
            CurriculumVitae updatedCurriculumVitae = cvService.saveCV(updatedCv);
            return ResponseEntity.ok(updatedCurriculumVitae);
        }
        return ResponseEntity.notFound().build();
    }



}
