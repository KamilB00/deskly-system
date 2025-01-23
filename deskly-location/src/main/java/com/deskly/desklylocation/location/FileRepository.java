package com.deskly.desklylocation.location;

import com.deskly.desklylocation.shared.language.URL;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileRepository {

    URL upload(MultipartFile file) throws IOException;

}
