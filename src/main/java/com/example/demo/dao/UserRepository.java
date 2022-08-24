package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	@Modifying
	@Query("update User set password = ?2 where id = ?1")
	int editUserQuery(int id, String password);

	@Query("select user from User user where loginName = ?1")
	User getUserByLoginName(String loginName);

	@Query("select user from User user where loginName = ?1 and password = ?2")
	User getUserByLoginNameAndPassword(String loginName, String password);
}