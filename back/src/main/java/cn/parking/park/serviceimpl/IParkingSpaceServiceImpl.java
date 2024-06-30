package cn.parking.park.serviceimpl;

import cn.parking.park.mapper.ParkingSpaceMapper;
import cn.parking.park.entity.ParkingSpace;
import cn.parking.park.service.IParkingSpaceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 停车位 服务层接口实现
 * @author zjl
 */
@Slf4j
@Service
@Transactional
public class IParkingSpaceServiceImpl extends ServiceImpl<ParkingSpaceMapper, ParkingSpace> implements IParkingSpaceService {

    @Autowired
    private ParkingSpaceMapper parkingSpaceMapper;
}