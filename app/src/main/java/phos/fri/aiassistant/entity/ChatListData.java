package phos.fri.aiassistant.entity;

import java.util.List;

// data 字段对应
public class ChatListData {
    private int total;
    private List<ChatItem> dataList;
    private int pageNo;
    private int pageSize;

    public int getTotal() {
        return total;
    }

    public List<ChatItem> getDataList() {
        return dataList;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }
}
