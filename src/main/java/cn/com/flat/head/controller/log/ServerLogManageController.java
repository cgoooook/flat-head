package cn.com.flat.head.controller.log;

import cn.com.flat.head.pojo.ServerLog;
import cn.com.flat.head.service.ServerLogService;
import cn.com.flat.head.web.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by panzhuowen on 2019/11/17.
 */
@Controller
@RequestMapping("/log/serverLog")
public class ServerLogManageController {

    @Autowired
    private ServerLogService serverLogService;

    @RequestMapping
    public String serverLog(HttpServletRequest request) {
        List<ServerLog> serverLogList = serverLogService.getServerLogList();
        request.setAttribute("serverLogList", serverLogList);
        return "log/serverLog";
    }

    @RequestMapping("/download")
    public void downLoad(String fileName, HttpServletResponse response) throws Exception {
        byte[] fileContent = serverLogService.getFileContent(fileName);
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
        InputStream input = new ByteArrayInputStream(fileContent);
        //3、 写出文件--输出流
        OutputStream out = response.getOutputStream();
        byte[] buff = new byte[1024];
        int index = 0;
        //4、执行 写出操作
        while ((index = input.read(buff)) != -1) {
            out.write(buff, 0, index);
            out.flush();
        }
        out.close();
        input.close();
    }


    @RequestMapping("/detail")
    @ResponseBody
    public AjaxResponse getLogDetail(String fileName) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        byte[] fileContent = serverLogService.getFileContent(fileName);
        ajaxResponse.setData(new String(fileContent));
        return ajaxResponse;
    }

}
