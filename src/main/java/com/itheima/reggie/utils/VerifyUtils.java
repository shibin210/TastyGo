package com.itheima.reggie.utils;

import com.vonage.client.VonageClient;
import com.vonage.client.verify.CheckResponse;
import com.vonage.client.verify.VerifyResponse;
import com.vonage.client.verify.VerifyStatus;
import com.vonage.client.verify.VerifyRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component // 让 Spring 托管该工具类
@ConfigurationProperties(prefix = "vonage") // 让 Spring 自动绑定 `vonage.api-key`
public class VerifyUtils {
    // 通过 @Value 读取配置文件中的 API_KEY 和 API_SECRET
    @Value("${vonage.api-key}")
    private String apiKey;

    @Value("${vonage.api-secret}")
    private String apiSecret;

    @Value("${vonage.sender-name}")
    private String apiSender;

    // 发送验证码
    public String sendVerificationCode(String phoneNumber) {
        VonageClient client = VonageClient.builder()
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .build();

        // 1️⃣ 使用新的 VerifyRequest 构建请求
        VerifyRequest request = VerifyRequest.builder(phoneNumber, apiSender).build();
        VerifyResponse response = client.getVerifyClient().verify(request); // ✅ 使用 verify() 方法

        if (response.getStatus() == VerifyStatus.OK) {
            System.out.println("验证码已发送: " + response.getRequestId());
            return response.getRequestId();  // 存储 requestId 以供后续验证
        } else {
            System.out.println("验证码发送失败: " + response.getErrorText());
            return null;
        }
    }

    // 校验验证码
    public boolean checkVerificationCode(String requestId, String code) {
        VonageClient client = VonageClient.builder()
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .build();

        CheckResponse response = client.getVerifyClient().check(requestId, code);

        if (response.getStatus() == VerifyStatus.OK) {
            System.out.println("验证码验证成功");
            return true;
        } else {
            System.out.println("验证码验证失败: " + response.getErrorText());
            return false;
        }
    }
}
