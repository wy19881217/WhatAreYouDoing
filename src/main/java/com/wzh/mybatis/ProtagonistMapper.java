package com.wzh.mybatis;

import com.wzh.entity.Protagonist;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface ProtagonistMapper {
    @Select("select * from Protagonist")
    public List<Protagonist> selectByDate();


}
