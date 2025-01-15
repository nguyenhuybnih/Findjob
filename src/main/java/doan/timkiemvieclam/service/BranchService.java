package doan.timkiemvieclam.service;

import doan.timkiemvieclam.entity.Branch;
import doan.timkiemvieclam.repository.BranchRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {
    @Autowired
    private BranchRepository branchRepository;

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public Optional<Branch> getBranchById(Integer id) {
        return branchRepository.findById(id);
    }

    public Branch saveBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    public Branch updateBranch(Integer id, Branch branchDetails) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id " + id));

        branch.setBranchName(branchDetails.getBranchName());
        branch.setIsActive(branchDetails.getIsActive());

        return branchRepository.save(branch);
    }

    public void deleteBranchById(Integer id) {
        branchRepository.deleteById(id);
    }
    public int countActiveBranch() {
        return branchRepository.countByIsActiveTrue();  // Đếm các công việc đang hoạt động
    }
}
