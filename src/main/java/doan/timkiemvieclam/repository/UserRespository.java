package doan.timkiemvieclam.repository;

import doan.timkiemvieclam.entity.users;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository

public interface UserRespository extends JpaRepository<users,Integer> {

}
