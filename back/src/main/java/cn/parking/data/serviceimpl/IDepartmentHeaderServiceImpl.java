package cn.parking.data.serviceimpl;

import cn.parking.data.dao.mapper.DepartmentHeaderMapper;
import cn.parking.data.entity.DepartmentHeader;
import cn.parking.data.service.IDepartmentHeaderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zjl
 *  
 */
@Service
public class IDepartmentHeaderServiceImpl extends ServiceImpl<DepartmentHeaderMapper, DepartmentHeader> implements IDepartmentHeaderService {

}
