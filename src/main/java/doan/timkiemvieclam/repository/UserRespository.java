package doan.timkiemvieclam.repository;

import doan.timkiemvieclam.entity.Users;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository

public interface UserRespository extends JpaRepository<Users,Integer> {

}
