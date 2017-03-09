package rusk.com.akira;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetResponse {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("status")
    @Expose
    private String status;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String answer) {
        this.url = url;
    }

}