package doan.timkiemvieclam.repository;
import doan.timkiemvieclam.entity.Jobs;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository

public interface JobRepository extends JpaRepository<Jobs,Integer>{
}
