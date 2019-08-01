package com.bot.polysubject.Service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class TelegramBotService {

    private static Logger logger =  LogManager.getLogger(TelegramBotService.class);

    private static final String BOT_TOKEN = "775562834:AAGK_MV3M7x3QKf8e4oniOwFs1QsB-YQ08k";

    private static final TelegramBot bot = new TelegramBot(BOT_TOKEN);

    private void sendmessage(long chatId, String message) {
        SendResponse response = bot.execute(new SendMessage(chatId, message));
    }

    public void dispatcher(String inputText) {
        if (StringUtils.isEmpty(inputText)) {
            return;
        }

        String parts[] = inputText.split(" ", 2);
        String command = parts[0];
        String parameters = parts[1];



        switch(command) {
            case "\\start":
                break;
            case "\\stop":
                break;
            case "\\adds":
                break;
            case "\\delete":
                break;


        }

    }
}
