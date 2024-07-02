package cn.parking.park.controller;

import cn.hutool.core.date.DateUtil;
import cn.parking.basics.utils.PageUtil;
import cn.parking.basics.utils.ResultUtil;
import cn.parking.basics.baseVo.PageVo;
import cn.parking.basics.baseVo.Result;
import cn.parking.basics.utils.SecurityUtil;
import cn.parking.data.entity.User;
import cn.parking.data.service.IUserService;
import cn.parking.data.utils.ANullUtils;
import cn.parking.park.entity.IcCard;
import cn.parking.park.service.IIcCardService;
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

/**
 * @author zjl
 *  
 */
@Slf4j
@RestController
@Api(tags = "IC卡管理接口")
@RequestMapping("/parking/icCard")
@Transactional
public class IcCardController {

    @Autowired
    private IIcCardService iIcCardService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ApiOperation(value = "查询单条IC卡")
    public Result<IcCard> get(@RequestParam String id){
        return new ResultUtil<IcCard>().setData(iIcCardService.getById(id));
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部IC卡个数")
    public Result<Long> getCount(){
        return new ResultUtil<Long>().setData(iIcCardService.count());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ApiOperation(value = "查询全部IC卡")
    public Result<List<IcCard>> getAll(){
        return new ResultUtil<List<IcCard>>().setData(iIcCardService.list());
    }

    @RequestMapping(value = "/getByPage", method = RequestMethod.GET)
    @ApiOperation(value = "查询IC卡")
    public Result<IPage<IcCard>> getByPage(@ModelAttribute IcCard icCard ,@ModelAttribute PageVo page){
        QueryWrapper<IcCard> qw = new QueryWrapper<>();
        User currUser = securityUtil.getCurrUser();
        QueryWrapper<User> userQw = new QueryWrapper<>();
        userQw.eq("id",currUser.getId());
        userQw.inSql("id","SELECT user_id FROM a_user_role WHERE del_flag = 0 AND role_id = '1536606659751841799'");
        if(iUserService.count(userQw) < 1L) {
            qw.eq("user_id",currUser.getId());
        }
        if(!ANullUtils.isNull(icCard.getCarNumber())) {
            qw.like("car_number",icCard.getCarNumber());
        }
        if(!ANullUtils.isNull(icCard.getWorkUser())) {
            qw.like("work_user",icCard.getWorkUser());
        }
        IPage<IcCard> data = iIcCardService.page(PageUtil.initMpPage(page),qw);
        return new ResultUtil<IPage<IcCard>>().setData(data);
    }

    @RequestMapping(value = "/insertOrUpdate", method = RequestMethod.POST)
    @ApiOperation(value = "增改IC卡")
    public Result<IcCard> saveOrUpdate(IcCard icCard){
        if(iIcCardService.saveOrUpdate(icCard)){
            return new ResultUtil<IcCard>().setData(icCard);
        }
        return ResultUtil.error();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ApiOperation(value = "新增IC卡")
    public Result<IcCard> insert(IcCard icCard){
        User user = iUserService.getById(icCard.getUserId());
        if(user == null) {
            return ResultUtil.error("归属人不存在");
        }
        icCard.setUserName(user.getNickname());
        User currUser = securityUtil.getCurrUser();
        icCard.setWorkUser(currUser.getNickname());
        icCard.setDate(DateUtil.today());
        iIcCardService.saveOrUpdate(icCard);
        return new ResultUtil<IcCard>().setData(icCard);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "编辑IC卡")
    public Result<IcCard> update(IcCard icCard){
        User user = iUserService.getById(icCard.getUserId());
        if(user == null) {
            return ResultUtil.error("归属人不存在");
        }
        icCard.setUserName(user.getNickname());
        iIcCardService.saveOrUpdate(icCard);
        return new ResultUtil<IcCard>().setData(icCard);
    }

    @RequestMapping(value = "/delByIds", method = RequestMethod.POST)
    @ApiOperation(value = "删除IC卡")
    public Result<Object> delByIds(@RequestParam String[] ids){
        for(String id : ids){
            iIcCardService.removeById(id);
        }
        return ResultUtil.success();
    }

    @RequestMapping(value = "/recharge", method = RequestMethod.POST)
    @ApiOperation(value = "充值")
    public Result<Object> recharge(@RequestParam String id, @RequestParam BigDecimal number){
        IcCard ic = iIcCardService.getById(id);
        if(ic == null) {
            return ResultUtil.error("IC卡不存在");
        }
        ic.setBalance(ic.getBalance().add(number));
        iIcCardService.saveOrUpdate(ic);
        return ResultUtil.success();
    }
}
