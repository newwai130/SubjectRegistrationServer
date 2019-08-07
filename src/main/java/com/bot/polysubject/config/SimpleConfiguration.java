package com.bot.polysubject.config;

import com.bot.polysubject.entity.subject.Subject;
import com.bot.polysubject.entity.subject.Subjects;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
public class SimpleConfiguration {

    private static Logger logger =  LogManager.getLogger(SimpleConfiguration.class);

    @Bean
    public Subjects readSubjectFile(){

        Subjects subjects = null;

        try {
            ClassPathResource resource  = new ClassPathResource("subjects.json");
            StringWriter writer = new StringWriter();
            IOUtils.copy(resource.getInputStream(), writer, StandardCharsets.UTF_8.name());
            //String jsonString = FileUtils.readFileToString(file);
            ObjectMapper objectMapper = new ObjectMapper();
            subjects = objectMapper.readValue(writer.toString(), Subjects.class);;
            for(Subject subject : subjects.getSubjects()) {
                subject.setComponents(
                        subject.getComponents()
                            .stream()
                            .distinct()
                            .collect(Collectors.toList())
                );
            }

        } catch (Exception e) {
            logger.info(ExceptionUtils.getStackTrace(e));
        } finally {
            return subjects;
        }

    }

}
