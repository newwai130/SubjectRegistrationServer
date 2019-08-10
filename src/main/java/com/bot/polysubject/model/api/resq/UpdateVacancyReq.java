package com.bot.polysubject.model.api.resq;

import com.bot.polysubject.entity.subject.Subjects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateVacancyReq {
    public Subjects subjects;
    public String time;
}
