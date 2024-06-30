package cn.parking.data.serviceimpl;

import cn.parking.data.dao.mapper.DictDataMapper;
import cn.parking.data.entity.DictData;
import cn.parking.data.service.IDictDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zjl
 *
 */
@Service
public class IDictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements IDictDataService {

}
