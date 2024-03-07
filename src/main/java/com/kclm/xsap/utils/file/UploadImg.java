//package com.kclm.xsap.utils.file;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.system.ApplicationHome;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.UUID;
//
//public class UploadImg {
//
//    private final String COVER_UPLOAD_DIR = "uploadImagesTeacherImg";
//
//    public static String uploadImg(MultipartFile file, String uploadImagesTeacherImg) {
//        private final Logger log = LoggerFactory.getLogger(UploadImg.class);
//        log.debug("上传图片");
//        //获取当前jar包的绝对路径
//        ApplicationHome applicationHome = new ApplicationHome(getClass());
//        log.debug(">>>> source: {}", applicationHome.getSource());
//        log.debug(">>> dir: {}", applicationHome.getDir()); //就是当前jar包所在的目录
//        String realPath = applicationHome.getDir().getAbsolutePath() + File.separatorChar + COVER_UPLOAD_DIR;
//        log.debug(realPath);
//        String fileName = null;
//        //上传图片
//        final String originalFilename = coverUrl.getOriginalFilename();
//        if (!originalFilename.isEmpty()) {
//            fileName = UUID.randomUUID().toString();
//            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
//            fileName += suffix;
//            try {
//                coverUrl.transferTo(new File(realPath + File.separatorChar + fileName));
//            } catch (IOException e) {
//                log.error("上传图片失败", e);
//            }
//        }
//        return fileName;
//    }
//}
