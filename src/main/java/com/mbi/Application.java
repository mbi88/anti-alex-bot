package com.mbi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class Application {

    public final static String ALEX_ID = System.getenv("ALEX_ID");
    public final static String MAX_GIF_COUNT = System.getenv("MAX_GIF_COUNT");
    public final static String TIME_LIMIT = System.getenv("TIME_LIMIT"); // minutes * seconds
    public final static String BOT_NAME = System.getenv("BOT_NAME");
    public final static String BOT_TOKEN = System.getenv("BOT_NAME");
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        ApiContextInitializer.init();
        final var telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new AntiAlexBot());
        } catch (TelegramApiRequestException e) {
            log.error(e.getMessage());
        }
    }
}
