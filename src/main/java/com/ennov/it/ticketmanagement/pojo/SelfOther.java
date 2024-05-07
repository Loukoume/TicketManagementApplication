package com.ennov.it.ticketmanagement.pojo;

import lombok.Data;

@Data
public class SelfOther <T,N>{
    private T self;
    private N other;

    public SelfOther(T self, N other) {
        this.self = self;
        this.other = other;
    }
}

