package pro.sky.telegrambot.tools;

import pro.sky.telegrambot.model.NotificationTask;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static NotificationTask createTask(String msgTxt) {
        Pattern pattern = Pattern.compile("(.{5})([0-9.:\\s]{16})(\\s)(.+)");
        Matcher matcher = pattern.matcher(msgTxt);
        NotificationTask task = new NotificationTask();
        if (matcher.matches()) {
            LocalDateTime dateTime = LocalDateTime.parse(matcher.group(2), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            task.setDateTime(dateTime);
            task.setNotification(matcher.group(4));
        } else {
            throw new IllegalArgumentException("uncorrected data format");
        }
        return task;
    }
}
