package com.ACGN.controller;

import com.ACGN.Dto.ClassificationDto;
import com.ACGN.Dto.DiscussPostDto;
import com.ACGN.Service.DiscussPostService;
import com.ACGN.Service.UserService;
import com.ACGN.entity.DiscussPost;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DiscussPostController {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;

    /**
     * 讨论帖分类
     * @param type
     * @return
     */
    @GetMapping("/classifiedDetails")
    @ResponseBody
    public R ClassifiedPost(int type,int current){
        Page<DiscussPost> classifiedPage;
        /**
         * 置顶帖的数量
         */
        int sum=0;
        QueryWrapper queryWrapperOne=new QueryWrapper();
        queryWrapperOne.eq("type",type);
        queryWrapperOne.eq("status",1);
        queryWrapperOne.orderByDesc("create_time");
        HashMap map=new HashMap();
        List<DiscussPost>list=discussPostService.list(queryWrapperOne);
        sum=list.size();
        QueryWrapper queryWrapperTwo=new QueryWrapper();
        queryWrapperTwo.eq("type",type);
        queryWrapperTwo.eq("status",0);
        queryWrapperTwo.orderByDesc("create_time");
        Page<DiscussPost> pageTwo=new Page<>();
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


    @PostMapping("/top")
    @ResponseBody
    public R top(Integer current) {
        Page<DiscussPost> page=new Page<>();
        page.setCurrent(current);
        page.setSize(10);
        LambdaQueryWrapper<DiscussPost> queryWrapper = Wrappers.<DiscussPost>lambdaQuery();
        // 按score排序，若score相同，则按create_time排序
        queryWrapper.orderByDesc(DiscussPost::getScore);
        queryWrapper.orderByDesc(DiscussPost::getCreateTime);
        Page<DiscussPost> topPage;
        topPage=discussPostService.page(page, queryWrapper);
        return RUtils.success(topPage);

    }

    @PostMapping("/addDiscussPost")
    @ResponseBody
    public R addDiscussPost(@RequestBody DiscussPostDto discussPostDto) {
        DiscussPost discussPost=new DiscussPost();
        System.out.println(discussPostDto.toString());
        if(discussPostDto.getUserId()==0||discussPostDto.getContent()==null||discussPostDto.getTitle()==null){
            return RUtils.Err(400,"数据为空");
        }
        discussPost.setUserId(discussPostDto.getUserId());
        discussPost.setUsername(userService.getById(discussPost.getUserId()).getUsername());
        discussPost.setContent(discussPostDto.getContent());
        discussPost.setTitle(discussPostDto.getTitle());
        discussPost.setCommentCount(0);
        discussPost.setCreateTime(new Date());
        discussPost.setType(0);
        discussPost.setStatus(0);
        discussPost.setDiscusspostType(discussPostDto.getDiscusspostType());
        discussPost.setScore(0);
        discussPostService.save(discussPost);
        return RUtils.success();
    }

    @PostMapping("/classification")
    @ResponseBody
    public R classification() {
       QueryWrapper queryWrapper=new QueryWrapper();
       queryWrapper.groupBy("type");
       queryWrapper.select("type,count(*) as sum");
       queryWrapper.eq("status",0);
       return RUtils.success(discussPostService.listMaps(queryWrapper));
    }
}