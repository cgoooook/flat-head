package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.TaskDao;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Task;
import cn.com.flat.head.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskDao taskDao;
    @Override
    public List<Task> getTaskListPage(Pageable pageable, Task task) {
        PageableInterceptor.startPage(pageable);
        return taskDao.getTaskListPage(task);
    }

    @Override
    public BooleanCarrier addTask(Task task) {
        BooleanCarrier booleanCarrier=new BooleanCarrier();
        taskDao.addTask(task);
        booleanCarrier.setResult(true);
        return booleanCarrier;
    }

    @Override
    public Task getTaskById(String id) {
        return taskDao.getTaskById(id);
    }

    @Override
    public void deleteTaskById(String id) {
        taskDao.deleteTaskById(id);
    }

    @Override
    public BooleanCarrier editTask(Task task) {
        BooleanCarrier booleanCarrier=new BooleanCarrier();
        taskDao.editTask(task);
        booleanCarrier.setResult(true);
        return booleanCarrier;
    }

    @Override
    public boolean updateTaskStatus(String id, int status) {
        int num  = taskDao.updateTaskStatus(id, status);
        if(num>0){
            return  true;
        }else {
            return false;
        }
    }
}
