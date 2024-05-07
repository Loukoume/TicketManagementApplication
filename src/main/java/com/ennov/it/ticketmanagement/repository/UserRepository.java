package com.ennov.it.ticketmanagement.repository;

import com.ennov.it.ticketmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User> {

    @Query("select t from ticket_management_user t where t.username = ?1")
    List<User> userByUsername(String username);


    @Query("select t from ticket_management_user t where t.email = ?1")
    List<User> userByEmail(String email);


}
