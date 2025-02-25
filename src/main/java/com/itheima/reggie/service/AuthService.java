package com.itheima.reggie.service;

import com.itheima.reggie.common.JwtUtil;
import com.itheima.reggie.common.R;
import com.itheima.reggie.common.RedisUtil;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * AuthService handles authentication, JWT token management, and Redis-based security mechanisms.
 */
@Slf4j
@Service
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtUtil jwtUtil;

    private static final String REDIS_PHONE_PREFIX = "phone:code:"; // Redis key prefix for storing verification codes

    /**
     * Sends a verification code and stores it in Redis with a 5-minute expiration.
     *
     * @param phone The user's phone number
     */
    public void sendVerificationCode(String phone) {
        String verificationCode = generateVerificationCode();

        // 🔹 Store the verification code in Redis with a 5-minute expiration
        redisUtil.set(REDIS_PHONE_PREFIX + phone, verificationCode, 5, TimeUnit.MINUTES);

        // Logging for debugging (remove this in production)
        System.out.println("📩 Verification Code Sent: " + verificationCode + " (Phone: " + phone + ")");
    }

    /**
     * Authenticates a user using their phone number and verification code.
     * @param phone User's phone number.
     * @param inputCode User's entered verification code.
     * @return JWT token response or error message.
     */
    public R<Map<String, String>> authenticateUser(String phone, String inputCode) {
        //  Retrieve the stored verification code from Redis
        String storedCode = redisUtil.get(REDIS_PHONE_PREFIX + phone);
        if (storedCode == null) {
            return R.error("Verification code expired, please request a new one.");
        }

        //  Check if the code matches
        if (!storedCode.equals(inputCode)) {
            return R.error("Incorrect verification code.");
        }

        //  Remove the verification code from Redis (to prevent reuse)
        redisUtil.delete(REDIS_PHONE_PREFIX + phone);

        //  Retrieve or register the user in the database
        User user = userService.registerOrRetrieveUser(phone);

        //  Generate a JWT token
        String token = jwtUtil.generateToken(user.getId().toString());

        //  Prepare response
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", user.getId().toString());

        return R.success(response);
    }

    /**
     * Handles user logout by adding their JWT token to the Redis blacklist.
     * @param authorizationHeader The Authorization header containing the JWT.
     * @return Success message or error response.
     */
    public R<String> logoutUser(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return R.error("Invalid token");
        }

        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        long expiration = jwtUtil.getExpiration(token);
        redisUtil.addToBlacklist(token, expiration);

        return R.success("Logged out successfully.");
    }

    public boolean validateToken(String token) {
        return jwtUtil.isValidToken(token) && !redisUtil.isBlacklisted(token);
    }

    public String getUserIdFromToken(String token) {
        return jwtUtil.getUserIdFromToken(token);
    }

    /**
     * Generates a random 6-digit verification code.
     *
     * @return A string representation of the verification code.
     */
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

}
