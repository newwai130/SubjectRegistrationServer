package com.bot.polysubject.controller;

import com.bot.polysubject.Service.TelegramBotService;
import com.bot.polysubject.model.api.resq.UpdateVacancyReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
@RequestMapping(BaseController.API_URL + BaseController.SUBJECTS)
public class SubjectsController {

    @Autowired
    TelegramBotService telegramBotService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void updateSubjectsVacancy(@RequestBody UpdateVacancyReq updateVacancyReq) throws Exception {
        telegramBotService.updateVacancy(updateVacancyReq.getSubjects(), updateVacancyReq.getTime());
        return;
    }

}
