package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * 通用文件上传和下载控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList(".jpg", ".png", ".jpeg", ".pdf");
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

    /**
     * 应用启动时检查目录是否存在，不存在则创建
     */
    @PostConstruct
    public void init() {
        File dir = new File(basePath);
        if (!dir.exists() && dir.mkdirs()) {
            log.info("✅ 文件存储目录创建成功: {}", basePath);
        } else {
            log.info("📂 文件存储目录已存在: {}", basePath);
        }
    }

    /**
     * 文件上传
     * @param file  上传的文件
     * @return      返回文件名
     */
    @PostMapping("/upload")
    public ResponseEntity<R<String>> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(R.error("上传的文件为空！"));
        }

        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 校验文件类型
        if (!ALLOWED_FILE_TYPES.contains(fileSuffix.toLowerCase())) {
            return ResponseEntity.badRequest().body(R.error("不支持的文件类型！"));
        }

        // 校验文件大小
        if (file.getSize() > MAX_FILE_SIZE) {
            return ResponseEntity.badRequest().body(R.error("文件大小超出限制（最大 10MB）！"));
        }

        // 生成唯一文件名
        String uniqueFileName = UUID.randomUUID().toString() + fileSuffix;

        // 保存文件
        Path filePath = Paths.get(basePath, uniqueFileName);
        try {
            Files.copy(file.getInputStream(), filePath);
            log.info("✅ 文件上传成功：{} -> {}", originalFilename, filePath);
            return ResponseEntity.ok(R.success(uniqueFileName));
        } catch (IOException e) {
            log.error("❌ 文件保存失败：{}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(R.error("文件上传失败，请稍后重试！"));
        }
    }

    /**
     * 文件下载
     * @param name     文件名
     * @param response HTTP 响应对象
     */
    @GetMapping("/download")
    public void download(@RequestParam("name") String name, HttpServletResponse response) {
        if (!StringUtils.hasText(name)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Path filePath = Paths.get(basePath, name);
        File file = filePath.toFile();

        if (!file.exists() || !file.isFile()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try {
                response.getWriter().write("File not found: " + name);
            } catch (IOException e) {
                log.error("❌ 发送 404 错误时发生异常", e);
            }
            return;
        }

        try (FileInputStream fileInputStream = new FileInputStream(file);
             ServletOutputStream outputStream = response.getOutputStream()) {

            // 设置 MIME 类型
            String mimeType = Files.probeContentType(filePath);
            response.setContentType(mimeType != null ? mimeType : "application/octet-stream");

            // 设置响应头
            response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
            response.setContentLengthLong(file.length());

            // 传输文件
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.flush();
            log.info("✅ 文件下载成功: {}", name);

        } catch (IOException e) {
            log.error("❌ 文件下载失败: {}", e.getMessage(), e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
