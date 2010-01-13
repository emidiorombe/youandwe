package br.com.yaw.guice;

import com.google.inject.Inject;

public class Customer {
	@Inject
	 private Notifier notifier;

     public void changeSomething(){
         this.notifier.sendNotification("This Customer");
   }
           
}
