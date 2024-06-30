package cn.parking.park.serviceimpl;

import cn.parking.park.mapper.IcCardMapper;
import cn.parking.park.entity.IcCard;
import cn.parking.park.service.IIcCardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * IC卡 服务层接口实现
 * @author zjl
 */
@Slf4j
@Service
@Transactional
public class IIcCardServiceImpl extends ServiceImpl<IcCardMapper, IcCard> implements IIcCardService {

    @Autowired
    private IcCardMapper icCardMapper;
}