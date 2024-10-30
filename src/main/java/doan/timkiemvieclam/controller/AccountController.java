package doan.timkiemvieclam.controller;

import doan.timkiemvieclam.entity.Accounts;

import doan.timkiemvieclam.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/Accounts")


public class AccountController {

    @Autowired
    private AccountService AccountService;

    @GetMapping
    public String showAcounntList() {
        return "admin/Accounts/List";
    }

    @GetMapping("/data")
    @ResponseBody
    public List<Accounts> getAllUser() {
        return AccountService.getAllAccounts();
    }

    @GetMapping("/Create")
    public String createAccount()
    {
        return "admin/Accounts/Create";
    }

    @PostMapping
    public ResponseEntity<Accounts> addAccount(@RequestBody Accounts account) {
        Accounts newAccount = AccountService.saveAccounts(account); // Lưu người dùng mới
        return ResponseEntity.status(HttpStatus.CREATED).body(newAccount); // Trả về người dùng mới với mã 201
    }

    @GetMapping("/{id}")
    public ResponseEntity<Accounts> getAccountById(@PathVariable Integer id) {
        return AccountService.getAccountsById(id)
                .map(accounts -> ResponseEntity.ok().body(accounts))
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<Accounts> updateUser(@PathVariable Integer id, @RequestBody Accounts accounts) {
        Optional<Accounts> existingAcc = AccountService.getAccountsById(id);
        if (existingAcc.isPresent()) {
            accounts.setAccountId(id);
            Accounts updatedAccount = AccountService.saveAccounts(accounts);
            return ResponseEntity.ok(updatedAccount);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcc(@PathVariable Integer id) {
        try {
            AccountService.deleteAccountsById(id);
            return ResponseEntity.noContent().build(); // Trả về 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy
        }
    }

}
