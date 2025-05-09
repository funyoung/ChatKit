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

    // Getters
}
