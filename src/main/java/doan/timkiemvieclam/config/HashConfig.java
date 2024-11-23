package doan.timkiemvieclam.config;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.context.annotation.Configuration;

@Configuration

public class HashConfig {

    public String hashPassword(String password) {
        String salt = BCrypt.gensalt(); // Tạo salt
        return BCrypt.hashpw(password, salt); // Mã hóa mật khẩu
    }

    // Kiểm tra mật khẩu
    public boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword); // Kiểm tra mật khẩu
    }
}
