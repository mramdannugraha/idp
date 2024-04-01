package com.ramdan.trainingkaryawan.repository.oauth;

import com.ramdan.trainingkaryawan.model.oauth.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    @Query("FROM User u WHERE LOWER(u.username) = LOWER(?1)")
    User findOneByUsername(String username);

    @Query("FROM User u WHERE u.otp = ?1")
    User findOneByOTP(String otp);

    @Query("FROM User u WHERE LOWER(u.username) = LOWER(:username)")
    User checkExistingEmail(String username);

    @Query("FROM User u WHERE LOWER(u.username) = LOWER(?1) AND u.otp = ?2")
    User findOneByUsernameAndOTP(String username, String otp);

}
