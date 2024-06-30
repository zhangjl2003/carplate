package cn.parking.park.controller;

import cn.parking.basics.utils.PageUtil;
import cn.parking.basics.utils.ResultUtil;
import cn.parking.basics.baseVo.PageVo;
import cn.parking.basics.baseVo.Result;
import cn.parking.basics.utils.SecurityUtil;
import cn.parking.data.entity.User;
import cn.parking.data.service.IUserService;
import cn.parking.data.utils.ZwzNullUtils;
import cn.parking.park.entity.IcCard;
import cn.parking.park.entity.ParkingData;
import cn.parking.park.entity.Vehicle;
import cn.parking.park.service.IIcCardService;
import cn.parking.park.service.IParkingDataService;
import cn.hutool.core.util.StrUtil;
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
@Api(tags = "停车记录管理接口")
@RequestMapping("/parking/parkingData")
@Transactional
public class ParkingDataController {

    @Autowired
    private IParkingDataService iParkingDataService;

    @Autowired
    private IVehicleService iVehicleService;

    @Autowired
    private IIcCardService iIcCardService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条停车记录")
    public Result<ParkingData> get(@RequestParam String id){
        return new ResultUtil<ParkingData>().setData(iParkingDataService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部停车记录个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iParkingDataService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部停车记录")
    public Result<List<ParkingData>> getAll(){
        return new ResultUtil<List<ParkingData>>().setData(iParkingDataService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询停车记录")
    public Result<IPage<ParkingData>> getByPage(@ModelAttribute ParkingData parkingData ,@ModelAttribute PageVo page){
        QueryWrapper<ParkingData> qw = new QueryWrapper<>();
        User currUser = securityUtil.getCurrUser();
        QueryWrapper<User> userQw = new QueryWrapper<>();
        userQw.eq("id",currUser.getId());
        userQw.inSql("id","SELECT user_id FROM a_user_role WHERE del_flag = 0 AND role_id = '1536606659751841799'");
        if(iUserService.count(userQw) < 1L) {
            qw.eq("owner_id",currUser.getId());
        }
        if(!ZwzNullUtils.isNull(parkingData.getCarNumber())) {
            qw.like("car_number",parkingData.getCarNumber());
        }
        if(!ZwzNullUtils.isNull(parkingData.getOwner())) {
            qw.like("owner",parkingData.getOwner());
        }
        IPage<ParkingData> data = iParkingDataService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<ParkingData>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改停车记录")
    public Result<ParkingData> saveOrUpdate(ParkingData parkingData){
        if(iParkingDataService.saveOrUpdate(parkingData)){
            return new ResultUtil<ParkingData>().setData(parkingData);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增停车记录")
    public Result<ParkingData> insert(ParkingData parkingData){
        Vehicle car = iVehicleService.getById(parkingData.getCarId());
        if(car == null) {
            return ResultUtil.error("车辆不存在");
        }
        // 判断余额
        QueryWrapper<IcCard> icQw = new QueryWrapper<>();
        icQw.eq("user_id",car.getOwnerId());
        icQw.last("limit 1");
        IcCard ic = iIcCardService.getOne(icQw);
        if(ic == null) {
            return ResultUtil.error("您没有IC卡，请申请");
        }
        if(parkingData.getCost().compareTo(ic.getBalance()) > 0) {
            return ResultUtil.error("您IC卡余额不足");
        }
        // 记录数据
        parkingData.setCarNumber(car.getCarNumber());
        parkingData.setOwnerId(car.getOwnerId());
        parkingData.setOwner(car.getOwnerName());
        iParkingDataService.saveOrUpdate(parkingData);
        ic.setBalance(ic.getBalance().subtract(parkingData.getCost()));
        iIcCardService.saveOrUpdate(ic);
        return new ResultUtil<ParkingData>().setData(parkingData);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑停车记录")
    public Result<ParkingData> update(ParkingData parkingData){
        Vehicle car = iVehicleService.getById(parkingData.getCarId());
        if(car == null) {
            return ResultUtil.error("车辆不存在");
        }
        parkingData.setCarNumber(car.getCarNumber());
        parkingData.setOwnerId(car.getOwnerId());
        parkingData.setOwner(car.getOwnerName());
        iParkingDataService.saveOrUpdate(parkingData);
        return new ResultUtil<ParkingData>().setData(parkingData);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除停车记录")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iParkingDataService.removeById(id);
        }
        return ResultUtil.success();
    }
}
