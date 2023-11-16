package com.inception.backend.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class OssUploadUtil {

    public static final String ALI_DOMAIN = "https://inception-avatar.oss-cn-shanghai.aliyuncs.com/";

    public static String uploadImage(MultipartFile file) throws IOException{
        String originalFilename = file.getOriginalFilename(); //获取原来的图片名;
        String ext = "." + FilenameUtils.getExtension(originalFilename);
        String uuid = UUID.randomUUID().toString().replace("-","");
        String fileName = uuid + ext;

        String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
        String accessKeyId = "LTAI5tDAmiAsr2xFQ8LP12rm";
        String accessKeySecret = "eT8FVfbSbOql9KsC6Rt1eIduU5KTA7";

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(
                "inception-avatar",
                fileName,
                file.getInputStream()
        );
        ossClient.shutdown();
        return ALI_DOMAIN + fileName;
    }
}
