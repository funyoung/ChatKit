package phos.fri.aiassistant.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

// data 字段对应
public class OcrData {
    private Result result;

    public static class Result {
        @SerializedName("page_contents")
        private List<PageContent> contentList;
        // 其他字段根据不需要，忽略
    }

    public static class PageContent {
        @SerializedName("page_layouts")
        private List<PageLayout> layoutList;
        // 其他字段根据不需要，忽略
    }

    public static class PageLayout {
        @SerializedName("node_id")
        private Integer id;
        private String type;
        private String text;
    }
}
