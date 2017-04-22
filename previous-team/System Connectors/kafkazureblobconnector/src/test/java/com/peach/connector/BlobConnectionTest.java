package com.peach.connector;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.CharSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudAppendBlob;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.ListBlobItem;


public class BlobConnectionTest {
    private BlobConnection bc;
    private Map<String, String> topictosubscribe=new HashMap<String, String>();
    private ConnectorProperties cp= new ConnectorProperties();
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		topictosubscribe.put("testtopic1","testtopic1 testvalue1\ntesttopic1 testvalue2");
		topictosubscribe.put("testtopic2","testtopic2 testvalue1\ntesttopic2 testvalue2");
	    bc = new BlobConnection(topictosubscribe);
	     
	}

	@After
	public void tearDown() throws Exception {
		CloudStorageAccount storageAccount = CloudStorageAccount.parse(cp.getConnectionString());
		CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
		CloudBlobContainer container = blobClient.getContainerReference("topiccontainer");
		CloudAppendBlob blob1 = container.getAppendBlobReference("testtopic1.log");
		CloudAppendBlob blob2 = container.getAppendBlobReference("testtopic2.log");
		blob1.deleteIfExists();
		blob2.deleteIfExists();
	}

	@Test()
	public void testCreateContainer()  {
		    bc.createContainer();
			try {
				assertTrue(bc.getContainer().exists());
			} catch (StorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		assertEquals("topiccontainer",bc.getContainer().getName());
		assertNotEquals("This is not a container name", bc.getContainer().getName());
		
	}

	@Test
	public void testCreateBlob() {
		String topicname="testtopic1";
		CloudAppendBlob appendBlob = null;
		try {
			bc.createContainer();
			appendBlob = bc.createBlob(topicname);
			assertTrue(appendBlob.exists());
		} catch (URISyntaxException | StorageException e) {
			e.printStackTrace();
		}
	}
    	@Test
	public void testAppendBlob() {
		bc.appendBlob();
        int count=0;
		for(ListBlobItem blobitem: bc.getContainer().listBlobs("testtopic"))
		{
			count++;
			if(blobitem instanceof CloudBlob)
			{
				
				CloudBlob blob=(CloudBlob)blobitem;
				ByteArrayOutputStream stream=new ByteArrayOutputStream();
			    try {
					blob.download(stream);
				} catch (StorageException e) {
					e.printStackTrace();
				}
			   
			    String content=new String(stream.toByteArray(),Charset.defaultCharset());
			    if(count==1)
			    	assertEquals(content,"testtopic1 testvalue1\ntesttopic1 testvalue2");
			    else if (count==2)
			    	assertEquals(content,"testtopic2 testvalue1\ntesttopic2 testvalue2");
				
			}
		}
		
	}


}
