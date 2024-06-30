package cn.parking.park.controller;

import cn.parking.basics.utils.PageUtil;
import cn.parking.basics.utils.ResultUtil;
import cn.parking.basics.baseVo.PageVo;
import cn.parking.basics.baseVo.Result;
import cn.parking.data.utils.ZwzNullUtils;
import cn.parking.park.entity.ParkingInfo;
import cn.parking.park.service.IParkingInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zjl
 *
 */
@Slf4j
@RestController
@Api(tags = "停车信息管理接口")
@RequestMapping("/parking/parkingInfo")
@Transactional
public class ParkingInfoController {

    @Autowired
    private IParkingInfoService iParkingInfoService;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条停车信息")
    public Result<ParkingInfo> get(@RequestParam String id){
        return new ResultUtil<ParkingInfo>().setData(iParkingInfoService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部停车信息个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iParkingInfoService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部停车信息")
    public Result<List<ParkingInfo>> getAll(){
        return new ResultUtil<List<ParkingInfo>>().setData(iParkingInfoService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询停车信息")
    public Result<IPage<ParkingInfo>> getByPage(@ModelAttribute ParkingInfo parkingInfo ,@ModelAttribute PageVo page){
        QueryWrapper<ParkingInfo> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(parkingInfo.getCreateBy())) {
        }
        IPage<ParkingInfo> data = iParkingInfoService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<ParkingInfo>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改停车信息")
    public Result<ParkingInfo> saveOrUpdate(ParkingInfo parkingInfo){
        if(iParkingInfoService.saveOrUpdate(parkingInfo)){
            return new ResultUtil<ParkingInfo>().setData(parkingInfo);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增停车信息")
    public Result<ParkingInfo> insert(ParkingInfo parkingInfo){
        iParkingInfoService.saveOrUpdate(parkingInfo);
        return new ResultUtil<ParkingInfo>().setData(parkingInfo);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑停车信息")
    public Result<ParkingInfo> update(ParkingInfo parkingInfo){
        iParkingInfoService.saveOrUpdate(parkingInfo);
        return new ResultUtil<ParkingInfo>().setData(parkingInfo);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除停车信息")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iParkingInfoService.removeById(id);
        }
        return ResultUtil.success();
    }
}
