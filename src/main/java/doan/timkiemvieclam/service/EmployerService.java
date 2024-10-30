package doan.timkiemvieclam.service;


import doan.timkiemvieclam.entity.Employersq;

import doan.timkiemvieclam.repository.EmployerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    // Lấy danh sách tất cả nhà tuyển dụng
    public List<Employersq> getAllEmployers() {
        return employerRepository.findAll();
    }

    // Lấy nhà tuyển dụng theo ID
    public Optional<Employersq> getEmployerById(Integer id) {
        return employerRepository.findById(id);
    }

    // Thêm nhà tuyển dụng
    public Employersq saveEmployer(Employersq employer) {
        return employerRepository.save(employer);
    }

    public Employersq updateEmployer(Integer id, Employersq employerDetails) {
        Employersq employer = employerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employer not found with id " + id));

        employer.setEmployerName(employerDetails.getEmployerName());
        employer.setEmployerAddress(employerDetails.getEmployerAddress());
        employer.setEmployerAvatar(employerDetails.getEmployerAvatar());
        employer.setEmployerWebsite(employerDetails.getEmployerWebsite());
        employer.setDescription(employerDetails.getDescription());
        employer.setQuantity(employerDetails.getQuantity());
        employer.setIsActive(employerDetails.getIsActive());

        return employerRepository.save(employer);
    }

    // Xóa nhà tuyển dụng theo ID
    public void deleteEmployerById(Integer id) {
        employerRepository.deleteById(id);
    }
}