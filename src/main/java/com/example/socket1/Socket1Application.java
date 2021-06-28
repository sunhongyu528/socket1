package com.example.socket1;

import com.example.socket1.socket.TcpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Socket1Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Socket1Application.class, args);
        applicationContext.getBean(TcpServer.class).start();
    }

}
