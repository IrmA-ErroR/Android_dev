package com.mirea.kabanovasvetlana.lesson7;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TimeClient {

    public static String getTimeFromServer() throws Exception {
        String server = "time.nist.gov";
        int connectionTimeout = 5000;
        int readTimeout = 5000;
        int port = 14;
        Socket socket = null;
        BufferedReader reader = null;

        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(server, port), connectionTimeout);
            socket.setSoTimeout(readTimeout); // timeout на чтение

            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line).append("\n");
            }

            return response.toString().trim();
        } catch (UnknownHostException e) {
            throw new Exception("Не удалось найти хост: " + server, e);
        } catch (java.net.SocketTimeoutException e) {
            throw new Exception("Истекло время ожидания соединения или чтения с сервера " + server, e);
        } catch (Exception e) {
            throw new Exception("Ошибка при подключении к серверу " + server, e);
        } finally {
            if (reader != null) reader.close();
            if (socket != null && !socket.isClosed()) socket.close();
        }
    }
}