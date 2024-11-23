package doan.timkiemvieclam.controller;

import doan.timkiemvieclam.entity.Jobs;
import doan.timkiemvieclam.service.JobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/Jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    // Hiển thị trang danh sách công việc
    @GetMapping()
    public String showJobList() {
        return "admin/Jobs/List";
    }

    // Lấy dữ liệu tất cả các công việc
    @GetMapping("/data")
    @ResponseBody
    public List<Jobs> getAllJobs() {
        return jobService.getAllJobs();
    }

    // Hiển thị trang tạo công việc mới
    @GetMapping("/Create")
    public String createJob() {
        return "admin/Jobs/Create";
    }

    // Thêm công việc mới
    @PostMapping
    public ResponseEntity<Jobs> addJob(@RequestBody Jobs job) {
        Jobs newJob = jobService.saveJob(job); // Lưu công việc mới
        return ResponseEntity.status(HttpStatus.CREATED).body(newJob); // Trả về công việc mới với mã 201
    }

    // Hiển thị trang chỉnh sửa công việc
    @GetMapping("/Edit/{id}")
    public String editJob(@PathVariable("id") Integer jobId) {
        return "admin/Jobs/Edit"; // Đảm bảo rằng đường dẫn này chính xác
    }

    // Lấy công việc theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Jobs> getJobById(@PathVariable Integer id) {
        Optional<Jobs> jobOptional = jobService.getJobById(id);
        if (jobOptional.isPresent()) {
            return ResponseEntity.ok(jobOptional.get()); // Trả về 200 OK cùng với thông tin công việc
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Trả về 404 nếu không tìm thấy
        }
    }

    // Cập nhật công việc
    @PutMapping("/{id}")
    public ResponseEntity<Jobs> updateJob(@PathVariable Integer id, @RequestBody Jobs jobDetails) {
        Optional<Jobs> existingJob = jobService.getJobById(id);
        if (existingJob.isPresent()) {
            jobDetails.setJobId(id);
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
