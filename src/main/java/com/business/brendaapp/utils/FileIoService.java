package com.business.brendaapp.utils;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileIoService {
        public static boolean  deleteFile(Path path) throws IOException{
            return Files.deleteIfExists(path);
        }

        public static String getExtension(String fileName){
            return com.google.common.io.Files.getFileExtension(fileName);
        }
}
