package com.bravostudio.service;

import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
import com.bravostudio.util.DbUtil;

/**
 * @author DamirPc
 *
 */
public class WikiSearch {


	
	/**
	 * @param searchPathern string koji je potrebno pretraziti
	 * @return Json reprezentacija rezultata pretrage
	 * @throws Exception
	 */
	public JsonArray getWikiTitle(String searchPathern) throws Exception{
	
	    //Definisanje povratnog niza
	    JsonArray array = new JsonArray();
        
	    //ucitavanje odgovarajuce kolekcije
	    Collection coll=null;
        coll=DbUtil.getInstance().getCollection();
        
        
        //query koji pretrazuje bazu
        //na osnovu ranije kreiranih indexa
        String xquery = "declare namespace h17=\"http://www.mediawiki.org/xml/export-0.9/\";\n" +
	                    "for $y in\n"+
	                    "(for $x in //h17:page[ft:query(h17:title,\""+searchPathern+"\") or ft:query(h17:revision,\""+searchPathern+"\")]\n"+
	                    "order by ft:score($x) descending\n"+
	                    "return $x/h17:title/text()\n"+
	                    ")\n"+
	                    "where $y/position()<11\n"+
	                    "return $y\n";
        
        XPathQueryService service = (XPathQueryService)coll.getService("XPathQueryService", "1.0");
        service.setProperty("indent", "yes");
        ResourceSet result = service.query(xquery);
       
        ResourceIterator i = result.getIterator();
       //pretvaranje rezultata pretrage u json objekat
        while(i.hasMoreResources()){
        	JsonObject json = new JsonObject();
            Resource r= i.nextResource();
            array.addElement(json.putString("title", (String)r.getContent()));
           
        }
        return array;
}
}
