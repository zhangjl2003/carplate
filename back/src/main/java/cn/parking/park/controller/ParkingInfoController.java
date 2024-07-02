package cn.parking.park.controller;

import cn.parking.basics.utils.PageUtil;
import cn.parking.basics.utils.ResultUtil;
import cn.parking.basics.baseVo.PageVo;
import cn.parking.basics.baseVo.Result;
import cn.parking.data.utils.ANullUtils;
import cn.parking.park.entity.FeeScale;
import cn.parking.park.entity.IcCard;
import cn.parking.park.entity.ParkingInfo;
import cn.parking.park.entity.Vehicle;
import cn.parking.park.service.IFeeScaleService;
import cn.parking.park.service.IParkingInfoService;
import cn.parking.park.service.IVehicleService;
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

    @Autowired
    private IVehicleService iVehicleService;

    @Autowired
    private IFeeScaleService iFeeScaleService;

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
        if(!ANullUtils.isNull(parkingInfo.getCreateBy())) {
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
        System.out.println("User Info:"+parkingInfo.toString());
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("carNumber:"+parkingInfo.getCarNumber());
        QueryWrapper<Vehicle> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_number", parkingInfo.getCarNumber()); // 构建查询条件

        queryWrapper.last("limit 1");
        Vehicle car = iVehicleService.getOne(queryWrapper);
        if(car == null) {
            iParkingInfoService.saveOrUpdate(parkingInfo);
            return new ResultUtil<ParkingInfo>().setData(parkingInfo);
        }else{

            parkingInfo.setUserId(car.getOwnerId());
            iParkingInfoService.saveOrUpdate(parkingInfo);
            return new ResultUtil<ParkingInfo>().setData(parkingInfo);
        }
//        if(parkingData.getCost().compareTo(ic.getBalance()) > 0) {
//            return ResultUtil.error("您IC卡余额不足");
//        }

//        return new ResultUtil<ParkingInfo>().setData(parkingInfo);
    }
    @RequestMapping(value = "/departure", method = RequestMethod.POST)
    @ApiOperation(value = "出库")
    public Result<ParkingInfo> departure(ParkingInfo parkingInfo){
        QueryWrapper<FeeScale> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fee_type", parkingInfo.getFeeId()); // 构建查询条件

//        queryWrapper.last("limit 1");
        FeeScale feescale = iFeeScaleService.getOne(queryWrapper);
        if(feescale == null){
            parkingInfo.setFee("500"); // 假设totalFee是ParkingInfo中用于存储总费用的字段
            // 保存或更新停车信息

        }else{
            parkingInfo.setFee("600");
            // 保存或更新停车信息

        }
        iParkingInfoService.saveOrUpdate(parkingInfo);
        return new ResultUtil<ParkingInfo>().setData(parkingInfo);


//        if (feescale == null) {
//            // 如果没有找到匹配的费用标准，这是一个异常情况，需要处理
//            return new ResultUtil<ParkingInfo>().setErrorMsg("未找到对应的费用标准");
//        } else {
//            // 计算费用逻辑（这里只是一个示意，具体计算逻辑依据实际需求）
//            // 假设calculateFee方法根据停车时长和FeeScale计算费用
//
//            parkingInfo.setFee("500"); // 假设totalFee是ParkingInfo中用于存储总费用的字段
//
//            // 保存或更新停车信息
//            iParkingInfoService.saveOrUpdate(parkingInfo);
//
//            return new ResultUtil<ParkingInfo>().setData(parkingInfo);
//        }
//        System.out.println("User Info:"+parkingInfo.toString());
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        System.out.println("carNumber:"+parkingInfo.getCarNumber());
//        QueryWrapper<Vehicle> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("car_number", parkingInfo.getCarNumber()); // 构建查询条件
//
//        queryWrapper.last("limit 1");
//        Vehicle car = iVehicleService.getOne(queryWrapper);
//        if(car == null) {
//            iParkingInfoService.saveOrUpdate(parkingInfo);
//            return new ResultUtil<ParkingInfo>().setData(parkingInfo);
//        }else{
//
//            parkingInfo.setUserId(car.getOwnerId());
//            iParkingInfoService.saveOrUpdate(parkingInfo);
//            return new ResultUtil<ParkingInfo>().setData(parkingInfo);
//        }
//        if(parkingData.getCost().compareTo(ic.getBalance()) > 0) {
//            return ResultUtil.error("您IC卡余额不足");
//        }

//        return new ResultUtil<ParkingInfo>().setData(parkingInfo);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑停车信息")
    public Result<ParkingInfo> update(ParkingInfo parkingInfo){
        if(parkingInfo.getDepartureTime()==null){
            iParkingInfoService.saveOrUpdate(parkingInfo);
            return new ResultUtil<ParkingInfo>().setData(parkingInfo);
        }else{
            QueryWrapper<FeeScale> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("fee_type", parkingInfo.getFeeId());
            FeeScale fee = iFeeScaleService.getOne(queryWrapper);
            iParkingInfoService.saveOrUpdate(parkingInfo);
            return new ResultUtil<ParkingInfo>().setData(parkingInfo);
        }

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
