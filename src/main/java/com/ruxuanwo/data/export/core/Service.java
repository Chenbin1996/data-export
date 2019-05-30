package com.ruxuanwo.data.export.core;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

import java.io.Serializable;
import java.util.List;

public interface Service<T, DTO extends T, PK extends Serializable>{
    void insert(T paramT);

    void batchInsert(List<T> paramList);

    T get(PK paramPK);

    List<T> find();

    List<T> find(OrderBy paramOrderBy);

    PageInfo<T> find(int paramInt1, int paramInt2);

    PageInfo<T> find(OrderBy paramOrderBy, int paramInt1, int paramInt2);

    List<T> find(T paramT);

    List<T> findByCondition(Condition paramCondition);

    PageInfo<T> findByCondition(Condition paramCondition, OrderBy paramOrderBy, int paramInt1, int paramInt2);

    PageInfo<T> findByCondition(Condition paramCondition, int paramInt1, int paramInt2);

    T findBy(String paramString, Object paramObject) throws TooManyResultsException;

    List<T> findByIds(String paramString);

    int count();

    int count(String paramString);

    int count(T paramT);

    int count(Condition paramCondition);

    int update(T paramT);

    void update(T paramT, PK paramPK) throws IllegalAccessException, NoSuchFieldException;

    void update(T paramT, Condition paramCondition);

    void remove(PK paramPK);

    void remove(T paramT);

    void remove(Condition paramCondition);

    void removeByIds(String paramString);

    void removeByIdList(List<PK> paramList);
}