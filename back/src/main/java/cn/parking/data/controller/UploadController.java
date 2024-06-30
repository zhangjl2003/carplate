package cn.parking.data.controller;

import cn.parking.basics.log.LogType;
import cn.parking.basics.log.SystemLog;
import cn.parking.basics.utils.*;
import cn.parking.data.entity.Setting;
import cn.parking.data.service.IFileService;
import cn.parking.data.service.ISettingService;
import cn.parking.data.utils.ZwzFileUtils;
import cn.parking.basics.baseVo.Result;
import cn.parking.data.entity.File;
import cn.hutool.core.util.StrUtil;
import cn.parking.data.vo.OssSettingVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author zjl
 *  
 */
@RestController
@Api(tags = "文件上传接口")
@RequestMapping("/parking/upload")
@Transactional
public class UploadController {

    @Autowired
    private ZwzFileUtils zwzFileUtils;

    @Autowired
    private ISettingService iSettingService;

    @Autowired
    private IFileService iFileService;

    @SystemLog(about = "文件上传", type = LogType.DATA_CENTER,doType = "FILE-06")
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    @ApiOperation(value = "文件上传")
    public Result<Object> upload(@RequestParam(required = false) MultipartFile file,@RequestParam(required = false) String base64) {
        if(StrUtil.isNotBlank(base64)){
            file = Base64DecodeMultipartFile.base64Convert(base64);
        }
        String result = null;
        String fKey = CommonUtil.renamePic(file.getOriginalFilename());
        File f = new File();
        try {
            InputStream inputStream = file.getInputStream();
            result = zwzFileUtils.inputStreamUpload(inputStream, fKey, file);
            f.setLocation(0);
            f.setName(file.getOriginalFilename());
            f.setSize(file.getSize());
            f.setType(file.getContentType());
            f.setFKey(fKey);
            f.setUrl(result);
            iFileService.saveOrUpdate(f);
        } catch (Exception e) {
            return ResultUtil.error(e.toString());
        }
        OssSettingVo vo = getOssSetting();
        return ResultUtil.data(vo.getFileHttp() + vo.getFileView() + "/" + f.getId());
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
