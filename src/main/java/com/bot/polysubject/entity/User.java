package com.bot.polysubject.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @Column(name = "userId")
    private Long id;

    @Column(name = "telegramId")
    private Long telegramId;

    @Column(name = "role")
    private String role;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "status")
    private String status;

    @Column(name = "repeatedMode")
    private String repeatedMode;

    public User(Long telegramId) {
        this.telegramId = telegramId;
        this.role = "normal user";
        this.rank = 5;
        this.status = "active";
        this.repeatedMode = "once";
    }
}
