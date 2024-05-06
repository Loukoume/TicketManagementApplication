package com.yaayi.gschool.filtre;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
@Data
@JsonIgnoreProperties
public class Pagination implements Serializable {
    private int nbreAfficher;
    private int currentPage;
    private int nbreOccurence;

}
