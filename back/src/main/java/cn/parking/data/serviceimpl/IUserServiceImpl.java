package cn.parking.data.serviceimpl;

import cn.parking.data.dao.mapper.UserMapper;
import cn.parking.data.entity.User;
import cn.parking.data.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zjl
 *  
 */
@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
