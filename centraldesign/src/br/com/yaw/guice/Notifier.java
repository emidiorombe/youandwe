package br.com.yaw.guice;

import com.google.inject.ImplementedBy;

public interface Notifier {
    public void sendNotification(String to);

}

