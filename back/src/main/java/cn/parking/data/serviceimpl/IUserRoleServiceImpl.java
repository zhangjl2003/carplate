package cn.parking.data.serviceimpl;

import cn.parking.data.dao.mapper.UserRoleMapper;
import cn.parking.data.entity.UserRole;
import cn.parking.data.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zjl
 *  
 */
@Service
public class IUserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
