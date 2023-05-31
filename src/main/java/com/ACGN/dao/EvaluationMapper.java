package com.ACGN.dao;


import com.ACGN.Dto.EvaluationDto;
import com.ACGN.entity.Evaluation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface EvaluationMapper extends BaseMapper<Evaluation> {
    //自定义接口
    @Select("select evaluation.*, user.username username,user.header_url headerUrl from evaluation,user where evaluation.user_id = user.user_id and evaluation.works_id = #{id}")
    List<EvaluationDto> SelectUerPro(Integer id);  //传入动画id

}
