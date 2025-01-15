package doan.timkiemvieclam.service;


import doan.timkiemvieclam.entity.Users;

import doan.timkiemvieclam.repository.UserRespository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRespository usersRepository;

    // Lấy danh sách tất cả nhà tuyển dụng
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    // Lấy nhà tuyển dụng theo ID
    public Optional<Users> getUsersById(Integer id) {
        return usersRepository.findById(id);
    }

    // Thêm nhà tuyển dụng
    public Users saveUsers(Users users) {
        return usersRepository.save(users);
    }

    public Users updateUsers(Integer id, Users usersDetails) {
        Users users = usersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employer not found with id " + id));

        users.setUserName(usersDetails.getUserName());
        users.setUserAddress(usersDetails.getUserAddress());
        users.setUserEmail(usersDetails.getUserEmail());
        users.setUserAvatar(usersDetails.getUserAvatar());
        users.setUserPhone(usersDetails.getUserPhone());
        users.setDescription(usersDetails.getDescription());
        users.setAccount(usersDetails.getAccount());


        return usersRepository.save(users);
    }

    // Xóa nhà tuyển dụng theo ID
    public void deleteUsersById(Integer id) {
        usersRepository.deleteById(id);
    }

    public int countbyUserId() {
        return usersRepository.countByUserId();  // Đếm các công việc đang hoạt động
    }
}