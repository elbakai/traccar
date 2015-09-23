/*
 * Copyright 2014 - 2015 Stefaan Van Dooren (stefaan.vandooren@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.traccar.geocode;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.traccar.helper.Log;
import org.w3c.dom.Document;

public class MapQuestReverseGeocoder extends JsonReverseGeocoder {
    public MapQuestReverseGeocoder() {
        this("http://www.mapquestapi.com/geocoding/v1/reverse", "ABCDE", 0);
    }
    public MapQuestReverseGeocoder(String url, String key, int cacheSize) {
        super(url +  "?key=" + key + "&location=%f,%f", cacheSize);
    }

    @Override
    protected Address parseAddress(JsonObject json) {
	JsonArray result = json.getJsonArray("results");
        if (result != null) {
	    JsonObject result1 = result.getJsonObject(0);
	    JsonArray location = result1.getJsonArray("locations");
	    if (location != null) {
		JsonObject location1 = location.getJsonObject(0);
	
        	Address address = new Address();
            
		if (location1.containsKey("street")) {
                    address.setStreet(location1.getString("street"));
            	}	

                if (location1.containsKey("adminArea5")) {
                    address.setSettlement(location1.getString("adminArea5"));
            	}	

            	if (location1.containsKey("adminArea4")) {
                    address.setDistrict(location1.getString("adminArea4"));
            	}

            	if (location1.containsKey("adminArea3")) {
                    address.setState(location1.getString("adminArea3"));
            	}

            	if (location1.containsKey("adminArea1")) {
                    address.setCountry(location1.getString("adminArea1").toUpperCase());
            	}

            	if (location1.containsKey("postalCode")) {
                    address.setPostcode(location1.getString("postalCode"));
            	} return address;
	    }
        }
        return null;
    }

}