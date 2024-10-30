package doan.timkiemvieclam.repository;

import doan.timkiemvieclam.entity.Accounts;




import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository

public interface AccountRepository extends JpaRepository<Accounts,Integer> {
}


