import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class MyTelegramBot extends TelegramLongPollingBot {

    public static final String BOT_TOKEN = "6070528267:AAEan369IUigepevLzWCADwCwsuBoQZ410o";

    public static final String BOT_USERNAME = "KlgdNasaFoto_bot";

    public static final String URI = "https://api.nasa.gov/planetary/apod?api_key=4meJAvbxgvUvbmHLncotdmboryetOeTAsliiy5mb";

    public static  long  chat_id;

    public MyTelegramBot() throws TelegramApiException{
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }


    @Override
    public String getBotUsername() {
    return BOT_USERNAME;
}

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            chat_id = update.getMessage().getChatId();

            switch (update.getMessage().getText()) {
                case "/help":
                    sendMessage("Привет! Я бот NASA! Я высылаю ссылки на картинки по запросу /give. " +
                            "Напоминаю, что картинки на сайте NASA обновляются раз в сутки");
                    break;
                case  "/give":
                    try {
                        sendMessage(Utils.getUrl(URI));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    sendMessage("Я не понимаю :(");
            }
        }
    }

    private void sendMessage(String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
