package com.example.serenakd.roundup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
@Getter
@ToString
public class Account {
    private String accountUid;
    private String defaultCategory;
    private String accountType;
    private String currency;
    private String createdAt;
    private String name;
}
