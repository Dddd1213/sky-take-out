package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommenController {

    @Autowired
    AliOssUtil aliOssUtil;

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file)  {

        String filepath = null;
        try {
            log.info("文件上传：{}");
            String originalFilename = file.getOriginalFilename();
            //截取原始文件名后缀
            String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
            String name = UUID.randomUUID().toString()+substring;
            filepath = aliOssUtil.upload(file.getBytes(), name);
            return Result.success(filepath);
        } catch (IOException e) {
            log.error("文件上传失败：{}",e);
            e.printStackTrace();
        }

        return Result.error("文件上传失败");
    }
}
