package cn.parking.park.controller;

import cn.parking.basics.utils.PageUtil;
import cn.parking.basics.utils.ResultUtil;
import cn.parking.basics.baseVo.PageVo;
import cn.parking.basics.baseVo.Result;
import cn.parking.data.utils.ZwzNullUtils;
import cn.parking.park.entity.FeeScale;
import cn.parking.park.service.IFeeScaleService;
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
@Api(tags = "收费标准管理接口")
@RequestMapping("/parking/feeScale")
@Transactional
public class FeeScaleController {

    @Autowired
    private IFeeScaleService iFeeScaleService;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条收费标准")
    public Result<FeeScale> get(@RequestParam String id){

        return new ResultUtil<FeeScale>().setData(iFeeScaleService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部收费标准个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iFeeScaleService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部收费标准")
    public Result<List<FeeScale>> getAll(){

        return new ResultUtil<List<FeeScale>>().setData(iFeeScaleService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询收费标准")
    public Result<IPage<FeeScale>> getByPage(@ModelAttribute FeeScale feeScale ,@ModelAttribute PageVo page){
        QueryWrapper<FeeScale> qw = new QueryWrapper<>();
//        if(!ZwzNullUtils.isNull(feeScale.getFree_time())) {
//            qw.like("free_time",feeScale.getFree_time());
//        }
//        if(!ZwzNullUtils.isNull(feeScale.getStart_price())){
//            qw.like("start_price",feeScale.getStart_price());
//        }
//        if(!ZwzNullUtils.isNull(feeScale.getBeyond_price())){
//            qw.like("beyond_price",feeScale.getBeyond_price());
//        }
        IPage<FeeScale> data = iFeeScaleService.page(PageUtil.initMpPage(page),qw);
//        data.getRecords().forEach(item -> {
//            System.out.println("qwqwqwq"+item.getStart_price());
//        });
        return new ResultUtil<IPage<FeeScale>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改收费标准")
    public Result<FeeScale> saveOrUpdate(FeeScale feeScale){
        if(iFeeScaleService.saveOrUpdate(feeScale)){
            return new ResultUtil<FeeScale>().setData(feeScale);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增收费标准")
    public Result<FeeScale> insert(FeeScale feeScale){
        iFeeScaleService.saveOrUpdate(feeScale);
        return new ResultUtil<FeeScale>().setData(feeScale);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑收费标准")
    public Result<FeeScale> update(FeeScale feeScale){
        iFeeScaleService.saveOrUpdate(feeScale);
        return new ResultUtil<FeeScale>().setData(feeScale);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除收费标准")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iFeeScaleService.removeById(id);
        }
        return ResultUtil.success();
    }
}
