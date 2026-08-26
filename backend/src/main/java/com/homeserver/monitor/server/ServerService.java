package com.homeserver.monitor.server;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import com.sun.management.OperatingSystemMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.stereotype.Service;


import com.homeserver.monitor.server.dto.CpuUsageResponse;
import com.homeserver.monitor.server.dto.DiskUsageResponse;
import com.homeserver.monitor.server.dto.MemoryUsageResponse;
import com.homeserver.monitor.server.dto.ServerSummaryResponse;
import com.homeserver.monitor.server.dto.UptimeResponse;

@Service // 이 클래스가 실제로 작업, 로직을 담당
public class ServerService {
    
    public String getHostname() {
        try {
            return InetAddress.getLocalHost() // 현재 이 Spring Boot 프로그램이 실행되고 있는 컴퓨터의 정보를 가져옴
                .getHostName();
        } catch(UnknownHostException e) {
            return "UNKNOWN";
        }
    }

    public DiskUsageResponse getDiskUsage(){
        File disk = new File("/"); // 경로를 넣어서 위치를 가르키는 객체
        // 사용함으로써 : 존재 여부, 파일 / 폴더 여부 판별, 파일 이름, 경로, 파일의 크기 를 알 수 있음 

        long total = disk.getTotalSpace();
        long free = disk.getUsableSpace();
        long used = total - free;
        // 반환 타입이 long이기 때문에 받고 double로 전환함

        long bytesPerGb = 1024L * 1024 * 1024;

        double totalGb = round(((double) total / bytesPerGb));
        double freeGb = round(((double)free / bytesPerGb));
        double usedGb = round(((double)used / bytesPerGb));

        double usagePercent = total > 0 ? round(((double) used / total * 100)) : 0; 
        // 연산에 double이 있으면 자동으로 double로 연산함
        
        return new DiskUsageResponse(totalGb, usedGb, freeGb, usagePercent); 
        // 이로써 dto에서 응답을 보낼 수 있도록 만듦
    }

    public MemoryUsageResponse getMemoryUsage(){
        
        //ManagementFactory : Java가 현재 실행되고 있는 JVM이나 운영체제 정보를 가져올 수 있게 해주는 Java 표준 클래스
        //OperatingSystemMXBean : 현재 Java 프로그램이 실행되고 있는 운영체제(OS)의 정보를 조회할 수 있는 기능을 정의한 인터페이스
        OperatingSystemMXBean osBean = 
            (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            //형변환을 해줌으로써 다운캐스팅을 함. 더 구체적인 타입으로 만들어줌
        
        long total = osBean.getTotalMemorySize();
        long free = osBean.getFreeMemorySize();
        long used = total - free;
        
        double bytesPerGb = 1024.0 * 1024 * 1024;

        double usagePercent = total > 0 ? round((double)used / total * 100) : 0;

        double totalGb = round(total / bytesPerGb);
        double usedGb = round(used / bytesPerGb);
        double freeGb = round(free / bytesPerGb);

        return new MemoryUsageResponse(totalGb, usedGb, freeGb, usagePercent);
    }

    public UptimeResponse getUptime(){

        //RuntimeMXBean : 현재 실행 중인 JAVA프로그램의 런타임 정보를 조회할 수 있는 인터페이스
        RuntimeMXBean runtimeBean = 
            ManagementFactory.getRuntimeMXBean();

        long totalSeconds = runtimeBean.getUptime() / 1000; //밀리초(ms)단위로 반환하기 때문에 초(s)로 변환

        long days = totalSeconds / 86400;
        long hours = (totalSeconds % 86400) / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        return new UptimeResponse(days, hours, minutes, seconds);
    }

    public CpuUsageResponse getCpuLoad(){

        OperatingSystemMXBean osBean = 
            (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        
        double cpuLoad = osBean.getCpuLoad();
        double cpuLoadPercent = cpuLoad > 0 ? round(cpuLoad * 100.0) : 0;

        return new CpuUsageResponse(cpuLoadPercent);
    }

    public ServerSummaryResponse getSummary() {
        return new ServerSummaryResponse(
            getHostname(),
            getCpuLoad(),
            getMemoryUsage(),
            getDiskUsage(),
            getUptime()
        );
    }


    private double round(double value){
        return Math.round(value * 100) / 100.0;
    }
}
