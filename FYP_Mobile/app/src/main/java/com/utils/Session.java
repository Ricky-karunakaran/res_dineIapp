
package com.utils;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private final Map<String, Object> attributes = new HashMap<>();
    
    public void setAttributes(String key, Object value){
        attributes.put(key, value);
    }
    public Object getAttributes(String key){
        return attributes.get(key);
    }
}
