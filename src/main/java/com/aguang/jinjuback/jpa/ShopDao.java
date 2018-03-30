package com.aguang.jinjuback.jpa;

import com.aguang.jinjuback.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopDao extends JpaRepository<Shop, Integer>, JpaSpecificationExecutor<Shop> {
//	public Administrator findByUsername(String username);
//
//	public List<Administrator> findByCreatedAdminId(Integer createdAdminId);
//
//	@Query(value="SELECT t2.* FROM tbl_administrator t1 , tbl_administrator t2 WHERE t1.ID = ? AND t1.CREATED_ADMIN_ID = t2.ID",nativeQuery=true)
//	public Administrator findParentAdminById(Integer id);
//
//
//	@Query(value="SELECT t1.ID FROM tbl_administrator t1 WHERE t1.CREATED_ADMIN_ID = ?",nativeQuery=true)
//	public Set<Integer> findChildrenAdminId(Integer adminId);

//	@Query(value=" select * from shop where is_delete=0 ",nativeQuery=true)
//	Integer countTotal();

//	@Query(value=" from Shop where isDelete=0 limit ?, ? ")
//	List<Shop> listByPage(Integer m, Integer n);
}
