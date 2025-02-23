package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.service.UserService;
import com.itheima.reggie.utils.VerifyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerifyUtils verifyUtils;


    /**
     * 测试用途-验证码直接显示在后台而不是通过手机接收
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        // 获取手机号
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {
            // ✅ 规范化号码（确保带国际区号）
            phone = formatPhoneNumber(phone);

            // **绕过第三方服务，自己生成 6 位验证码**
            String verificationCode = generateVerificationCode();

            // **打印验证码到控制台（测试用，不适用于生产环境）**
            System.out.println("验证码已发送：" + verificationCode + "，手机号：" + phone);

            // **将验证码存储到 Session**
            session.setAttribute(phone, verificationCode);

            return R.success("手机验证码短信发送成功（控制台可见验证码）");
        }
        return R.error("手机号不能为空");
    }

    // 🔹 生成 6 位随机验证码的方法
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 生成 100000-999999 的 6 位随机数
        return String.valueOf(code);
    }

    // 🔹 号码格式化方法（假设默认英国区号）
    private String formatPhoneNumber(String phone) {
        if (!phone.startsWith("+")) {
            phone = "+44" + phone.substring(1);  // 例如：输入 `07123456789` 会变成 `+447123456789`
        }
        return phone;
    }


    /**
     * 发送手机短信验证码-通过手机接收
     * @param session
     * @param user
     * @return
     */
    /*// 发送手机短信验证码
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        // 获取手机号
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {
            // ✅ 规范化号码（确保带国际区号）
            phone = formatPhoneNumber(phone);

            // 调用 VerifyUtils 发送验证码
            String requestId = verifyUtils.sendVerificationCode(phone);

            if (requestId != null) {
                session.setAttribute(phone, requestId);
                return R.success("手机验证码短信发送成功");
            } else {
                return R.error("短信发送失败");
            }
        }
        return R.error("手机号不能为空");
    }

    // 🔹 号码格式化方法
    private String formatPhoneNumber(String phone) {
        // 如果号码没有 `+` 号，假设是英国号码，自动加上区号 `+44`
        if (!phone.startsWith("+")) {
            phone = "+44" + phone.substring(1);  // 假设是英国号码，去掉前导 0，加上 `+44`
        }
        return phone;
    }*/


    /**
     * 移动端用户登录- 绕过第三方登录验证-测试代码使用
     * @param paramMap
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String, String> paramMap, HttpSession session) {
        // 获取手机号和验证码
        String phone = paramMap.get("phone");
        String inputCode = paramMap.get("code");

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(inputCode)) {
            return R.error("手机号或验证码不能为空");
        }

        // ✅ 确保号码格式一致（防止存储的 session key 变更）
        phone = formatPhoneNumber(phone);

        // 从 Session 获取存储的验证码（之前 sendMsg 里存的）
        String sessionCode = (String) session.getAttribute(phone);

        if (sessionCode == null) {
            return R.error("验证码已过期，请重新获取");
        }

        // ✅ 直接对比用户输入的验证码
        if (!sessionCode.equals(inputCode)) {
            return R.error("验证码错误");
        }

        // 验证成功，删除 session 里的验证码，防止重复使用
        session.removeAttribute(phone);

        // 查询数据库，看看该用户是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, phone);
        User user = userService.getOne(queryWrapper);

        // 如果用户不存在，则注册新用户
        if (user == null) {
            user = new User();
            user.setPhone(phone);
            userService.save(user);
        }

        // ✅ 存入 session（用于后续请求）
        session.setAttribute("user", user);

        // 返回用户信息
        return R.success(user);
    }



    /* *//**
     * 移动端用户登录-通过第三方验证登录-使用手机接收
     * @param paramMap
     * @param session
     * @return
     *//*
    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String, String> paramMap, HttpSession session) {
        // 获取手机号和验证码
        String phone = paramMap.get("phone");
        String code = paramMap.get("code");

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
            return R.error("手机号或验证码不能为空");
        }

        // ✅ 确保号码格式一致
        phone = formatPhoneNumber(phone);

        // 从 Session 获取 requestId（Vonage 返回的验证码 ID）
        String requestId = (String) session.getAttribute(phone);
        if (requestId == null) {
            return R.error("验证码已过期，请重新获取");
        }

        // 调用工具类校验验证码
        boolean isValid = verifyUtils.checkVerificationCode(requestId, code);
        if (!isValid) {
            return R.error("验证码错误");
        }

        // 查询数据库，看看该用户是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, phone);
        User user = userService.getOne(queryWrapper);

        // 如果用户不存在，则注册新用户
        if (user == null) {
            user = new User();
            user.setPhone(phone);
            userService.save(user);
        }

        // 存入 session（用于后续请求）
        session.setAttribute("user", user);

        // 返回用户信息
        return R.success(user);
    }*/
}
