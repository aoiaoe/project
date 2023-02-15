package com.cz.spring_boot_mix.upload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author jzm
 * @date 2023/2/9 : 11:06
 */
@Setter
@Getter
public class CommonInputStreamResource extends InputStreamResource {

    private long length;
    private String fileName;

    public CommonInputStreamResource(InputStream inputStream) {
        super(inputStream);
    }
    public CommonInputStreamResource(InputStream inputStream, long length, String fileName){
        super(inputStream);
        this.fileName = fileName;
        this.length = length;
    }

    @Override
    public String getFilename() {
        return fileName;
    }

    @Override
    public long contentLength() throws IOException {
        return length;
    }
}
