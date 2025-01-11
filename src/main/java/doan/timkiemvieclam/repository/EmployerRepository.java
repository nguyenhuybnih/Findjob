package doan.timkiemvieclam.repository;

import doan.timkiemvieclam.entity.Accounts;
import doan.timkiemvieclam.entity.Employersq;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository

public interface EmployerRepository extends JpaRepository<Employersq,Integer> {
    Employersq findByAccount(Accounts account);

}
