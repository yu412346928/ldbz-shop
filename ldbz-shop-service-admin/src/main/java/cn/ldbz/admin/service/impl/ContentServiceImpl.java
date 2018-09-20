package cn.ldbz.admin.service.impl;

import cn.ldbz.admin.service.ContentService;
import cn.ldbz.constant.Const;
import cn.ldbz.mapper.TbCategoryMapper;
import cn.ldbz.mapper.TbCategorySecondaryMapper;
import cn.ldbz.pojo.*;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 内容维护 Service
 *
 */
@Component
@Service(version = Const.LDBZ_SHOP_ADMIN_VERSION)
@Transactional
public class ContentServiceImpl implements ContentService {

    private static final Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);

    @Autowired
    private TbCategoryMapper categoryMapper;
    @Autowired
    private TbCategorySecondaryMapper categorySecondaryMapper;


    @Override
    public Map<String, Object> getCategoryList(Integer sEcho, Integer iDisplayStart, Integer iDisplayLength) {
        HashMap<String, Object> map = new HashMap<>();
        int pageNum = iDisplayStart / iDisplayLength + 1;
        //System.out.println(pageNum);
        PageHelper.startPage(pageNum, iDisplayLength);

        TbCategoryExample example = new TbCategoryExample();
        TbCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andSortOrderEqualTo(1);

        List<TbCategory> list = categoryMapper.selectByExample(example);
        //System.out.println(list.size());
        PageInfo<TbCategory> pageInfo = new PageInfo<>(list);

        map.put("sEcho", sEcho + 1);
        map.put("iTotalRecords", pageInfo.getTotal());//数据总条数
        map.put("iTotalDisplayRecords", pageInfo.getTotal());//显示的条数
        map.put("aData", list);//数据集合

        return map;
    }

    @Override
    public LdbzResult saveCategory(String id, String name, Integer sort_order) {

        TbCategory category = new TbCategory();
        category.setId(id);
        category.setName(name);
        category.setSortOrder(sort_order);
        category.setUpdated(new Date());

        int i = categoryMapper.updateByPrimaryKey(category);

        return i > 0 ? LdbzResult.ok() : LdbzResult.build(400, "更新失败！");
    }

    @Override
    public Map<String, Object> getCategorySecondaryList(Integer sEcho, Integer iDisplayStart, Integer iDisplayLength) {

        HashMap<String, Object> map = new HashMap<>();
        int pageNum = iDisplayStart / iDisplayLength + 1;
        //System.out.println(pageNum);
        PageHelper.startPage(pageNum, iDisplayLength);

        TbCategorySecondaryExample example = new TbCategorySecondaryExample();
        TbCategorySecondaryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(0L);

        List<TbCategorySecondary> list = categorySecondaryMapper.selectByExample(example);
        //System.out.println(list.size());
        PageInfo<TbCategorySecondary> pageInfo = new PageInfo<>(list);

        map.put("sEcho", sEcho + 1);
        map.put("iTotalRecords", pageInfo.getTotal());//数据总条数
        map.put("iTotalDisplayRecords", pageInfo.getTotal());//显示的条数
        map.put("aData", list);//数据集合

        return map;
    }

    @Override
    public Map<String, Object> getSearchCategorySecondaryList(String sSearch, Integer sEcho, Integer iDisplayStart, Integer iDisplayLength) {

        HashMap<String, Object> map = new HashMap<>();
        int pageNum = iDisplayStart / iDisplayLength + 1;
        //System.out.println(pageNum);
        PageHelper.startPage(pageNum, iDisplayLength);

        TbCategorySecondaryExample example = new TbCategorySecondaryExample();
        TbCategorySecondaryExample.Criteria criteria = example.createCriteria();
        criteria.andNameLike("%" + sSearch + "%");

        List<TbCategorySecondary> list = categorySecondaryMapper.selectByExample(example);
        //System.out.println(list.size());
        PageInfo<TbCategorySecondary> pageInfo = new PageInfo<>(list);

        map.put("sEcho", sEcho + 1);
        map.put("iTotalRecords", pageInfo.getTotal());//数据总条数
        map.put("iTotalDisplayRecords", pageInfo.getTotal());//显示的条数
        map.put("aData", list);//数据集合

        return map;
    }

    @Override
    public LdbzResult saveCategorySecondary(TbCategorySecondary categorySecondary) {

        categorySecondary.setUpdated(new Date());

        int i = categorySecondaryMapper.updateByPrimaryKeySelective(categorySecondary);

        return i>0?LdbzResult.ok():LdbzResult.build(400,"服务器出错!");
    }
}