package com.example.serenakd.roundup.model;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Amount {
    private String currency;
    private int minorUnits;
}
