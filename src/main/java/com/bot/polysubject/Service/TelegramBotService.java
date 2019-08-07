package com.bot.polysubject.Service;

import com.bot.polysubject.entity.SubjectToBeNotified;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TelegramBotService {

    private static Logger logger =  LogManager.getLogger(TelegramBotService.class);

    private static final String BOT_TOKEN = "775562834:AAGK_MV3M7x3QKf8e4oniOwFs1QsB-YQ08k";

    private static final TelegramBot bot = new TelegramBot(BOT_TOKEN);

    @Autowired
    UserService userService;

    @Autowired
    SubjectToBeNotifiedService subjectToBeNotifiedService;

    @Autowired
    SubjectsService subjectsService;

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

        logger.info("command: " + command);
        logger.info("parameters: " + String.join(", ",parameters));

        switch(command) {
            case "\\start":
                startCommandHandler(telegramId, chatId, parameters);
                break;
            case "\\stop":
                stopCommandHandler(telegramId, chatId, parameters);
                break;
            case "\\add":
                addCommandHandler(telegramId, chatId, parameters);
                break;
            case "\\delete":
                deleteCommandHandler(telegramId, chatId, parameters);
                break;
            case "\\list":
                listCommandHandler(telegramId, chatId, parameters);
                break;
            case "\\test":
                testCommandHandler(telegramId, chatId, parameters);
                break;
        }
    }

    private void startCommandHandler(long telegramId, long chatId, String[] parameters) {
        createUserIfNotExist(telegramId);
        User user = userService.findUserByTelegramId(telegramId);
        user = userService.activateUser(telegramId);
        showUserInfo(telegramId, chatId, parameters);
    }

    private void stopCommandHandler(long telegramId, long chatId, String[] parameters) {
        createUserIfNotExist(telegramId);
        User user = userService.findUserByTelegramId(telegramId);
        user = userService.deactivateUser(telegramId);
        showUserInfo(telegramId, chatId, parameters);
    }

    private void addCommandHandler(long telegramId, long chatId, String[] parameters) {

        createUserIfNotExist(telegramId);
        User user = userService.findUserByTelegramId(telegramId);

        if(parameters.length != 2) {
            this.sendMessage(chatId, "The command should be \\add <subjectCode> <componentCode>");
            return;
        }

        String subjectCode = parameters[0];
        String componentCode = parameters[1];

        if(!subjectsService.isComponentExists(subjectCode, componentCode)) {
            this.sendMessage(chatId, "The subject code or component code is wrong");
            return;
        }

        subjectToBeNotifiedService.addSubjectNotification(user.getId(), subjectCode, componentCode);

    }

    private void deleteCommandHandler(long telegramId, long chatId, String[] parameters) {

        createUserIfNotExist(telegramId);
        User user = userService.findUserByTelegramId(telegramId);

        if(parameters.length != 2) {
            this.sendMessage(chatId, "The command should be \\delete <subjectCode> <componentCode>");
            return;
        }

        String subjectCode = parameters[0];
        String componentCode = parameters[1];

        subjectToBeNotifiedService.deleteSubjectNotification(user.getId(), subjectCode, componentCode);
    }

    private void listCommandHandler(long telegramId, long chatId, String[] parameters) {

        createUserIfNotExist(telegramId);
        showUserInfo(telegramId, chatId, parameters);
    }

    private void testCommandHandler(long telegramId, long chatId, String[] parameters) {

        createUserIfNotExist(telegramId);
        String message = subjectsService.getAllSubject()
                            .stream()
                            .map(x->x.getCode() + " - " + x.getComponents()
                                                            .stream()
                                                            .map(y->y.getCode())
                                                            .collect(Collectors.joining(", ")))
                            .collect(Collectors.joining("\n"));
        this.sendMessage(chatId, message);
    }

    private void createUserIfNotExist(long telegramId) {
        if (userService.findUserByTelegramId(telegramId) == null) {
            userService.createUser(telegramId);
        }
    }

    private void showUserInfo(long telegramId, long chatId, String[] parameters ) {
        User user = userService.findUserByTelegramId(telegramId);

        if(user==null) {
            throw new ApiException(ApiExceptionType.UserNotFound);
        }

        List<SubjectToBeNotified> subjectToBeNotifiedList = subjectToBeNotifiedService.getAllSubjects(user.getId());

        this.sendMessage(chatId, "telegramId: " + telegramId + "\n" +
                "chatId: " + chatId + "\n" +
                "status: " + user.getStatus() +
                "subjects: " +  subjectToBeNotifiedList.stream()
                                    .map(x->x.getSubjectCode() + " - " + x.getComponentCode())
                                    .collect(Collectors.joining("\n"))
                );
    }
}
