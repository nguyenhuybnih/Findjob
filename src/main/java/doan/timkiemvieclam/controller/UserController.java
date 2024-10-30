package doan.timkiemvieclam.controller;
import doan.timkiemvieclam.entity.Employersq;
import doan.timkiemvieclam.entity.users;
import doan.timkiemvieclam.service.EmployerService;
import doan.timkiemvieclam.service.UserService;

import org.apache.catalina.User;
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
@RequestMapping("/admin/Users")

public class UserController {
    @Autowired
    private UserService UserService;

    @GetMapping
    public String showUserList() {
        return "admin/Users/List";
    }

    @GetMapping("/data")
    @ResponseBody
    public List<users> getAllUser() {
        return UserService.getAllUsers();
    }
    @GetMapping("/Create")
    public String CreateUser() {
        return "admin/Users/Create";
    }


    //them moi
    @PostMapping
    public ResponseEntity<users> addUser(@RequestBody users user) {
        users newUser = UserService.saveUsers(user); // Lưu người dùng mới
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser); // Trả về người dùng mới với mã 201
    }

//    //lay ra id nguoi dung
//    @GetMapping("/{id}")
//    public ResponseEntity<users> getUserById(@PathVariable Integer id) {
//        Optional<users> users = UserService.getUsersById(id);
//
//        users user = users.get();
//
//        return ResponseEntity.ok(user);
//    }
    @GetMapping("/edit")
    public String editUser(@RequestParam Integer id, Model model) {
        Optional<users> userOpt = UserService.getUsersById(id);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "admin/Users/Edit"; // Tên của trang HTML để sửa
        }
        return "redirect:/admin/Users"; // Chuyển hướng nếu không tìm thấy người dùng
    }

    @PutMapping("/{id}")
    public ResponseEntity<users> updateUser(@PathVariable Integer id, @RequestBody users user) {
        Optional<users> existingUser = UserService.getUsersById(id);
        if (existingUser.isPresent()) {
            user.setUserId(id); // Cập nhật ID cho đối tượng user
            users updatedUser = UserService.saveUsers(user);
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }



    //xoa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployer(@PathVariable Integer id) {
        try {
            UserService.deleteUsersById(id);
            return ResponseEntity.noContent().build(); // Trả về 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy
        }
    }

}