package phos.fri.aiassistant.entity;

// 嵌套的data数据套装
class EmbeddedData<T> {
    private T data;

    public T getData() { return data; }
}

// /ai/create/api/v1/chats接口返回data嵌套data
public class FuckNewChatData extends EmbeddedData<NewChatData> {
}

