package doan.timkiemvieclam.repository;


import doan.timkiemvieclam.entity.Accounts;
import doan.timkiemvieclam.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {

    List<JobApplication> findByJobs(Accounts acc);

    @Query(value = "SELECT j.job_name, e.employer_name, j.job_address, ja.application_date " +
            "FROM job_application ja " +
            "JOIN jobs j ON ja.job_id = j.job_id " +
            "JOIN accounts a ON ja.account_id = a.account_id " +
            "JOIN employers e ON e.account_id = a.account_id " +
            "WHERE a.account_id = ?1", nativeQuery = true)
    List<Object[]> findJobApplicationsByAccountId(Integer accountId);

}
