package cn.parking.data.serviceimpl;

import cn.parking.data.dao.mapper.FileMapper;
import cn.parking.data.entity.File;
import cn.parking.data.service.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zjl
 *
 */
@Service
public class IFileServiceImpl extends ServiceImpl<FileMapper, File> implements IFileService {

}
