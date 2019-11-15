package cn.com.flat.head.dal.impl;

import cn.com.flat.head.dal.TaskDao;
import cn.com.flat.head.dal.mappers.TaskMapper;
import cn.com.flat.head.pojo.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class TaskDaoImlp implements TaskDao {
    @Autowired
    TaskMapper taskMapper;
    @Override
    public List<Task> getTaskListPage(Task task) {
        return taskMapper.getTaskListPage(task);
    }

    @Override
    public void addTask(Task task) {
        taskMapper.addTask(task);
    }

    @Override
    public Task getTaskById(String id) {
        return taskMapper.getTaskById(id);
    }

    @Override
    public void deleteTaskById(String id) {
        taskMapper.deleteTaskById(id);
    }

    @Override
    public void editTask(Task task) {
        taskMapper.editTask(task);
    }
}
