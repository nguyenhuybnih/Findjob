package doan.timkiemvieclam.service;


import doan.timkiemvieclam.entity.users;

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
    public List<users> getAllUsers() {
        return usersRepository.findAll();
    }

    // Lấy nhà tuyển dụng theo ID
    public Optional<users> getUsersById(Integer id) {
        return usersRepository.findById(id);
    }

    // Thêm nhà tuyển dụng
    public users saveUsers(users users) {
        return usersRepository.save(users);
    }

    public users updateUsers(Integer id, users usersDetails) {
        users users = usersRepository.findById(id)
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
}