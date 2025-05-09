package phos.fri.aiassistant.entity;

import com.google.gson.annotations.SerializedName;

public class DatasetItem {
    @SerializedName("oid")
    private String oid;

    @SerializedName("datasetId")
    private String datasetId;

    @SerializedName("datasetName")
    private String datasetName;

    @SerializedName("datasetDesc")
    private String datasetDesc;

    @SerializedName("createTime")
    private String createTime;

    public String getOid() {
        return oid;
    }

    public String getDatasetId() {
        return datasetId;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public String getDatasetDesc() {
        return datasetDesc;
    }

    public String getCreateTime() {
        return createTime;
    }

    // Setters
    public void setOid(String oid) {
        this.oid = oid;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public void setDatasetDesc(String datasetDesc) {
        this.datasetDesc = datasetDesc;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
