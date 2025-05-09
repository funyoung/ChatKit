package phos.fri.aiassistant.entity;

import java.util.List;

public class ChatCompletionRequest {
    private String model;
    private boolean stream;
    private List<ChatMessage> messages;
    private List<ChatFile> files;

    public ChatCompletionRequest(String model,
                                 boolean stream,
                                 List<ChatMessage> messages,
                                 List<ChatFile> files) {
        this.model = model;
        this.stream = stream;
        this.messages = messages;
        this.files = files;
    }
    // … getter & setter …
}
