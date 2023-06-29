package com.cz.spring_cloud_oauth2_server.dao;

import com.cz.spring_cloud_oauth2_server.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {

    Users findByUserName(String username);
}
