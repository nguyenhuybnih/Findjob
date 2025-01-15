package doan.timkiemvieclam.controller;


import doan.timkiemvieclam.entity.Accounts;
import doan.timkiemvieclam.entity.JobApplication;
import doan.timkiemvieclam.service.CurriculumVitaeService;
import doan.timkiemvieclam.service.JobApplicationService;
import doan.timkiemvieclam.service.JobService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class JobApplicationController {
    @Autowired
    private CurriculumVitaeService cvService;
    @Autowired
    private JobService JobService;

    @Autowired
    private JobApplicationService JobApplicationService;


    @GetMapping("/apply/job/{jobId}")
    public String applyForJobPage(@PathVariable Integer jobId, HttpSession session, Model model) {
        Accounts account = (Accounts) session.getAttribute("account");

        if (account == null) {
            return "redirect:/login";
        }

        model.addAttribute("jobId", jobId);
        model.addAttribute("account", account);

        model.addAttribute("cvs", cvService.getCvsByAccount(account));

        return "user/manageCv";
    }

    @PostMapping("/apply/job/{jobId}/cv/{cvId}")
    public String applyForJob(@PathVariable Integer jobId, @PathVariable Integer cvId, HttpSession session, RedirectAttributes redirectAttributes) {
        Accounts account = (Accounts) session.getAttribute("account");


        if (account == null) {
            return "redirect:/login";
        }

        JobApplication jobApplication = new JobApplication();
        jobApplication.setJobs(JobService.getJobById(jobId).get());
        jobApplication.setCV(cvService.getCvByCvId(cvId).get());
        jobApplication.setAccount(account);
        jobApplication.setApplicationDate(LocalDate.now());

        JobApplicationService.saveApplication(jobApplication);

        redirectAttributes.addFlashAttribute("successMessage", "Ứng tuyển thành công!");

        return "redirect:/apply/job/" + jobId;
    }

    @GetMapping("/employer/applications")
    public String viewApplications(Model model, HttpSession session) {
        Accounts account = (Accounts) session.getAttribute("account");

        if (account == null) {
            // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
            return "redirect:/login";
        }

        // Lấy danh sách ứng tuyển theo accountId
        List<Map<String, Object>> applications = JobApplicationService.getApplicationsByEmployerAccount(account.getAccountId());

        // Đưa dữ liệu vào model để hiển thị trên giao diện
        model.addAttribute("applications", applications);

        return "employer/JobApplication/List";  // Trả về trang ứng tuyển quản lý
    }

    @GetMapping("/employer/statistical")
    public String getEmployerApplications(HttpSession session, Model model) {
        Accounts account = (Accounts) session.getAttribute("account"); // Lấy accountId từ session
        if (account == null) {
            // Xử lý nếu session không có accountId (người dùng chưa đăng nhập)
            return "redirect:/login"; // Ví dụ: redirect đến trang đăng nhập
        }

        int totalJob = JobService.countApplicationsByAccountId(account.getAccountId());
        int totalApplications = JobApplicationService.countApplicationsByAccountId(account.getAccountId());

        model.addAttribute("totalApplications", totalApplications);
        model.addAttribute("totalJob", totalJob);

        return "employer/statistical"; // Trả về trang hiển thị số lượng ứng tuyển
    }


}
