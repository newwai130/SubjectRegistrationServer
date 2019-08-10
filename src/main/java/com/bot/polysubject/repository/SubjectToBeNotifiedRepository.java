package com.bot.polysubject.repository;

import com.bot.polysubject.entity.SubjectToBeNotified;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectToBeNotifiedRepository extends JpaRepository<SubjectToBeNotified, Long> {

    public List<SubjectToBeNotified> findAllById(Long id);

    public List<SubjectToBeNotified> findAllByUserId(Long userId);

    public List<SubjectToBeNotified> findBySubjectCodeAndComponentCode(String subjectCode, String componentCode);

    public SubjectToBeNotified findFirstByUserIdAndSubjectCodeAndComponentCode(Long userId, String subjectCode, String componentCode);

    public void delete(SubjectToBeNotified subjectToBeNotified);

    public Integer countByUserId(Long userId);

}
