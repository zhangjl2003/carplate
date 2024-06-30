package cn.parking.data.serviceimpl;

import cn.parking.data.dao.mapper.PermissionMapper;
import cn.parking.data.entity.Permission;
import cn.parking.data.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zjl
 *  
 */
@Service
public class IPermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
