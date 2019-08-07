package com.bot.polysubject.entity.subject;

import com.bot.polysubject.entity.subject.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    private String code;
    private List<Component> components;
}
