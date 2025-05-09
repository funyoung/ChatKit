package phos.fri.aiassistant.entity;

// 返回的最外层
public class ApiResponse<T> {
    private int code;
    private T data;
    private String message; // 如果后端有返回

    public boolean isSuccess() {
        return code == 0;
    }
    public int getCode() { return code; }
    public T getData() { return data; }
    public String getMessage() { return message; }

    public T getDataOrThrow() throws ApiException {
        if (isSuccess()) {
            return data;
        }
        throw new ApiException(code, "API请求失败");
    }
}

