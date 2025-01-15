package doan.timkiemvieclam.repository;

import doan.timkiemvieclam.entity.Accounts;


import doan.timkiemvieclam.entity.Blogs;
import doan.timkiemvieclam.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Accounts,Integer> {
    Optional<Accounts> findByAccountName(String accountName);

//    @Query(value = "",nativeQuery = true)
//    List<Accounts> findAllSortedId();

}
