package doan.timkiemvieclam.service;

import doan.timkiemvieclam.entity.Jobs;
import doan.timkiemvieclam.repository.JobRepository;
import jakarta.persistence.EntityNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    // Lấy danh sách tất cả công việc
    public List<Jobs> getAllJobs() {
        List<Jobs> jobs = jobRepository.findAll();

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
        job.setEmployersq(jobDetails.getEmployersq());
        job.setSalary(jobDetails.getSalary());

        return jobRepository.save(job);
    }

    // Xóa công việc theo ID
    public void deleteJobById(Integer id) {
        jobRepository.deleteById(id);
    }
}
