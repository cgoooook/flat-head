package cn.com.flat.head.scheduled;

import cn.com.flat.head.dal.KeyPairDao;
import cn.com.flat.head.dal.TaskDao;
import cn.com.flat.head.pojo.Task;
import cn.com.flat.head.service.KeyGenService;
import cn.com.flat.head.util.KeyTaskGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by panzhuowen on 2019/12/29.
 */
@Component
public class KeyPairScheduled {

    @Autowired
    private KeyPairDao keyPairDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    ExecutorService executorService;

    @Autowired
    private KeyGenService keyGenService;

    @Value("${keyNum}")
    private int keyNum;


    @Scheduled(fixedDelay = 60000)
    public void keyGenTask() {

        List<Task> taskList = taskDao.getTaskList();
        for (Task taskById : taskList) {
            int keyPairNum = keyPairDao.getKeyPairNum(taskById.getAlgorithm(), taskById.getLength() + "");
            if (Integer.parseInt(taskById.getPlannedQuantity()) > keyPairNum) {
                executorService.submit(new KeyTaskGen(keyPairDao,
                        keyGenService, taskById,
                        keyNum - keyPairNum, taskById.getTaskName()));
            }
        }


    }

}
