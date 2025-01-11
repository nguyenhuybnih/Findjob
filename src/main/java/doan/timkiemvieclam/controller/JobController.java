package doan.timkiemvieclam.controller;

import doan.timkiemvieclam.entity.Accounts;
import doan.timkiemvieclam.entity.Jobs;
import doan.timkiemvieclam.service.JobService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/employer/Jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    // Hiển thị trang danh sách công việc
    @GetMapping()
    public String showJobList() {
        return "employer/Jobs/List";
    }

    // Lấy dữ liệu tất cả các công việc

    @GetMapping("/data")
    @ResponseBody
    public List<Jobs> getAllJobs(HttpSession session) {
        Accounts account = (Accounts) session.getAttribute("account"); // Lấy đối tượng account từ session

        if (account != null) {
            // Lọc các công việc mà tài khoản này đã đăng
            return jobService.getAllJobs(account); // Cần sửa phương thức trong service để hỗ trợ lọc theo account
        }
        return List.of();
    }


    @GetMapping("/data1")
    @ResponseBody
    public Page<Map<String, Object>> getJobList(
            @RequestParam(defaultValue = "0") int page,  // Trang bắt đầu từ 0
            @RequestParam(defaultValue = "4") int size  // Mặc định 10 công việc mỗi trang
    )
    {
        return jobService.getAll(page, size);

    }

    // Hiển thị trang tạo công việc mới
    @GetMapping("/Create")
    public String createJob() {
        return "employer/Jobs/Create";
    }

    // Thêm công việc mới
    @PostMapping
    public ResponseEntity<Jobs> addJob(@RequestBody Jobs job,HttpSession session) {
        Accounts account = (Accounts) session.getAttribute("account"); // Lấy đối tượng account từ session
        if (account != null) {
            job.setAccount(account);
        }
        Jobs newJob = jobService.saveJob(job); // Lưu công việc mới
        return ResponseEntity.status(HttpStatus.CREATED).body(newJob); // Trả về công việc mới với mã 201
    }

    // Hiển thị trang chỉnh sửa công việc
    @GetMapping("/Edit/{id}")
    public String editJob(@PathVariable("id") Integer jobId) {
        return "employer/Jobs/Edit"; // Đảm bảo rằng đường dẫn này chính xác
    }

    // Lấy công việc theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getJobDetails(@PathVariable Integer id) {
        Map<String, Object> response = jobService.getJobDetails(id);  // Gọi service để lấy chi tiết công việc
        return ResponseEntity.ok(response);


    }

    // Cập nhật công việc
    @PutMapping("/{id}")
    public ResponseEntity<Jobs> updateJob(@PathVariable Integer id, @RequestBody Jobs jobDetails, HttpSession session) {
        Optional<Jobs> existingJob = jobService.getJobById(id);
        if (existingJob.isPresent()) {
            jobDetails.setJobId(id);
            Accounts account = (Accounts) session.getAttribute("account");

            if (account != null) {
                jobDetails.setAccount(account); // Gán đối tượng account vào blog
            }
            Jobs updatedJob = jobService.updateJob(id, jobDetails);
            return ResponseEntity.ok(updatedJob);
        }
        return ResponseEntity.notFound().build();
    }

    // Xóa công việc theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Integer id) {
        try {
            jobService.deleteJobById(id);
            return ResponseEntity.noContent().build(); // Trả về 204 No Content
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy
        }
    }
}
