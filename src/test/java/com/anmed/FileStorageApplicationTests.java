package com.anmed;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileStorageApplicationTests {

	@Test
	public void contextLoads() throws IOException {
		SpringApplication.run(FileStorageApplication.class);
		Path path = Paths.get("src", "test", "resources", "test.txt");
		File file = path.toFile();
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost("http://localhost:8080/upload");
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addTextBody("field1", "yes", ContentType.TEXT_PLAIN);


		builder.addBinaryBody(
				"file",
				new FileInputStream(file),
				ContentType.APPLICATION_OCTET_STREAM,
				file.getName()
		);

		HttpEntity multipart = builder.build();
		post.setEntity(multipart);
		CloseableHttpResponse response = httpclient.execute(post);
		HttpEntity responseEntity = response.getEntity();
		String fileCreated = IOUtils.toString(responseEntity.getContent());
		fileCreated = fileCreated.replaceFirst("created file with id ", "");
		Assert.assertTrue(fileCreated.length() > 0);
	}

}
