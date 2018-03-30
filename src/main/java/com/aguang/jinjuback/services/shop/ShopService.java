package com.aguang.jinjuback.services.shop;

import com.aguang.jinjuback.constants.DeleteStatusConstants;
import com.aguang.jinjuback.jpa.ShopDao;
import com.aguang.jinjuback.model.Shop;
import com.aguang.jinjuback.pojo.common.PageInfo;
import com.aguang.jinjuback.utils.DateUtils;
import com.aguang.jinjuback.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService {

    public final static Logger logger = LoggerFactory.getLogger(ShopService.class);

    @Autowired
    ShopDao shopDao;

    public void create(Shop shop, Integer userId) {

        if(StringUtils.isBlank(shop.getUserId())) {
            if(StringUtils.isBlank(userId)) {
                shop.setUserId(335);
            } else {
                shop.setUserId(userId);
            }
        }
        shop.setCreateTime(DateUtils.getCurrentTime());
        shop.setUpdateTime(DateUtils.getCurrentTime());
        shop.setAuditStatus(0);
        shop.setIsDelete(0);

        shopDao.save(shop);
    }

    public PageInfo<Shop> list(int pageIndex, int pageSize, Integer userId) {

        Integer m = (pageIndex - 1) * pageSize;

        //创建条件查询对象
        Specification<Shop> specification = new Specification<Shop>() {

            @Override
            public Predicate toPredicate(Root<Shop> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 创建一个集合，将所有创建好的条件存进集合，一次查询。
                List<Predicate> predicateList = new ArrayList<Predicate>();


                Predicate predicate = cb.equal(root.get("isDelete"), 0);
                    predicateList.add(predicate);
//
//                if (!StringUtils.isEmpty(role.getRoleName())) {
//                    //角色名模糊查询
//                    Predicate predicate = cb.like(root.get("roleName"), "%" + role.getRoleName() + "%");
//                    predicateList.add(predicate);
//                }
//
//                if (!StringUtils.isEmpty(role.getAppId())) {
//                    //应用id查询
//                    Predicate predicate = cb.equal(root.get("appId"), role.getAppId());
//                    predicateList.add(predicate);
//                }

                //将条件集合转换为数组并返回
                Predicate[] p = new Predicate[predicateList.size()];
                return cb.and(predicateList.toArray(p));
            }
        };

        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        PageRequest pageRequest = new PageRequest(m, pageSize, sort);
        Page<Shop> page = shopDao.findAll(specification, pageRequest);

        Long totalElements = page.getTotalElements();
        List<Shop> content = page.getContent();

        return new PageInfo(totalElements.intValue(), content);
    }

    /**
     * 删除
     * @param productId
     * @param userId
     */
    @Transactional
    public void delete(Integer productId, Integer userId) {

        Shop shop = shopDao.getOne(productId);
        shop.setUserId(userId);
        shop.setUpdateTime(DateUtils.getCurrentTime());

        shop.setIsDelete(DeleteStatusConstants.DELETE);
    }
}
