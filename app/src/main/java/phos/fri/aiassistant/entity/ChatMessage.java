package phos.fri.aiassistant.entity;

public class ChatMessage {
    private String role;
    private String content;

    public ChatMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }
    // … getter & setter …
}

