
import java.io.*;
import javax.swing.*;
import java.sql.*;
import org.sqlite.SQLiteConfig;
import java.util.*;

public class CustomerApplication {

   public static final String DB_URL = "jdbc:sqlite:C:/Users/teodo/Documents/TIG059/gymdb"; //"jdbc:sqlite:C:/TIG058/PersonTidbok.sqlite";  
   public static final String DRIVER = "org.sqlite.JDBC";  

   public static void main(String[] args) throws IOException, SQLException {
      Connection conn = null;
   
      try {
         Class.forName(DRIVER);
         SQLiteConfig config = new SQLiteConfig();  
         config.enforceForeignKeys(true);       } catch (Exception e) {
         System.out.println( e.toString() );
         System.exit(0);
      }
      
      
//"sign in"
      boolean success = true;
      String sign = JOptionPane.showInputDialog("Date of birth");
      double signIn = Double.parseDouble(sign);
         try {
            //SQL-command
               conn = DriverManager.getConnection(DB_URL);
               PreparedStatement stS = conn.prepareStatement("SELECT * FROM Customers WHERE CustomerID = " + sign + ";");       
            
            //send
               ResultSet sSvar = stS.executeQuery();               
               
            //receive
               while (sSvar.getString("CustomerID") == null || sSvar.getString("FirstName") == null) {
                  JOptionPane.showMessageDialog(null, "Account not found" + success);
                  success = false;
               }
               
               stS.close();
         }  
               catch (Exception e) {
                  success = false;
                  JOptionPane.showMessageDialog(null, "Account not found");
               }    
      
//Choose option
   while (success == false) {
      break;
   } 
   while (success == true) {   
      String[] action = {"Membership", "Courses", "Gyms", "Account", "Quit"};
      int act = JOptionPane.showOptionDialog(null, "Please select the action you want to perform",
            "Choose action",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, action, action[0]);
            if (act == 4) {
               break;
            }

// membership, choose
         
      if (act == 0) {
         String[] membership = {"View membership tier", "View payment history"};
         int memb = JOptionPane.showOptionDialog(null, "Please select the action you want to perform",
               "Choose action",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, membership, membership[0]);
         
//View membership tier
         if (memb == 0) {
            try {
            //SQL-command
               conn = DriverManager.getConnection(DB_URL);
               PreparedStatement stS = conn.prepareStatement("SELECT TierRate, MembershipTier.MembershipTierID FROM Customers, MembershipTier, Membership WHERE Customers.CustomerID = Membership.CustomerID AND Membership.MembershipTierID = MembershipTier.MembershipTierID AND Customers.CustomerID = ? AND MembershipEnd BETWEEN '2020-05-01' AND '2025-12-31';");       
            
            //feed values
               stS.setString(1, sign);
            
            //send
               ResultSet sSvar = stS.executeQuery();               
               
            //receive
               String ansS = "";
               
               if (sSvar.getString("TierRate") == null || sSvar.getString("MembershipTierID") == null) {
                  JOptionPane.showMessageDialog(null, "No value to display");
               }
               
               while (sSvar.next()) {
                  String rate = sSvar.getString("TierRate");
                  String tier = sSvar.getString("MembershipTierID"); 
                  
                  ansS = ansS + "Tier: " + tier + " " + '\n' + "Monthly payment: " + rate + " kr" + '\n';
               }

               JOptionPane.showMessageDialog(null, ansS);
               
               stS.close();
            }  
               catch (Exception e) {
                  JOptionPane.showMessageDialog(null, "Incorrect input");
               }
         }
            
//View payment history                              
         if (memb == 1) {
             
            try {  
            //SQL-command
               conn = DriverManager.getConnection(DB_URL);
               PreparedStatement stS = conn.prepareStatement("SELECT * FROM Customers, Membership, MembershipTier, Payment WHERE Customers.CustomerID = Membership.CustomerID AND Membership.MembershipTierID = MembershipTier.MembershipTierID AND Payment.MembershipID = Membership.MembershipID AND Membership.CustomerID = ? ORDER BY PaidDate DESC;");       
            
            //feed values
               stS.setString(1, sign);
            
            //send
               ResultSet sSvar = stS.executeQuery();               
               
            //receive
               String ansS = "";
               
               if (sSvar.getString("PaidDate") == null || sSvar.getString("TierRate") == null) {
                  JOptionPane.showMessageDialog(null, "No value to display");
               }
               
               while (sSvar.next()) {
                  String payDate = sSvar.getString("PaidDate");
                  String rate = sSvar.getString("TierRate"); 
                  
                  ansS = ansS + "Payment date: " + payDate + " " + '\n' + "Amount: " + rate + " kr" + '\n' + '\n';
               }

               JOptionPane.showMessageDialog(null, ansS);
               
               stS.close();
            }
               catch (Exception e) {
                  JOptionPane.showMessageDialog(null, "No payments to display");
               }
         }
      }        
      
//Courses, choose
      if (act == 1) {   
         String[] courses = {"View courses", "Filter courses", "Book course", "View booked courses"};
         int cour = JOptionPane.showOptionDialog(null, "Please select the action you want to perform",
               "Choose action",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, courses, courses[0]);
         
//View courses
         if (cour == 0) {
            
            try {
            //SQL-command
               conn = DriverManager.getConnection(DB_URL);
               PreparedStatement stS = conn.prepareStatement("SELECT * FROM Courses, Instructors, Rooms WHERE Instructors.InstructorID = Courses.InstructorID AND Courses.RoomID = Rooms.RoomID;");       
            
            //send
               ResultSet sSvar = stS.executeQuery();               
               
            //receive
               String ansS = "";
               
               if (sSvar.getString("CourseName") == null || sSvar.getString("RoomName") == null) {
                  JOptionPane.showMessageDialog(null, "No value to display");
               }
               
               while (sSvar.next()) {
                  String name = sSvar.getString("CourseName");
                  String date = sSvar.getString("Date"); 
                  String time = sSvar.getString("Time");
                  String room = sSvar.getString("RoomName");
                  String gymID = sSvar.getString("GymID");
                  String id = sSvar.getString("CourseID");
                  String fname = sSvar.getString("FirstName");
                  String lname = sSvar.getString("LastName");
                  
                  ansS = ansS + "Course: " + name + " " + '\n' + "Time: " + time + ", date: " + date + '\n' + "Room: " + room + '\n' + "Gym: " + gymID + '\n' + "Instructor: " + fname + " " + lname + '\n' + "Course ID: " + id + '\n' + '\n';
               }

               JOptionPane.showMessageDialog(null, ansS);
               
               stS.close();  
            }
                  catch (Exception e) {
                  JOptionPane.showMessageDialog(null, "Incorrect input");
               }
         }
          
//Filter course
          if (cour == 1) {
            
            JTextField field1 = new JTextField();
            JTextField field2 = new JTextField();
            JTextField field3 = new JTextField();
            Object[] message = {
                "Course name:", field1,
                "Gym:", field2,
                "Instructor name:", field3,

            };
            
            String cname, gym, iname = "";
            String value1, value2, value3 = "";
            
            int filterCourse = JOptionPane.showConfirmDialog(null, message, "Enter instructor information", JOptionPane.OK_CANCEL_OPTION);
            if (filterCourse == JOptionPane.OK_OPTION)
               //filter, null check
               {
                if (field1.getText() != null && !field1.getText().isEmpty()) {
                  value1 = field1.getText();
                  cname = "AND CourseName = '" + value1 + "'";
               } else {
                  cname = "";
               }
               
               if (field2.getText() != null && !field2.getText().isEmpty()) {
                  value2 = field2.getText();
                  gym = "AND Gyms.GymID = '" + value2 + "'";
               } else {
                  gym = "";
               }
                
               if (field3.getText() != null && !field3.getText().isEmpty()) {
                  value3 = field3.getText();
                  iname = "AND FirstName = '" + value3 + "'";
               } else {
                  iname = "";
               } 
                        
            try {
            //SQL-command
               conn = DriverManager.getConnection(DB_URL);
               PreparedStatement stP = conn.prepareStatement("SELECT * FROM Courses, Instructors, Rooms, Gyms WHERE Instructors.InstructorID = Courses.InstructorID AND Courses.RoomID = Rooms.RoomID AND Courses.GymID = Gyms.GymID " + cname + gym + iname + ";");       
            
            //feed values
               ResultSet sSvar = stP.executeQuery();             
               
            //receive
               String ansS = "";
               
               if (sSvar.getString("CourseName") == null || sSvar.getString("RoomName") == null) {
                  JOptionPane.showMessageDialog(null, "No value to display");
               }
               
               while (sSvar.next()) {
                  String name = sSvar.getString("CourseName");
                  String date = sSvar.getString("Date"); 
                  String time = sSvar.getString("Time");
                  String room = sSvar.getString("RoomName");
                  String gymID = sSvar.getString("GymID");
                  String id = sSvar.getString("CourseID");
                  String fname = sSvar.getString("FirstName");
                  String lname = sSvar.getString("LastName");
                  
                  ansS = ansS + "Course: " + name + " " + '\n' + "Time: " + time + ", date: " + date + '\n' + "Room: " + room + '\n' + "Gym: " + gymID + '\n' + "Instructor: " + fname + " " + lname + '\n' + "Course ID: " + id + '\n' + '\n';
               }

               JOptionPane.showMessageDialog(null, ansS);
               
               stP.close();  
            }
                  catch (Exception e) {
                  JOptionPane.showMessageDialog(null, "Incorrect input");
               }
            }
         }
   
//Book course
         if (cour == 2) {
            
            String enrID = UUID.randomUUID().toString();
            String field1 = JOptionPane.showInputDialog(null, "Enter course ID to book");
                             
         try {
         //SQL-command
            conn = DriverManager.getConnection(DB_URL);
            PreparedStatement stP = conn.prepareStatement("INSERT INTO CourseEnrollment VALUES (?, ?, ?);");
         
         //feed values
            stP.setString(3, sign);
            stP.setString(1, enrID);
            stP.setString(2, field1);       
            
         //send
            stP.executeUpdate();
            stP.close();       
            }  
               catch (Exception e) {
               JOptionPane.showMessageDialog(null, "Incorrect input");
            }
            
            try {   
            //SQL-command
               conn = DriverManager.getConnection(DB_URL);
               PreparedStatement stS = conn.prepareStatement("SELECT * FROM Courses, CourseEnrollment WHERE Courses.CourseID = " + field1 + ";");       
            
            //send
               ResultSet sSvar = stS.executeQuery();               
               
            //receive
               String ansS = "";
               
               if (sSvar.getString("CourseID") == null) {
                  JOptionPane.showMessageDialog(null, "No value to display");
               }
               
               while (sSvar.next()) {
                  String name = sSvar.getString("CourseName");
                  String time = sSvar.getString("Time");
                  String date = sSvar.getString("Date");

                  ansS = "A booking has been successfully made. \nBooked course: " + name + " at " + time + ", " + date + "";
               }

               JOptionPane.showMessageDialog(null, ansS);
               
               stS.close();
            }
               catch (Exception e) {
                  JOptionPane.showMessageDialog(null, "Incorrect input");
               }
         }
      
//View booked courses
         if (cour == 3) {
            try {   
            //SQL-command
               conn = DriverManager.getConnection(DB_URL);
               PreparedStatement stS = conn.prepareStatement("SELECT CourseName, Date, Time, RoomName, Rooms.GymID, Instructors.FirstName, Instructors.LastName FROM Courses, CourseEnrollment, Customers, Rooms, Instructors WHERE Customers.CustomerID = CourseEnrollment.CustomerID AND CourseEnrollment.CourseID = Courses.CourseID AND Rooms.RoomID = Courses.RoomID AND Courses.InstructorID = Instructors.InstructorID AND Customers.CustomerID = ?;");       
            
            //feed values
               stS.setString(1, sign);
            
            //send
               ResultSet sSvar = stS.executeQuery();               
               
            //receive
               String ansS = "";
               
               if (sSvar.getString("CourseName") == null || sSvar.getString("Date") == null) {
                  JOptionPane.showMessageDialog(null, "No value to display");
               }
               
               while (sSvar.next()) {
                  String name = sSvar.getString("CourseName");
                  String date = sSvar.getString("Date"); 
                  String time = sSvar.getString("Time");
                  String room = sSvar.getString("RoomName");
                  String gymID = sSvar.getString("GymID");
                  String fname = sSvar.getString("FirstName");
                  String lname = sSvar.getString("LastName");
                  
                  ansS = ansS + "Course: " + name + " " + '\n' + "Time: " + time + ", date: " + date + '\n' + "Room: " + room + '\n' + "Gym: " + gymID + '\n' + "Instructor: " + fname + " " + lname + '\n' + '\n';
               }

               JOptionPane.showMessageDialog(null, ansS);
               
               stS.close();
            }
               catch (Exception e) {
               JOptionPane.showMessageDialog(null, "Incorrect input");
            }
         }
      }

//Gym, choose
      if (act == 2) {
         String[] gymlocation = {"Look up equipment", "Filter equipment", "Look up gym info"};
         int gymloc = JOptionPane.showOptionDialog(null, "Please select the action you want to perform",
               "Choose action",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, gymlocation, gymlocation[0]);
         
//View gym equipment
         if (gymloc == 0) {
         
            try {
            //SQL-command
               conn = DriverManager.getConnection(DB_URL);
               PreparedStatement stS = conn.prepareStatement("SELECT COUNT(*), GymID, EquipmentType FROM GymEquipment GROUP BY EquipmentType, GymID ORDER BY EquipmentType;");       
            
            //send
               ResultSet sSvar = stS.executeQuery();               
               
            //receive
               String ansS = "";
               
               if (sSvar.getString("EquipmentType") == null || sSvar.getString("GymID") == null) {
                  JOptionPane.showMessageDialog(null, "No value to display");
               }
               
               while (sSvar.next()) {
                  String equiType = sSvar.getString("EquipmentType");
                  String equiLoca = sSvar.getString("GymID");

                    // SELECT GymID, COUNT(*) FROM GymEquipment WHERE EquipmentType = 'Crosstrainer' GROUP BY GymID ORDER BY EquipmentType;
                  
                  ansS = ansS + "Equipment type: " + equiType + " " + '\n' + "Avaliable at: " + equiLoca + '\n' + '\n';
                  
               }

               JOptionPane.showMessageDialog(null, ansS);
               
               stS.close();
               
            }
               catch (Exception e) {
                  JOptionPane.showMessageDialog(null, "Incorrect input");
               }
         }
               
//Filter equipment
         if (gymloc == 1) {
            
            JTextField field1 = new JTextField();
            JTextField field2 = new JTextField();
            Object[] message = {
                "Equipment:", field1,
                "Gym:", field2,

            };
            
            String equi, loca = "";
            String value1, value2 = "";
            
            int filterEquipment = JOptionPane.showConfirmDialog(null, message, "Filter", JOptionPane.OK_CANCEL_OPTION);
            if (filterEquipment == JOptionPane.OK_OPTION)
               //filter, null check
               {
                if (field1.getText() != null && !field1.getText().isEmpty()) {
                  value1 = field1.getText();
                  equi = "AND EquipmentType = '" + value1 + "' ";
               } else {
                  equi = "";
               }
               
               if (field2.getText() != null && !field2.getText().isEmpty()) {
                  value2 = field2.getText();
                  loca = "AND Gyms.GymID = '" + value2 + "'";
               } else {
                  loca = "";
               }
                        
            try {
            //SQL-command
               conn = DriverManager.getConnection(DB_URL);
               PreparedStatement stP = conn.prepareStatement("SELECT EquipmentType, COUNT(EquipmentType), Gyms.GymID FROM Gyms, GymEquipment WHERE Gyms.GymID = GymEquipment.GymID " + equi + loca + " GROUP BY Gyms.GymID, EquipmentType HAVING COUNT (EquipmentType) ORDER BY Gyms.GymID;");       
            
            //feed values
               ResultSet sSvar = stP.executeQuery();             
               
            //receive
               String ansS = "";
               
               if (sSvar.getString("EquipmentType") == null || sSvar.getString("GymID") == null) {
                  JOptionPane.showMessageDialog(null, "No value to display");
               }
               
               while (sSvar.next()) {
                  String equiType = sSvar.getString("EquipmentType");
                  String amntEqui = sSvar.getString("COUNT(EquipmentType)"); 
                  String equiLoca = sSvar.getString("GymID");
                  
                  ansS = ansS + "Equipment type: " + equiType + " " + '\n' + "Amount: " + amntEqui + '\n' + "Avaliable at: " + equiLoca + '\n' + '\n';
               }

               JOptionPane.showMessageDialog(null, ansS);
               
               stP.close();  
            }
                  catch (Exception e) {
                  JOptionPane.showMessageDialog(null, "Incorrect input");
               }
            }
         }
         
//Look up gym info
         if (gymloc == 2) {
            
               try {
               //SQL-command
                  conn = DriverManager.getConnection(DB_URL);
                  PreparedStatement stS = conn.prepareStatement("SELECT * FROM Gyms;");       
               
               //send
                  ResultSet sSvar = stS.executeQuery();               
                  
               //receive
                  String ansS = "";
                  
               if (sSvar.getString("GymID") == null || sSvar.getString("TimeOpen") == null) {
                  JOptionPane.showMessageDialog(null, "No value to display");
               }
                  
                  while (sSvar.next()) {
                     String gymNo = sSvar.getString("GymID");
                     String address = sSvar.getString("Address"); 
                     String gymPhoneNo = sSvar.getString("GymPhoneNo");
                     String openHrs = sSvar.getString("TimeOpen");
                     String closeHrs = sSvar.getString("TimeClose");
                     
                     ansS = ansS + "Gym: " + gymNo + " " + '\n' + "Address: " + address + '\n' + "Phone number: " + gymPhoneNo + '\n' + "Opens: " + openHrs + "       " + "Closes: " + closeHrs + '\n' + '\n';
                  }
   
                  JOptionPane.showMessageDialog(null, ansS);
                  
                  stS.close();
                  
               }
                  catch (Exception e) {
                  JOptionPane.showMessageDialog(null, "Incorrect input");
                  }
            }
         }

//Account, choose
      if (act == 3) {
         String[] account = {"View account information", "Edit account information"};
         int acc = JOptionPane.showOptionDialog(null, "Please select the action you want to perform",
               "Choose action",
               JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, account, account[0]);
         
//View account info
         if (acc == 0) {
            
            try { 
            //SQL-command
               conn = DriverManager.getConnection(DB_URL);
               PreparedStatement stS = conn.prepareStatement("SELECT * FROM Customers WHERE CustomerID = ?;");       
            
            //feed values
               stS.setString(1, sign);
            
            //send
               ResultSet sSvar = stS.executeQuery();               
               
            //receive
               String ansS = "";
               
               if (sSvar.getString("FirstName") == null || sSvar.getString("CustomerID") == null) {
                  JOptionPane.showMessageDialog(null, "No value to display");
               }
               
               while (sSvar.next()) {
                  String fname = sSvar.getString("FirstName");
                  String lname = sSvar.getString("LastName");
                  String dateofBirth = sSvar.getString("CustomerID");
                  String phone = sSvar.getString("PhoneNo");
                  String email = sSvar.getString("Email");
                  String address = sSvar.getString("Address");
                  String city = sSvar.getString("City");
                  String dateofRegistration = sSvar.getString("DateJoined");
                  
                  ansS = ansS + "First name: " + fname + '\n' + "Last name: " + lname + '\n' + "Date of birth: " + dateofBirth + '\n' + "Phone number: " + phone + '\n' + "Email address: " + email + '\n' + "Address: " + address + '\n' + "City: " + city + '\n' + "Date joined: " + dateofRegistration;
               }
            
               JOptionPane.showMessageDialog(null, ansS);
            
               stS.close();
            }
            
               catch (Exception e) {
               JOptionPane.showMessageDialog(null, "Incorrect input");
               
            }
         }
           
//Edit account info                              
         if (acc == 1) {
               
            JTextField field1 = new JTextField();
            JTextField field2 = new JTextField();
            JTextField field4 = new JTextField();
            JTextField field5 = new JTextField();
            JTextField field6 = new JTextField();
            JTextField field7 = new JTextField();
            Object[] message = {
                "First name:", field1,
                "Last name:", field2,
                "Phone number:", field4,
                "Email:", field5,
                "Address:", field6,
                "City:", field7,
            };
            
            int editAcct = JOptionPane.showConfirmDialog(null, message, "Enter customer information", JOptionPane.OK_CANCEL_OPTION);
            if (editAcct == JOptionPane.OK_OPTION)
            {
                String value1 = field1.getText();
                String value2 = field2.getText();
                String value4 = field4.getText();
                String value5 = field5.getText();
                String value6 = field6.getText();
                String value7 = field7.getText();
            }
            
               String editAcc = field1.getText() + field2.getText() + field4.getText() + field5.getText() + field6.getText() + field7.getText();
                 
         try {
         //SQL-command
            conn = DriverManager.getConnection(DB_URL);
            PreparedStatement stP = conn.prepareStatement("UPDATE Customers SET FirstName = ?, LastName = ?, PhoneNo = ?, Email = ?, Address = ?, City = ? WHERE CustomerID = " + sign + ";");
         
         //feed values
            stP.setString(1, field1.getText());
            stP.setString(2, field2.getText());
            stP.setString(3, field4.getText());
            stP.setString(4, field5.getText());
            stP.setString(5, field6.getText());
            stP.setString(6, field7.getText());       
            
         //send
            stP.executeUpdate();
            stP.close();       
            }
              
            catch (Exception e) {
               JOptionPane.showMessageDialog(null, "Incorrect input");

            }
            
            try {   
            //SQL-command
               conn = DriverManager.getConnection(DB_URL);
               PreparedStatement stS = conn.prepareStatement("SELECT * FROM Customers WHERE CustomerID = " + sign + ";");       
            
            //send
               ResultSet sSvar = stS.executeQuery();               
               
            //receive
               String ansS = "";
               
               if (sSvar.getString("CustomerID") == null) {
                  JOptionPane.showMessageDialog(null, "No value to display");
               }

               ansS = "Account details have been successfully changed.";

               JOptionPane.showMessageDialog(null, ansS);
               
               stS.close();
            }
               catch (Exception e) {
                  JOptionPane.showMessageDialog(null, "Incorrect input");
               }
         } 
      }        
   }   
   }
}
