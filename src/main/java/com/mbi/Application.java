package com.mbi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Application {

    public final static String BOT_NAME = System.getenv("BOT_NAME");
    public final static String BOT_TOKEN = System.getenv("BOT_TOKEN");
    public final static String ALEX_ID = System.getenv("ALEX_ID");
    public final static String MAX_GIF_COUNT = System.getenv("MAX_GIF_COUNT");
    public final static String TIME_LIMIT = System.getenv("TIME_LIMIT"); // minutes * seconds
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        try {
            final var telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new AntiAlexBot(BOT_TOKEN));
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
