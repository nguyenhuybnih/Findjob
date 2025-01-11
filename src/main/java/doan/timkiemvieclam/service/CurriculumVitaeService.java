package doan.timkiemvieclam.service;

import doan.timkiemvieclam.entity.Accounts;
import doan.timkiemvieclam.entity.Blogs;
import doan.timkiemvieclam.entity.CurriculumVitae;
import doan.timkiemvieclam.entity.Employersq;
import doan.timkiemvieclam.repository.CurriculumVitaeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurriculumVitaeService {
    @Autowired
    private CurriculumVitaeRepository cvRepository;


    public CurriculumVitae saveCV(CurriculumVitae cv) {
        return cvRepository.save(cv);
    }

    public List<CurriculumVitae> getCvsByAccount(Accounts account) {
        return cvRepository.findByAccount(account);
    }
    public Optional<CurriculumVitae> getCvByCvId(Integer id) {
        return cvRepository.findById(id);
    }

    public CurriculumVitae updateCv(Integer cvId, CurriculumVitae updatedCv) {
        CurriculumVitae cv = cvRepository.findById(cvId)
                .orElseThrow(() -> new RuntimeException("CV không tìm thấy"));
        cv.setAccount(updatedCv.getAccount());
        cv.setFullName(updatedCv.getFullName());
        cv.setAddress(updatedCv.getAddress());
        cv.setDateOfBirth(updatedCv.getDateOfBirth());
        cv.setPhoneNumber(updatedCv.getPhoneNumber());
        cv.setSkill(updatedCv.getSkill());
        cv.setCareerObjective(updatedCv.getCareerObjective());
        cv.setEducation(updatedCv.getEducation());
        cv.setProject(updatedCv.getProject());
        return cvRepository.save(cv);
    }
}
