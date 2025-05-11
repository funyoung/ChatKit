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

    public String getOcrText() {
        if (null == result) {
            return null;
        }

        List<PageContent> contentList = result.getPageContents();
        if (null == contentList) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (PageContent content : contentList) {
            if (null != content) {
                List<PageLayout> layoutList = content.getPageLayouts();
                if (null != layoutList) {
                    for (PageLayout layout : layoutList) {
                        if (null != layout) {
                            String text = layout.getText();
                            if (null != text) {
                                stringBuilder.append(text);
                            }
                        }
                    }
                }
            }
        }
        return stringBuilder.length() > 0 ? stringBuilder.toString() : null;
    }

    public static class Result {
        @SerializedName("para_node_tree")
        private List<ParaNode> paraNodeTree;

        @SerializedName("pdf_data")
        private String pdfData;
        @SerializedName("page_contents")
        private List<PageContent> pageContents;

        public String getPdfData() {
            return pdfData;
        }

        public void setPdfData(String pdfData) {
            this.pdfData = pdfData;
        }

        public List<PageContent> getPageContents() {
            return pageContents;
        }

        public void setPageContents(List<PageContent> pageContents) {
            this.pageContents = pageContents;
        }

        public List<ParaNode> getParaNodeTree() {
            return paraNodeTree;
        }

        public void setParaNodeTree(List<ParaNode> paraNodeTree) {
            this.paraNodeTree = paraNodeTree;
        }
    }

    public static class ParaNode {
        private Integer parent;
        private List<Integer> children;
        @SerializedName("para_type")
        private String paraType;
        private String text;
        private List<Position> position;
        private Object table;
        @SerializedName("node_id")
        private int nodeId;

        public Integer getParent() {
            return parent;
        }

        public void setParent(Integer parent) {
            this.parent = parent;
        }

        public List<Integer> getChildren() {
            return children;
        }

        public void setChildren(List<Integer> children) {
            this.children = children;
        }

        public String getParaType() {
            return paraType;
        }

        public void setParaType(String paraType) {
            this.paraType = paraType;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<Position> getPosition() {
            return position;
        }

        public void setPosition(List<Position> position) {
            this.position = position;
        }

        public Object getTable() {
            return table;
        }

        public void setTable(Object table) {
            this.table = table;
        }

        public int getNodeId() {
            return nodeId;
        }

        public void setNodeId(int nodeId) {
            this.nodeId = nodeId;
        }
    }

    public static class Position {
        @SerializedName("page_num")
        private int pageNum;
        private List<Integer> box;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public List<Integer> getBox() {
            return box;
        }

        public void setBox(List<Integer> box) {
            this.box = box;
        }
    }

    public static class PageContent {
        @SerializedName("page_type")
        private String pageType;
        @SerializedName("page_angle")
        private int pageAngle;
        @SerializedName("page_num")
        private int pageNum;
        @SerializedName("page_layouts")
        private List<PageLayout> pageLayouts;
        private List<String> titles;
        @SerializedName("page_width")
        private int pageWidth;
        @SerializedName("page_height")
        private int pageHeight;

        public String getPageType() {
            return pageType;
        }

        public void setPageType(String pageType) {
            this.pageType = pageType;
        }

        public int getPageAngle() {
            return pageAngle;
        }

        public void setPageAngle(int pageAngle) {
            this.pageAngle = pageAngle;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public List<PageLayout> getPageLayouts() {
            return pageLayouts;
        }

        public void setPageLayouts(List<PageLayout> pageLayouts) {
            this.pageLayouts = pageLayouts;
        }

        public List<String> getTitles() {
            return titles;
        }

        public void setTitles(List<String> titles) {
            this.titles = titles;
        }

        public int getPageWidth() {
            return pageWidth;
        }

        public void setPageWidth(int pageWidth) {
            this.pageWidth = pageWidth;
        }

        public int getPageHeight() {
            return pageHeight;
        }

        public void setPageHeight(int pageHeight) {
            this.pageHeight = pageHeight;
        }
    }

    public static class PageLayout {
        private String text;
        private String type;
        @SerializedName("node_id")
        private int nodeId;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getNodeId() {
            return nodeId;
        }

        public void setNodeId(int nodeId) {
            this.nodeId = nodeId;
        }
    }
}


