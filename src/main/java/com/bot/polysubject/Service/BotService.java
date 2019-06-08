package com.bot.polysubject.Service;

import com.bot.polysubject.Exception.ApiException;
import com.bot.polysubject.Exception.ApiExceptionType;
import com.bot.polysubject.entity.User;
import com.bot.polysubject.model.dto.UserDTO;
import com.bot.polysubject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BotService {

    @Autowired
    UserRepository userRepository;

    public List<UserDTO> getAllUser(){
        return userRepository
                .findAll()
                .stream()
                .map(x -> new UserDTO(x))
                .collect(Collectors.toList());
    };

    @Transactional(rollbackOn=Exception.class)
    public UserDTO activateUser(Long userId){

        User user = userRepository.findOneById(userId);

        if(user == null) {
            user = new User(userId, "Normal User", 5, "active");
        }else{
            user.setStatus("active");
        }

        user = userRepository.save(user);

        return new UserDTO(user);
    }

    @Transactional(rollbackOn=Exception.class)
    public UserDTO deactivateUser(Long userId) throws Exception{

        User user = userRepository.findOneById(userId);

        if(user == null) {
            throw new ApiException(ApiExceptionType.RecordNotFound);
        }else{
            user.setStatus("inactive");
        }

        user = userRepository.save(user);

        return new UserDTO(user);
    }
}
