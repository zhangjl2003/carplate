package cn.parking.park.controller;

import cn.parking.basics.baseVo.Result;
import cn.parking.basics.utils.ResultUtil;
import cn.parking.data.entity.User;
import cn.parking.data.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zjl
 *  
 */
@Slf4j
@RestController
@Api(tags = "超级用户")
@RequestMapping("/parking/superUser")
@Transactional
public class SuperController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有车主")
    public Result<List<User>> getUserList(@RequestParam Integer type){
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("type",type);
        return new ResultUtil<List<User>>().setData(iUserService.list(qw));
    }
}
