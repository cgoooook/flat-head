package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.DevServiceDao;
import cn.com.flat.head.dal.LogDao;
import cn.com.flat.head.log.LoggerBuilder;
import cn.com.flat.head.log.OperateType;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Device;
import cn.com.flat.head.service.DevService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DevServiceImpl implements DevService {

    private static Logger logger = LoggerFactory.getLogger(DevServiceImpl.class);

    @Autowired
    private LogDao logDao;

    @Autowired
    private DevServiceDao devServiceDao;

    @Override
    public List<Device> getDevListPage(Device dev, Pageable pageable) {
        PageableInterceptor.startPage(pageable);
        return devServiceDao.getDevListPage(dev);


    }

    @Override
    public int getDevCountByCollectionId(String collectionId) {
        return devServiceDao.getCollectionCountByCollectionId(collectionId);
    }

    @Override
    public Device getDevByDevCode(String deviceCode) {
        return devServiceDao.getDevByDevCode(deviceCode);
    }

    @Override
    public Device getDevByDevId(String deviceId) {
        return devServiceDao.getDevById(deviceId);
    }

    @Override
    public BooleanCarrier editDev(Device dev) {
        boolean result = true;
        BooleanCarrier b = new BooleanCarrier();
        try {
            b.setResult(true);
            String devId = dev.getDeviceId();
            String devName = dev.getDeviceName();
            String deviceCode = dev.getDeviceCode();

            Device devById = devServiceDao.getDevById(devId);

            Device devByName = devServiceDao.getDevByName(devName);
            Device devByDevCode = devServiceDao.getDevByDevCode(deviceCode);

            if (devByName != null) {

                if ((!devId.equalsIgnoreCase(devByName.getDeviceId())) && devByName.equals(devByName.getDeviceName())) {
                    b.setResult(false);
                    b.setMessage("dev.nameIsRepeat");
                    return b;
                }
            }

            if (devByDevCode != null) {
                if ((!devId.equalsIgnoreCase(devByDevCode.getDeviceId())) && deviceCode.equals(devById.getDeviceId())) {
                    b.setResult(false);
                    b.setMessage("dev.codeIsRepeat");
                    return b;
                }
            }
            devServiceDao.editOrg(dev);
        } catch (Exception e) {
            result = false;
            b.setResult(false);
            logger.error("delete device error", e);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.updateDevice, result, "update device devicename is" + dev.getDeviceName()));
        }
        return b;
    }

    @Override
    public boolean deleteDevById(String id) {
        boolean result = true;
        try {
            result = devServiceDao.deleteDevgById(id);
        } catch (Exception e) {
            result = false;
            logger.error("delete device error", e);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.deleteDevice, result, "delete device id is " + id));
        }
        return result;
    }

    @Override
    public BooleanCarrier addDev(Device dev) {
        boolean result = true;
        BooleanCarrier b = new BooleanCarrier();
        try {
            Device devById = devServiceDao.getDevByDevCode(dev.getDeviceCode());
            Device devByName = devServiceDao.getDevByName(dev.getDeviceName());
            if (null != devById) {
                b.setResult(false);
                b.setMessage("dev.codeIsRepeat");
                return b;
            }
            if (null != devByName) {
                b.setResult(false);
                b.setMessage("dev.nameIsRepeat");
                return b;
            }
            dev.setDeviceId(UUID.randomUUID().toString());
            int num = devServiceDao.addDev(dev);
            if (num != 1) {
                b.setMessage("common.addError");
                b.setResult(false);
            } else {
                b.setResult(true);
            }
        } catch (Exception e) {
            b.setResult(false);
            result = false;
            logger.error("add device error", e);
        } finally {
            logDao.addLog(LoggerBuilder.builder(OperateType.addDevice, result, "add device name is" + dev.getDeviceName()));
        }

        return b;
    }


}
