package com.aguang.jinjuback.services;

import com.aguang.jinjuback.dao.UserDao;
import com.aguang.jinjuback.model.User;
import com.aguang.jinjuback.pojo.Result;
import com.aguang.jinjuback.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 注册用户
     * @param username
     * @param password
     * @return
     */
    public Result createUser(String username, String password) {
        Result result = new Result();
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            Long curTime = DateUtils.getCurrentTime();
            userDao.createUser(username, password, curTime);
            result.setSuccess("注册成功");
            return result;
        }
        result.setError("用户名已存在");
        return result;
    }

    /**
     * 根据id获取用户详细信息
     * @param id
     * @return
     */
    public Result getUser(int id) {
        Result result = new Result();
        User user = userDao.getUser(id);
        if (user == null) {
            result.setError(null, "用户不存在");
            return result;
        }
        user.setPassword(null);
        result.setSuccess(user, "用户数据获取成功");
        return result;
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    public Result login(String username, String password) {
        Result result = new Result();
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            result.setError(null, "用户不存在");
            return result;
        } else if (!password.equals(user.getPassword())) {
            result.setError(null, "密码错误");
            return result;
        }

        // 更新最后登录时间
        userDao.updateLastLoginTime(user.getUserId(), DateUtils.getCurrentTime());

        user.setPassword(null);
        result.setSuccess(user, "登录成功");
        return result;
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    public Result updateUser(User user){
        Result result = new Result();
        User userInfo = userDao.getUser(user.getUserId());
        if (userInfo == null) {
            result.setError("用户不存在");
            return result;
        }

        // 判断用户名是否与别人重复
        User userInfo2 = userDao.getUserByUsername(userInfo.getUsername());
        if (userInfo2 != null && (!userInfo2.getUserId().equals(userInfo.getUserId()))) {
            result.setError(null, "该用户名已存在，不要与别人一样哦~");
            return result;
        }

        user.setUpdateTime(DateUtils.getCurrentTime());

        userDao.updateUser(user);
        result.setSuccess(user, "更新成功");
        return result;
    }

    public boolean hasUser(Integer userId) {
        User user = userDao.getUserById(userId);
        return user != null;
    }

    /**
     * 更新用户头像
     * @param userId
     * @param photoUrl
     * @return
     */
    public void updatePhotoUrl(Integer userId, String photoUrl) {

        userDao.updatePhotoUrl(userId, photoUrl);
    }
}
