package com.bot.polysubject.Service;

import com.bot.polysubject.entity.SubjectToBeNotified;
import com.bot.polysubject.exception.ApiException;
import com.bot.polysubject.exception.ApiExceptionType;
import com.bot.polysubject.entity.User;
import com.bot.polysubject.model.dto.SubjectToBeNotifiedDTO;
import com.bot.polysubject.model.dto.UserDTO;
import com.bot.polysubject.repository.SubjectToBeNotifiedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectToBeNotifiedService {

    @Autowired
    SubjectToBeNotifiedRepository subjectToBeNotifiedRepository;

    public List<SubjectToBeNotifiedDTO> getAllSubjects(Long userId){
        return subjectToBeNotifiedRepository
                .findAllById(userId)
                .stream()
                .map(x -> new SubjectToBeNotifiedDTO(x))
                .collect(Collectors.toList());
    };

    @Transactional(rollbackOn=Exception.class)
    public SubjectToBeNotifiedDTO addSubjectNotification(Long userId, String subjectCode, String componentCode){
        return new SubjectToBeNotifiedDTO(
                    subjectToBeNotifiedRepository.save(new SubjectToBeNotified(userId, subjectCode, componentCode))
                );

    };

    @Transactional(rollbackOn=Exception.class)
    public void deleteSubjectNotification(Long userId, String subjectCode, String componentCode){
        SubjectToBeNotified subjectToBeNotified = subjectToBeNotifiedRepository.findByUserIdAndSubjectCodeAndComponentCode(userId, subjectCode, componentCode);
        if(subjectToBeNotified != null) {
            subjectToBeNotifiedRepository.delete(subjectToBeNotified);
        }
    }
}
