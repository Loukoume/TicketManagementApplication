package com.yaayi.gschool.filtre;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties
public class Where implements Serializable {
    private String slug;
    private String name;
    private String value;
    private String action;
    private String condition;

    public Where(String name, String value, String action) {
        this.name = name;
        this.value = value;
        this.action = action;
    }

    private String[] splite(){
       // System.out.println("====splite============="+name);
        return this.name.split(":");
    }
    public String getObjectObj(){
        return this.splite()[this.splite().length-2];
    }
    public String getObjectPropriete(){
        return this.splite()[this.splite().length-1];
    }

}
