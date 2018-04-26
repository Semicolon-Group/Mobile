package com.semicolon.service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.semicolon.entity.Address;
import com.semicolon.entity.Enumerations;
import com.semicolon.entity.Member;
import com.semicolon.entity.Post;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;

public class MemberService {
    private Member member;
    
    private static MemberService instance;
    
    private MemberService(){}
    
    public static MemberService getInstance(){
        if(instance == null)
            instance = new MemberService();
        return instance;
    }
    
    public Member getMember(int id){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/getUser/"+id;
        con.setUrl(url);
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            member = parseMember(str);
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return member;
    }
    
    private Member parseMember(String json){
        Member m = new Member();
        try {
            JSONParser j = new JSONParser();
            
            Map<String, Object> memberMap = j.parseJSON(new CharArrayReader(json.toCharArray()));
            
            if(memberMap != null){
                m.setBirthDate(new Date((((Double)((Map<String, Object>)memberMap.get("birthDate")).get("timestamp")).longValue()*1000)));
                m.setId(((Double)memberMap.get("id")).intValue());
                m.setPseudo((String)memberMap.get("username"));
                m.setFirstname((String)memberMap.get("firstname"));
                m.setLastname((String)memberMap.get("lastname"));
                m.setGender(Boolean.valueOf((String)memberMap.get("gender")));
                m.setHeight(((Double)memberMap.get("height")).floatValue());
                m.setBodyType(Enumerations.BodyType.values()[((Double)memberMap.get("bodyType")).intValue()]);
                m.setChildrenNumber(((Double)memberMap.get("childrenNumber")).intValue());
                m.setReligion(Enumerations.Religion.values()[((Double)memberMap.get("relegion")).intValue()]);
                m.setReligionImportance(Enumerations.Importance.values()[((Double)memberMap.get("relegionImportance")).intValue()]);
                m.setSmoker(Boolean.valueOf((String)memberMap.get("smoker")));
                m.setDrinker(Boolean.valueOf((String)memberMap.get("drinker")));
                m.setMinAge(((Double)memberMap.get("minAge")).intValue());
                m.setMaxAge(((Double)memberMap.get("maxAge")).intValue());
                m.setPhone(((Double)memberMap.get("phone")).intValue());
                m.setCreatedAt(new Date((((Double)((Map<String, Object>)memberMap.get("createdAt")).get("timestamp")).longValue()*1000)));
                m.setAbout((String)memberMap.get("about"));
                m.setMaritalStatus(Enumerations.MaritalStatus.values()[((Double)memberMap.get("civilStatus")).intValue()]);
                m.setEmail((String)memberMap.get("email"));
                
                Address address = new Address();
                Map<String, Object> addressMap = (Map<String, Object>)memberMap.get("address");
                address.setCity((String)addressMap.get("city"));
                address.setCountry((String)addressMap.get("country"));
                address.setLongitude((double)addressMap.get("longitude"));
                address.setLatitude((double)addressMap.get("latitude"));
                
                m.setAddress(address);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return m;
    }
}
