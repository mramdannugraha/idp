package com.ramdan.trainingkaryawan.utils;


import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Response {

    public Map sukses(Object obj){
        Map map = new HashMap();
        map.put("data", obj);
        map.put("code", 200);
        map.put("status", "sukses");
        return map;
    }

    public Map error(Object obj, Object code){
        Map map = new HashMap();
        map.put("code", code);
        map.put("status", obj);
        return map;
    }

    public Boolean isRequired(Object obj){
        if(obj == null){
            return  true;
        }
        return false;
    }

    public Map templateSukses(Object objek){
        Map map = new HashMap();
        map.put("data", objek);
        map.put("message", "sukses");
        map.put("status", "200");
        return map;
    }

    public Map templateEror(Object objek){
        Map map = new HashMap();
        map.put("message", objek);
        map.put("status", "404");
        return map;
    }
    public Map notFound(Object objek){
        Map map = new HashMap();
        map.put("message", objek);
        map.put("status", "404");
        return map;
    }
}
