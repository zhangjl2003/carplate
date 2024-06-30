package cn.parking.park.serviceimpl;

import cn.parking.park.mapper.VehicleMapper;
import cn.parking.park.entity.Vehicle;
import cn.parking.park.service.IVehicleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 车辆 服务层接口实现
 * @author zjl
 */
@Slf4j
@Service
@Transactional
public class IVehicleServiceImpl extends ServiceImpl<VehicleMapper, Vehicle> implements IVehicleService {

    @Autowired
    private VehicleMapper vehicleMapper;
}