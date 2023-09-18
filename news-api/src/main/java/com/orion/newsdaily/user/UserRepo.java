package com.orion.newsdaily.user;

import com.orion.newsdaily.basic.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Page<User> findByOrderByIdDesc(Pageable pageable);

    Page<User> findByUserNameLikeOrderByIdDesc(Pageable pageable, String name);

    User findByUserName(String userName);
}
