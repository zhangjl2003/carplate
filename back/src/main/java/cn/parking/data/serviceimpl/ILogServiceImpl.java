package cn.parking.data.serviceimpl;

import cn.parking.data.dao.mapper.LogMapper;
import cn.parking.data.entity.Log;
import cn.parking.data.service.ILogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zjl
 *
 */
@Service
public class ILogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

}
