package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tele_bot")
public class NotificationTask {
    @Id
    @GeneratedValue
    private long id;//идентификатор сообщения
    private long chatId;//идентификатор чата
    private LocalDateTime dateTime;//дата и время отправки сообщения
    private String notification;//текст сообщения
    private boolean sent;//статус отправления - отправлено или нет

    public NotificationTask() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime data) {
        this.dateTime = data;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationTask)) return false;
        NotificationTask task = (NotificationTask) o;
        return getChatId() == task.getChatId() && Objects.equals(getDateTime(), task.getDateTime()) && Objects.equals(getNotification(), task.getNotification());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChatId(), getDateTime(), getNotification());
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", data=" + dateTime +
                ", notification='" + notification + '\'' +
                ", sent=" + sent +
                '}';
    }
}
