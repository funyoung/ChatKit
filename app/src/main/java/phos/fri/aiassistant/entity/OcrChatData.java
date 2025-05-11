package phos.fri.aiassistant.entity;

import java.util.List;

public class OcrChatData {
    public String id;
    public String model;
    public List<Choice> choices;

    public static class Choice {
        public Message message;
    }
    public static class Message {
        public String role;
        public String content;
    }
}
