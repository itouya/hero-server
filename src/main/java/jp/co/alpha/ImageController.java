package jp.co.alpha;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins="*")
public class ImageController {
	@Autowired
	HeroService service;
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	String upload(
			@RequestParam("account_image") MultipartFile multipartFile,
			@RequestParam("id") String id,
			@RequestParam("name") String name) {
		System.out.println("filename: " + multipartFile.getOriginalFilename());
		Path imagePath = Paths.get(System.getProperty("user.home"), "images");
		Path filePath = Paths.get(imagePath.toString(), multipartFile.getOriginalFilename());
		try (OutputStream os = Files.newOutputStream(filePath, StandardOpenOption.CREATE)) {
			byte[] bytes = multipartFile.getBytes();
			os.write(bytes);
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString();
		}
		return "upload successfull.";
	}
	
	@RequestMapping(value="/images/{filename:.*}", method=RequestMethod.GET)
	ResponseEntity<ByteArrayResource>download(@PathVariable("filename") String filename) {
		System.out.println("download file: " + filename);
		Path imagePath = Paths.get(System.getProperty("user.home"), "images");
		Path filePath = Paths.get(imagePath.toString(), filename);
		File f = new File(filePath.toString());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Conrtrol", "no-chache, no-store, must-revalidate");
		headers.add("Pragma", "no-chache");
		headers.add("Expire", "0");
		try {
			ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(filePath)); 
			return ResponseEntity.ok()
								 .headers(headers)
								 .contentLength(f.length())
								 .contentType(MediaType.parseMediaType(getMimeType(filename)))
								 .body(resource);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String getMimeType(String filename) {
		StringTokenizer st = new StringTokenizer(filename, ".");
		String suffix = null;
		while(st.hasMoreTokens()) {
			suffix = st.nextToken();
		}
		System.out.println("suffix: " + suffix);
		if(suffix != null) {
			if(suffix.equalsIgnoreCase("png")) {
				return "image/png";
			}
			if(suffix.equalsIgnoreCase("jpg") || suffix.equalsIgnoreCase("jpeg")) {
				return "image/jpeg";
			}
		}
		return null;
	}
}