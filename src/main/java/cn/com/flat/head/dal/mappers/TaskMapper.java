package cn.com.flat.head.dal.mappers;

import cn.com.flat.head.mybatis.RepositoryImpl;
import cn.com.flat.head.pojo.Task;

import java.util.List;

@RepositoryImpl
public interface TaskMapper {
     Task getTaskById(String id);

     void addTask(Task task);

     List<Task> getTaskListPage(Task task) ;

    void deleteTaskById(String id);

    void editTask(Task task);
}
