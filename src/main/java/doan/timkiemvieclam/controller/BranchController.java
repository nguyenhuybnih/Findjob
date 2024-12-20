package doan.timkiemvieclam.controller;

import doan.timkiemvieclam.entity.Branch;
import doan.timkiemvieclam.entity.Employersq;
import doan.timkiemvieclam.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/Branch") // Đường dẫn cho controller này
public class BranchController {
    @Autowired
    private BranchService branchService;

    @GetMapping
    public String showBranchList() {
        return "admin/Branch/List"; // Trả về tên của trang hiển thị danh sách chi nhánh
    }

    @GetMapping("/data")
    @ResponseBody
    public List<Branch> getAllBranches() {
        return branchService.getAllBranches(); // Lấy danh sách tất cả chi nhánh
    }

    @GetMapping("/Create")
    public String createBranchForm() {
        return "admin/Branch/Create"; // Trả về tên của trang tạo chi nhánh
    }

    // Thêm mới chi nhánh
    @PostMapping
    public ResponseEntity<Branch> addBranch(@RequestBody Branch branch) {
        Branch newBranch = branchService.saveBranch(branch); // Lưu chi nhánh mới
        return ResponseEntity.status(HttpStatus.CREATED).body(newBranch); // Trả về chi nhánh mới với mã 201
    }

    // Lấy ra chi nhánh theo ID
    @GetMapping("/Edit/{id}")
    public String editBranch(@PathVariable("id") Integer id) {
            return "admin/Branch/Edit"; // Tên của trang HTML để sửa
    }

    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Integer id) {
        Optional<Branch> brOptional = branchService.getBranchById(id);
        if (brOptional.isPresent()) {
            return ResponseEntity.ok(brOptional.get()); // Trả về 200 OK cùng với thông tin blog
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Integer id, @RequestBody Branch branch) {
        Optional<Branch> existingBranch = branchService.getBranchById(id);
        if (existingBranch.isPresent()) {
            branch.setBranchId(id); // Cập nhật ID cho đối tượng branch
            Branch updatedBranch = branchService.saveBranch(branch);
            return ResponseEntity.ok(updatedBranch); // Trả về chi nhánh đã cập nhật
        }
        return ResponseEntity.notFound().build(); // Trả về 404 nếu không tìm thấy
    }

    // Xóa chi nhánh
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Integer id) {
        try {
            branchService.deleteBranchById(id);
            return ResponseEntity.noContent().build(); // Trả về 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy
        }
    }
}
