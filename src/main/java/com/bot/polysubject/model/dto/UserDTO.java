package com.bot.polysubject.model.dto;

import com.bot.polysubject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String role;
    private Integer rank;
    private String status;

    public UserDTO(User user){
        this.userId = user.getId();
        this.role = user.getRole();
        this.rank = user.getRank();
        this.status = user.getStatus();
    }
}
