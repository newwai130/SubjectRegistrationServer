package com.bot.polysubject.Service;

import com.bot.polysubject.entity.User;
import com.bot.polysubject.exception.ApiException;
import com.bot.polysubject.exception.ApiExceptionType;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotService {

    private static Logger logger =  LogManager.getLogger(TelegramBotService.class);

    private static final String BOT_TOKEN = "775562834:AAGK_MV3M7x3QKf8e4oniOwFs1QsB-YQ08k";

    private static final TelegramBot bot = new TelegramBot(BOT_TOKEN);

    @Autowired
    UserService userService;

    @Autowired
    SubjectToBeNotifiedService subjectToBeNotifiedService;

    private void sendMessage(long chatId, String message) {
        SendResponse response = bot.execute(new SendMessage(chatId, message));
    }

    public void dispatcher(long telegramId, long chatId, String inputText) {
        if (StringUtils.isEmpty(inputText)) {
            return;
        }

        String parts[] = inputText.split(" ", 2);
        String command = parts[0];
        String[] parameters = parts[1].split(" ");

        switch(command) {
            case "\\start":
                startCommandHandler(telegramId, chatId, parameters);
                break;
            case "\\stop":
                break;
            case "\\add":
                break;
            case "\\delete":
                break;
            case "\\list":
                break;
        }
    }

    private void startCommandHandler(long telegramId, long chatId, String[] parameters) {
        createUserIfNotExist(telegramId);
        User user = userService.findUserByTelegramId(telegramId);
        user = userService.activateUser(telegramId);
        showUserInfo(telegramId, chatId, parameters);
    }

    private void stopCommandHandler(long telegramId, long chatId, String[] parameters) throws Exception {
        createUserIfNotExist(telegramId);
        User user = userService.findUserByTelegramId(telegramId);
        user = userService.deactivateUser(telegramId);
        showUserInfo(telegramId, chatId, parameters);
    }

    private void addCommandHandler(long telegramId, long chatId, String[] parameters) {
        createUserIfNotExist(telegramId);
        if(parameters.length != 2) {
            this.sendMessage(chatId, "The command should be \\add <subjectCode> <componentCode>");
        }
    }

    private void deleteCommandHandler(long telegramId, long chatId, String[] parameters) {
        createUserIfNotExist(telegramId);
    }

    private void createUserIfNotExist(long telegramId) {
        if (userService.findUserByTelegramId(telegramId) == null) {
            userService.createUser(telegramId);
        }
    }

    private void showUserInfo(long telegramId, long chatId, String[] parameters) {
        User user = userService.findUserByTelegramId(telegramId);
        if(user==null) {
            throw new ApiException(ApiExceptionType.UserNotFound);
        }
        this.sendMessage(chatId, "telegramId: " + telegramId + "\n" +
                "chatId: " + chatId + "\n" +
                "status" + user.getStatus()
                );
    }
}
