package phos.fri.aiassistant.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OcrChatData {
    public String id;
    public String model;
    public List<Choice> choices;

    public static class Choice {
        @SerializedName("finish_reason")
        public String finishReason;
        public Message message;
    }
    public static class Message {
        public String role;
        public String content;
    }
}
