/*
package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    */
/**
     * 文件上传
     *
     * @param file
     * @return
     *//*

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        // 检查文件是否为空
        if (file == null || file.isEmpty()) {
            return R.error("上传的文件为空！");
        }

        // 获取原始文件名并提取文件后缀
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return R.error("无法获取原始文件名！");
        }
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        List<String> allowedSuffixes = Arrays.asList(".jpg", ".png", ".jpeg", ".pdf");
        if (!allowedSuffixes.contains(suffix.toLowerCase())) {
            return R.error("不支持的文件类型！");
        }

        // 检查文件大小
        long maxFileSize = 10 * 1024 * 1024; // 10MB
        if (file.getSize() > maxFileSize) {
            return R.error("文件大小超出限制（最大 10MB）！");
        }

        // 生成唯一文件名
        String fileName = UUID.randomUUID().toString() + suffix;

        // 检查并创建存储目录
        File dir = new File(basePath);
        if (!dir.exists() && !dir.mkdirs()) {
            return R.error("创建文件上传目录失败！");
        }

        // 保存文件
        try {
            file.transferTo(new File(basePath + fileName));
            log.info("文件上传成功：{}，大小：{} 字节，路径：{}", originalFilename, file.getSize(), basePath + fileName);
        } catch (IOException e) {
            log.error("文件保存失败：{}", e.getMessage(), e);
            return R.error("文件上传失败，请稍后重试！");
        }

        // 返回文件访问 URL
        //String fileUrl = "/download?name=" + fileName;
        return R.success(fileName);
    }


*/
/*    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        //file 是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会被删除
        // 检查文件是否为空
        if (file == null || file.isEmpty()) {
            return R.error("上传的文件为空！");
        }

        // 打印文件信息
        log.info("接收到的文件：{}", file.toString());

        // 获取原始文件名并提取文件后缀
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return R.error("无法获取原始文件名！");
        }
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 生成唯一文件名，防止文件名冲突
        String fileName = UUID.randomUUID().toString() + suffix;

        // 创建文件保存目录的对象
        File dir = new File(basePath);

        // 检查目录是否存在，如果不存在则创建
        if (!dir.exists()) {
            boolean isDirCreated = dir.mkdirs();
            if (!isDirCreated) {
                return R.error("创建文件上传目录失败！");
            }
        }

        try {
            // 将文件保存到指定位置，使用生成的唯一文件名
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            log.error("保存文件时发生错误", e);
            throw new RuntimeException("文件上传失败", e);
        }

        // 返回成功响应，附带新文件名
        return R.success(fileName);
    }*//*


    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        // 检查文件名是否为空
        if (name == null || name.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 定义文件路径
        File file = new File(basePath + name);

        // 校验文件是否存在
        if (!file.exists() || !file.isFile()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try {
                response.getWriter().write("File not found: " + name);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        try (
                // 使用 try-with-resources 自动管理资源
                FileInputStream fileInputStream = new FileInputStream(file);
                ServletOutputStream outputStream = response.getOutputStream()
        ) {
            // 设置响应类型（根据文件类型动态设置）
            String mimeType = Files.probeContentType(file.toPath());
            if (mimeType == null) {
                mimeType = "application/octet-stream"; // 默认二进制流类型
            }
            response.setContentType(mimeType);

            // 设置 Content-Disposition，支持下载模式
            response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
            response.setContentLengthLong(file.length()); // 设置文件大小

            // 使用缓冲区传输数据
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // 输出流刷新
            outputStream.flush();
        } catch (IOException e) {
            // 捕获并处理 IO 异常
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    */
/*@GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

            //输出流，通过输出流将文件写回浏览器，在浏览器展示图片了。
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) { //循环读取数据直到不等于-1
                outputStream.write(bytes, 0, len); //使用输出流写入数据
                outputStream.flush();
            }

            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*//*

}



















*/
