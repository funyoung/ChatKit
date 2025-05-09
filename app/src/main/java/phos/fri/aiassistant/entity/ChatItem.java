package phos.fri.aiassistant.entity;

// 单条聊天记录实体
public class ChatItem {
    private String chatId;
    private String datasetId;
    private String title;
    private String createTime;

    public String getChatId() {
        return chatId;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public String getTitle() {
        return title;
    }

    public String getCreateTime() {
        return createTime;
    }
}
