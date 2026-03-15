package dev.metehan.datamatrix_scanner.config;


import dev.metehan.datamatrix_scanner.handler.ScannerWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Bean
    public ScannerWebSocketHandler scannerWebSocketHandler() {
        return new ScannerWebSocketHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(scannerWebSocketHandler(), "/ws/scanner")
                .setAllowedOrigins("*");
    }
}
