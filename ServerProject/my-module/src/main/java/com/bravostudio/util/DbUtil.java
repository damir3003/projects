package com.bravostudio.util;

import org.xmldb.api.base.*;
import org.xmldb.api.*;

/**
 * @author DamirPc
 *Klasa koja sluzi za uspostavljanje veze sa bazom
 *realizovana kao singleton klasa
 */
public class DbUtil {
	
	//Ucitavanje parametara potrebnih za pristup bazi
	//iz config fajla
	private static final String URI=PropertieManager.getPropertie("db.URI");
	private static final String DRIVER=PropertieManager.getPropertie("db.driver");
	private static final String COLLECTION_PATH=PropertieManager.getPropertie("db.collection.path");
	
	private static DbUtil dbUtil;
	private Database database;
	
	
	private DbUtil(){
		
	}
	
	private void registerDataBase() throws ClassNotFoundException, XMLDBException, InstantiationException, IllegalAccessException{
		Class c1=Class.forName(DRIVER);
        database = (Database) c1.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
	}
	
	public static DbUtil getInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException{
		if(dbUtil==null){
			dbUtil = new DbUtil();
			dbUtil.registerDataBase();
		}
		return dbUtil;
	}
	public Collection getCollection() throws XMLDBException{
		return DatabaseManager.getCollection(URI+COLLECTION_PATH);
		
	}


}
