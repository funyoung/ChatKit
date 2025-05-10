package phos.fri.aiassistant.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class OcrData {
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String parseContent() {
        if (null != result) {
            return result.concatContent();
        }
        return null;
    }

    public static class Result {
        @SerializedName("page_contents")
        private List<PageContent> contentList;

        public List<PageContent> getContentList() {
            return contentList;
        }

        public void setContentList(List<PageContent> contentList) {
            this.contentList = contentList;
        }

        public String concatContent() {
            if (null != contentList && !contentList.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (PageContent content: contentList) {
                    List<PageLayout> layoutList = null == content ? Collections.emptyList() : content.getLayoutList();
                    if (null != layoutList) {
                        for (PageLayout layout : layoutList) {
                            String text = null == layout ? null : layout.getText();
                            if (null != text) {
                                sb.append(text);
                            }
                        }
                    }
                }
                return sb.toString();
            }
            return null;
        }
    }

    public static class PageContent {
        @SerializedName("page_layouts")
        private List<PageLayout> layoutList;

        public List<PageLayout> getLayoutList() {
            return layoutList;
        }

        public void setLayoutList(List<PageLayout> layoutList) {
            this.layoutList = layoutList;
        }
    }

    public static class PageLayout {
        @SerializedName("node_id")
        private Integer id;
        private String type;
        private String text;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}

