package com.semicolon.service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.semicolon.entity.Address;
import com.semicolon.entity.Enumerations;
import com.semicolon.entity.*;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class MemberService {
    /*
        Member m = MemberService.getInstance().getMember(12);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        m.setBirthDate(sdf.parse("05/07/1995"));
        m.setPseudo("SS");
        m.setFirstname("SOO");
        m.setLastname("Abd");
        m.setGender(true);
        m.setHeight(1.60f);
        m.setBodyType(Enumerations.BodyType.THIN);
        m.setChildrenNumber(10);
        m.setReligion(Enumerations.Religion.ATHEISM);
        m.setReligionImportance(Enumerations.Importance.SOMEWHAT_IMPORTANT);
        m.setSmoker(true);
        m.setDrinker(false);
        m.setMinAge(18);
        m.setMaxAge(25);
        m.setPhone(53057885);
        m.setAbout("About test");
        m.setMaritalStatus(Enumerations.MaritalStatus.DIVORCED);
        m.setEmail("m.abdennadher.seif@gmail.com");
        m.getAddress().setCity("TTTT");
        m.getAddress().setCountry("jjjjj");
    */
    
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
    
    public Member editMemeber(Member m){
        ConnectionRequest con = new ConnectionRequest();
        String url = "http://localhost/mysoulmate/web/app_dev.php/service/seif/editUser/"+member.getId();
        con.setUrl(url);
        con.setPost(true);
        
        con.addArgument("firstname", m.getFirstname());
        con.addArgument("lastname", m.getLastname());
        con.addArgument("username", m.getPseudo());
        con.addArgument("gender", String.valueOf(m.isGender()?1:0));
        con.addArgument("birthday", m.getBirthDate().toString());
        con.addArgument("min_age", String.valueOf(m.getMinAge()));
        con.addArgument("max_age", String.valueOf(m.getMaxAge()));
        con.addArgument("height", String.valueOf(m.getHeight()));
        con.addArgument("body_type", String.valueOf(m.getBodyType().ordinal()));
        con.addArgument("religion", String.valueOf(m.getReligion().ordinal()));
        con.addArgument("religion_importance", String.valueOf(m.getReligionImportance().ordinal()));
        con.addArgument("chilren_number", String.valueOf(m.getChildrenNumber()));
        con.addArgument("smoker", String.valueOf(m.isSmoker()?1:0));
        con.addArgument("drinker", String.valueOf(m.isDrinker()?1:0));
        con.addArgument("phone", String.valueOf(m.getPhone()));
        con.addArgument("email", m.getEmail());
        con.addArgument("civil_status", String.valueOf(m.getMaritalStatus().ordinal()));
        con.addArgument("city", m.getAddress().getCity());
        con.addArgument("country", m.getAddress().getCountry());
        con.addArgument("lng", String.valueOf(m.getAddress().getLongitude()));
        con.addArgument("lat", String.valueOf(m.getAddress().getLatitude()));
        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            member = getMember(m.getId());
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