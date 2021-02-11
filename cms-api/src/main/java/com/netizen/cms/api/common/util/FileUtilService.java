package com.netizen.cms.api.common.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class FileUtilService {


	public String uploadImgHeightWidth(String folderName, String imageName, MultipartFile uploadFile, int width,
			int height) {
		String message = "";
		String fileType = uploadFile.getContentType().substring(6, uploadFile.getContentType().length());
		if (fileType.equals("jpeg") || fileType.equals("png") || fileType.equals("jpg")) {
			String filePath = "";

			filePath = ApplicationUtils.filePath(folderName) + imageName;

			try {
				InputStream is;
				try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
					is = uploadFile.getInputStream();
					java.awt.Image img = null;
					img = ImageIO.read(is);
					if (fileType.equals("png")) {
						BufferedImage buffImage = (BufferedImage) img;
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						ImageIO.write(buffImage, "png", os);
						is = new ByteArrayInputStream(os.toByteArray());
					} else {
						BufferedImage buffImage = resizeImage(img, width, height);
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						ImageIO.write(buffImage, "jpg", os);
						is = new ByteArrayInputStream(os.toByteArray());
					}

					int BUFFER_SIZE = 8192;
					byte[] buffer = new byte[BUFFER_SIZE];
					int a;
					while (true) {
						a = is.read(buffer);
						if (a < 0) {
							break;
						}
						fos.write(buffer, 0, a);
						fos.flush();
					}
				}

				is.close();
			} catch (IOException e) {
			}

			message = "Image Uploaded Successfully...";

		} else {
			message = "Format doesn't match...";
		}
		return message;
	}

	public String uploadImgHeightWidth(String folderName, String imageName, String fileType, byte[] fileContent,
			int width, int height) {
		String message = "";

		if (fileType.equals("jpeg") || fileType.equals("png") || fileType.equals("jpg")) {
			String filePath = "";

			filePath = ApplicationUtils.filePath(folderName) + imageName;

			try {
				InputStream is;
				try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
					is = new ByteArrayInputStream(fileContent);
					java.awt.Image img = null;
					img = ImageIO.read(is);
					if (fileType.equals("png")) {
						BufferedImage buffImage = (BufferedImage) img;
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						ImageIO.write(buffImage, "png", os);
						is = new ByteArrayInputStream(os.toByteArray());
					} else {
						BufferedImage buffImage = resizeImage(img, width, height);
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						ImageIO.write(buffImage, "jpg", os);
						is = new ByteArrayInputStream(os.toByteArray());
					}

					int BUFFER_SIZE = 8192;
					byte[] buffer = new byte[BUFFER_SIZE];
					int a;
					while (true) {
						a = is.read(buffer);
						if (a < 0) {
							break;
						}
						fos.write(buffer, 0, a);
						fos.flush();
					}
				}

				is.close();
			} catch (IOException e) {
			}

			message = "Image Uploaded Successfully...";

		} else {
			message = "Format doesn't match...";
		}
		return message;
	}

	public static BufferedImage resizeImage(final java.awt.Image image, int width, int height) {
		final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setComposite(AlphaComposite.Src);
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		graphics2D.drawImage(image, 0, 0, width, height, null);
		graphics2D.dispose();
		return bufferedImage;
	}

	public String imageUpload(String folderName, String imageName, MultipartFile uploadFile) {

		String message = "";
		String fileType = uploadFile.getContentType().substring(6, uploadFile.getContentType().length());

		if (fileType.equals("jpeg") || fileType.equals("png") || fileType.equals("jpg")) {

			String filePath = "";
			filePath = ApplicationUtils.filePath(folderName) + imageName;

			try {

				InputStream is;

				try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
					is = uploadFile.getInputStream();
					java.awt.Image img = null;
					img = ImageIO.read(is);

					BufferedImage buffImage = (BufferedImage) img;
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					ImageIO.write(buffImage, fileType, os);
					is = new ByteArrayInputStream(os.toByteArray());

					int BUFFER_SIZE = 8192;
					byte[] buffer = new byte[BUFFER_SIZE];
					int a;
					while (true) {
						a = is.read(buffer);
						if (a < 0) {
							break;
						}
						fos.write(buffer, 0, a);
						fos.flush();
					}
				}

				is.close();
			} catch (IOException e) {
			}
			message = "Image Uploaded Successfully...";
		} else {
			message = "Format doesn't match...";
		}
		return message;
	}

	public String fileUpload(String folderName, String fileName, MultipartFile uploadFile) {
		String message = "";
		String[] extention = provideFileExtension(uploadFile);
		if (extention[1].equals("pdf") || extention[1].equals("png") || extention[1].equals("jpg")
				|| extention[1].equals("jpeg") || extention[1].equals("ppt") || extention[1].equals("doc")
				|| extention[1].equals("docx") || extention[1].equals("txt") || extention[1].equals("plain")
				|| extention[1].equals("csv") || extention[1].equals("xlsx") || extention[1].equals("ppt")
				|| extention[1].equals("xlsx")) {

			String filePath = "";
			filePath = ApplicationUtils.filePath(folderName) + fileName;

			if (!uploadFile.isEmpty()) {
				if (uploadFile.getSize() <= 512000) {
					try {
						byte[] bytes = uploadFile.getBytes();
						Path path = Paths.get(filePath);
						Files.write(path, bytes);
					} catch (IOException e) {

					}
				} else {
					message = "File is too big...";
				}
			} else {
				message = "File is empty...";
			}
		} else {
			message = "Format Doesn't Match...";
		}
		return message;
	}

	public String[] provideFileExtension(MultipartFile uploadFile) {
		String extention[] = uploadFile.getContentType().split("/");
		switch (extention[1]) {
		case "vnd.ms-excel":
			extention[1] = "csv";
			break;
		case "vnd.openxmlformats-officedocument.spreadsheetml.sheet":
			extention[1] = "xlsx";
			break;
		case "vnd.openxmlformats-officedocument.presentationml.presentation":
			extention[1] = "ppt";
		case "plain":
			extention[1] = "txt";

		case "vnd.openxmlformats-officedocument.wordprocessingml.document":
			extention[1] = "docx";
			break;
		}
		return extention;
	}

	public byte[] fetchFileInBase64Encoding(String folderName, String fileName) throws IOException {

		String filePath = "";

		filePath = ApplicationUtils.filePath(folderName) + fileName;

		File serverFile = new File(filePath);

		return Base64.getEncoder().encode(Files.readAllBytes(serverFile.toPath()));
	}

	public byte[] fetchFileInByte(String folderName, String fileName) throws Exception {

		String filePath = "";

		filePath = ApplicationUtils.filePath(folderName) + fileName;

		try {
			Path path = new File(filePath).toPath();

			byte[] file = Files.readAllBytes(path);

			return file;

		} catch (Exception e) {

			return new byte[0];
		}
	}

	
	public synchronized String writeFileToPath(String folderName, byte[] content, String fileName) {

		String message = "";

		String fileFullPath = ApplicationUtils.filePath(folderName) + fileName;
		System.out.println("Image Path :"+fileFullPath);

		File file = new File(fileFullPath);
		/*
		 * if (!file.exists()) { file.getParentFile().mkdirs(); }
		 */
		try {
			try (FileOutputStream fos = new FileOutputStream(file)) {
				fos.write(content);
				fos.flush();
			}

		} catch (FileNotFoundException e) {
			message = "No File found in path: " + fileFullPath;
			System.out.println(e);
		} catch (IOException e) {
			message = "No File found in path: " + fileFullPath;
			System.out.println(e);
		}
		return message;
	}

	public Optional<String> provideFileExtension(String filename) {
		return Optional.ofNullable(filename).filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}
	
	
	public String deleteFile(String folderName, String fileName) {
	        
	    	String message = "";
	 
	        String filePath = "";
	        
	        filePath = ApplicationUtils.filePath(folderName) + fileName;
	            	
	        File fdelete = new File(filePath);
	            
	            if (fdelete.exists()) {
	                if (fdelete.delete()) {
	                	message="File Successfully Deleted";
	                } else {
	                	message="File not Deleted";
	                }
	            }
	                
	      
	        return message;
	    }

}
