package com.example.serenakd.roundup.model;

public record Account(String accountUid,
                      String defaultCategory,
                      String accountType,
                      String currency,
                      String createdAt,
                      String name) {
}
