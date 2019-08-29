package com.bot.polysubject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SubjectToBeNotified",
        indexes = {
                @Index(name = "userId_subjectCode_componentCode",  columnList="userId,subjectCode,componentCode"),
                @Index(name = "subjectCode_componentCode",  columnList="subjectCode,componentCode")
        })
public class SubjectToBeNotified {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name="userId")
    private Long userId;

    @Column(name = "subjectCode")
    private String subjectCode;

    @Column(name = "componentCode")
    private String componentCode;

    public SubjectToBeNotified(Long userId, String subjectCode, String componentCode) {
        this. userId = userId;
        this.subjectCode = subjectCode;
        this.componentCode = componentCode;
    }
}
