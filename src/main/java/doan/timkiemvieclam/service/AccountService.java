package doan.timkiemvieclam.service;
import doan.timkiemvieclam.config.HashConfig;
import doan.timkiemvieclam.entity.Accounts;

import doan.timkiemvieclam.entity.Roles;
import doan.timkiemvieclam.repository.AccountRepository;
import doan.timkiemvieclam.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;
@Service
public class AccountService {

    @Autowired
    private AccountRepository AccountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Accounts> getAllAccounts() {

        return AccountRepository.findAll();
    }

    public Optional<Accounts> getAccountsById(Integer id) {
        return AccountRepository.findById(id);
    }

    public Accounts saveAccounts(Accounts Account) {
        String hashedPassword = passwordEncoder.encode(Account.getPassword());
        Account.setPassword(hashedPassword);  // Cập nhật mật khẩu đã mã hóa

        return AccountRepository.save(Account);
    }
    public boolean checkPassword(String rawPassword, String storedHashedPassword) {
        return BCrypt.checkpw(rawPassword, storedHashedPassword);
    }


    public void deleteAccountsById(Integer id) {
        AccountRepository.deleteById(id);
    }

    public boolean register(String accountName, String password) {
        Optional<Accounts> existingAccount = AccountRepository.findByAccountName(accountName);
        if (existingAccount.isPresent()) {
            return false; // Tên tài khoản đã tồn tại
        }

        Accounts newAccount = new Accounts();
        newAccount.setAccountName(accountName);
        newAccount.setPassword(passwordEncoder.encode(password)); // Mã hóa mật khẩu
        newAccount.setIsActive(true); // Mặc định tài khoản được kích hoạt

        // Tìm role "USER" trong cơ sở dữ liệu và gán cho tài khoản
        Roles userRole = roleRepository.findByRoleName("User")
                .orElseThrow(() -> new RuntimeException("Role 'USER' not found"));
        newAccount.setRole(userRole); // Gán đối tượng Role cho tài khoản

        AccountRepository.save(newAccount);
        return true;
    }

    // Đăng nhập tài khoản
    public boolean login(String accountName, String password) {

        Accounts account = findByAccountName(accountName);
        if (account == null) {
            return false;
        }
        return passwordEncoder.matches(password, account.getPassword()); // Kiểm tra mật khẩu


    }

    // Kiểm tra xem tài khoản có phải là admin không
    public boolean isAdmin(Accounts account) {
        return account != null && account.getRole() != null && "Admin".equals(account.getRole().getRoleName());
    }

    // Kiểm tra xem tài khoản có phải là người dùng không
    public boolean isUser(Accounts account) {
        return account != null && account.getRole() != null && "Seeker".equals(account.getRole().getRoleName());
    }

    public boolean isEmployer(Accounts account) {
        return account != null && account.getRole() != null && "Employer".equals(account.getRole().getRoleName());
    }
    public Accounts findByAccountName(String accountName) {
        return AccountRepository.findByAccountName(accountName)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));
    }
}
