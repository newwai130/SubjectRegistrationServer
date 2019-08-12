package com.bot.polysubject.repository;

import com.bot.polysubject.entity.SubjectToBeNotified;
import com.bot.polysubject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findOneById(Long id);

    public User findOneByTelegramId(Long id);

    @Query(value="SELECT u FROM User u WHERE u.status = 'active' AND u.id IN (SELECT s.userId FROM SubjectToBeNotified s WHERE s.subjectCode = :subjectCode AND s.componentCode = :componentCode)")
    public List<User> findActiveUserThatGoingToBeNotifiedAvailableSubjectComponent(String subjectCode, String componentCode);
}
