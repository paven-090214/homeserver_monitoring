package com.homeserver.monitor.server.dto;

public record MemoryUsageResponse (
    double totalGb,
    double usedGb,
    double freeGb,
    double usagePercent
){
    
}
