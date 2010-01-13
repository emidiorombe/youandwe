package br.com.yaw.guice;

import br.com.yaw.ioc.IoCBinder;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

    public static void main(String[] args) {
    	    Injector injector = Guice.createInjector(new IoCBinder());
    	    Customer customer = injector.getInstance(Customer.class);
    	    customer.changeSomething(); // the customer must be notified about the change

    }
}