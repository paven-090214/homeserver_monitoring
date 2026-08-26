package com.homeserver.monitor.server.dto;

public record UptimeResponse (
    long days,
    long hours,
    long minutes,
    long seconds
){
    
}
