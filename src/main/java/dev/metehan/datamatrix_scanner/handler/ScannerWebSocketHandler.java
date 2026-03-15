package dev.metehan.datamatrix_scanner.handler;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ScannerWebSocketHandler extends TextWebSocketHandler {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    private final Set<WebSocketSession> sessions =
            Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        String ip = getClientIp(session);
        System.out.println("─────────────────────────────────────");
        System.out.println("  ✔ Bağlantı kuruldu");
        System.out.println("  IP      : " + ip);
        System.out.println("  Session : " + session.getId());
        System.out.println("  Toplam  : " + sessions.size() + " bağlı client");
        System.out.println("─────────────────────────────────────");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload().trim();
        String time = LocalDateTime.now().format(FORMATTER);
        String ip = getClientIp(session);

        System.out.println();
        System.out.println("  ▶ DataMatrix Okundu");
        System.out.println("  Zaman : " + time);
        System.out.println("  IP    : " + ip);
        System.out.println("  Değer : " + payload);
        System.out.println();

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        System.out.println("  ✖ Bağlantı kapandı - " + getClientIp(session) +
                " | Kalan: " + sessions.size());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.err.println("  ! Hata - " + getClientIp(session) + " : " + exception.getMessage());
        sessions.remove(session);
    }

    private String getClientIp(WebSocketSession session) {
        try {
            return session.getRemoteAddress() != null
                    ? session.getRemoteAddress().getAddress().getHostAddress()
                    : "bilinmiyor";
        } catch (Exception e) {
            return "bilinmiyor";
        }
    }
}
