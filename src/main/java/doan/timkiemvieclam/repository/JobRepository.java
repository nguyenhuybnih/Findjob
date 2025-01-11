package doan.timkiemvieclam.repository;
import doan.timkiemvieclam.entity.Accounts;
import doan.timkiemvieclam.entity.Jobs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository

public interface JobRepository extends JpaRepository<Jobs,Integer>{
    List<Jobs> findByAccount(Accounts account); // Tìm công việc theo account

    @Query(value = "SELECT j.job_id,j.job_name,j.job_address, e.employer_name, e.employer_avatar " +
            "FROM jobs j " +
            "JOIN accounts a ON j.account_id = a.account_id " +
            "JOIN employersq e ON a.account_id = e.account_id",
            nativeQuery = true)
    Page<Object[]> findAllJobsWithEmployers(Pageable pageable);
}
