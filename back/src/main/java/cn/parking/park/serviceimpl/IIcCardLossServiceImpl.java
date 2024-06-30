package cn.parking.park.serviceimpl;

import cn.parking.park.mapper.IcCardLossMapper;
import cn.parking.park.entity.IcCardLoss;
import cn.parking.park.service.IIcCardLossService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * IC卡挂失 服务层接口实现
 * @author zjl
 */
@Slf4j
@Service
@Transactional
public class IIcCardLossServiceImpl extends ServiceImpl<IcCardLossMapper, IcCardLoss> implements IIcCardLossService {

    @Autowired
    private IcCardLossMapper icCardLossMapper;
}