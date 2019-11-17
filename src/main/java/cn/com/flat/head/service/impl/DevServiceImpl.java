package cn.com.flat.head.service.impl;

import cn.com.flat.head.dal.DevServiceDao;
import cn.com.flat.head.mybatis.interceptor.PageableInterceptor;
import cn.com.flat.head.mybatis.model.Pageable;
import cn.com.flat.head.pojo.BooleanCarrier;
import cn.com.flat.head.pojo.Device;
import cn.com.flat.head.pojo.Organization;
import cn.com.flat.head.service.DevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DevServiceImpl implements DevService {
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
        BooleanCarrier b = new BooleanCarrier();
        b.setResult(true);
        String devId =dev.getDeviceId();
        String devName = dev.getDeviceName();
        String deviceCode = dev.getDeviceCode();

        Device devById = devServiceDao.getDevById(devId);

        Device devByName = devServiceDao.getDevByName(devName);
        Device devByDevCode = devServiceDao.getDevByDevCode(deviceCode);

        if(devByName!=null){

            if((!devId.equalsIgnoreCase(devByName.getDeviceId()))&&devByName.equals(devByName.getDeviceName())){
                b.setResult(false);
                b.setMessage("dev.nameIsRepeat");
                return b;

            }
        }

        if(devByDevCode!=null){
            if((!devId.equalsIgnoreCase(devByDevCode.getDeviceId()))&&deviceCode.equals(devById.getDeviceId())){
                b.setResult(false);
                b.setMessage("dev.codeIsRepeat");
                return b;
            }
        }





        devServiceDao.editOrg(dev);
        return b;
    }

    @Override
    public boolean deleteDevById(String id) {
        return   devServiceDao.deleteDevgById(id);
    }

    @Override
    public BooleanCarrier addDev(Device dev) {
        Device devById = devServiceDao.getDevById(dev.getDeviceId());
        Device devByName = devServiceDao.getDevByName(dev.getDeviceName());
        BooleanCarrier b = new BooleanCarrier();
        if(null != devById){
            b.setResult(false);
            b.setMessage("dev.codeIsRepeat");
            return b;
        }
        if(null != devByName){
            b.setResult(false);
            b.setMessage("dev.nameIsRepeat");
            return b;
        }
        dev.setDeviceId(UUID.randomUUID().toString());
        int num = devServiceDao.addDev(dev);
        if(num!=1){
            b.setMessage("common.addError");
            b.setResult(false);
        }else {
            b.setResult(true);
        }

        return b;
    }


}
