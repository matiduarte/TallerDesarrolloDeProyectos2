package daemon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import entities.Course;
import entities.CourseSession;
import entities.CourseUnity;
import entities.NotificationSent;

public class Daemon implements Runnable {
	
	private String apiKey = "AIzaSyAPy8S5yDjd1v0kubeHwrGS7l8rHiytrOU";
	
    public void run() {
    	while (true) {
			checkNotificationsToSend();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }

    private void checkNotificationsToSend() {
    	List<Course> courses = Course.getAllActive(0);
    	for (Course course : courses) {
    		CourseSession session = course.getActiveSession();
    		if(session != null){
				String topic = "/topics/course_" + course.getId() + "_" + session.getId();

    			//If course begins
    			if(session.startsToday()){
    				String key = "/course_" + course.getId() + "_" + session.getId() + "_begin";
    				if(!notificationSent(key)){
    					sendNotification("El curso " + course.getName() + " ha comenzado!", "FIUBA Cursos", topic);
    					NotificationSent ns = new NotificationSent();
    					ns.setNotification(key);
    					ns.save();
    					
    					System.out.println("Notificacion: Comienzo curso");
    				}
    			}
    			
    			//If exam is available
    			CourseUnity unity = course.getUnityWithExam();
    			if(unity != null){
    				String key = "/course_" + course.getId() + "_" + session.getId() + "_examn_unity_" + unity.getId();
    				if(!notificationSent(key)){
    					sendNotification("El examen de la unidad " + unity.getName() + " del curso " + course.getName() + " ya se encuentra disponible! Recuerde que tiene una semana para realizarlo.", "FIUBA Cursos", topic);
    					NotificationSent ns = new NotificationSent();
    					ns.setNotification(key);
    					ns.save();
    					System.out.println("Notificacion: Examen disponible");
    				}
    			}
    			
    			//If new unity is available
    			CourseUnity newUnity = course.getNewUnity();
    			if(newUnity != null){
    				String key = "/course_" + course.getId() + "_" + session.getId() + "_new_unity_" + newUnity.getId();
    				if(!notificationSent(key)){
    					sendNotification("La unidad " + newUnity.getName() + " se encuentra disponible", "FIUBA Cursos", topic);
    					NotificationSent ns = new NotificationSent();
    					ns.setNotification(key);
    					ns.save();
    					
    					System.out.println("Notificacion: Nueva unidad disponible");
    				}
    			}
    			
    			//If final exam is available
    			boolean finalExamAvailable = course.isFinalExamEvailable(session);
    			if(finalExamAvailable){
    				String key = "/course_" + course.getId() + "_" + session.getId() + "_final_exam";
    				if(!notificationSent(key)){
    					sendNotification("El examen final del curso " + course.getName() + " ya se encuentra disponible! Recuerde que tiene una semana para realizarlo.", "FIUBA Cursos", topic);
    					NotificationSent ns = new NotificationSent();
    					ns.setNotification(key);
    					ns.save();
    					System.out.println("Notificacion: Examen Final disponible");
    				}
    			}
    		}
		}
    }
    
    private boolean notificationSent(String key) {
    	List<NotificationSent> notifications = NotificationSent.getByKey(key);
    	if(notifications != null && !notifications.isEmpty()){
    		return true;
    	}
    	
    	return false;
    }
    
	private void sendNotification(String message, String title, String receptor) {
		HttpURLConnection con;
		try {
		    
			JSONObject data = new JSONObject();
			data.put("to", receptor);
			
			JSONObject msgObject = new JSONObject();
		    msgObject.put("body", message);
		    msgObject.put("title", title);
		    msgObject.put("icon", "ic_school");
			
		    data.put("notification", msgObject);
		    
			con = (HttpURLConnection) new URL("https://gcm-http.googleapis.com/gcm/send").openConnection();
			con.setRequestProperty("Content-type", "application/json");
			con.setRequestProperty("Authorization", "key=" + this.apiKey);			
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			
			 // Send GCM message content.
            OutputStream outputStream = con.getOutputStream();
            outputStream.write(data.toString().getBytes());

            // Read GCM response.
            InputStream inputStream = con.getInputStream();
            String resp = this.streamToString(inputStream);
            System.out.println(resp);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static String streamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}