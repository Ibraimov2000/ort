package com.exam.ort.service.impl;

import com.exam.ort.service.ReminderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReminderServiceImpl implements ReminderService {

    @Scheduled(cron = "0 0 9 * * ?")  // Каждый день в 9 утра
    public void sendDailyReminders() {
        // Логика отправки напоминаний всем пользователям о доступных тестах
        System.out.println("Sending daily test reminders.");
    }
}
