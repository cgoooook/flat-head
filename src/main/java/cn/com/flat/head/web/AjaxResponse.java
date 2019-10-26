package cn.com.flat.head.web;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by poney on 2019-09-30.
 */
public class AjaxResponse {

    private ReturnState returnState;
    private String msg;
    private Object data;

    public AjaxResponse() {
        this(ReturnState.OK, "");
    }

    public AjaxResponse(String returnMsg) {
        this(ReturnState.OK, returnMsg);
    }

    public AjaxResponse(ReturnState returnState, String returnMsg) {
        this.returnState = ReturnState.OK;
        this.data = new HashMap();
        this.returnState = returnState;
        this.msg = returnMsg;
    }

    public AjaxResponse(BindingResult result) {
        this.returnState = ReturnState.OK;
        this.data = new Object();
        this.returnState = ReturnState.ERROR;
        this.msg = "";

        ObjectError objectError;
        for (Iterator iterator = result.getAllErrors().iterator(); iterator.hasNext(); this.msg = this.msg + objectError.getDefaultMessage()) {
            objectError = (ObjectError) iterator.next();
        }

    }

    public static AjaxResponse getInstanceByResult(boolean result, HttpSession session) {
        return result ? new AjaxResponse(ReturnState.OK, "操作成功") : new AjaxResponse(ReturnState.ERROR, "操作失败");
    }

    public boolean isOk() {
        return this.returnState == ReturnState.OK;
    }

    public boolean isWarning() {
        return this.returnState == ReturnState.WARNING;
    }

    public boolean isError() {
        return this.returnState == ReturnState.ERROR;
    }

    public ReturnState getReturnState() {
        return this.returnState;
    }

    public void setReturnState(ReturnState returnState) {
        this.returnState = returnState;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
