package doan.timkiemvieclam.repository;


import doan.timkiemvieclam.entity.Accounts;
import doan.timkiemvieclam.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    @Query(value = """
        SELECT 
            cv.cv_id,
            j.job_name,
            us.user_name,
            ja.application_date
                        
        FROM 
            job_application ja
        JOIN 
            jobs j ON ja.job_id = j.job_id
        JOIN 
            curriculum_vitae cv ON ja.cv_id = cv.cv_id
        JOIN 
            accounts acc ON ja.account_id = acc.account_id
        JOIN 
            users us ON us.account_id = acc.account_id
        JOIN 
            accounts ac ON j.account_id = ac.account_id
        WHERE 
            ac.account_id = :accountId
        """, nativeQuery = true)
    List<Map<String, Object>> findApplicationsByAccountId(@Param("accountId") Integer accountId);

    @Query(value = """
            SELECT COUNT(cv.cv_id) AS totalApplications
            FROM job_application ja
            JOIN jobs j ON ja.job_id = j.job_id
            JOIN curriculum_vitae cv ON ja.cv_id = cv.cv_id
            JOIN accounts acc ON ja.account_id = acc.account_id
            JOIN users us ON us.account_id = acc.account_id
            JOIN accounts ac ON j.account_id = ac.account_id
            WHERE ac.account_id = :accountId
            """, nativeQuery = true)
    int countApplicationsByAccountId(@Param("accountId") Integer accountId);

}
