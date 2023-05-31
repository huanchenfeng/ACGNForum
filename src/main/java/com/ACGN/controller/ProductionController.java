package com.ACGN.controller;
import com.ACGN.Dto.EvaluationDto;
import com.ACGN.Service.EvaluationService;
import com.ACGN.Service.ProductionService;
import com.ACGN.dao.EvaluationMapper;
import com.ACGN.entity.Comment;
import com.ACGN.entity.Evaluation;
import com.ACGN.entity.Production;
import com.ACGN.entity.User;
import com.ACGN.util.R;

import com.ACGN.util.RUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Controller
public class ProductionController {
    @Autowired
    private ProductionService productionService;
    @Autowired
    private EvaluationService evaluationService;
    @Autowired
    private EvaluationMapper evaluationMapper;

    @PostMapping("/production")
    @ResponseBody
    public R production(String type,int current){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("type",type);
        Page<Production> page=new Page<>();
        page.setCurrent(current);
        page.setSize(10);
        page=productionService.page(page,queryWrapper);
        return RUtils.success(page);
    }
    @PostMapping("/animation")
    @ResponseBody
    public R animation(int current){
        QueryWrapper queryWrapper=new QueryWrapper();
        Page<Production> page=new Page<>();
        page.setCurrent(current);
        page.setSize(10);
        page=productionService.page(page,queryWrapper);
        return RUtils.success(page);
    }
    @PostMapping("/animationDetail")
    @ResponseBody
    public R animationDetail(String id){
        int productionId= Integer.parseInt(id);
        Production production=productionService.getById(productionId);

        return RUtils.success(production);
    }

    @PostMapping("/animationWriting")
    @ResponseBody
    public R animationWriting(double score,String content,String userId,String worksId){
        Evaluation evaluation=new Evaluation();
        evaluation.setContent(content);
        evaluation.setScores(score);
        evaluation.setUserId(Integer.valueOf(userId));
        evaluation.setWorksId(Integer.valueOf(worksId));
        evaluationService.save(evaluation);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.select("IFNULL(sum(scores),0) as totalScore")
                .eq("works_id",Integer.valueOf(worksId));
        Map<String, Object> map =evaluationService.getMap(queryWrapper);
        Double sumCount = (Double) map.get("totalScore");
        QueryWrapper queryWrapper1=new QueryWrapper();
        queryWrapper1.eq("works_id",Integer.valueOf(worksId));
        int sum=evaluationService.count(queryWrapper1);
        UpdateWrapper updateWrapper=new UpdateWrapper();
        updateWrapper.eq("production_id",Integer.valueOf(worksId));
        System.out.println(sumCount / sum+"--------------------------------------------------------------------------");
        updateWrapper.set("score",sumCount/sum);
        productionService.update(updateWrapper);
        return RUtils.success();
    }
    @PostMapping("/evaluation")
    @ResponseBody
    public R evaluation(String id){
        List<EvaluationDto> evaluations=evaluationMapper.SelectUerPro(Integer.valueOf(id));
        return RUtils.success(evaluations);
    }
}
