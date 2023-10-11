package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTasksRepository;
import pro.sky.telegrambot.service.TelegramBotService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private NotificationTasksRepository repository;

    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private TelegramBotService telegramBotService;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            String message = update.message().text();
            if (update.message() == null) {
                logger.error("Message is null");
                return;
            } else {
                logger.info("Processing message: \"{}\"", message);
            }
            String usage = "Напишите время отправки и текст напоминания в следующем виде:\n" +
                    "01.01.2022 20:00 Сделать домашнюю работу";
            if (message.equals("/start")) {
                replyToMessage(update, "Hello!\n" + usage);
            }
            if (message.equals("/help")) {
                replyToMessage(update, usage);
            }
            if (update.message().text().startsWith("/add")){
                telegramBotService.addTask(update.message());
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Scheduled(cron="0 0/1 * * * *")
    public void NotificationSentNow(){
        LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        Collection<NotificationTask> tasks_now = repository.findByDateTime(dateTime);
        for(NotificationTask task:tasks_now){
            var response = new SendMessage(task.getChatId(),task.getNotification());
            telegramBot.execute(response);
            task.setSent(true);
        }
    }

    private void replyToMessage(Update update, String text) {
        telegramBot.execute(new SendMessage(update.message().chat().id(), text));
    }
}
