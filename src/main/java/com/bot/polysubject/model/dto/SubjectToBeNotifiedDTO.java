package com.bot.polysubject.model.dto;

import com.bot.polysubject.entity.SubjectToBeNotified;
import com.bot.polysubject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
public class SubjectToBeNotifiedDTO {

    private Long id;
    private User user;
    private String subjectCode;
    private String componentCode;

    public SubjectToBeNotifiedDTO(SubjectToBeNotified subjectToBeNotified) {
        this.id = subjectToBeNotified.getId();
        this.user = subjectToBeNotified.getUser();
        this.subjectCode = subjectToBeNotified.getSubjectCode();
        this.componentCode = subjectToBeNotified.getComponentCode();
    }

}
