package doan.timkiemvieclam.controller;
import doan.timkiemvieclam.entity.Accounts;
import doan.timkiemvieclam.entity.Employersq;
import doan.timkiemvieclam.entity.Users;
import doan.timkiemvieclam.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    public List< Users> getAllUser() {
        return UserService.getAllUsers();
    }
    @GetMapping("/Create")
    public String CreateUser() {
        return "admin/Users/Create";
    }

    @PostMapping
    public ResponseEntity<Users> addUser(@RequestBody Users user, HttpSession session) {

        Users newUser = UserService.saveUsers(user); // Lưu người dùng mới
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser); // Trả về người dùng mới với mã 201
    }

    @GetMapping("/Edit/{id}")
    public String editUser(@PathVariable("id") Integer id) {
        return "admin/Users/Edit"; // Tên của trang HTML
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Integer id) {
        Optional<Users> usersOptional = UserService.getUsersById(id);
        if (usersOptional.isPresent()) {
            return ResponseEntity.ok(usersOptional.get()); // Trả về 200 OK cùng với thông tin blog
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Integer id, @RequestBody Users user, HttpSession session) {
        Optional<Users> existingUser = UserService.getUsersById(id);
        if (existingUser.isPresent()) {

            user.setAccount(existingUser.get().getAccount());

            user.setUserId(id); // Cập nhật ID cho đối tượng user


            Users updatedUser = UserService.saveUsers(user);
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        try {
            UserService.deleteUsersById(id);
            return ResponseEntity.noContent().build(); // Trả về 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy
        }
    }

}
