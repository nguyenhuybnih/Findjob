package doan.timkiemvieclam.repository;

import doan.timkiemvieclam.entity.Accounts;
import doan.timkiemvieclam.entity.CurriculumVitae;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository

public interface CurriculumVitaeRepository extends JpaRepository<CurriculumVitae, Integer>{

    List<CurriculumVitae> findByAccount(Accounts account);

}
