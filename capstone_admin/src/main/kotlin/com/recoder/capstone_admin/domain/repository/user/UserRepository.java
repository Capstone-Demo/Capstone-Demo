package com.recoder.capstone_admin.domain.repository.user;

import com.recoder.capstone_admin.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.pw = :pw")
    User findUserByIdAndPw(@Param("id")String id,
                           @Param("pw")String pw);
}

