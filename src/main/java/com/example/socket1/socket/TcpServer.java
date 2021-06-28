package com.example.socket1.socket;

import lombok.Data;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@Data
@PropertySource("classpath:socket.properties")
public class TcpServer {

    @Value("${port}")
    private Integer port;

    @Value("${keepAlive}")
    private boolean keepAlive;

    private boolean started;
    private ServerSocket serverSocket;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);


    public static void main(String[] args) throws Exception {
        new TcpServer().start(6000);
    }

    public void start() {
        start(null);
    }

    public void start(Integer port) {
        log.info("port: 配置端口{}, 主动指定启动端口{}", this.port, port);
        try {
            serverSocket = new ServerSocket(port == null ? this.port : port);
            started = true;
            log.info("Socket服务已启动，占用端口： {}", serverSocket.getLocalPort());
        } catch (IOException e) {
            log.error("端口冲突,异常信息：{}", e);
            System.exit(0);
        }
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                log.info("服务启动...");
                log.info("客户端:" + socket + "已连接到服务器");
                socket.setKeepAlive(keepAlive);
                //socket.setSoTimeout(100000);
                BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
                byte[] buffer=new byte[1024];
                int length=0;
                while(bis.read(buffer,0,buffer.length)!=-1) {
                    String result = new String(buffer, 0, buffer.length);
                    System.out.println(result);
                }
//                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                String mess = br.readLine();
//                log.info(mess);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

