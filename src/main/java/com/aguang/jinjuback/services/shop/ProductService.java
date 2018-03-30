package com.aguang.jinjuback.services.shop;

import com.aguang.jinjuback.configuration.CustomException;
import com.aguang.jinjuback.constants.AuditStatusConstants;
import com.aguang.jinjuback.constants.DeleteStatusConstants;
import com.aguang.jinjuback.jpa.ProductDao;
import com.aguang.jinjuback.model.Product;
import com.aguang.jinjuback.pojo.common.PageInfo;
import com.aguang.jinjuback.utils.DateUtils;
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
public class ProductService {

    public final static Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    ProductDao productDao;

    /**
     * 创建商品
     * @param product
     * @param userId
     */
    public void create(Product product, Integer userId) {

        product.setUserId(335);
        product.setSalesVolume(0);
        product.setCreateTime(DateUtils.getCurrentTime());
        product.setUpdateTime(DateUtils.getCurrentTime());
        product.setAuditStatus(AuditStatusConstants.UN_AUDITED);
        product.setIsDelete(DeleteStatusConstants.UN_DELETE);

        productDao.save(product);
    }

    /**
     * 查询列表
     * @param pageIndex
     * @param pageSize
     * @param userId
     * @return
     */
    public PageInfo<Product> list(int pageIndex, int pageSize, Integer userId) {

        Integer m = (pageIndex - 1) * pageSize;

        //创建条件查询对象
        Specification<Product> specification = new Specification<Product>() {

            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 创建一个集合，将所有创建好的条件存进集合，一次查询。
                List<Predicate> predicateList = new ArrayList<Predicate>();

                Predicate predicate = cb.equal(root.get("isDelete"), 0);
                predicateList.add(predicate);

                //将条件集合转换为数组并返回
                Predicate[] p = new Predicate[predicateList.size()];
                return cb.and(predicateList.toArray(p));
            }
        };

        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        PageRequest pageRequest = new PageRequest(m, pageSize, sort);
        Page<Product> pageInfo = productDao.findAll(specification, pageRequest);

        Long totalElements = pageInfo.getTotalElements();
        List<Product> content = pageInfo.getContent();

        PageInfo<Product> pageInfo2 = new PageInfo(totalElements.intValue(), content);

        return pageInfo2;
    }

    /**
     * 审核
     * @param productId
     * @param userId
     */
    @Transactional
    public void audit(Integer productId, Integer userId) {

        Product product = productDao.getOne(productId);

        if(product == null) {
            throw new CustomException("该商品Id不存在!");
        }

        product.setUserId(userId);
        product.setUpdateTime(DateUtils.getCurrentTime());

        product.setAuditStatus(AuditStatusConstants.AUDITED);
    }

    /**
     * 删除
     * @param productId
     * @param userId
     */
    @Transactional
    public void delete(Integer productId, Integer userId) {

        Product product = productDao.getOne(productId);
        product.setUserId(userId);
        product.setUpdateTime(DateUtils.getCurrentTime());

        product.setIsDelete(DeleteStatusConstants.DELETE);
    }
}
