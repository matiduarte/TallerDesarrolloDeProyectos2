package actions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;

import entities.CourseSession;
import entities.CourseUnity;
import entities.User;
import service.ServiceResponse;

/**
 * Servlet implementation class SignInController
 */
public class SaveUnityVideoActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveUnityVideoActionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    } 
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {   
    	int unityId = Integer.valueOf(request.getParameter("unityId"));
    	final Part filePart = request.getPart("video");
    	
    	CourseUnity unity = CourseUnity.getById(unityId);
    	if(!(unityId > 0)){
    		//TODO: ver que hacer
    	}
    	
    	final String path = "WebContent/Files/CourseUnity/" + unityId + "/";
        final String urlPath = "Files/CourseUnity/" + unityId + "/";
        final String fileName = getFileName(filePart);
        if(!(fileName.compareTo("") == 0)){

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
            
            unity.setVideoUrl(urlPath + fileName);
            unity.save();
        }
    	
    	String json = new Gson().toJson(new  ServiceResponse(true, "Video agregado", ""));
    	response.setContentType("application/json");
    	response.setCharacterEncoding("UTF-8"); 
    	response.getWriter().write(json); 
    	
    }
    
    private String getFileName(final Part part) {
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
