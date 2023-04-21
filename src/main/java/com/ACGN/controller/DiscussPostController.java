package com.ACGN.controller;

import com.ACGN.Service.DiscussPostService;
import com.ACGN.entity.Discusspost;
import com.ACGN.util.R;
import com.ACGN.util.RUtils;
import com.ACGN.util.Renum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class DiscussPostController {
    @Autowired
    private DiscussPostService discussPostService;

    /**
     * 讨论帖分类
     * @param type
     * @return
     */
    @GetMapping("/classified")
    @ResponseBody
    public R ClassifiedPost(int type,int current){
        Page<Discusspost> classifiedPage;
        /**
         * 置顶帖的数量
         */
        int sum=0;
        QueryWrapper queryWrapperOne=new QueryWrapper();
        queryWrapperOne.eq("type",type);
        queryWrapperOne.eq("status",1);
        queryWrapperOne.orderByDesc("create_time");
        HashMap map=new HashMap();
        List<Discusspost>list=discussPostService.list(queryWrapperOne);
        sum=list.size();
        QueryWrapper queryWrapperTwo=new QueryWrapper();
        queryWrapperTwo.eq("type",type);
        queryWrapperTwo.eq("status",0);
        queryWrapperTwo.orderByDesc("create_time");
        Page<Discusspost> pageTwo=new Page<>();
        pageTwo.setCurrent(current);
        pageTwo.setSize(10-sum);
        classifiedPage=discussPostService.page(pageTwo,queryWrapperTwo);
        map.put("normal",classifiedPage);
        map.put("top",list);
        if(classifiedPage!=null){
            return RUtils.success(map);
        }
            return RUtils.Err(Renum.DATA_IS_NULL.getCode(),Renum.DATA_IS_NULL.getMsg());


    }


    @GetMapping("/top")
    @ResponseBody
    public R wrapper(int current) {
        Page<Discusspost> page=new Page<>();
        page.setCurrent(current);
        page.setSize(10);
        LambdaQueryWrapper<Discusspost> queryWrapper = Wrappers.<Discusspost>lambdaQuery();
        // 按score排序，若score相同，则按create_time排序
        queryWrapper.orderByDesc(Discusspost::getScore);
        queryWrapper.orderByDesc(Discusspost::getCreateTime);
        Page<Discusspost> topPage;
        topPage=discussPostService.page(page, queryWrapper);
        return RUtils.success(topPage);

    }

}