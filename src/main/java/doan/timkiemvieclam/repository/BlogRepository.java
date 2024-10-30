package doan.timkiemvieclam.repository;

import doan.timkiemvieclam.entity.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface BlogRepository extends JpaRepository<Blogs,Integer> {
}