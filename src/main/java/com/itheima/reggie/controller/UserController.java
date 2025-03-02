package com.itheima.reggie.controller;

import com.itheima.reggie.common.JwtUtil;
import com.itheima.reggie.common.R;
import com.itheima.reggie.common.RedisUtil;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.repository.UserRepository;
import com.itheima.reggie.service.AuthService;
import com.itheima.reggie.service.UserService;
import com.itheima.reggie.utils.VerifyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * refactored UserController using Hibernate (Spring Data JPA) for database interactions
 * while maintaining pagination compatibility with MyBatis-Plus frontend format.
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthService authService; // Using AuthService for authentication

    /**
     * Send a verification code (stored in Redis)
     */
    @PostMapping("/sendMsg")
    public R<String> sendVerificationCode(@RequestBody Map<String, String> request) {
        String phone = request.get("phone");
        if (StringUtils.isEmpty(phone)) {
            return R.error("Phone number cannot be empty");
        }

        // Call AuthService to send verification code
        authService.sendVerificationCode(phone);

        return R.success("Verification code sent successfully");
    }


    /**
     * Handles user login using a phone number and verification code.
     */
    @PostMapping("/login")
    public R<Map<String, String>> login(@RequestBody Map<String, String> paramMap) {
        return authService.authenticateUser(paramMap.get("phone"), paramMap.get("code"));
    }

    /**
     * Handles user logout by adding the JWT token to the Redis blacklist.
     */
    @PostMapping("/logout")
    public R<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
        return authService.logoutUser(authorizationHeader);
    }


    /**
     * Generates a random 6-digit verification code
     *
     * @return A string representation of the verification code
     */
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    /**
     * Normalizes phone numbers (assumes UK format if missing country code)
     *
     * @param phone Raw phone number
     * @return Formatted phone number with international code
     */
    private String formatPhoneNumber(String phone) {
        if (!phone.startsWith("+")) {
            phone = "+44" + phone.substring(1);
        }
        return phone;
    }
}



    /*
    */
/**
     * Sends a verification code (Test Mode: Displays in console)
     * @param user User object containing the phone number
     * @param session HTTP session for storing the verification code
     * @return Response message
     *//*

    @PostMapping("/sendMsg")
    public R<String> sendTestVerificationCode(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {
            phone = formatPhoneNumber(phone);
            String verificationCode = generateVerificationCode();

            log.info("Test Mode - Verification Code Sent: {} (Phone: {})", verificationCode, phone);
            session.setAttribute(phone, verificationCode);

            return R.success("Verification code sent successfully (Check logs for code)");
        }
        return R.error("Phone number cannot be empty");
    }

    */
/**
     * Sends a real verification code via SMS (Production Mode)
     * @param user User object containing the phone number
     * @param session HTTP session for storing the request ID
     * @return Response message
     *//*

    @PostMapping("/sendMsgReal")
    public R<String> sendRealVerificationCode(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {
            phone = formatPhoneNumber(phone);
            String requestId = verifyUtils.sendVerificationCode(phone);

            if (requestId != null) {
                session.setAttribute(phone, requestId);
                return R.success("SMS verification code sent successfully");
            } else {
                return R.error("Failed to send SMS");
            }
        }
        return R.error("Phone number cannot be empty");
    }
*/

 /*   *//**
     * Login using phone verification code (Test Mode)
     * @param paramMap Contains "phone" and "code"
     * @param session HTTP session to manage user login state
     * @return Logged-in user details
     *//*
    @PostMapping("/login")
    public R<User> loginTest(@RequestBody Map<String, String> paramMap, HttpSession session) {
        String phone = paramMap.get("phone");
        String inputCode = paramMap.get("code");

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(inputCode)) {
            return R.error("Phone number or verification code cannot be empty");
        }

        phone = formatPhoneNumber(phone);
        String sessionCode = (String) session.getAttribute(phone);

        if (sessionCode == null) {
            return R.error("Verification code has expired, please request a new one");
        }

        if (!sessionCode.equals(inputCode)) {
            return R.error("Incorrect verification code");
        }

        session.removeAttribute(phone);
        User user = registerOrRetrieveUser(phone, session);

        return R.success(user);
    }

    *//**
     * Login using SMS verification (Production Mode)
     * @param paramMap Contains "phone" and "code"
     * @param session HTTP session to manage user login state
     * @return Logged-in user details
     *//*
    @PostMapping("/loginReal")
    public R<User> loginReal(@RequestBody Map<String, String> paramMap, HttpSession session) {
        String phone = paramMap.get("phone");
        String code = paramMap.get("code");

        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
            return R.error("Phone number or verification code cannot be empty");
        }

        phone = formatPhoneNumber(phone);
        String requestId = (String) session.getAttribute(phone);

        if (requestId == null) {
            return R.error("Verification code has expired, please request a new one");
        }

        if (!verifyUtils.checkVerificationCode(requestId, code)) {
            return R.error("Incorrect verification code");
        }

        session.removeAttribute(phone);
        User user = registerOrRetrieveUser(phone, session);

        return R.success(user);
    }
*/

