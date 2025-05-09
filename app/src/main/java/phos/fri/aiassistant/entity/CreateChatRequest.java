package phos.fri.aiassistant.entity;

import com.google.gson.annotations.SerializedName;

public class CreateChatRequest {
    @SerializedName("dataset_ids")
    private String datasetIds;

    @SerializedName("name")
    private String name;

    public CreateChatRequest(String datasetIds, String name) {
        this.datasetIds = datasetIds;
        this.name = name;
    }

    // Getter & Setter（可选，根据需要添加）
    public String getDatasetIds() {
        return datasetIds;
    }

    public void setDatasetIds(String datasetIds) {
        this.datasetIds = datasetIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

