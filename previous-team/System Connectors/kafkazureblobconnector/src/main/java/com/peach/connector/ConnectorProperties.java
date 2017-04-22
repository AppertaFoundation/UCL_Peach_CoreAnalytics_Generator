package com.peach.connector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.kafka.common.serialization.StringDeserializer;
public class ConnectorProperties {
private Properties prop=new Properties();
OutputStream os=null;
String path="/home/peach/config.properties";

//Create a properties file
public void loadProperties()
{
	try {
		os=new FileOutputStream(path);
		prop.setProperty("connectionstring", "DefaultEndpointsProtocol=http;"+
        "AccountName=narminstorageaccount;"+ 
        "AccountKey=8c38ZOyvQxb8HhWHHjjUnteiRXiPS8psaYqs2HzGggyZKPLL1nmi/YQd1+vXuKLJQSJsOvUVwmFrJAXd6zVtRg==");
		prop.setProperty("containername","topiccontainer");
		prop.store(os,"azureblogstorageproperties");
		
	    }
	    catch (IOException e) {
		e.printStackTrace();
	
	    }
	    finally{
	    	 if (os!=null)
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    }
	
	}
//Get Azure Storage Connection String
public String getConnectionString()
{
	Properties prop= new Properties();
	InputStream input=null;
	try {
		input=new FileInputStream(path);
		prop.load(input);
	    return prop.getProperty("connectionstring").toString();
	   
	} catch (IOException e) {
		e.printStackTrace();
	}
	return(null);
}
//Get a Blob Storage container name
public String getContainername()
{
   Properties prop= new Properties();
   InputStream input=null;
   try {
	input=new FileInputStream(path);   
	prop.load(input);
    return prop.getProperty("containername").toString();
   } catch (IOException e) {
	e.printStackTrace();
}
return null;

}

}
