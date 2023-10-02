package com.business.brendaapp.utils;

import java.util.UUID;

public class GenerateurMatricile {
    
    public static String generateNameFile(){
        return UUID.randomUUID().toString();
    }


}
