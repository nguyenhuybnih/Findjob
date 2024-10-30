package doan.timkiemvieclam.controller;


import doan.timkiemvieclam.entity.Employersq;
import doan.timkiemvieclam.service.EmployerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class ManageEmployerController {

    @Autowired
    private EmployerService employerService;
    @GetMapping("/Employer/List")
    public String index(Model model)
    {
        List<Employersq> list1 = this.employerService.getAllEmployers();
        model.addAttribute("list",list1);
        return "admin/Employers/List";
    }



    @GetMapping("/Employer/Add")
    public String add(Model model)
    {
        Employersq employersq = new Employersq();
        employersq.setIsActive(true);
        model.addAttribute("add",employersq);
        return "admin/Employers/Create";
    }
    @PostMapping("/Employer/Add")
    public String addEmployer(@ModelAttribute("add") Employersq employer, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "/admin/Employers/Create";
        }
        employerService.saveEmployer(employer);

        return "redirect:/admin/Employer/List";
    }

    @GetMapping("/Employer/Edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        // Lấy thông tin nhà tuyển dụng từ cơ sở dữ liệu theo id
        Optional<Employersq> optionalEmployer = employerService.getEmployerById(id);

        // Lấy nhà tuyển dụng từ Optional
        Employersq employersq = optionalEmployer.get();

        // Đưa nhà tuyển dụng vào model để truyền sang view
        model.addAttribute("edit1", employersq);

        return "/admin/Employers/Edit"; // Trả về view "Edit"
    }
    @PostMapping("/Employer/Edit/{id}")

    public String update(@PathVariable("id") Integer id, @ModelAttribute("employer") Employersq employersq, BindingResult result, Model model) {
        // Kiểm tra xem có lỗi validate không
        if (result.hasErrors()) {
            return "admin/Employers/Edit"; // Nếu có lỗi, quay lại form chỉnh sửa
        }

        Optional<Employersq> optionalEmployer = employerService.getEmployerById(id);
        // Lấy nhà tuyển dụng hiện tại từ cơ sở dữ liệu

        Employersq existingEmployer = optionalEmployer.get();
        if (existingEmployer == null) {
            return "redirect:/admin/Employer/List"; // Nếu không tồn tại, quay lại danh sách
        }

        // Cập nhật thông tin nhà tuyển dụng
        existingEmployer.setEmployerName(employersq.getEmployerName());
        existingEmployer.setEmployerAddress(employersq.getEmployerAddress());
        existingEmployer.setEmployerAvatar(employersq.getEmployerAvatar());
        existingEmployer.setEmployerWebsite(employersq.getEmployerWebsite());
        existingEmployer.setDescription(employersq.getDescription());
        existingEmployer.setQuantity(employersq.getQuantity());
        existingEmployer.setIsActive(employersq.getIsActive());

        // Lưu cập nhật vào cơ sở dữ liệu
        employerService.updateEmployer(existingEmployer.getEmployerId(), existingEmployer);

        // Quay lại trang danh sách sau khi cập nhật thành công
        return "redirect:/admin/Employer/List";
    }
    @GetMapping("/Employer/Delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        // Gọi service để xóa nhà tuyển dụng theo ID
        employerService.deleteEmployerById(id);
        // Quay lại trang danh sách sau khi xóa thành công
        return "redirect:/admin/Employer/List";
    }


//    // Lấy danh sách tất cả nhà tuyển dụng
//    @GetMapping
//    public List<Employers> getAllEmployers() {
//        return employerService.getAllEmployers();
//    }
//
//    // Lấy nhà tuyển dụng theo ID
//    @GetMapping("/{id}")
//    public ResponseEntity<Employers> getEmployerById(@PathVariable Integer id) {
//        return employerService.getEmployerById(id)
//                .map(employer -> ResponseEntity.ok().body(employer))
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    // Thêm nhà tuyển dụng mới
//    @PostMapping
//    public ResponseEntity<Employers> createEmployer(@RequestBody Employers employer) {
//        Employers savedEmployer = employerService.saveEmployer(employer);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployer);
//    }
//
//    // Cập nhật nhà tuyển dụng
//    @PutMapping("/{id}")
//    public ResponseEntity<Employers> updateEmployer(@PathVariable Integer id, @RequestBody Employers employerDetails) {
//        try {
//            Employers updatedEmployer = employerService.updateEmployer(id, employerDetails);
//            return ResponseEntity.ok(updatedEmployer);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }
//
//    // Xóa nhà tuyển dụng
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteEmployer(@PathVariable Integer id) {
//        try {
//            employerService.deleteEmployerById(id);
//            return ResponseEntity.noContent().build(); // Trả về 204 No Content
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy
//        }
//    }
}
