package com.example.serenakd.roundup.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class Utilities {

    public static HttpEntity<String> createHttpHeaders(String bearerToken, HttpHeaders httpHeaders) {
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        httpHeaders.set(AUTHORIZATION, bearerToken);
        return new HttpEntity<>(httpHeaders);
    }
}
