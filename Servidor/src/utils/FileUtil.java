package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.Part;

public class FileUtil{

	public static void saveFile(final String path, final Part filePart, final String fileName) throws IOException {
		OutputStream out = null;
		InputStream filecontent = null;
		
		final File parent = new File(path);
		parent.mkdirs();

		try {
		    out = new FileOutputStream(new File(path, fileName));
		    filecontent = filePart.getInputStream();

		    int read = 0;
		    final byte[] bytes = new byte[1024];

		    while ((read = filecontent.read(bytes)) != -1) {
		        out.write(bytes, 0, read);
		    }
		    //writer.println("New file " + fileName + " created at " + path);
		} catch (FileNotFoundException fne) {
//                writer.println("You either did not specify a file to upload or are "
//                        + "trying to upload a file to a protected or nonexistent "
//                        + "location.");
//                writer.println("<br/> ERROR: " + fne.getMessage());
		} finally {
		    if (out != null) {
		        out.close();
		    }
		    if (filecontent != null) {
		        filecontent.close();
		    }
		}
	}
    
    public static String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}