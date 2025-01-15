package doan.timkiemvieclam.service;

import doan.timkiemvieclam.entity.Accounts;
import doan.timkiemvieclam.entity.Blogs;
import doan.timkiemvieclam.entity.Employersq;
import doan.timkiemvieclam.entity.Jobs;
import doan.timkiemvieclam.repository.EmployerRepository;
import doan.timkiemvieclam.repository.JobRepository;
import jakarta.persistence.EntityNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private EmployerRepository employerRepository;

    // Lấy danh sách tất cả công việc
    public List<Jobs> getAllJobs(Accounts account ) {
        List<Jobs> jobs = jobRepository.findByAccount(account); // Tìm công việc theo tài khoản

        jobs.forEach(job -> {
            String jobDetails = job.getJobDetails();
            if (jobDetails != null) {
                // Lấy text từ HTML và rút gọn chuỗi
                String plainText = Jsoup.parse(jobDetails).text(); // Loại bỏ thẻ HTML
                if (plainText.length() > 20) {
                    plainText = plainText.substring(0, 20) + "...";
                }
                // Đưa nội dung rút gọn quay lại dạng HTML đơn giản
                String truncatedHtml = Jsoup.clean(plainText, Safelist.basic());
                job.setJobDetails(truncatedHtml);
            }
        });

        return jobs;
    }

    public Page<Map<String, Object>> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Object[]> jobsWithEmployer = jobRepository.findAllJobsWithEmployers(pageable);

        List<Map<String, Object>> jobList = new ArrayList<>();

        for (Object[] row : jobsWithEmployer) {
            Map<String, Object> jobMap = new HashMap<>();
            jobMap.put("jobId", row[0]);
            jobMap.put("jobName", row[1]);
            jobMap.put("jobAddress", row[2]);
            jobMap.put("employerName", row[3]);
            jobMap.put("employerAvatar", row[4]);
            jobList.add(jobMap);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("jobs", jobList);
        response.put("currentPage", jobsWithEmployer.getNumber());
        response.put("totalItems", jobsWithEmployer.getTotalElements());
        response.put("totalPages", jobsWithEmployer.getTotalPages());

        return new PageImpl<>(jobList, pageable, jobsWithEmployer.getTotalElements());
    }
    public Map<String, Object> getJobDetails(Integer jobId) {
        // Lấy công việc theo ID
        Jobs job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy công việc"));

        // Lấy tài khoản liên quan đến công việc
        Accounts account = job.getAccount();

        // Lấy employer thông qua tài khoản
        Employersq employer = employerRepository.findByAccount(account);

        // Tạo một Map để trả về các thông tin chi tiết
        Map<String, Object> response = new HashMap<>();
        response.put("jobId", job.getJobId());
        response.put("jobName", job.getJobName());
        response.put("jobDetails", job.getJobDetails());
        response.put("salary", job.getSalary());
        response.put("jobAddress", job.getJobAddress());
        response.put("employerName", employer.getEmployerName());
        response.put("employerAvatar", employer.getEmployerAvatar());

        return response;
    }

    // Lấy công việc theo ID
    public Optional<Jobs> getJobById(Integer id) {
        return jobRepository.findById(id);
    }

    // Thêm công việc mới
    public Jobs saveJob(Jobs job) {
        return jobRepository.save(job);
    }

    // Cập nhật công việc theo ID
    public Jobs updateJob(Integer id, Jobs jobDetails) {
        Jobs job = jobRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job not found with id " + id));

        job.setJobName(jobDetails.getJobName());
        job.setJobDetails(jobDetails.getJobDetails());
        job.setJobAddress(jobDetails.getJobAddress());
        job.setBranch(jobDetails.getBranch());
        job.setAccount(jobDetails.getAccount());
        job.setSalary(jobDetails.getSalary());
        job.setIsActive(jobDetails.getIsActive());

        return jobRepository.save(job);
    }

    // Xóa công việc theo ID
    public void deleteJobById(Integer id) {
        jobRepository.deleteById(id);
    }

    public int countActiveJobs() {
        return jobRepository.countByIsActiveTrue();  // Đếm các công việc đang hoạt động
    }
    public int countApplicationsByAccountId(Integer accountId) {
        return jobRepository.countJobsByAccountId(accountId);
    }


}
