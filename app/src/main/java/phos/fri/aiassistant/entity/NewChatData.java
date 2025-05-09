package phos.fri.aiassistant.entity;

import com.google.gson.annotations.SerializedName;

public class NewChatData extends ChatItem {

    @SerializedName("oid")
    private String oid;


    public String getOid() {
        return oid;
    }

    // Setters
    public void setOid(String oid) {
        this.oid = oid;
    }

}
