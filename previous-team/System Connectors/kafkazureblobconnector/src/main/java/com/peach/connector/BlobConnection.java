package com.peach.connector;
import java.io.IOException;

import java.net.URISyntaxException;
import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;
import java.util.HashMap;
import java.util.Map;
public class BlobConnection {
	private CloudStorageAccount storageAccount=null;
	private CloudBlobClient blobClient=null;
	public  CloudBlobContainer container=null;
	private Map<String, String> topictocontent = new HashMap<String, String>();
	private ConnectorProperties cp= new ConnectorProperties();
	
	public BlobConnection(Map<String, String> topictocontent)
	{
		this.topictocontent=topictocontent;
	}
	// Create Blob container
	public  void createContainer(){
		try {
      
		storageAccount = CloudStorageAccount.parse(cp.getConnectionString());
		blobClient = storageAccount.createCloudBlobClient();
		container = blobClient.getContainerReference(cp.getContainername());
		container.createIfNotExists();  
		
        //System.out.println("Container");
		}

		catch(Exception e)
		{
         e.printStackTrace();
		}
		}
	//Create Blob
	public CloudAppendBlob createBlob(String blobname) throws URISyntaxException, StorageException
	{
	CloudAppendBlob appendBlob=container.getAppendBlobReference(blobname+".log");
	if(appendBlob.exists()==false)
	appendBlob.createOrReplace();
	
	return appendBlob;
	}
	//Append to the end of existing Blob
	public void appendBlob(){
		createContainer();
		for(Map.Entry<String, String> record: topictocontent.entrySet()){
	    try {
			CloudAppendBlob appendBlob=createBlob(record.getKey());
			
			try {
				appendBlob.appendText(record.getValue());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		   } catch (URISyntaxException e) {
			e.printStackTrace();
		  } 
	        catch (StorageException e) {
			e.printStackTrace();
		   }
		}
	}
	public CloudBlobContainer getContainer()
	{
		return this.container;
	}
	public CloudAppendBlob getAppenBlob()
	{
		return null;
	}
	public CloudStorageAccount getStorageAccount()
	{
		return this.storageAccount;
	}
	
}
