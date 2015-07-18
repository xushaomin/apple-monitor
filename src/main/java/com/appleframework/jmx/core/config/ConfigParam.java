package com.appleframework.jmx.core.config;

/**
 *
 * date:  Jun 13, 2004
 * @author	Rakesh Kalra
 */
public class ConfigParam {

    private String name;
    private String displayName;
    private String defaultValue;

    public ConfigParam(String name, String displayName){
        this.name = name;
        this.displayName = displayName;
    }

    public ConfigParam(String name,
                       String displayName,
                       String defaultValue){
        this(name, displayName);
        this.defaultValue = defaultValue;
    }

    public String getName(){
        return name;
    }

    public String getDisplayName(){
        return displayName;
    }

    public String getDefaultValue(){
        return defaultValue;
    }

    public int hashCode(){
        return name.hashCode();
    }

    public boolean equals(Object obj){
        if(obj instanceof ConfigParam){
            ConfigParam param = (ConfigParam)obj;
            if(this.name.equals(param.name)){
                return true;
            }
        }
        return false;
    }
}
