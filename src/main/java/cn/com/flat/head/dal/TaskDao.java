package cn.com.flat.head.dal;

import cn.com.flat.head.pojo.Task;

import java.util.List;

public interface TaskDao {
    List<Task> getTaskListPage(Task task);

    void addTask(Task task);

    Task getTaskById(String id);

    void deleteTaskById(String id);

    void editTask(Task task);
}
