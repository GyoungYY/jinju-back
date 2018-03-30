package com.aguang.jinjuback.security;

//import com.cd.uap.bean.Administrator;
//import com.cd.uap.dao.AdministratorDao;
//import com.cd.uap.exception.AdminFrozenException;
//import com.cd.uap.exception.AdministratorNotExistException;
//import com.cd.uap.exception.LoginPasswordErrorException;
//import com.cd.uap.utils.CodeMessage;
//import com.cd.uap.utils.MD5Utils;
import com.aguang.jinjuback.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SecurityLoginService {
	@Autowired
	private UserInfoDao adminDao;

	public UserDetails loadUser(com.aguang.jinjuback.model.User user) throws Exception {
		com.aguang.jinjuback.model.User uapUser = adminDao.findByUsername(user.getUsername());
		User userDetails = null;
		if(uapUser==null) {
			throw new Exception("XXXXXXXXXXXXXXXXX");
		}
//		if(uapUser.getStatus()==0) {
//			throw new AdminFrozenException(CodeMessage.ADMIN_FROZEN_FAILED);
//		}
//		if(!uapUser.getPassword().equals(MD5Utils.md5Digest(user.getPassword()))) {
//			throw new LoginPasswordErrorException(CodeMessage.LOGIN_PASSWORD_ERROR);
//		}
		 //数据库中查询权限写死了。
        Set<GrantedAuthority> authoritySet = new HashSet<GrantedAuthority>();
        authoritySet.add(new SimpleGrantedAuthority("NORMALLY"));
        userDetails=new User(user.getUsername(),"pwd",authoritySet);
		return userDetails;
	}

}
