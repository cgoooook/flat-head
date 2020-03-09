package cn.com.flat.head.service;

import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Task;

import java.util.List;

public interface TaskService {
    List<Task> getTaskListPage(Pageable pageable, Task task);

    BooleanCarrier addTask(Task task);

    Task getTaskById(String id);

    void deleteTaskById(String id);

    BooleanCarrier editTask(Task task);

    boolean updateTaskStatus(String id, int status);

    BooleanCarrier runTaskById(String id);
}
