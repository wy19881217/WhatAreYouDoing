package com.wzh.mybatis;

import com.wzh.entity.Integralevent;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IntegraleventMapper {
    @Insert("insert into Integraleven (protagonistId,eventInfo,integralVariation) valuse (#{protagonistId},#{eventInfo},#{integralVariation})")
    public void insert(Integralevent integralevent);
}
