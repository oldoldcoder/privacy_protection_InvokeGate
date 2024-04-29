package com.xd.hufei.utils;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class DataBaseUrlParser {
    public static DatabaseInfo parseDatabaseUrl(String databaseUrl) {
        try {
            URI uri = new URI(databaseUrl);
            String userInfo = uri.getUserInfo();
            String username = null;
            String password = null;
            if (userInfo != null) {
                String[] parts = userInfo.split(":");
                if (parts.length == 2) {

                    username = parts[0];
                    password = parts[1];
                }
            }
            String host = uri.getHost();
            int port = uri.getPort();
            String path = uri.getPath();
            String database = null;
            if (path != null && path.length() > 1) {
                database = path.substring(1); // Remove leading slash
            }
            return new DatabaseInfo(uri.toString(), username, password, host, port, database);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
        }

    public static class DatabaseInfo {
        private final String url;
        private final String username;
        private final String password;
        private final String host;
        private final int port;
        private final String database;

        public DatabaseInfo(String url, String username, String password, String host, int port, String database) {
            this.url = url;
            this.username = username;
            this.password = password;
            this.host = host;
            this.port = port;
            this.database = database;
        }

        public String getUrl() {
            return url;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }

        public String getDatabase() {
            return database;
        }
    }
}
