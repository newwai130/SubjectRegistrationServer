package com.bot.polysubject.Service;

import com.bot.polysubject.entity.SubjectToBeNotified;
import com.bot.polysubject.repository.SubjectToBeNotifiedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SubjectToBeNotifiedService {

    @Autowired
    SubjectToBeNotifiedRepository subjectToBeNotifiedRepository;

    public List<SubjectToBeNotified> getAllSubjectsByUsedId(Long userId){
        return subjectToBeNotifiedRepository.findAllByUserId(userId);
    };

    public SubjectToBeNotified findSubjectNotification(Long userId, String subjectCode, String componentCode){
        return subjectToBeNotifiedRepository.findFirstByUserIdAndSubjectCodeAndComponentCode(userId, subjectCode, componentCode);
    };

    public List<SubjectToBeNotified> findSubjectNotificationBySubjectCodeAndComponentCode (String subjectCode, String componentCode){
        return subjectToBeNotifiedRepository.findBySubjectCodeAndComponentCode(subjectCode, componentCode);
    };



    public Long countUserAddedSubjectNotification(Long userId){
        return subjectToBeNotifiedRepository.countByUserId(userId);
    }

    @Transactional(rollbackOn=Exception.class)
    public SubjectToBeNotified addSubjectNotification(Long userId, String subjectCode, String componentCode){
        return subjectToBeNotifiedRepository.save(new SubjectToBeNotified(userId, subjectCode, componentCode));
    };

    @Transactional(rollbackOn=Exception.class)
    public void deleteSubjectNotification(long userId, String subjectCode, String componentCode){
        SubjectToBeNotified subjectToBeNotified = subjectToBeNotifiedRepository.findFirstByUserIdAndSubjectCodeAndComponentCode(userId, subjectCode, componentCode);
        if(subjectToBeNotified != null) {
            subjectToBeNotifiedRepository.delete(subjectToBeNotified);
        }
    }
}
