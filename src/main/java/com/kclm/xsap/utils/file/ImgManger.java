package com.kclm.xsap.utils.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ImgManger {

    private final static String IMG_UPLOAD_DIR = "images";
    private final static Logger log = LoggerFactory.getLogger(ImgManger.class);


    public static String uploadImg(MultipartFile originImg, ApplicationHome applicationHome, String imgDir) {

        log.info("上传图片");
        log.info(">>>> source: {}", applicationHome.getSource());
        log.info(">>> dir: {}", applicationHome.getDir()); //当前jar包所在的目录

        String realPath = applicationHome.getDir().getAbsolutePath() + File.separatorChar + IMG_UPLOAD_DIR +
                File.separatorChar + imgDir;
        log.debug(realPath);

        // 判断目录是否存在，如果不存在则创建
        File dir = new File(realPath);
        if (!dir.exists()) {
            boolean mkdirsResult = dir.mkdirs();
            if (mkdirsResult) {
                log.info("目录 {} 不存在，已成功创建", realPath);
            } else {
                log.error("尝试创建目录 {} 失败，请检查权限或其他问题", realPath);
            }
        }

        String fileName = null;

        //上传图片
        final String originalFilename = originImg.getOriginalFilename();

        if (originalFilename != null && !originalFilename.isEmpty()) {
            fileName = UUID.randomUUID().toString();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            fileName += suffix;
            try {
                originImg.transferTo(new File(realPath + File.separatorChar + fileName));
            } catch (IOException e) {
                log.error("上传图片失败", e);
            }
        }
        return fileName;
    }
}