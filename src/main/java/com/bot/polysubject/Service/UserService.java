package com.bot.polysubject.Service;

import com.bot.polysubject.exception.ApiException;
import com.bot.polysubject.exception.ApiExceptionType;
import com.bot.polysubject.entity.User;
import com.bot.polysubject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUser(){
        return userRepository.findAll();
    };

    public User findUserById(long id){
        return userRepository.findOneById(id);
    };

    public User findUserByTelegramId(long telegramId){
        return userRepository.findOneByTelegramId(telegramId);
    };

    public User createUser(Long telegramId){
        return userRepository.save(new User(telegramId));
    };

    @Transactional(rollbackOn=Exception.class)
    public User activateUser(long telegramId){

        User user = userRepository.findOneByTelegramId(telegramId);

        if(user == null) {
            this.createUser(telegramId);
        }else {
            user.setStatus("active");
        }

        return userRepository.save(user);
    }

    @Transactional(rollbackOn=Exception.class)
    public User deactivateUser(long telegramId) {

        User user = userRepository.findOneByTelegramId(telegramId);

        if(user == null) {
            throw new ApiException(ApiExceptionType.UserNotFound);
        }else{
            user.setStatus("inactive");
        }

        return userRepository.save(user);
    }
}
