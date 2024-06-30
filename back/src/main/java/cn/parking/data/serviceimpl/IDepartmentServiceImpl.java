package cn.parking.data.serviceimpl;

import cn.parking.data.dao.mapper.DepartmentMapper;
import cn.parking.data.entity.Department;
import cn.parking.data.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zjl
 *  
 */
@Service
public class IDepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
