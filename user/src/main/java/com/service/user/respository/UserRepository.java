package com.service.user.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.user.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

  List<Users> findByDeletedFalse();

}
