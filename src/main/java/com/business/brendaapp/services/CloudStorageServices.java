package com.business.brendaapp.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.business.brendaapp.config.GlobalConfig;
import com.business.brendaapp.exception.ConvertMultipart2FileException;
import com.business.brendaapp.exception.ProviderFileException;
import com.business.brendaapp.interfaces.FileServiceInterface;
import com.business.brendaapp.payloads.out.ResponseFileService;
import com.business.brendaapp.utils.FileIoService;
import com.business.brendaapp.utils.GenerateurMatricile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CloudStorageServices implements FileServiceInterface{

    @Value("${bucketName}")
    private String bucketName;

    @Autowired
    private AmazonS3 clientS3;

    @Override
    public ResponseFileService saveFile(MultipartFile file, String fileType) throws IOException, ConvertMultipart2FileException {
        String originalFileName = file.getOriginalFilename();
        String extension = FileIoService.getExtension(originalFileName);
        String uniqueName = GenerateurMatricile.generateNameFile();
        String finalFileName = uniqueName+"."+extension;
        File file1 = convertMultiPartToFile(file);
        PutObjectResult putObjectResult = clientS3.putObject(bucketName,fileType+finalFileName, file1);
        String finalValue = GlobalConfig.DNSURL+"/"+fileType+finalFileName;
        FileIoService.deleteFile(Paths.get(originalFileName));
        return new ResponseFileService(200,finalValue, putObjectResult.getContentMd5());
    }

    @Override
    public ResponseFileService updateFile_Save(MultipartFile file, String fileType, String nom) throws IOException, ConvertMultipart2FileException {
        String originalFileName = file.getOriginalFilename();
        String finalPath = fileType+nom;
        File file1 = convertMultiPartToFile(file);
        PutObjectResult putObjectResult = clientS3.putObject(bucketName,finalPath, file1);
        FileIoService.deleteFile(Paths.get(originalFileName));
        return new ResponseFileService(200,finalPath, putObjectResult.getContentMd5());
    }

    @Override
    public byte[] download(String filename) throws IOException {
        S3Object object = clientS3.getObject(bucketName, filename);
        S3ObjectInputStream ojectContent = object.getObjectContent();
        return IOUtils.toByteArray(ojectContent);
    }

    @Override
    public ResponseFileService deleteFile(String filename) {
        clientS3.deleteObject(bucketName, filename);
        return new ResponseFileService(200, "file Deleted", "");
    }

    @Override
    public ResponseFileService updateFile(String url, MultipartFile file, String fileType)
        throws IOException, ProviderFileException {
        try {
                String value2 = url.split("d3trzf76mis50s.cloudfront.net")[0];
                String value = url.split("/")[-1];
                deleteFile(value2);
                System.out.println("/*******"+value);
                return updateFile_Save(file, fileType,value);
            } catch (Exception e) {
            throw new ProviderFileException("Erreur survenue lors de la modification!");
       }
    }
    private File convertMultiPartToFile(MultipartFile file) throws IOException, ConvertMultipart2FileException{
        try {
            File convfile = new File(file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convfile);
            fos.write(file.getBytes());
            fos.close();
            return convfile;
        } catch (Exception e) {
            throw new ConvertMultipart2FileException("Error while converting the multipart file "+file+" to a file");
        }
        
    }
}
