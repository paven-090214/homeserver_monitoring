package com.homeserver.monitor.server.dto;

public record ServerSummaryResponse (
    String hostname,
    CpuUsageResponse cpu,
    MemoryUsageResponse memory,
    DiskUsageResponse disk,
    UptimeResponse uptime
){
    
}
