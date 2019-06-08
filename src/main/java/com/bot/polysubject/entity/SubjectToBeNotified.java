package com.bot.polysubject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@Table(name = "SubjectToBeNotified")
public class SubjectToBeNotified {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_userId")
    private User user;

    @Column(name = "subject_code")
    private String subjectCode;

    @Column(name = "component_code")
    private String componentCode;

}
