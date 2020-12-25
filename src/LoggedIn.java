import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LoggedIn {
    
public static void main(String[]args){
    
    LoggingOut();
    
}


//method for login to existing account
public static void LoggingIn(){
    
    Scanner s=new Scanner(System.in);
        
        try{
              
        //create connection to database
        String path="jdbc:mysql://localhost:3306/yuutube";
        String userid="root";
        String password="";
        
        Connection con=DriverManager.getConnection(path,userid,password);

                System.out.println("-----------------------------------------------"); 
                System.out.println("\t\tLog In Page");
               
                System.out.println("------------------------------------------------");
                
                
                
                System.out.print("Email address: ");
                String user_email=s.next();
                boolean checku=RegistrationForm.check_user_email(user_email);
 
                while(true){
                if(checku==false){
              
                
                        System.out.println("This email address does not have an account with us.Would you like to register a new account?(Y/N)");
                        char yesno=s.next().charAt(0);
                    
                    if (yesno=='Y'){
                        RegistrationForm.Register();
                      
                    }
                    else if(yesno=='N')    {                 
                        System.out.println("Back To Homepage");
                        break;
                    }
                }
                
                else
     
                s.nextLine();  
                System.out.print("Password: ");
                String userpassword=s.nextLine();
                boolean checkpw=RegistrationForm.check_user_password(userpassword);
                
                while(true){
                    
                    checkpw=RegistrationForm.check_user_password(userpassword);

                if (checkpw==false){
                    
                    System.out.println("Incorrect password. Please try again");
                    userpassword=s.nextLine();
                    checkpw=RegistrationForm.check_user_password(userpassword);
 
                }
                
               
                else if (checkpw=true) {
                    
                String mysql="SELECT username FROM userinfo where user_email='"+user_email+"' and userpassword='"+userpassword+"';";
                String column="username";
                PreparedStatement ps=con.prepareStatement(mysql);
                ResultSet rs=ps.executeQuery();
                
                 String getusername="";  
                
                if(rs.next()){
               
                getusername=rs.getString(column);    
                
                }

                System.out.println("Welcome back,"+getusername+"!");
                
                UpdateLogIn(user_email);
                break;
                }

                    
                }
                }

                 } catch (SQLException ex) {Logger lgr = Logger.getLogger(RegistrationForm.class.getName());lgr.log(Level.SEVERE, ex.getMessage(), ex);  }
                

}
    
 //updates the logged in status for the user, status=1 for user that has logged in
  public static void UpdateLogIn(String user_email){

    try{
        //create connection to database
        String path="jdbc:mysql://localhost:3306/yuutube";
        String userid="root";
        String password="";
        
        Connection con=DriverManager.getConnection(path,userid,password);
  
         String mysql = "UPDATE userinfo SET loggedin=1 where user_email='"+user_email+"';"; 
         String mysql1="UPDATE userinfo SET loggedin=0 where user_email!='"+user_email+"';"; 
         
         PreparedStatement ps=con.prepareStatement(mysql);
         PreparedStatement ps1=con.prepareStatement(mysql1);
         
         ps.executeUpdate();
         ps1.executeUpdate();

         
        } catch (SQLException ex) {Logger lgr = Logger.getLogger(RegistrationForm.class.getName());lgr.log(Level.SEVERE, ex.getMessage(), ex);  }

  }
  
  
  //method that logs out user from account
  public static void LoggingOut(){

      System.out.println("Key in 'Log Out' to log out of your account");
      
      Scanner s=new Scanner(System.in);
     
      String loggingout="Log Out";
      
      String userlogout=s.nextLine();
      
      if (userlogout.equalsIgnoreCase(loggingout)){
          
          System.out.println("Are you sure you want to log out?(Y/N)");
          String yesno=s.nextLine();
                String yes="y";
                String no="n";
             
             if(yesno.equalsIgnoreCase(yes)){
                 
                 System.out.println("Logging Out....");
                 System.out.println("----------------");
                 UpdateLogOut();
                 System.out.println("Logged Out Successfully!");
                 
                    }
             
                   
                }
          }

         
 //method that updates status of log out in database after user has logged out
 // loggedin=0 means user has logged out
  public static void UpdateLogOut(){

    try{
        //create connection to database
        String path="jdbc:mysql://localhost:3306/yuutube";
        String userid="root";
        String password="";
        
        Connection con=DriverManager.getConnection(path,userid,password);
        
          String user_email="";  
        
                String column="loggedin";


         String mysql = "UPDATE userinfo SET loggedin=0 where loggedin=1";
                //user_email='"+user_email+"';";

                PreparedStatement ps=con.prepareStatement(mysql);
                ResultSet rs=ps.executeQuery();
//                
//                if(rs.next()){
//               
//               user_email=rs.getString(column);    
//                
//                }   

         
        } catch (SQLException ex) {Logger lgr = Logger.getLogger(RegistrationForm.class.getName());lgr.log(Level.SEVERE, ex.getMessage(), ex);  }
                
  
  } 

  //checks which user is logged in and returns the id of that user
  public static int LoggedIn(){

      int id =0;
    try{
        //create connection to database
        String path="jdbc:mysql://localhost:3306/yuutube";
        String userid="root";
        String password="";
        
        Connection con=DriverManager.getConnection(path,userid,password);
        
         String mysql = "Select id from userinfo where loggedin=1"; 
         PreparedStatement ps=con.prepareStatement(mysql);
         ResultSet rs=ps.executeQuery();
         
         while(rs.next()){
 
            id=rs.getInt(1);

         }
         
        } catch (SQLException ex) {Logger lgr = Logger.getLogger(RegistrationForm.class.getName());lgr.log(Level.SEVERE, ex.getMessage(), ex);  }
                
    return id;
  }
  
  
}
