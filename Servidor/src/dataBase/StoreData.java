package dataBase;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.hibernate.Session;  
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;  
import org.hibernate.cfg.Configuration;


public class StoreData {  


	private static StoreData instance = null;
	private SessionFactory factory = null;

	private StoreData() {
		//creating configuration object  
		Configuration cfg=new Configuration();  

		URI dbUri;
		try {
			String dbUrl = System.getenv("DATABASE_URL");
			if(dbUrl == null){
				cfg.configure("dataBase/hibernate.cfg.xml");//populates the data of the configuration file  
			}else{
				cfg.configure("dataBase/hibernate.cfg.xml.sample");  
				dbUri = new URI(dbUrl);
				String username = dbUri.getUserInfo().split(":")[0];
				String password = dbUri.getUserInfo().split(":")[1];
				dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

				cfg.setProperty("hibernate.connection.username", username);
				cfg.setProperty("hibernate.connection.password", password); 
				cfg.setProperty("hibernate.connection.url", dbUrl); 
			}
			
			//Aca se agregan los configs de cada clase
			cfg.addResource("dataBase/question.hbm.xml");
			cfg.addResource("dataBase/user.hbm.xml");
			cfg.addResource("dataBase/course.hbm.xml");
			cfg.addResource("dataBase/category.hbm.xml");
			cfg.addResource("dataBase/courseCategory.hbm.xml");
			cfg.addResource("dataBase/courseUnity.hbm.xml");
			cfg.addResource("dataBase/courseSession.hbm.xml");
			cfg.addResource("dataBase/studentSession.hbm.xml");
			cfg.addResource("dataBase/answer.hbm.xml");


			//creating seession factory object  
			this.factory = cfg.buildSessionFactory();

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			System.out.println("AAA");  
			e.printStackTrace();
		}
	}

	public static StoreData getInstance() {
		if(instance == null){
			instance = new StoreData();
		}
		return instance;
	}

	public static void main(String[] args) throws IOException {  

		//creating configuration object  
		//    Configuration cfg=new Configuration();  
		//    cfg.configure("DataBase/hibernate.cfg.xml");//populates the data of the configuration file  
		//      
		//    //creating seession factory object  
		//    SessionFactory factory=cfg.buildSessionFactory();  
		//      
		//    //creating session object  
		//    Session session=factory.openSession();  
		//      
		//    //creating transaction object  
		//    Transaction t=session.beginTransaction();  

		//
		//list = null;
		/*UserMedicine e1= new UserMedicine() ;
    e1.setPatientId(1);
    e1.setMedicineId(1);
    e1.setDoctorId(4);
    e1.setObservations("20mg");  

    UserMedicine e2= new UserMedicine() ;
    e1.setPatientId(1);
    e1.setMedicineId(2);
    e1.setDoctorId(4);
    e1.setObservations("1mg"); 

    e1.save();  
    e2.save()*/;

    /*Medicine m2 = new Medicine();
	//m2.setId(2);
	m2.setName("Clonazepan");
	m2.setObservations("1mg");

	m2.save();*/

    /* UserTreatment m = new UserTreatment();
    //m.setDate(21/05/2016);
    m.setDoctorId(1);
    m.save();*/
//    MedicalShift m = new MedicalShift();
//    m.setDate("14-3-2016");
//    m.setTime("15:00");
//    m.save();
    //t.commit();//transaction is committed  
    //session.close();  

    System.out.println("successfully saved");  

	}  

	public static Object getById(Class<?> objectClass, int id){ 
		//creating session object  
		Session session = StoreData.getInstance().factory.openSession();  

		Object obj = null;

		try{
			obj = (Object) session.get(objectClass, id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return obj;
	}

	public static void save(Object obj) {
		Session session = StoreData.getInstance().factory.openSession();  

		Transaction t=session.beginTransaction();  
		try{
			session.saveOrUpdate(obj);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

	}

	public static void delete(Object obj) {
		Session session = StoreData.getInstance().factory.openSession();  

		Transaction t=session.beginTransaction();  
		try{
			session.delete(obj);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

	}

	public static List<?> getByField(Class<?> objectClass, String field, String value){ 
		//creating session object  
		Session session = StoreData.getInstance().factory.openSession();  

		List<Object> obj = null;
		String tableName = objectClass.getSimpleName();

		//hack para heroku
		if(System.getenv("DATABASE_URL") != null && tableName.compareTo("User") == 0){
			tableName = '"' + tableName + '"';
		}

		String query = "SELECT * FROM " + tableName + " WHERE " + field + " = '" + value + "'";
		try{
			return session.createSQLQuery(query).addEntity(objectClass).list();
		} catch (Exception e) {
			System.out.println(query);
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return obj;
	}
	
	public static List<?> getByTwoFields(Class<?> objectClass, String fieldOne, 
										String valueOne,String fieldTwo, String valueTwo){ 
		//creating session object  
		Session session = StoreData.getInstance().factory.openSession();  

		List<Object> obj = null;
		String tableName = objectClass.getSimpleName();

		//hack para heroku
		if(System.getenv("DATABASE_URL") != null && tableName.compareTo("User") == 0){
			tableName = '"' + tableName + '"';
		}

		String query = "SELECT * FROM " + tableName + " WHERE " + fieldOne + " = '" + valueOne + "'"
						+ " AND " + fieldTwo + " = '" + valueTwo + "'" ;
		try{
			return session.createSQLQuery(query).addEntity(objectClass).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return obj;
	}

	public static long getCount(Class<?> objectClass) {
		Session session = StoreData.getInstance().factory.openSession();  

		String tableName = objectClass.getSimpleName();

		//hack para heroku
		if(System.getenv("DATABASE_URL") != null && tableName.compareTo("User") == 0){
			tableName = '"' + tableName + '"';
		}

		String query = "SELECT COUNT(*) FROM " + tableName;
		try{
			int n = ((Long) session.createQuery(query).uniqueResult()).intValue(); 
			return n;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return 0;
	}

	public static List<?> getByCustomQuery(Class<?> objectClass, String query) {
		//creating session object  
		Session session = StoreData.getInstance().factory.openSession();  

		List<Object> obj = null;
		try{
			return session.createSQLQuery(query).addEntity(objectClass).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return obj;
	}

}  
