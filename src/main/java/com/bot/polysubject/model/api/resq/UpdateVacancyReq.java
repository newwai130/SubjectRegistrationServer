package com.bot.polysubject.model.api.resq;

import com.bot.polysubject.entity.subject.Subject;
import com.bot.polysubject.entity.subject.Subjects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVacancyReq {
    public List<Subject> subjects;
    public String time;
}
