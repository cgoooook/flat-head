package cn.com.flat.head.web;

/**
 * Created by poney on 2019-09-30.
 */
public class AjaxResponse {

    private boolean ok;

    private String message;

    private Object data;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
