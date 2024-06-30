package cn.parking.data.serviceimpl;

import cn.parking.data.dao.mapper.SettingMapper;
import cn.parking.data.entity.Setting;
import cn.parking.data.service.ISettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zjl
 *  
 */
@Service
public class ISettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements ISettingService {

}
