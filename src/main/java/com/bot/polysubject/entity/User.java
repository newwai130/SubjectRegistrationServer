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

    @Column(name = "role")
    private String role;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "status")
    private String status;
}
