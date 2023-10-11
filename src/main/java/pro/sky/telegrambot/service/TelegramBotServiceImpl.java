package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTasksRepository;
import pro.sky.telegrambot.tools.Parser;
@Service
public class TelegramBotServiceImpl implements TelegramBotService {

    private final TelegramBot telegramBot;
    private final NotificationTasksRepository taskRepository;

    public TelegramBotServiceImpl(TelegramBot telegramBot, NotificationTasksRepository taskRepository) {
        this.telegramBot = telegramBot;
        this.taskRepository = taskRepository;
    }

    @Override
    public void addTask(Message message) {
        NotificationTask task;
        long chatId = message.chat().id();
        SendMessage result;
        try {
            task = Parser.createTask(message.text());
            task.setChatId(chatId);
        } catch (Exception ex) {
            result = new SendMessage(chatId, "uncorrected data format");
            telegramBot.execute(result);
            return;
        }
        taskRepository.save(task);
        result = new SendMessage(chatId, "notification added");
        telegramBot.execute(result);
    }
}
