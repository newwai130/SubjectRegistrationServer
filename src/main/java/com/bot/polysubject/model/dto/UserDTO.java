package com.bot.polysubject.model.dto;

import com.bot.polysubject.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private Long telegramId;
    private String role;
    private Integer rank;
    private String status;
    private String repeatedMode;

    public UserDTO(User user){
        this.userId = user.getId();
        this.telegramId = user.getTelegramId();
        this.role = user.getRole();
        this.rank = user.getRank();
        this.status = user.getStatus();
        this.repeatedMode = user.getRepeatedMode();
    }
}
