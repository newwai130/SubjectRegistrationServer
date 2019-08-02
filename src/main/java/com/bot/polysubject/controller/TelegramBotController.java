package com.bot.polysubject.controller;


import com.bot.polysubject.Service.TelegramBotService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(BaseController.API_URL + BaseController.TELEGRAM_BOT)
public class TelegramBotController {

    private static Logger logger =  LogManager.getLogger(TelegramBotController.class);

    @Autowired
    TelegramBotService telegramBotService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String test() throws Exception {
        logger.info(1);
        return "Telegram Bot run normally";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String botRequest2(HttpServletRequest request) throws Exception {
        String body = IOUtils.toString(request.getInputStream());
        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();

        long telegramId = jsonObject.get("message").getAsJsonObject().get("from").getAsJsonObject().get("id").getAsLong();
        long chatId = jsonObject.get("message").getAsJsonObject().get("chat").getAsJsonObject().get("id").getAsLong();
        String text = jsonObject.get("message").getAsJsonObject().get("text").getAsString();

        logger.info("chatId: " + chatId);
        logger.info("text: " + text);
        telegramBotService.dispatcher(telegramId, chatId, text);
        return "Nice to meet you";
    }

}
