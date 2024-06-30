package cn.parking.data.serviceimpl;

import cn.parking.data.dao.mapper.RoleMapper;
import cn.parking.data.entity.Role;
import cn.parking.data.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zjl
 *
 */
@Service
public class IRoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
