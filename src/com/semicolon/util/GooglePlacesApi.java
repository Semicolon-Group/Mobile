package com.semicolon.util;

import com.codename1.util.regex.StringReader;
import com.semicolon.entity.Address;
import com.semicolon.entity.PlaceSuggestion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class GooglePlacesApi {
    public enum TYPE {
        REST("restaurant"), 
        CAFE("cafe"),
        PARK("park");

        private String name;

        TYPE(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    
    private static final String URLString = "https://maps.googleapis.com/maps/api/place/#&key=";
    private static final String KEY = "AIzaSyAsnCz2NQYg03HRAqsT3ip_6pw3IP4JjvA";
    
    public static List<Address> autoCompleteAddress(String input){
        input = input.replace(" ", "+");
        String preparedURL = URLString.replace("#", "autocomplete/json?input="+input+"&types=(regions)&language=fr")+KEY;
        String content = HTTPConnector.connect(preparedURL);
        if(content != null){
            JSONObject jsonObject = JSONParserUtils.extractor(new StringReader(content.toString()));
            JSONArray addressArray = (JSONArray) jsonObject.get("predictions");
            List<Address> addresses = new ArrayList<>();
            for(int i=0; i<addressArray.size(); i++){
                Address address = new Address();
                
                JSONObject jsonAddress = (JSONObject) addressArray.get(i);
                address.setPlaceId((String)jsonAddress.get("place_id"));
                
                JSONArray addressTerms = (JSONArray)jsonAddress.get("terms");
                address.setCity((String)((JSONObject)addressTerms.get(0)).get("value"));
                address.setCountry((String)((JSONObject)addressTerms.get(addressTerms.size()-1)).get("value"));
                addresses.add(address);
            }
            return addresses;
        }
        return null;
    }
    
    public static Address getPlaceDetails(Address address){
        String preparedURL = URLString.replace("#", "details/json?placeid="+address.getPlaceId()+"&language=fr")+KEY;
        String content = HTTPConnector.connect(preparedURL);
        if(content!=null){
            JSONObject jsonObject = JSONParserUtils.extractor(new StringReader(content.toString()));
            JSONObject jsonAddress = (JSONObject) jsonObject.get("result");
            JSONObject jsonLocation = (JSONObject) ((JSONObject)jsonAddress.get("geometry")).get("location");
            address.setLatitude((double)jsonLocation.get("lat"));
            address.setLongitude((double)jsonLocation.get("lng"));
            return address;
        }
        return null;
    }
    
    public static List<PlaceSuggestion> getNearByPlaces(Address address, TYPE type, int radius) throws IOException, ParseException{
        String locationString = "location="+address.getLatitude()+","+address.getLongitude();
        String radiusString = "radius="+radius;
        String typeString = "type="+type.getName();
        String preparedURL = URLString.replace("#", "nearbysearch/json?"+locationString+"&"+radiusString+"&"+typeString+"&language=fr")+KEY;
        String content = HTTPConnector.connect(preparedURL);
        if(content!=null){
            JSONObject jsonObject = JSONParserUtils.extractor(new StringReader(content.toString()));
            JSONArray jsonPlaces = (JSONArray) jsonObject.get("results");
            List<PlaceSuggestion> suggestions = new ArrayList<>();
            for(int i=0; i<jsonPlaces.size(); i++){
                JSONObject place = (JSONObject)jsonPlaces.get(i);
                JSONObject jsonLocation = (JSONObject)((JSONObject)place.get("geometry")).get("location");
                JSONArray photos = (JSONArray)place.get("photos");
                JSONObject jsonOpeningHours = (JSONObject)place.get("opening_hours");
                
                PlaceSuggestion suggestion = new PlaceSuggestion();
                suggestion.setLat((double)jsonLocation.get("lat"));
                suggestion.setLng((double)jsonLocation.get("lng"));
                suggestion.setName((String)place.get("name"));
                suggestion.setOpen(jsonOpeningHours==null?false:(boolean)jsonOpeningHours.get("open_now"));
                suggestion.setPlaceId((String)place.get("place_id"));
                suggestion.setAddress((String)place.get("vicinity"));
                double rating = ((place.get("rating")==null) || (place.get("rating") instanceof Long))?
                        0
                        :(double)place.get("rating");
                suggestion.setRating(rating);
                if(photos!= null && photos.size() != 0){
                    suggestion.setPhotoRef((String)((JSONObject)photos.get(0)).get("photo_reference"));
                }
                suggestion = GoogleDistanceMatrixAPI.getDistanceAndDuration(address, suggestion);
                
                suggestions.add(suggestion);
            }
            return suggestions;
        }
        return null;
    }
    
    public static String getPhoto(String photoRef, int maxWidth){
        return URLString.replace("#", "photo?maxwidth="+maxWidth+"&photoreference="+photoRef)+KEY;
    }
}
