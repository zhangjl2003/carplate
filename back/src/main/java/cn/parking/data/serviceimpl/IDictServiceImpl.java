package cn.parking.data.serviceimpl;

import cn.parking.data.dao.mapper.DictMapper;
import cn.parking.data.entity.Dict;
import cn.parking.data.service.IDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zjl
 *  
 */
@Service
public class IDictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

}
