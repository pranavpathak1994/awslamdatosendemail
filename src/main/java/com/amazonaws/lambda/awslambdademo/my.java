package com.amazonaws.lambda.awslambdademo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

/**
 * @author Pranav - 11-May-2017 6:45:53 pm
 *
 */
public class my {
	
	public static void main(String[] args) {
		ArrayList<File> files = new ArrayList<>();
		
//		for (File file : files) {
//			System.out.println(file.getAbsolutePath());
//		}
		
		AWSCredentials credentials = new ProfileCredentialsProvider().getCredentials();
		AmazonS3 s3client = new AmazonS3Client(credentials);
		String bucketName=s3client.listBuckets().get(1).getName();
		my my= new my();
		my.listf("/home/wt-a-c-18/Documents/Java/My/MyProfile", files,bucketName,s3client,null);
	}
	
	public void listf(String directoryName, ArrayList<File> files,String bucketName, AmazonS3 client,String folder) {
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	            files.add(file);
	            String filename=null;
	            if(folder==null){
	            	filename=file.getName();
	            }else{
	            	filename=folder+"/"+file.getName();
	            }
	            client.putObject(new PutObjectRequest(bucketName, filename, 
	            		new File(file.getAbsolutePath())).withCannedAcl(CannedAccessControlList.PublicRead));
	        } else if (file.isDirectory()) {
//	        	System.out.println(file.getAbsolutePath());
	        	String[] arr=file.getAbsolutePath().split("MyProfile/");
	        	
	        	createFolder(bucketName,arr[1],client);
	            listf(file.getAbsolutePath(), files,bucketName,client,arr[1]);
	        }
	    }
	}
	
	public static void createFolder(String bucketName, String folderName, AmazonS3 client) {
		// create meta-data for your folder and set content-length to 0
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);
		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
		// create a PutObjectRequest passing the folder name suffixed by /
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
					folderName, emptyContent, metadata).withCannedAcl(CannedAccessControlList.PublicRead);
		// send request to S3 to create folder
		client.putObject(putObjectRequest);
	}

}
