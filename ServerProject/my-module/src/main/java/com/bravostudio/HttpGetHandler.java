package com.bravostudio;

import org.vertx.java.core.Handler;
import org.vertx.java.core.MultiMap;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.logging.Logger;

import com.bravostudio.service.WikiSearch;
//Handler za GET zahtjev
public class HttpGetHandler implements Handler<HttpServerRequest> {
   
	private Logger logger;
	
	public HttpGetHandler(Logger logger) {
		this.logger=logger;
		
	}

	@Override
	public void handle(HttpServerRequest request) {
		
		MultiMap param = request.params();
        //Ukoliko postoji odgovarajuci parametar
		//koji je poslan preko get zahtjeva
		//vrsimo petragu i vracamo 10 najrelevantnijih naslova
		//kao JSON reprezentaciju
		try {
			if (param.contains("wikiSearch")) {
				WikiSearch wikiSearch = new WikiSearch();
				JsonArray ob = wikiSearch.getWikiTitle(param
						.get("wikiSearch"));

				request.response().end(ob.encode());
			}else{
				request.response().end("Not correct parametar!!!");
			}
		} catch (Exception e) {
			//Ukoliko se desi bilo kakva greska u komunikaciji sa bazom ili tokom pretrage
			//logujemo gresku i saljemo odgovor korisniku
			logger.error("Database connection problem: "+e.getLocalizedMessage());
			request.response().end("Currently we are not able to perform the search. Try again later!");
		}

	}

}
