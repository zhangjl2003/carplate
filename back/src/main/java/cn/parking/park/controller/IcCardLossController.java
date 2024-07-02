package cn.parking.park.controller;

import cn.parking.basics.utils.PageUtil;
import cn.parking.basics.utils.ResultUtil;
import cn.parking.basics.baseVo.PageVo;
import cn.parking.basics.baseVo.Result;
import cn.parking.basics.utils.SecurityUtil;
import cn.parking.data.entity.User;
import cn.parking.data.service.IUserService;
import cn.parking.data.utils.ANullUtils;
import cn.parking.park.entity.IcCard;
import cn.parking.park.entity.IcCardLoss;
import cn.parking.park.service.IIcCardLossService;
import cn.hutool.core.util.StrUtil;
import cn.parking.park.service.IIcCardService;
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
@Api(tags = "IC卡挂失管理接口")
@RequestMapping("/parking/icCardLoss")
@Transactional
public class IcCardLossController {

    @Autowired
    private IIcCardLossService iIcCardLossService;

    @Autowired
    private IIcCardService iIcCardService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条IC卡挂失")
    public Result<IcCardLoss> get(@RequestParam String id){
        return new ResultUtil<IcCardLoss>().setData(iIcCardLossService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部IC卡挂失个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iIcCardLossService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部IC卡挂失")
    public Result<List<IcCardLoss>> getAll(){
        return new ResultUtil<List<IcCardLoss>>().setData(iIcCardLossService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询IC卡挂失")
    public Result<IPage<IcCardLoss>> getByPage(@ModelAttribute IcCardLoss icCardLoss ,@ModelAttribute PageVo page){
        QueryWrapper<IcCardLoss> qw = new QueryWrapper<>();
        User currUser = securityUtil.getCurrUser();
        QueryWrapper<User> userQw = new QueryWrapper<>();
        userQw.eq("id",currUser.getId());
        userQw.inSql("id","SELECT user_id FROM a_user_role WHERE del_flag = 0 AND role_id = '1536606659751841799'");
        if(iUserService.count(userQw) < 1L) {
            qw.eq("apply_id",currUser.getId());
        }
        if(!ANullUtils.isNull(icCardLoss.getCarNumber())) {
            qw.like("car_number",icCardLoss.getCarNumber());
        }
        if(!ANullUtils.isNull(icCardLoss.getApplyUser())) {
            qw.like("apply_user",icCardLoss.getApplyUser());
        }
        if(!ANullUtils.isNull(icCardLoss.getStatus())) {
            qw.eq("status",icCardLoss.getStatus());
        }
        IPage<IcCardLoss> data = iIcCardLossService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<IcCardLoss>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改IC卡挂失")
    public Result<IcCardLoss> saveOrUpdate(IcCardLoss icCardLoss){
        if(iIcCardLossService.saveOrUpdate(icCardLoss)){
            return new ResultUtil<IcCardLoss>().setData(icCardLoss);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增IC卡挂失")
    public Result<IcCardLoss> insert(IcCardLoss icCardLoss){
        IcCard ic = iIcCardService.getById(icCardLoss.getCarId());
        if(ic == null) {
            return ResultUtil.error("IC卡不存在");
        }
        icCardLoss.setCarNumber(ic.getCarNumber());
        User currUser = securityUtil.getCurrUser();
        icCardLoss.setApplyId(currUser.getId());
        icCardLoss.setApplyUser(currUser.getNickname());
        iIcCardLossService.saveOrUpdate(icCardLoss);
        return new ResultUtil<IcCardLoss>().setData(icCardLoss);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑IC卡挂失")
    public Result<IcCardLoss> update(IcCardLoss icCardLoss){
        IcCard ic = iIcCardService.getById(icCardLoss.getCarId());
        if(ic == null) {
            return ResultUtil.error("IC卡不存在");
        }
        icCardLoss.setCarNumber(ic.getCarNumber());
        iIcCardLossService.saveOrUpdate(icCardLoss);
        return new ResultUtil<IcCardLoss>().setData(icCardLoss);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除IC卡挂失")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iIcCardLossService.removeById(id);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    @ApiOperation(value = "审核IC卡挂失")
    public Result<Object> audit(@RequestParam String id){
        IcCardLoss cl = iIcCardLossService.getById(id);
        if(cl == null) {
            return ResultUtil.error("挂失单不存在");
        }
        cl.setStatus("已审核");
        iIcCardLossService.saveOrUpdate(cl);
        IcCard ic = iIcCardService.getById(cl.getCarId());
        if(ic != null) {
            ic.setCarNumber(cl.getNewNumber());
            iIcCardService.saveOrUpdate(ic);
        }
        return ResultUtil.success();
    }
}
