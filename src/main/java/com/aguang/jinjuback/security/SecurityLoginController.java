package com.aguang.jinjuback.security;

import com.aguang.jinjuback.jwt.CacheConfModel;
import com.aguang.jinjuback.jwt.CacheMgr;
import com.aguang.jinjuback.jwt.JWTUtils;
import com.aguang.jinjuback.model.User;
import com.aguang.jinjuback.pojo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class SecurityLoginController {
	private static Logger log = LoggerFactory.getLogger(SecurityLoginController.class);
	@Autowired
	JWTUtils jwtUtils;
	@Autowired
	SecurityLoginService securityLoginService;
	@Autowired
	CacheConfModel cModel;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result createAuthenticationToken(User user) {
		Result result=new Result();
        UserDetails userDetails = null;
		try {
			//对前台传过来的密码进行解密操作
//			String password = user.getPassword();
//			user.setPassword(AESUtils.desEncrypt(password));
			
			userDetails = securityLoginService.loadUser(user);
		} catch (Exception e) {
//			response = new Response(1, e.getCodeMessage());
//			log.error(response.getError().getCode() + ":" + response.getError().getMessage());
//			return response;
			result.setError("登录失败!");
			return result;
		}
//		catch (NoSuchAlgorithmException e) {
//			response = new Response(1, CodeMessage.MD5_FAILED);
//			log.error(response.getError().getCode() + ":" + response.getError().getMessage());
//			return response;
//		} catch (Exception e) {
//			response = new Response(1, CodeMessage.LOGIN_ERROR);
//			log.error(response.getError().getCode() + ":" + response.getError().getMessage());
//			return response;
//		}
		//缓存用户token
		CacheMgr cm=CacheMgr.getInstance();
		//查询用户记录是否存在，若已存在token不更新
		Object token = cm.getValue(user.getUsername());
		if(!StringUtils.isEmpty(token)) {
			cm.removeCache(user.getUsername());
		}
		
		cModel.setBeginTime(new Date().getTime());
		token = jwtUtils.generateAccessToken(userDetails);
		cm.addCache(user.getUsername(), token, cModel);
		result.setSuccess(token, "success");
        return result;
    }
    
//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public Response logout() {
//    	CacheMgr cm=CacheMgr.getInstance();
//    	try {
//			cm.removeCache(SecurityUserUtils.getSecurityUser().getUsername());
//		} catch (SecurityUserException e) {
//			 return new Response(0,CodeMessage.LOGOUT_ERROR,null);
//		}
//        return new Response(0,CodeMessage.LOGOUT_SUCCESS,"logout success");
//    }
    
}
