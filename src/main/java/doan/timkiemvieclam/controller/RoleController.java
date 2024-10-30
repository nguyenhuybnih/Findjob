package doan.timkiemvieclam.controller;

import doan.timkiemvieclam.entity.Roles;
import doan.timkiemvieclam.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/Role")

public class RoleController {
    @Autowired
    private RoleService RoleService;

    @GetMapping
    public List<Roles> getAllRole(){
        return RoleService.getRoles();
    }
}
