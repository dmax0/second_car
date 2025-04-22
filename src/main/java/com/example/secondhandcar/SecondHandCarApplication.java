package com.example.secondhandcar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.awt.Desktop;
import java.net.URI;

/**
 * 二手车评估系统主应用程序
 */
@SpringBootApplication
public class SecondHandCarApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SecondHandCarApplication.class, args);
        
        // 启动后自动打开浏览器访问Swagger UI
        openSwaggerUI();
    }
    
    /**
     * 打开浏览器访问Swagger UI
     */
    private static void openSwaggerUI() {
        try {
            String url = "http://localhost:8080/api/swagger-ui.html";
            // 判断当前系统是否支持Desktop
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {

                Desktop.getDesktop().browse(new URI(url));
                System.out.println("已自动打开Swagger UI: " + url);
            }else {
                // 如果系统不支持Desktop，则使用Runtime打开浏览器
                String os = System.getProperty("os.name").toLowerCase();
                if (os.contains("windows")) {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
                }else {
                    Runtime.getRuntime().exec("open " + url);
                }
                
            }
        } catch (Exception e) {
            System.err.println("自动打开Swagger UI失败: " + e.getMessage());
        }
    }
} 