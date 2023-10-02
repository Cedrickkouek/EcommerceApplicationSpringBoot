package com.business.brendaapp.interfaces;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.business.brendaapp.exception.ConvertMultipart2FileException;
import com.business.brendaapp.exception.ProviderFileException;
import com.business.brendaapp.payloads.out.ResponseFileService;


public interface FileServiceInterface {
   public ResponseFileService saveFile(MultipartFile file,String fileType) throws IOException, ConvertMultipart2FileException;
   public ResponseFileService updateFile_Save(MultipartFile file,String fileType,String nom) throws IOException, ConvertMultipart2FileException;
   public byte[] download(String filename) throws IOException;
   public ResponseFileService deleteFile(String filename);
   public ResponseFileService updateFile(String filename, MultipartFile file,String fileType) throws IOException, ProviderFileException;
}
