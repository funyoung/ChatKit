package phos.fri.aiassistant.net;

// 自定义异常
public class ApiException extends Exception {
    private int code;
    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }
    public int getCode() { return code; }
}
