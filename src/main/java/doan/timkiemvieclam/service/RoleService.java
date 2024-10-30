package doan.timkiemvieclam.service;

import doan.timkiemvieclam.entity.Roles;

import doan.timkiemvieclam.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RoleService {
    @Autowired
    private RoleRepository RoleRepositorys;

    public List<Roles> getRoles() {
        return RoleRepositorys.findAll();
    }
}
