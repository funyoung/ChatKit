package phos.fri.aiassistant.entity;

import com.google.gson.annotations.SerializedName;

public class NewChatData {
    @SerializedName("id")
    private String chatId;


    public String getChatId() {
        return chatId;
        // 其他字段按需添加
    }

    // Setters
    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

}
