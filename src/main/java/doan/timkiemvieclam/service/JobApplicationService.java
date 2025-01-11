package doan.timkiemvieclam.service;

import doan.timkiemvieclam.entity.JobApplication;
import doan.timkiemvieclam.repository.JobApplicationRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    public JobApplication saveApplication(JobApplication jobApplication) {
        return jobApplicationRepository.save(jobApplication);
    }
    @Autowired
    private HttpSession session;

    public List<Object[]> getJobApplicationsByAccountId() {
        // Lấy accountId từ session
        Integer accountId = (Integer) session.getAttribute("accountId");

        // Gọi repository để lấy dữ liệu theo accountId
        return jobApplicationRepository.findJobApplicationsByAccountId(accountId);
    }
    public List<JobApplication> getAllApplications() {
        return jobApplicationRepository.findAll(); // Trả về tất cả các đơn ứng tuyển
    }


}