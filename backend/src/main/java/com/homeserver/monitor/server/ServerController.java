package com.homeserver.monitor.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homeserver.monitor.server.dto.CpuUsageResponse;
import com.homeserver.monitor.server.dto.DiskUsageResponse;
import com.homeserver.monitor.server.dto.MemoryUsageResponse;
import com.homeserver.monitor.server.dto.ServerSummaryResponse;
import com.homeserver.monitor.server.dto.UptimeResponse;
@RestController // 이 클래스는 웹 요청을 처리하는 컨트롤러임을 명시
@RequestMapping("/api/server") // 컨트롤러의 기본 주소
public class ServerController {
    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }
    
    @GetMapping("/hostname") // Get /api/server/hostname 요청을 처리하는 메서드
    public String getHostname() {
        return serverService.getHostname();
    }

    @GetMapping("disk")
    public DiskUsageResponse getDiskUsage() {
        return serverService.getDiskUsage();
    }

    @GetMapping("/memory")
    public MemoryUsageResponse getMemoryUsage(){
        return serverService.getMemoryUsage();
    }

    @GetMapping("/uptime")
    public UptimeResponse getUptime() {
        return serverService.getUptime();
    }

    @GetMapping("/cpu")
    public CpuUsageResponse getCpuLoad(){
        return serverService.getCpuLoad();
    }

    @GetMapping("/summary")
    public ServerSummaryResponse getSummary(){
        return serverService.getSummary();
    }
}
