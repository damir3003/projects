package com.bravostudio.util;

import java.io.IOException;
import java.util.Properties;

/**
 * Util klasa, sadrzi samo staticke metode
 * sluzi za citanje properie fajla
 * 
 * @author DamirPc
 *
 */
public class PropertieManager {
	
	
	
	private static Properties properties;
	
	/*
	 * Onemoguceno kreiranje instanci objekta date klase
	 * */
	private PropertieManager(){
		
	}

	/**
     * Učitavanje propery-ja definisanih u config.properties dokumentu.
     * @param key jedinstveni naziv property-ja
     * @return vrijednost property-ja
     */
    public static String getPropertie(String key){
        if(properties == null){
            properties = new Properties();
            try {
                properties.load(PropertieManager.class.getResourceAsStream("/com/bravostudio/properties/config.properties"));
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return properties.getProperty(key);
    }

}
