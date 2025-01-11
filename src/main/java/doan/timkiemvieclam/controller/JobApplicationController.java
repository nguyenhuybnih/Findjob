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
    public String viewApplications() {


        return "employer/Application/index";  // Trả về trang ứng tuyển quản lý
    }

    @GetMapping("/api/job-applications")
    @ResponseBody
    public ResponseEntity<List<JobApplication>> getAllApplications() {
        List<JobApplication> applications = JobApplicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }


}
