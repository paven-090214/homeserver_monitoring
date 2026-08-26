package com.homeserver.monitor.server.dto;

public record DiskUsageResponse (
    double total, // 총 사용량
    double used, // 현재 사용량
    double free, // 남은 용량 
    double usagePercent // 사용량의 %
){
    
}
