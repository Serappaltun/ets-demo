package com.seraptemel.etsdemo.validator;

import org.springframework.web.multipart.MultipartFile;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;


public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    public static final String[] SUPPORTED_CONTENT_TYPES =  {"application/vnd.ms-excel",
            "image/png", "image/jpg", "image/jpeg", "application/pdf","application/vnd.openxmlformats-officedocument.wordprocessingml.document"};

    @Override
    public void initialize(ValidFile constraintAnnotation) { }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        String contentType = multipartFile.getContentType();
        return isSupportedContentType(contentType);
    }

    private boolean isSupportedContentType(String contentType) {
        return Arrays.asList(SUPPORTED_CONTENT_TYPES).contains(contentType);
    }
}
