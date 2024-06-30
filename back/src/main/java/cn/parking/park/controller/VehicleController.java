package cn.parking.park.controller;

import cn.parking.basics.utils.PageUtil;
import cn.parking.basics.utils.ResultUtil;
import cn.parking.basics.baseVo.PageVo;
import cn.parking.basics.baseVo.Result;
import cn.parking.basics.utils.SecurityUtil;
import cn.parking.data.entity.User;
import cn.parking.data.service.IUserService;
import cn.parking.data.utils.ZwzNullUtils;
import cn.parking.park.entity.Vehicle;
import cn.parking.park.service.IVehicleService;
import cn.hutool.core.util.StrUtil;
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
@Api(tags = "车辆管理接口")
@RequestMapping("/parking/vehicle")
@Transactional
public class VehicleController {

    @Autowired
    private IVehicleService iVehicleService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条车辆")
    public Result<Vehicle> get(@RequestParam String id){
        return new ResultUtil<Vehicle>().setData(iVehicleService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部车辆个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iVehicleService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部车辆")
    public Result<List<Vehicle>> getAll(){
        return new ResultUtil<List<Vehicle>>().setData(iVehicleService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询车辆")
    public Result<IPage<Vehicle>> getByPage(@ModelAttribute Vehicle vehicle ,@ModelAttribute PageVo page){
        QueryWrapper<Vehicle> qw = new QueryWrapper<>();
        User currUser = securityUtil.getCurrUser();
        QueryWrapper<User> userQw = new QueryWrapper<>();
        userQw.eq("id",currUser.getId());
        userQw.inSql("id","SELECT user_id FROM a_user_role WHERE del_flag = 0 AND role_id = '1536606659751841799'");
        if(iUserService.count(userQw) < 1L) {
            qw.eq("owner_id",currUser.getId());
        }
        if(!ZwzNullUtils.isNull(vehicle.getCarNumber())) {
            qw.like("car_number",vehicle.getCarNumber());
        }
        if(!ZwzNullUtils.isNull(vehicle.getCarType())) {
            qw.like("car_type",vehicle.getCarType());
        }
        if(!ZwzNullUtils.isNull(vehicle.getOwnerName())) {
            qw.like("owner_name",vehicle.getOwnerName());
        }
        IPage<Vehicle> data = iVehicleService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<Vehicle>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改车辆")
    public Result<Vehicle> saveOrUpdate(Vehicle vehicle){
        if(iVehicleService.saveOrUpdate(vehicle)){
            return new ResultUtil<Vehicle>().setData(vehicle);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增车辆")
    public Result<Vehicle> insert(Vehicle vehicle){
        User user = iUserService.getById(vehicle.getOwnerId());
        if(user == null) {
            return ResultUtil.error("车主不存在");
        }
        vehicle.setOwnerName(user.getNickname());
        iVehicleService.saveOrUpdate(vehicle);
        return new ResultUtil<Vehicle>().setData(vehicle);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑车辆")
    public Result<Vehicle> update(Vehicle vehicle){
        User user = iUserService.getById(vehicle.getOwnerId());
        if(user == null) {
            return ResultUtil.error("车主不存在");
        }
        vehicle.setOwnerName(user.getNickname());
        iVehicleService.saveOrUpdate(vehicle);
        return new ResultUtil<Vehicle>().setData(vehicle);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除车辆")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iVehicleService.removeById(id);
        }
        return ResultUtil.success();
    }
}
