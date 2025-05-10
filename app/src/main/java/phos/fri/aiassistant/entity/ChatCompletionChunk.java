package phos.fri.aiassistant.entity;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ChatCompletionChunk {

    @SerializedName("id")
    private String id;

    @SerializedName("choices")
    private List<Choice> choices;

    @SerializedName("created")
    private long created;

    @SerializedName("model")
    private String model;

    @SerializedName("object")
    private String object;

    @SerializedName("system_fingerprint")
    private String systemFingerprint;

    @SerializedName("usage")
    private Usage usage;

    // --- getters and setters ---

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public List<Choice> getChoices() { return choices; }
    public void setChoices(List<Choice> choices) { this.choices = choices; }

    public long getCreated() { return created; }
    public void setCreated(long created) { this.created = created; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getObject() { return object; }
    public void setObject(String object) { this.object = object; }

    public String getSystemFingerprint() { return systemFingerprint; }
    public void setSystemFingerprint(String systemFingerprint) { this.systemFingerprint = systemFingerprint; }

    public Usage getUsage() { return usage; }
    public void setUsage(Usage usage) { this.usage = usage; }

    // 嵌套的 Choice 类
    public static class Choice {

        @SerializedName("delta")
        private Delta delta;

        @SerializedName("finish_reason")
        private String finishReason;

        @SerializedName("index")
        private int index;

        @SerializedName("logprobs")
        private Object logprobs;

        public Delta getDelta() { return delta; }
        public void setDelta(Delta delta) { this.delta = delta; }

        public String getFinishReason() { return finishReason; }
        public void setFinishReason(String finishReason) { this.finishReason = finishReason; }

        public int getIndex() { return index; }
        public void setIndex(int index) { this.index = index; }

        public Object getLogprobs() { return logprobs; }
        public void setLogprobs(Object logprobs) { this.logprobs = logprobs; }
    }

    // 嵌套的 Delta 类
    public static class Delta {

        @SerializedName("content")
        private String content;

        @SerializedName("role")
        private String role;

        @SerializedName("function_call")
        private Object functionCall;

        @SerializedName("tool_calls")
        private Object toolCalls;

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public Object getFunctionCall() { return functionCall; }
        public void setFunctionCall(Object functionCall) { this.functionCall = functionCall; }

        public Object getToolCalls() { return toolCalls; }
        public void setToolCalls(Object toolCalls) { this.toolCalls = toolCalls; }
    }

    // 根据需求，你可以定义一个空的 Usage 类，或者根据真实结构补充字段
    public static class Usage {
        // JSON 中 "usage": null，目前没有字段，留空或定义为 Object
    }
}

