package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.KeyPairDao;
import cn.com.flat.head.dal.LogDao;
import cn.com.flat.head.dal.TaskDao;
import cn.com.flat.head.log.LoggerBuilder;
import cn.com.flat.head.log.OperateType;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Task;
import cn.com.flat.head.service.KeyGenService;
import cn.com.flat.head.service.TaskService;
import cn.com.flat.head.util.KeyTaskGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private KeyPairDao keyPairDao;
    @Autowired
    private LogDao logDao;
    @Autowired
    private KeyGenService keyGenService;
    private static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
    @Override
    public List<Task> getTaskListPage(Pageable pageable, Task task) {
        PageableInterceptor.startPage(pageable);
        return taskDao.getTaskListPage(task);
    }

    @Override
    public BooleanCarrier addTask(Task task) {
        boolean result = true;
        BooleanCarrier booleanCarrier=new BooleanCarrier();
        booleanCarrier.setResult(false);
        try{

            taskDao.addTask(task);
            booleanCarrier.setResult(true);

        } catch (Exception e) {
            result = false;
            logger.error("editUiConfig", e);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.addTeak, result, "add task error"));
        }
        return booleanCarrier;
    }

    @Override
    public Task getTaskById(String id) {
        return taskDao.getTaskById(id);
    }

    @Override
    public void deleteTaskById(String id) {
        boolean result = true;
        try{
        taskDao.deleteTaskById(id);
        } catch (Exception e) {
            result = false;
            logger.error("deleteTaskById", e);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.deleteTask, result, "delete task error"));
        }

    }

    @Override
    public BooleanCarrier editTask(Task task) {
        BooleanCarrier booleanCarrier=new BooleanCarrier();
        booleanCarrier.setResult(false);
        boolean result = true;
        try{
        taskDao.editTask(task);
        booleanCarrier.setResult(true);
        }catch (Exception e) {
            result = false;
            logger.error("editTask", e);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.addTeak, result, "edit task error"));
        }
        return booleanCarrier;
    }

    @Override
    public boolean updateTaskStatus(String id, int status) {
        boolean result = true;
        int num;
        try{
         num  = taskDao.updateTaskStatus(id, status);
        }catch (Exception e) {
            result = false;
            logger.error("updateTaskStatus", e);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.updateTask, result, "update task status error"));
        }
        return result;
    }

    @Override
    public BooleanCarrier runTaskById(String id) {
        BooleanCarrier booleanCarrier = new BooleanCarrier();
        Task taskById = taskDao.getTaskById(id);
        int keyPairNum = keyPairDao.getKeyPairNum(taskById.getAlgorithm(), taskById.getLength() + "");
        if (Integer.parseInt(taskById.getPlannedQuantity()) > keyPairNum) {
            new Thread(new KeyTaskGen(keyPairDao,
                    keyGenService, taskById,
                    Integer.parseInt(taskById.getPlannedQuantity()) - keyPairNum)).start();
        }
        booleanCarrier.setResult(true);
        return booleanCarrier;
    }
}
