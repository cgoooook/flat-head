package cn.com.flat.head.controller.config;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Device;
import cn.com.flat.head.pojo.Task;
import cn.com.flat.head.service.TaskService;
import cn.com.flat.head.web.AjaxResponse;
import cn.com.flat.head.web.DataTablesResponse;
import cn.com.flat.head.web.ReturnState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/task/config")
public class TaskController {
    @Autowired
    TaskService taskService;


    @RequestMapping
    public String getTaskUrl(){

        return "config/taskConfig";
    }

    @PostMapping("/list")
    @ResponseBody
    public DataTablesResponse<Task> getTaskListPage(Pageable pageable, Task task) {
        List<Task> taskListPage = taskService.getTaskListPage(pageable, task);
        return new DataTablesResponse<>(pageable, taskListPage);
    }
    @PutMapping("/add")
    @ResponseBody
    public AjaxResponse addTask(@RequestBody Task task, HttpSession httpSession) {
        task.setStatus("0");
        BooleanCarrier booleanCarrier = taskService.addTask(task);
        if(!booleanCarrier.getResult()){
            AjaxResponse ajaxResponse = new AjaxResponse();
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(booleanCarrier.getMessage());
            return ajaxResponse;
        }
        return AjaxResponse.getInstanceByResult(booleanCarrier.getResult(), httpSession);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public AjaxResponse getTask(@PathVariable("id") String id, HttpSession session) {
        Task task = taskService.getTaskById(id);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setReturnState(ReturnState.OK);
        ajaxResponse.setData(task);
        return ajaxResponse;
    }

    @PostMapping("/run/{id}")
    @ResponseBody
    public AjaxResponse runTask(@PathVariable("id") String id) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        BooleanCarrier booleanCarrier = taskService.runTaskById(id);
        if (!booleanCarrier.getResult()) {
            ajaxResponse.setReturnState(ReturnState.ERROR);
        }
        return ajaxResponse;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public AjaxResponse deleteTask(@PathVariable("id") String id, HttpSession session) {
        taskService.deleteTaskById(id);
        return AjaxResponse.getInstanceByResult(true, session);
    }
    @PostMapping("/status/{id}")
    @ResponseBody
    public AjaxResponse updateKeyStatus(@PathVariable("id") String id, int status, HttpSession session) {
        return AjaxResponse
                .getInstanceByResult(taskService.updateTaskStatus(id, status), session);
    }


    @PutMapping("/edit")
    @ResponseBody
    public AjaxResponse editOrg(@RequestBody Task task, HttpSession httpSession) {
        BooleanCarrier booleanCarrier = taskService.editTask(task);
        if(!booleanCarrier.getResult()){
            AjaxResponse ajaxResponse = new AjaxResponse();
            ajaxResponse.setReturnState(ReturnState.ERROR);
            ajaxResponse.setMsg(booleanCarrier.getMessage());
            return ajaxResponse;
        }
        return AjaxResponse.getInstanceByResult(booleanCarrier.getResult(), httpSession);
    }
}
