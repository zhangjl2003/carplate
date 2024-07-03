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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
//import java.util.Optional.*
import java.time.Duration;
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
        QueryWrapper<ParkingInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_number", parkingInfo.getCarNumber());
        // 查询所有具有相同car_number的ParkingInfo记录
        List<ParkingInfo> existingParkingInfos = iParkingInfoService.list(queryWrapper);
        // 检查是否存在departure_time为空的记录
        boolean hasNullDepartureTime = existingParkingInfos.stream()
                .anyMatch(pi -> pi.getDepartureTime() == null);
        if (hasNullDepartureTime) {
            return new ResultUtil<ParkingInfo>().setErrorMsg("存在未完成的停车记录，无法新增停车信息");
        } else {
            QueryWrapper<Vehicle> qw = new QueryWrapper<>();
            qw.eq("car_number", parkingInfo.getCarNumber());
            qw.last("limit 1");
            Vehicle car = iVehicleService.getOne(qw);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedNow = now.format(formatter);
            parkingInfo.setEntryTime(formattedNow);
            if(car == null) {
                iParkingInfoService.saveOrUpdate(parkingInfo);
                return new ResultUtil<ParkingInfo>().setData(parkingInfo);
            }else{
                parkingInfo.setUserId(car.getOwnerId());
                iParkingInfoService.saveOrUpdate(parkingInfo);
                return new ResultUtil<ParkingInfo>().setData(parkingInfo);
            }
        }

    }
    @RequestMapping(value = "/departure", method = RequestMethod.POST)
    @ApiOperation(value = "出库")
    public Result<ParkingInfo> departure(ParkingInfo parkingInfo){
        QueryWrapper<ParkingInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_number", parkingInfo.getCarNumber());
        // 查询所有具有相同car_number的ParkingInfo记录
        List<ParkingInfo> existingParkingInfos = iParkingInfoService.list(queryWrapper);

        System.out.println("aaaaaa"+existingParkingInfos.get(0).getDepartureTime());
        // 检查是否存在departure_time为空的记录
        // 使用Stream API从列表中找到第一个departureTime为null的ParkingInfo
        Optional<ParkingInfo> parkingInfoWithNullDepartureTime = existingParkingInfos.stream()
                .filter(pi -> pi.getDepartureTime() == null||pi.getDepartureTime() == "")
                .findFirst();
        if (parkingInfoWithNullDepartureTime.isPresent()) {
            ParkingInfo foundParkingInfo = parkingInfoWithNullDepartureTime.get();
            // 在这里处理foundParkingInfo，它是departureTime为null的ParkingInfo对象
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // 你可以在这里设置departureTime为当前时间
            QueryWrapper<FeeScale> qw = new QueryWrapper<>();
            qw.eq("fee_type", foundParkingInfo.getFeeId()); //
            queryWrapper.last("limit 1");
            FeeScale feescale = iFeeScaleService.getOne(qw);
            // 获取当前时间作为departureTime
            LocalDateTime departureTime = LocalDateTime.now();
            LocalDateTime entryTime = LocalDateTime.parse(foundParkingInfo.getEntryTime(), formatter);
            foundParkingInfo.setDepartureTime(departureTime.format(formatter));
// 计算两个时间点之间的时间差（使用Duration）
            Duration duration = Duration.between(entryTime, departureTime);
            long minutes = duration.toMinutes();
// 或者使用ChronoUnit直接计算分钟数
            long minutesByChronoUnit = java.time.temporal.ChronoUnit.MINUTES.between(entryTime, departureTime);
// 输出结果
            System.out.println("Time difference in minutes (using Duration): " + minutes);
            System.out.println("Time difference in minutes (using ChronoUnit): " + minutesByChronoUnit);

            double startfee = Double.parseDouble(feescale.getStartPrice());
            double freetime = Double.parseDouble(feescale.getFreeTime());
            double beyondfee = Double.parseDouble(feescale.getBeyondPrice());
            if (minutes <= freetime) {
                foundParkingInfo.setFee("0");
            } else {
                foundParkingInfo.setFee(String.valueOf(startfee + (minutes - freetime) * beyondfee));
            }
            // 输出结果
//            System.out.println("Time difference in minutes (using Duration): " + duration);
////        System.out.println("Time difference in minutes (using ChronoUnit): " + minutesByChronoUnit);
//            iParkingInfoService.saveOrUpdate(parkingInfo);
////        parkingInfo.setEntryTime(String(parkingInfo.getCreateTime()));
//            System.out.println("[[[[[[[[[[[[[[[");
//            System.out.println(parkingInfo.getCreateTime());
////        return new ResultUtil<ParkingInfo>().setData(parkingInfo);
//
            iParkingInfoService.saveOrUpdate(foundParkingInfo);

            return new ResultUtil<ParkingInfo>().setData(foundParkingInfo);
        }else{
            System.out.println("++++++++++++++");
            return new ResultUtil<ParkingInfo>().setErrorMsg("yitingche，无法新增停车信息");
        }


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
