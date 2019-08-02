package com.bot.polysubject.repository;

import com.bot.polysubject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findOneById(Long id);

    public User findOneByTelegramId(Long id);
}
