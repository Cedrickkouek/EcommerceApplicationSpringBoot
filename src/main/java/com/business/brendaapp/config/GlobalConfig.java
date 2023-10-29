package com.business.brendaapp.config;

import org.springframework.context.annotation.Configuration;

import com.business.brendaapp.enumeration.Category;

@Configuration
public class GlobalConfig {
    public static String DNSURL = "https://brendaecommerceapp.s3.eu-west-3.amazonaws.com";
    
    public final static String IMAGE_URL="Categorie/IMAGE/";

    public static String getPathUrl(Category category){
        switch(category){
            case MONTRE:
                return IMAGE_URL;
            case BIJOUX:
                return IMAGE_URL;    
            default:
                return IMAGE_URL;
        }
    }

   
}
