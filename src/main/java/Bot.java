import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Bot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        System.getProperties().put( "proxySet", "true" );
        System.getProperties().put( "socksProxyHost", "127.0.0.1" );
        System.getProperties().put( "socksProxyPort", "9150" );
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if(message != null && message.hasText()) {
            String text = message.getText();
            if ("Привет".equals(text)) {
                sendMsg(message, "Привет, Я показываю погоду.");
            } else if ("Как тебя зовут".equals(text)) {
                sendMsg(message, "Меня зовут Паша.");
            } else {
                sendMsg(message, "У меня 2 функции:\n" +
                        "Привет\n" +
                        "Как тебя зовут");
            }
        }
    }

    public String getBotUsername() {
        return "WeatherAround";
    }

    public String getBotToken() {
        return "986809256:AAHhIhKn6v5F9Zt0SWy4LkRY8kQV8ZO3sIs";
    }
}
