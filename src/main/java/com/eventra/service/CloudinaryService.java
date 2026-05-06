package com.eventra.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public class CloudinaryService {

    private static final Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "detm79j5t",
            "api_key",    "367823232799998",
            "api_secret", "i_otld5ZA7T4DbeFyHWpe0wbRIU"
    ));

    @SuppressWarnings("unchecked")
    public static String uploadImage(MultipartFile file) throws IOException {
        Map<String, Object> result = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap("folder", "eventra/events")
        );
        return (String) result.get("secure_url");
    }
}
