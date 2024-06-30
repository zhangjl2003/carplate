package cn.parking.park.serviceimpl;

import cn.parking.park.mapper.FeeScaleMapper;
import cn.parking.park.entity.FeeScale;
import cn.parking.park.service.IFeeScaleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 收费标准 服务层接口实现
 * @author zjl
 *
 */
@Slf4j
@Service
@Transactional
public class IFeeScaleServiceImpl extends ServiceImpl<FeeScaleMapper, FeeScale> implements IFeeScaleService {

    @Autowired
    private FeeScaleMapper feeScaleMapper;
}