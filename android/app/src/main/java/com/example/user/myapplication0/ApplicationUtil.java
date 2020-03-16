package com.example.user.myapplication0;
import android.app.Application;
import java.net.Socket;

public class ApplicationUtil extends Application {

    Socket socket = null;
    public Socket getSocket(){
        return socket;
    }
    public void setSocket(Socket socket){
        this.socket = socket;
    }
}
