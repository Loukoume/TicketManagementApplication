package com.yaayi.gschool.filtre;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties
public class Sort implements Serializable {

    private String attribute;
    private String order;

    public boolean oui() {
        return attribute != null && order != null;
    }
}
