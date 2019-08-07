package com.bot.polysubject.Service;

import com.bot.polysubject.entity.subject.Subject;
import com.bot.polysubject.entity.SubjectToBeNotified;
import com.bot.polysubject.entity.subject.Subjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class SubjectsService {

    @Autowired
    private Subjects subjects;

    public boolean isSubjectExists(String subjectCode){
        return subjects.getSubjects()
                    .stream()
                    .anyMatch(x->subjectCode.equals(x.getCode()));
    };

    public boolean isComponentExists(String subjectCode, String componentCode) {
        return subjects.getSubjects()
                .stream()
                .filter(x->subjectCode.equals(x.getCode()))
                .anyMatch(x->x.getComponents()
                            .stream()
                            .anyMatch(y -> componentCode.equals(y.getCode()))
                );
    };

    public List<Subject> getAllSubject() {
        return subjects.getSubjects();
    }

}
