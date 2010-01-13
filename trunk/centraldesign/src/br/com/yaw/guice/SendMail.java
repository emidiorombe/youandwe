package br.com.yaw.guice;

public class SendMail implements Notifier{

    public void sendNotification(String to){

          System.out.println("Notifying " + to);
    }
}
