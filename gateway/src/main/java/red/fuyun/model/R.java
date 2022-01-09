package red.fuyun.model;


import lombok.Data;

@Data
public class R<T> {
    /** 结果状态 ,具体状态码参见ResultData.java*/
    private int status;
    private String message;
    private T data;
    private long timestamp ;


    public R(){
        this.timestamp = System.currentTimeMillis();
    }


    public static <T> R<T> success(T data) {
        R<T> resultData = new R<T>();
        resultData.setStatus(ReturnCode.RC100.getCode());
        resultData.setMessage(ReturnCode.RC100.getMessage());
        resultData.setData(data);
        return resultData;
    }

    public static <T> R<T> fail(int code, String message) {
        R<T> resultData = new R<T>();
        resultData.setStatus(code);
        resultData.setMessage(message);
        return resultData;
    }

}

