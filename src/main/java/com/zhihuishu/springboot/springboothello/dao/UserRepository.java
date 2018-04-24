package com.zhihuishu.springboot.springboothello.dao;

import com.zhihuishu.springboot.springboothello.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);
    User findByUserNameOrEmail(String username, String email);
}
