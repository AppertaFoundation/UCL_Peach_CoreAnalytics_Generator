package com.peach.connector;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainConsumer {
public static void main(String[] args) throws InterruptedException,IOException{
	try {			   
		    Consumer consumer= new Consumer();
			consumer.initialize();
			consumer.consume();
			consumer.shutdown();
						
		}
	catch(IOException ie){
			ie.printStackTrace();
		}
	}
}
