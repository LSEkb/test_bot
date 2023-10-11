package pro.sky.telegrambot.service;


import com.pengrad.telegrambot.model.Message;

public interface TelegramBotService {
    void addTask(Message message);
}
