
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrationForm {
 
 public static void main(String[]args) //throws SQLException
 {
     
     //gives user option to either register or log in
     LogIn();
     
     //allows user to log out
//     LoggedIn.LoggingOut();

 }
 
 
 //method that gives users an option to either register or log in
 public static void LogIn(){
        Scanner s=new Scanner(System.in);
        try{   
        //create connection to database
        String path="jdbc:mysql://localhost:3306/yuutube";
        String userid="root";
        String password="";
        
        Connection con=DriverManager.getConnection(path,userid,password);

        System.out.println("-------Welcome to YuuTube v1.0-------------");
        System.out.println("What would you like to do today?(Please enter your option number)");
        
        System.out.println("1:Register a new account");
        System.out.println("2:Log in");
        
        int option=s.nextInt();

        switch(option){

            case 1:
                Register();
                break;
                
            case 2:
               LoggedIn.LoggingIn();          
               break;
                
            } con.close();
            }catch (SQLException ex) {Logger lgr = Logger.getLogger(RegistrationForm.class.getName());lgr.log(Level.SEVERE, ex.getMessage(), ex);  }
            } 
 
 
 //method that brings users to registration page
 public static void Register(){
    
    Scanner s=new Scanner(System.in);
    
    try{
         //create connection to database
        String path="jdbc:mysql://localhost:3306/yuutube";
        String userid="root";
        String password="";
        
        Connection con=DriverManager.getConnection(path,userid,password);

    
                   //register new account
                System.out.println("Registration Page");
                
                System.out.println("Please fill in your details:");
                
                System.out.print("Email address: ");
                String user_email=s.next();
                
                s.nextLine();
                
                System.out.println(); 
                System.out.print("Username(You cannot change your username after this) : ");
                String username=s.nextLine();
                           
                 boolean test=true;
                 label : while(true){
                
                for(int i=0;i<username.length();i++) {
                if(Character.isUpperCase(username.charAt(i))){
                    username=username;
                    test=false;
                    break label;
                }
                else
                        System.out.println("Your username has to include at least one capital letter!");
                        System.out.println("Please retype your username with at least one capital letter:");
                        username=s.nextLine();
                        break;
                }
                
                    
                        
}
                 System.out.println();
                  System.out.print("Display Name: ");
                  String displayname=s.nextLine();
                  
                 System.out.println(); 
                System.out.print("Password: ");
                String userpassword=s.nextLine();
                boolean testpw=true;
            
               label : while(true){
                
                for(int i=0;i<userpassword.length();i++){
                int p=userpassword.length();
  
                if(p<8){
                    System.out.println("Your password must contain at least 8 characters");
                        System.out.println("Please retype your password:");
                        userpassword=s.nextLine();
                        break;
                }
                else{
                   
                    testpw=false;
                     break label;
                }
                     
                }
            }
                
      
                // create mysql insert statement
                 String mysql = "INSERT INTO userinfo(user_email,username,displayname,userpassword)"
                 +"values (?, ?, ?,?)";

             //create preparedstatement
             PreparedStatement ps=con.prepareStatement(mysql);

            //set paramter values
             ps.setString(1,user_email);
             ps.setString(2, username);
             ps.setString(3,displayname);
              ps.setString(4, userpassword);
             
                //execute update  
                ps.executeUpdate();
                
                System.out.println("Your account has been created!"); 
               System.out.println("Would you like to log in now?(Y/N)");
               String yesno=s.nextLine();
                String yes="y";
                String no="n";
             
             if(yesno.equalsIgnoreCase(yes)){
                 
                 LoggedIn.LoggingIn();
                    }
             
                    else if(yesno.equalsIgnoreCase(no))    {                 
                        System.out.println("Back To Homepage");
                }
             
}catch (SQLException ex) {Logger lgr = Logger.getLogger(RegistrationForm.class.getName());lgr.log(Level.SEVERE, ex.getMessage(), ex);  }
    
}
 
 //method that checks whether the email entered by user during registration matches emails available in database, if yes, ask user to log in (not register)
public static void Register_check_user_email(String st){
    
    Scanner s=new Scanner(System.in);

    try{
        //create connection to database
        String path="jdbc:mysql://localhost:3306/yuutube";
        String userid="root";
        String password="";
        
        String user_email=st;
        
        Connection con=DriverManager.getConnection(path,userid,password);
        
         String mysql = "SELECT * FROM userinfo WHERE user_email='"+user_email+"';";
         PreparedStatement ps=con.prepareStatement(mysql);
         ResultSet rs=ps.executeQuery();
         
         while(rs.next()){
             
             System.out.println("This email already has an account with us. Would you like to log in to your account now?(Y/N)");
             String yesno=s.nextLine();
             String yes="y";
             
             if(yesno.equalsIgnoreCase(yes)){
                 
                 LoggedIn.LoggedIn();
                 break;

             }
             else {
                 System.out.println("Back to homepage");
                 break;
             }
                 
             
         }

        } catch (SQLException ex) {Logger lgr = Logger.getLogger(RegistrationForm.class.getName());lgr.log(Level.SEVERE, ex.getMessage(), ex);  }

        }


//method that checks whether the email entered by user matches the email available in database
public static boolean check_user_email(String st){
    boolean check_email=false;
    try{
        //create connection to database
        String path="jdbc:mysql://localhost:3306/yuutube";
        String userid="root";
        String password="";
        
        String user_email=st;
        
        Connection con=DriverManager.getConnection(path,userid,password);
        
         String mysql = "SELECT * FROM userinfo WHERE user_email='"+user_email+"';";
         PreparedStatement ps=con.prepareStatement(mysql);
         ResultSet rs=ps.executeQuery();
         
         while(rs.next()){
             
             check_email=true;
            return check_email;
         }

        } catch (SQLException ex) {Logger lgr = Logger.getLogger(RegistrationForm.class.getName());lgr.log(Level.SEVERE, ex.getMessage(), ex);  }
                
               return check_email;
        }

//method that checks whether password entered by user matches password in database
public static boolean check_user_password(String pw){

    boolean check_password=false;
    
    try{
        //create connection to database
        String path="jdbc:mysql://localhost:3306/yuutube";
        String userid="root";
        String password="";
        
       String user_password=pw;
        
        Connection con=DriverManager.getConnection(path,userid,password);
        
        
        String mysql = "SELECT * FROM userinfo where userpassword='"+user_password+"';";
        PreparedStatement ps=con.prepareStatement(mysql);
        ResultSet rs=ps.executeQuery();
                
                 while (rs.next())
                 {
                     check_password=true;
                     return check_password;
                 }
                 
        } catch (SQLException ex) {Logger lgr = Logger.getLogger(RegistrationForm.class.getName());lgr.log(Level.SEVERE, ex.getMessage(), ex);  }
                
            return check_password;   
        }
   
//  Class.forName("com.mysql.cj.jdbc.Driver");   


}



