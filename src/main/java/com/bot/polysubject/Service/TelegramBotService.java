package com.bot.polysubject.Service;

import com.bot.polysubject.entity.SubjectToBeNotified;
import com.bot.polysubject.entity.User;
import com.bot.polysubject.entity.subject.Component;
import com.bot.polysubject.entity.subject.Subject;
import com.bot.polysubject.entity.subject.Subjects;
import com.bot.polysubject.entity.telagramNotification.NotificationMessage;
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

import java.util.ArrayList;
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

        String[] parameters = null;
        if(parts.length > 1) {
            parameters = parts[1].split(" ");
            logger.info("parameters: " + String.join(", ",parameters));
        }

        logger.info("command: " + command);


        switch(command) {
            case "/start":
                startCommandHandler(telegramId, chatId, parameters);
                break;
            case "/stop":
                stopCommandHandler(telegramId, chatId, parameters);
                break;
            case "/add":
                addCommandHandler(telegramId, chatId, parameters);
                break;
            case "/delete":
                deleteCommandHandler(telegramId, chatId, parameters);
                break;
            case "/list":
                listCommandHandler(telegramId, chatId, parameters);
                break;
            case "/showSubjects":
                showSubjectsCommandHandler(telegramId, chatId, parameters); //get all subject
                break;
            case "/commands":
                showCommandsCommandHandler(telegramId, chatId, parameters);
                break;
        }
    }

    public void startCommandHandler(long telegramId, long chatId, String[] parameters) {
        createUserIfNotExist(telegramId, chatId);
        User user = userService.findUserByTelegramId(telegramId);
        user = userService.activateUser(telegramId);
        showUserInfo(telegramId, chatId, parameters);
    }

    public void stopCommandHandler(long telegramId, long chatId, String[] parameters) {
        createUserIfNotExist(telegramId, chatId);
        User user = userService.findUserByTelegramId(telegramId);
        user = userService.deactivateUser(telegramId);
        showUserInfo(telegramId, chatId, parameters);
    }

    public void addCommandHandler(long telegramId, long chatId, String[] parameters) {

        createUserIfNotExist(telegramId, chatId);
        User user = userService.findUserByTelegramId(telegramId);

        if(parameters==null || parameters.length != 2) {
            this.sendMessage(chatId, "The command should be /add <subjectCode> <componentCode>");
            return;
        }

        String subjectCode = parameters[0];
        String componentCode = parameters[1];

        if(!subjectsService.isComponentExists(subjectCode, componentCode)) {
            this.sendMessage(chatId, "The subject code or component code is wrong");
            return;
        }

        logger.info("total: " + subjectToBeNotifiedService.countUserAddedSubjectNotification(user.getId()));
        if(subjectToBeNotifiedService.countUserAddedSubjectNotification(user.getId()) > 5 ) {
            this.sendMessage(chatId, "Maximum number of subject that can be added is 5");
            return;
        }

        if(subjectToBeNotifiedService.findSubjectNotification(user.getId(), subjectCode, componentCode) == null) {
            subjectToBeNotifiedService.addSubjectNotification(user.getId(), subjectCode, componentCode);
        }

        showUserInfo(telegramId, chatId, parameters);
    }

    public void deleteCommandHandler(long telegramId, long chatId, String[] parameters) {

        createUserIfNotExist(telegramId, chatId);
        User user = userService.findUserByTelegramId(telegramId);

        if(parameters==null || parameters.length != 2) {
            this.sendMessage(chatId, "The command should be delete <subjectCode> <componentCode>");
            return;
        }

        String subjectCode = parameters[0];
        String componentCode = parameters[1];

        subjectToBeNotifiedService.deleteSubjectNotification(user.getId(), subjectCode, componentCode);

        showUserInfo(telegramId, chatId, parameters);
    }

    public void listCommandHandler(long telegramId, long chatId, String[] parameters) {

        createUserIfNotExist(telegramId, chatId);
        showUserInfo(telegramId, chatId, parameters);
    }

    public void showSubjectsCommandHandler(long telegramId, long chatId, String[] parameters) {

        createUserIfNotExist(telegramId, chatId);
        String message = subjectsService.getAllSubject()
                            .stream()
                            .map(x->x.getCode() + " - " + x.getComponents()
                                                            .stream()
                                                            .map(y->y.getCode())
                                                            .collect(Collectors.joining(", ")))
                            .collect(Collectors.joining("\n"));
        this.sendMessage(chatId, message);
    }

    public void showCommandsCommandHandler(long telegramId, long chatId, String[] parameters) {

        String message = "/start\n" +
                          "/stop\n" +
                          "/add <SubjectCode> <ComponentCode>\n" +
                          "/delete <SubjectCode> <ComponentCode>\n" +
                          "/list\n" +
                          "/showSubjects\n" +
                          "/commands\n";

        this.sendMessage(chatId, message);
    }

    public void createUserIfNotExist(long telegramId, long chatId) {
        if (userService.findUserByTelegramId(telegramId) == null) {
            userService.createUser(telegramId, chatId);
        }
    }

    public void showUserInfo(long telegramId, long chatId, String[] parameters ) {
        User user = userService.findUserByTelegramId(telegramId);

        if(user==null) {
            throw new ApiException(ApiExceptionType.UserNotFound);
        }

        List<SubjectToBeNotified> subjectToBeNotifiedList = subjectToBeNotifiedService.getAllSubjectsByUsedId(user.getId());

        this.sendMessage(chatId,
                "telegramId: " + telegramId + "\n" +
                "chatId: " + chatId + "\n" +
                "status: " + user.getStatus() + "\n" +
                "subjects: " +  subjectToBeNotifiedList.stream()
                                    .map(x->x.getSubjectCode() + " - " + x.getComponentCode())
                                    .collect(Collectors.joining("\n")) + "\n"
                );
    }

    public void updateVacancy(List<Subject> subjects, String time) {

        List<NotificationMessage> notificationMessageList = new ArrayList<>();

        for(Subject subject: subjects) {
            for(Component component: subject.getComponents()) {
                if(component.getVacancy() > 0) {
                    List<User> userList = userService.findActiveUserThatGoingToBeNotifiedAvailableSubjectComponent(subject.getCode(), component.getCode());
                    //logger.info("vacancy: " + component.getVacancy() );
                    //userList.stream().forEach(x->logger.info(x.getId()));
                    userList.stream()
                            .forEach(x-> notificationMessageList.add(new NotificationMessage(x.getChatId(), subject.getCode() + " " + component.getCode() + " could be added @ " + time)));
                }
            }
        }

        //logger.info("start notify length: " + notificationMessageList.size() );
        for(NotificationMessage notificationMessage: notificationMessageList) {

            this.sendMessage(notificationMessage.getChatId(), notificationMessage.getMessage());
            try {
                Thread.sleep(40);
            } catch (Exception e) {
                //
            }
        }
    }
}
