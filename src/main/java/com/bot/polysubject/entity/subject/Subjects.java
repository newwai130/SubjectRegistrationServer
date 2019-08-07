package com.bot.polysubject.entity.subject;

import com.bot.polysubject.entity.subject.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subjects {
    private List<Subject> subjects;
}
