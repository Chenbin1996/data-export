package com.ruxuanwo.data.export.core;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public abstract class AbstractService<T, DTO extends T, PK extends Serializable> implements Service<T, DTO, PK>{

    @Autowired
    protected Mapper<T> mapper;
    private Class<T> modelClass;

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType)getClass().getGenericSuperclass();
        this.modelClass = ((Class)pt.getActualTypeArguments()[0]);
    }

    @Override
    public void insert(T entity) {
        this.mapper.insertSelective(entity);
    }

    @Override
    public void batchInsert(List<T> entities) {
        this.mapper.insertList(entities);
    }

    @Override
    public T get(PK id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    @Override
    public void remove(PK id) {
        this.mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void remove(T condition) {
        if (condition == null) {
            throw new RuntimeException("the condition is null when deleting the records!");
        }
        this.mapper.delete(condition);
    }

    @Override
    public void remove(Condition condition) {
        if (condition == null) {
        throw new RuntimeException("the condition is null when deleting the records!");
        }
        this.mapper.deleteByCondition(condition);
    }

    @Override
    public void removeByIds(String ids) {
        this.mapper.deleteByIds(ids);
    }

    @Override
    public void removeByIdList(List<PK> idList) {
        int length = idList.size();
        String[] str = new String[length];
        for (int i = 0; i < length; i++) {
            str[i] = ((Serializable)idList.get(i)).toString();
        }
        this.mapper.deleteByIds(String.join(",", str));
    }

    @Override
    public int update(T model) {
        return this.mapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public void update(T model, PK id) throws IllegalAccessException, NoSuchFieldException {
        Field field = this.modelClass.getDeclaredField("id");
        field.setAccessible(true);
        field.set(model, id);
        this.mapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public void update(T model, Condition condition) {
        if (condition == null) {
           throw new RuntimeException("the condition is null when updating the records!");
        }
        this.mapper.updateByConditionSelective(model, condition);
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = this.modelClass.newInstance();
            Field field = this.modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return this.mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public List<T> findByIds(String ids) {
        return this.mapper.selectByIds(ids);
    }

    @Override
    public int count() {
        return this.mapper.selectCount(null);
    }

    @Override
    public int count(String column) {
        Condition condition = new Condition(this.modelClass);
        condition.setCountProperty(column);
        return this.mapper.selectCountByCondition(condition);
    }

    @Override
    public int count(T condition) {
        return this.mapper.selectCount(condition);
    }

    @Override
    public int count(Condition condition) {
        return this.mapper.selectCountByCondition(condition);
    }

    @Override
    public List<T> findByCondition(Condition condition) {
        return this.mapper.selectByCondition(condition);
    }

    @Override
    public PageInfo<T> findByCondition(Condition condition, OrderBy orderBy, int pageSize, int pageNumber) {
        if (orderBy != null) {
            condition = condition == null ? new Condition(this.modelClass) : condition;
            condition.setOrderByClause(orderBy.toString());
        }
        if ((pageSize > 0) && (pageNumber >= 0)) {
            PageHelper.startPage(pageNumber, pageSize);
        }
        List list = this.mapper.selectByCondition(condition);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public PageInfo<T> findByCondition(Condition condition, int pageSize, int pageNumber) {
        return findByCondition(condition, null, pageSize, pageNumber);
    }

    @Override
    public PageInfo<T> find(int pageSize, int pageNumber) {
        return findByCondition(null, null, pageSize, pageNumber);
    }

    @Override
    public PageInfo<T> find(OrderBy orderBy, int pageSize, int pageNumber) {
        return findByCondition(null, orderBy, pageSize, pageNumber);
    }

    @Override
    public List<T> find(T condition) {
        return this.mapper.select(condition);
    }

    @Override
    public List<T> find() {
        return this.mapper.selectAll();
    }

    @Override
    public List<T> find(OrderBy orderBy) {
        Condition condition = new Condition(this.modelClass);
        condition.setOrderByClause(orderBy.toString());
        return this.mapper.selectByCondition(condition);
    }

    protected void setMybatisPageParams(int pageSize, int pageNumber, Map<String, Object> params) {
        if ((pageSize > 0) && (pageNumber >= 0)) {
            int startRow = pageSize * (pageNumber - 1) + 1;
            int endRow = pageSize * pageNumber;
            int offset = pageSize * (pageNumber - 1);

            params.put("startRow", Integer.valueOf(startRow));
            params.put("endRow", Integer.valueOf(endRow));
            params.put("offset", Integer.valueOf(offset));
            params.put("limit", Integer.valueOf(pageSize));
        }
    }

    protected Map<String, Object> setMybatisPageParams(T condition, int pageSize, int pageNumber) {
        Map params = Maps.newHashMap();
        params.put("condition", condition);
        setMybatisPageParams(pageSize, pageNumber, params);
        return params;
    }

    protected Map<String, Object> setMybatisPageParams(T condition, OrderBy orderBy, int pageSize, int pageNumber) {
        Map params = Maps.newHashMap();
        params.put("condition", condition);
        setMybatisPageParams(pageSize, pageNumber, params);
        setMybatisPageParams(orderBy, params);
        return params;
    }

    protected Map<String, Object> setMybatisPageParams(T condition) {
        Map params = Maps.newHashMap();
        params.put("condition", condition);
        return params;
    }

    protected Map<String, Object> setMybatisPageParams(T condition, OrderBy orderBy) {
        Map params = setMybatisPageParams(condition);
        if (orderBy != null) {
            params.put("orderBy", orderBy.toString());
        }
        return params;
    }

    protected Map<String, Object> setMybatisPageParams(OrderBy orderBy, Map<String, Object> params) {
        if (orderBy != null) {
           params.put("orderBy", orderBy.toString());
        }
        return params;
    }
}