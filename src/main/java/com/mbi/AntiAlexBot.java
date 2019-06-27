package com.mbi;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.mbi.Application.*;

/**
 * Do you have a gif-spammer in your telegram chat? Is his name Alex? Let this bot kill that gif garbage!
 * Bot deletes gifs sent by Alex. Alex is allowed to send MAX_GIF_COUNT within TIME_LIMIT. Every extra gif will be
 * deleted.
 * For example: Alex sent 3 gifs with date:
 * Gif_1: 2019-06-07T12:00:00:00Z
 * Gif_2: 2019-06-07T12:01:00:00Z
 * Gif_3: 2019-06-07T12:02:00:00Z
 * Then he tried to send one more gif. He got the result: 4th gif was deleted, message has been shown:
 * "Gifs limit exceeded! Next gif available at Fri Jun 07 13:00:00 EEST 2019".
 */
public class AntiAlexBot extends TelegramBot {

    private List<Message> shownGifList = new ArrayList<>();
    private List<Message> nextGifMessages = new ArrayList<>();

    @Override
    public void onUpdateReceived(final Update update) {
        final var telegramMessage = update.getMessage();

        if (isAlex(telegramMessage.getFrom())) {
            deleteGif(telegramMessage);
            deleteOutdatedNextGifMessage();
        }
    }

    /**
     * Deletes gifs sent by Alex. Alex is allowed to send MAX_GIF_COUNT within TIME_LIMIT. Every extra gif will be
     * deleted.
     * For example: Alex sent 3 gifs with date:
     * Gif_1: 2019-06-07T12:00:00:00Z
     * Gif_2: 2019-06-07T12:01:00:00Z
     * Gif_3: 2019-06-07T12:02:00:00Z
     * Then he tried to send one more gif. He got the result: 4th gif was deleted, message has been shown:
     * "Gifs limit exceeded! Next gif available at Fri Jun 07 13:00:00 EEST 2019".
     *
     * @param message telegram message.
     */
    private void deleteGif(final Message message) {
        if (!isGif(message)) {
            return;
        }

        if (allowedToShow(message)) {
            shownGifList.add(message);
        } else {
            nextGifMessages.add(sendTelegramMessage(message,
                    String.format("Gifs limit exceeded! Next gif available in %s", getNextGifTime(message))));
            deleteTelegramMessage(message);
        }
    }

    /**
     * Deletes repeating messages with next available gif time.
     */
    private void deleteOutdatedNextGifMessage() {
        if (nextGifMessages.size() > 1) {
            deleteTelegramMessage(nextGifMessages.get(nextGifMessages.size() - 2));
        }
    }

    /**
     * @param message telegram message.
     * @return if the message allowed to be shown.
     */
    private boolean allowedToShow(final Message message) {
        if (shownGifList.size() < Integer.parseInt(MAX_GIF_COUNT)) {
            return true;
        }

        return getOldestMessage().getDate() + Integer.parseInt(TIME_LIMIT) < message.getDate();
    }

    /**
     * @param user user
     * @return if user is Alex.
     */
    private boolean isAlex(final User user) {
        return user.getId().equals(Integer.parseInt(ALEX_ID));
    }

    /**
     * @param message telegram message.
     * @return if message is gif.
     */
    private boolean isGif(final Message message) {
        return !Objects.isNull(message.getDocument()) && message.getDocument().getMimeType().equals("video/mp4");
    }

    /**
     * @return the oldest shown message within time limit.
     */
    private Message getOldestMessage() {
        return shownGifList.get(shownGifList.size() - Integer.parseInt(MAX_GIF_COUNT));
    }

    /**
     * @param message telegram message.
     * @return next gif time in format "Nmin Nsec".
     */
    private String getNextGifTime(final Message message) {
        final var nextTime = getOldestMessage().getDate() + Integer.parseInt(TIME_LIMIT) - message.getDate();
        long minutes = TimeUnit.SECONDS.toMinutes(nextTime) - (TimeUnit.SECONDS.toHours(nextTime) * 60);
        long seconds = TimeUnit.SECONDS.toSeconds(nextTime) - (TimeUnit.SECONDS.toMinutes(nextTime) * 60);

        return minutes == 0 ? String.format("%dsec", seconds) : String.format("%dmin %dsec", minutes, seconds);
    }
}
