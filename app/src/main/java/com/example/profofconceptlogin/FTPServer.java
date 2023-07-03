package com.example.profofconceptlogin;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.util.ArrayList;
import java.util.List;

public class FTPServer {
    private static final int FTP_PORT = 6560;

    private FtpServer ftpServer;

    public void startServer() {
        FtpServerFactory serverFactory = new FtpServerFactory();

        // Configure listener
        ListenerFactory listenerFactory = new ListenerFactory();
        listenerFactory.setPort(FTP_PORT);
        serverFactory.addListener("default", listenerFactory.createListener());

        // Configure user manager
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        UserManager userManager = userManagerFactory.createUserManager();
        BaseUser user = new BaseUser();
        user.setName("ftpuser");
        user.setPassword("password");
        user.setHomeDirectory("/sdcard"); // Set the home directory for the user

        // Grant write permission to the user
        Authority writePermission = new WritePermission();
        List<Authority> authorities = new ArrayList<>();
        authorities.add(writePermission);
        user.setAuthorities(authorities);

        try {
            userManager.save(user);
        } catch (FtpException e) {
            e.printStackTrace();
        }
        serverFactory.setUserManager(userManager);

        // Create FTP server
        ftpServer = serverFactory.createServer();

        // Start the server
        try {
            ftpServer.start();
            System.out.println("FTP server started on port " + FTP_PORT);
        } catch (FtpException e) {
            System.out.println("Failed to start the FTP server: " + e.getMessage());
        }
    }

    public void stopServer() {
        if (ftpServer != null && !ftpServer.isStopped()) {
            ftpServer.stop();
            System.out.println("FTP server stopped");
        }
    }
}