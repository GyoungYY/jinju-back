package com.aguang.jinjuback.controllers;

import com.aguang.jinjuback.model.User;
import com.aguang.jinjuback.pojo.Result;
import com.aguang.jinjuback.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册用户
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/create")
    public Result createUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userService.createUser(username, password);
    }

    /**
     * 获取用户详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getUser(@PathVariable("id") int id) {
        return userService.getUser(id);
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {
        Result result = userService.login(username, password);
        if(Result.OK.equals(result.getCode())) {
            request.getSession().setAttribute("userId", ((User)result.getData()).getUserId());
        }
        return result;
    }

    /**
     * 用户更新
     * @param user
     * @return
     */
    @PostMapping("/update")
    public Result updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    /**
     * 用户退出
     * @param id
     * @return
     */
    @PostMapping("/logout/{id}")
    public Result logout(@PathVariable("id") Integer id, HttpServletRequest request) {
        Result result = new Result();

        request.getSession().removeAttribute("userId");

        result.setSuccess("退出成功!");

        return result;
    }
}
