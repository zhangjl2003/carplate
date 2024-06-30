package cn.parking.park.controller;

import cn.parking.basics.utils.PageUtil;
import cn.parking.basics.utils.ResultUtil;
import cn.parking.basics.baseVo.PageVo;
import cn.parking.basics.baseVo.Result;
import cn.parking.data.utils.ZwzNullUtils;
import cn.parking.park.entity.ParkingSpace;
import cn.parking.park.service.IParkingSpaceService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @author zjl
 *  
 */
@Slf4j
@RestController
@Api(tags = "停车位管理接口")
@RequestMapping("/parking/parkingSpace")
@Transactional
public class ParkingSpaceController {

    @Autowired
    private IParkingSpaceService iParkingSpaceService;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条停车位")
    public Result<ParkingSpace> get(@RequestParam String id){
        return new ResultUtil<ParkingSpace>().setData(iParkingSpaceService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部停车位个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iParkingSpaceService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部停车位")
    public Result<List<ParkingSpace>> getAll(){
        return new ResultUtil<List<ParkingSpace>>().setData(iParkingSpaceService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询停车位")
    public Result<IPage<ParkingSpace>> getByPage(@ModelAttribute ParkingSpace parkingSpace ,@ModelAttribute PageVo page){
        QueryWrapper<ParkingSpace> qw = new QueryWrapper<>();
        if(!ZwzNullUtils.isNull(parkingSpace.getTitle())) {
            qw.like("title",parkingSpace.getTitle());
        }
        if(!ZwzNullUtils.isNull(parkingSpace.getStatus())) {
            qw.eq("status",parkingSpace.getStatus());
        }
        IPage<ParkingSpace> data = iParkingSpaceService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<ParkingSpace>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改停车位")
    public Result<ParkingSpace> saveOrUpdate(ParkingSpace parkingSpace){
        if(iParkingSpaceService.saveOrUpdate(parkingSpace)){
            return new ResultUtil<ParkingSpace>().setData(parkingSpace);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增停车位")
    public Result<ParkingSpace> insert(ParkingSpace parkingSpace){
        if(Objects.equals(0, parkingSpace.getSortOrder().compareTo(BigDecimal.ZERO))) {
            parkingSpace.setSortOrder(BigDecimal.valueOf(iParkingSpaceService.count() + 1L));
        }
        iParkingSpaceService.saveOrUpdate(parkingSpace);
        return new ResultUtil<ParkingSpace>().setData(parkingSpace);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑停车位")
    public Result<ParkingSpace> update(ParkingSpace parkingSpace){
        iParkingSpaceService.saveOrUpdate(parkingSpace);
        return new ResultUtil<ParkingSpace>().setData(parkingSpace);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除停车位")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iParkingSpaceService.removeById(id);
        }
        return ResultUtil.success();
    }
}
