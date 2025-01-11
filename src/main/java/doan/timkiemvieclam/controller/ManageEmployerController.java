package doan.timkiemvieclam.controller;

import doan.timkiemvieclam.entity.Accounts;
import doan.timkiemvieclam.entity.Blogs;
import doan.timkiemvieclam.entity.Employersq;
import doan.timkiemvieclam.service.EmployerService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/admin/Employer")
public class ManageEmployerController {

    @Autowired
    private EmployerService employerService;

    @GetMapping
    public String showEmployer( )
    {
        return "admin/Employers/List";
    }

    @GetMapping("/data")
    @ResponseBody
    public List<Employersq> getAllEmployers() {
        return employerService.getAllEmployers();
    }

    @GetMapping("/Create")
    public String createAccount()
    {
        return "admin/Employers/Create";
    }

    @PostMapping
    public ResponseEntity<Employersq> addBlog(@RequestBody Employersq em, HttpSession session) {
        Accounts account = (Accounts) session.getAttribute("account"); // Lấy đối tượng account từ session
        if (account != null) {
            em.setAccount(account); // Gán đối tượng account vào blog
        }
        Employersq newbEm = employerService.saveEmployer(em);// Lưu blog
        return ResponseEntity.status(HttpStatus.CREATED).body(newbEm);
    }

    @GetMapping("/Edit/{id}")
    public String editBlogPage(@PathVariable("id") Integer blogId) {
        // Trả về trang chỉnh sửa
        return "admin/Employers/Edit";
    }

    // Lấy nhà tuyển dụng theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Employersq> getEmployerById(@PathVariable Integer id) {
        Optional<Employersq> emOptional = employerService.getEmployerById(id);
        if (emOptional.isPresent()) {
            return ResponseEntity.ok(emOptional.get()); // Trả về 200 OK cùng với thông tin blog
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employersq> updateEmp(@PathVariable Integer id, @RequestBody Employersq em, HttpSession session) {
        Optional<Employersq> existingEm = employerService.getEmployerById(id);
        if (existingEm.isPresent()) {
            em.setEmployerId(id);
            Employersq existingEmployer = existingEm.get();
            em.setAccount(existingEmployer.getAccount());

            Employersq updatedEm = employerService.saveEmployer(em);
            return ResponseEntity.ok(updatedEm);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa nhà tuyển dụng
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployer(@PathVariable Integer id) {
        try {
            employerService.deleteEmployerById(id);
            return ResponseEntity.noContent().build(); // Trả về 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy
        }
    }
}
