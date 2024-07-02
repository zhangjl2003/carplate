package cn.parking.data.controller;

import cn.parking.basics.exception.AException;
import cn.parking.basics.log.LogType;
import cn.parking.basics.log.SystemLog;
import cn.parking.basics.utils.*;
import cn.parking.data.entity.Setting;
import cn.parking.data.entity.User;
import cn.parking.data.service.IFileService;
import cn.parking.data.service.ISettingService;
import cn.parking.data.service.IUserService;
import cn.parking.data.utils.AFileUtils;
import cn.parking.data.utils.ANullUtils;
import cn.parking.basics.baseVo.PageVo;
import cn.parking.basics.baseVo.Result;
import cn.parking.data.entity.File;
import cn.hutool.core.util.StrUtil;
import cn.parking.data.vo.OssSettingVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zjl
 *
 */
@Slf4j
@Controller
@Api(tags = "文件管理接口")
@RequestMapping("/parking/file")
@Transactional
public class FileController {

    @Autowired
    private AFileUtils AFileUtils;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IFileService iFileService;

    @Autowired
    private ISettingService iSettingService;

    @PersistenceContext
    private EntityManager entityManager;

    @SystemLog(about = "查询系统文件", type = LogType.DATA_CENTER,doType = "FILE-01")
    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "查询系统文件")
    @ResponseBody
    public Result<IPage<File>> getByCondition(@ModelAttribute File file,@ModelAttribute PageVo page) {
        QueryWrapper<File> qw = new QueryWrapper<>();
        if(!ANullUtils.isNull(file.getFKey())) {
            qw.eq("f_key",file.getFKey());
        }
        if(!ANullUtils.isNull(file.getType())) {
            qw.eq("type",file.getType());
        }
        if(!ANullUtils.isNull(file.getName())) {
            qw.eq("name",file.getName());
        }
        IPage<File> fileList = iFileService.page(PageUtil.initMpPage(page),qw);

        OssSettingVo os = getOssSetting();
        Map<String, String> map = new HashMap<>(16);
        for(File e : fileList.getRecords()) {
            if (e.getLocation() != null && Objects.equals(0,e.getLocation())) {
                String url = os.getFileHttp() + os.getFileView() + "/";
                entityManager.detach(e);
                e.setUrl(url + e.getId());
            }
            if (StrUtil.isNotBlank(e.getCreateBy())) {
                if (!map.containsKey(e.getCreateBy())) {
                    QueryWrapper<User> userQw = new QueryWrapper<>();
                    userQw.eq("username",e.getCreateBy());
                    User u = iUserService.getOne(userQw);
                    if (u != null) {
                        e.setNickname(u.getNickname());
                    }
                    map.put(e.getCreateBy(), u.getNickname());
                } else {
                    e.setNickname(map.get(e.getCreateBy()));
                }
            }
        }
        map = null;
        return new ResultUtil<IPage<File>>().setData(fileList);
    }

    @SystemLog(about = "文件复制", type = LogType.DATA_CENTER,doType = "FILE-02")
    @RequestMapping(value = "/copy", method = RequestMethod.POST)
    @ApiOperation(value = "文件复制")
    @ResponseBody
    public Result<Object> copy(@RequestParam String id,@RequestParam String key) {
        File file = iFileService.getById(id);
        if(file.getLocation() == null){
            file.setLocation(0);
        }
        String toKey = "副本_" + key;
        key = file.getUrl();
        String newUrl = AFileUtils.copyFile(key, toKey);
        File newFile = new File().setName(file.getName()).setFKey(toKey).setSize(file.getSize()).setType(file.getType()).setLocation(file.getLocation()).setUrl(newUrl);
        iFileService.saveOrUpdate(newFile);
        return ResultUtil.data();
    }

    @SystemLog(about = "文件重命名", type = LogType.DATA_CENTER,doType = "FILE-03")
    @RequestMapping(value = "/rename", method = RequestMethod.POST)
    @ApiOperation(value = "文件重命名")
    @ResponseBody
    public Result<Object> upload(@RequestParam String id, @RequestParam String newKey, @RequestParam String newName) {
        File file = iFileService.getById(id);
        if(file.getLocation() == null){
            file.setLocation(0);
        }
        String newUrl = "";
        String oldKey = file.getFKey();
        if(!Objects.equals(newKey,oldKey)){
            oldKey = file.getUrl();
            newUrl = AFileUtils.renameFile(oldKey, newKey);
        }
        file.setName(newName);
        file.setFKey(newKey);
        if(!oldKey.equals(newKey)) {
            file.setUrl(newUrl);
        }
        iFileService.saveOrUpdate(file);
        return ResultUtil.data();
    }

    @SystemLog(about = "文件重命名", type = LogType.DATA_CENTER,doType = "FILE-04")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "文件重命名")
    @ResponseBody
    public Result<Object> delete(@RequestParam String[] ids) {
        for(String id : ids) {
            File file = iFileService.getById(id);
            if(file.getLocation() == null){
                file.setLocation(0);
            }
            String key = file.getUrl();
            AFileUtils.deleteFile(key);
            iFileService.removeById(id);
        }
        return ResultUtil.data();
    }

    @SystemLog(about = "预览文件", type = LogType.DATA_CENTER,doType = "FILE-05")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "预览文件")
    public void view(@PathVariable String id,@RequestParam(required = false) String filename,@RequestParam(required = false, defaultValue = "false") Boolean preview,HttpServletResponse httpServletResponse) throws IOException {
        File selectFile = iFileService.getById(id);
        if(selectFile == null){
            throw new AException("文件不存在");
        }
        if(ANullUtils.isNull(filename)){
            filename =  selectFile.getFKey();
        }
        if(!preview){
            httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        }
        httpServletResponse.setContentLengthLong(selectFile.getSize());
        httpServletResponse.setContentType(selectFile.getType());
        httpServletResponse.addHeader("Accept-Ranges", "bytes");
        if(selectFile.getSize() != null && selectFile.getSize() > 0){
            httpServletResponse.addHeader("Content-Range", "bytes " + 0 + "-" + (selectFile.getSize()-1) + "/" + selectFile.getSize());
        }
        AFileUtils.view(selectFile.getUrl(), httpServletResponse);
    }

    public OssSettingVo getOssSetting() {
        Setting s1 = iSettingService.getById("FILE_VIEW");
        Setting s2 = iSettingService.getById("FILE_HTTP");
        Setting s3 = iSettingService.getById("FILE_PATH");
        if(s1 == null || s1 == null || s1 == null) {
            return null;
        }
        return new OssSettingVo(s1.getValue(),s2.getValue(),s3.getValue());
    }
}
