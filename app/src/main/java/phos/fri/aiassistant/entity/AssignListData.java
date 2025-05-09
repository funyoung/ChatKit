package phos.fri.aiassistant.entity;

import java.util.List;

public class AssignListData {
    private int total;
    private List<DatasetItem> dataList;
    private int pageNo;
    private int pageSize;

    // Getters
    public int getTotal() { return total; }
    public List<DatasetItem> getDataList() { return dataList; }
    public int getPageNo() { return pageNo; }
    public int getPageSize() { return pageSize; }
}
