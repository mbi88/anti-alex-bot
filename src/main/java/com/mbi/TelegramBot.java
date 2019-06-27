package com.mbi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static com.mbi.Application.BOT_NAME;
import static com.mbi.Application.BOT_TOKEN;

public abstract class TelegramBot extends TelegramLongPollingBot {

    private final Logger log = LoggerFactory.getLogger(TelegramBot.class);

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    protected synchronized void deleteTelegramMessage(final Message msg) {
        var message = new DeleteMessage(msg.getChatId(), msg.getMessageId());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    protected synchronized Message sendTelegramMessage(final Message msg, final String text) {
        final var message = new SendMessage();
        message.setChatId(msg.getChatId());
        message.setText(text);

        Message response = null;
        try {
            response = execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }

        return response;
    }
}
