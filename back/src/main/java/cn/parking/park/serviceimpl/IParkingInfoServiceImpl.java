package cn.parking.park.serviceimpl;

import cn.parking.park.mapper.ParkingInfoMapper;
import cn.parking.park.entity.ParkingInfo;
import cn.parking.park.service.IParkingInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 停车信息 服务层接口实现
 * @author zjl
 *
 */
@Slf4j
@Service
@Transactional
public class IParkingInfoServiceImpl extends ServiceImpl<ParkingInfoMapper, ParkingInfo> implements IParkingInfoService {

    @Autowired
    private ParkingInfoMapper parkingInfoMapper;
}