# anti-alex-bot
Do you have a gif-spammer in your telegram chat? Is his name Alex? Let this bot kill that gif garbage!
Bot deletes gifs sent by Alex. Alex is allowed to send MAX_GIF_COUNT within TIME_LIMIT. Every extra gif will be deleted.
  
  
## Example
Alex sent 3 gifs with date:
- Gif_1: 2019-06-07T12:00:00:00Z
- Gif_2: 2019-06-07T12:01:00:00Z
- Gif_3: 2019-06-07T12:02:00:00Z
  
  
Then he tried to send one more gif. He got the result: 4th gif was deleted, message has been shown: 
"Gifs limit exceeded! Next gif available at Fri Jun 07 13:00:00 EEST 2019".


## Expected system variables
- ALEX_ID=_telegram alex-user id_
- MAX_GIF_COUNT=_max allowed if count within time limit_
- TIME_LIMIT=_minutes * seconds_
- BOT_NAME=_telegram bot name_
- BOT_TOKEN=_telegram bot token_
