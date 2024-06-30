package cn.parking.data.serviceimpl;

import cn.parking.data.dao.mapper.RolePermissionMapper;
import cn.parking.data.entity.RolePermission;
import cn.parking.data.service.IRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zjl
 *
 */
@Service
public class IRolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

}
