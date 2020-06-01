import java.io.*;
import java.sql.*;
import org.sqlite.SQLiteConfig;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.*;
import java.util.UUID;

public class gymStaffFinal {

   // Sökväg till SQLite-databas. OBS! Ändra sökväg så att den pekar ut din databas
   public static final String DB_URL = "jdbc:sqlite:C:/Users/teodo/Documents/TIG059/gymdb";  
   // Namnet på den driver som används av java för att prata med SQLite
   public static final String DRIVER = "org.sqlite.JDBC";  

   public static void main(String[] args) throws IOException{
      Connection conn = null;

      // Kod för att skapa uppkoppling mot SQLite-dabatasen
      try {
         Class.forName(DRIVER);
         SQLiteConfig config = new SQLiteConfig();  
         config.enforceForeignKeys(true); // Denna kodrad ser till att sätta databasen i ett läge där den ger felmeddelande ifall man bryter mot någon främmande-nyckel-regel
         conn = DriverManager.getConnection(DB_URL,config.toProperties());  
      } catch (Exception e) {
         // Om java-progammet inte lyckas koppla upp sig mot databasen (t ex om fel sökväg eller om driver inte hittas) så kommer ett felmeddelande skrivas ut
         System.out.println( e.toString() );
         System.exit(0);
      } 
      while(true){
   /* Object[] options1 = {"NC", "UC", "LC","NM", "VM", "VG", "VR", "VE", "VC", "VI", "NE", "NC", "NI", "Q"};
    JPanel panel = new JPanel();
    panel.add(new JLabel("NC = New Customer, UC = Update Customer, LC = Look up Customer, NM = New Membership, VM = View Membership, VG = View Gym, VR = View Rooms, VE = View Equipment, VC = View Courses, VI = View Instructors, NE = New Equipment, NC = New Course, NI = New Instructor, Q = Quit"));
    
    int result = JOptionPane.showOptionDialog(null, panel, "Enter a Number",
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
        options1, null); */
        
        
    Object[] options1 = {"Customers & Memberships", "Gyms, Rooms & Equipment", "Courses & Instructors", "Enrollment", "Quit"};    
    JPanel panel = new JPanel();
    panel.add(new JLabel("What do you want to manage?"));
    int result = JOptionPane.showOptionDialog(null, panel, "Enter a Number",
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, null);
        
    if (result == 0) {
     Object[] options2 = {"New Customer", "Update Customer", "View Custmore", "New Membership", "Edit Membership", "View Membership", "Quit"};
    JPanel panel2 = new JPanel();
    panel2.add(new JLabel("Customers & Membershits"));
    
    int result2 = JOptionPane.showOptionDialog(null, panel2, "Enter a Number",
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
        options2, null);
        
        if (result2 == 0) {
        String Fname = JOptionPane.showInputDialog(null, "Provide first name");
         String Lname = JOptionPane.showInputDialog(null, "Provide last name");
         String DOB = JOptionPane.showInputDialog(null, "Provide date of birth (10 numbers)");
         String Email = JOptionPane.showInputDialog(null, "Provide email");
         String Address = JOptionPane.showInputDialog(null,"Provide address");
         String City = JOptionPane.showInputDialog(null,"Provide City");
         String Phone = JOptionPane.showInputDialog(null, "Provide phone number.");
         String Date = JOptionPane.showInputDialog(null, "Provide the date of today (YYYY-MM-DD)");
         String SQL = "INSERT INTO Customers (FirstName, LastName, CustomerID, Email, Address, City, PhoneNo, DateJoined) VALUES ('" + Fname + "', '" +Lname + "', '" + DOB + "', '" + Email + "','" + Address + "','" + City + "','" + Phone + "','" + Date + "');";
        Statement stm= null;
       try{
       stm=conn.createStatement();
       stm.executeUpdate(SQL);
       }catch (SQLException sqle){
       JOptionPane.showMessageDialog(null, sqle.getMessage());
       }
       }
       if (result2 == 1) {
       String ID = JOptionPane.showInputDialog(null, "Provide date of birth of the customer whos info you want to update (10 numbers)");
         String Fname = JOptionPane.showInputDialog(null, "Provide first name");
         String Lname = JOptionPane.showInputDialog(null, "Provide last name");
         String Email = JOptionPane.showInputDialog(null, "Provide email");
         String Address = JOptionPane.showInputDialog(null,"Provide address");
         String City = JOptionPane.showInputDialog(null,"Provide City");
         String Phone = JOptionPane.showInputDialog(null, "Provide phone number.");
         String SQL = "UPDATE Customers SET (FirstName, Lastname, Email, Address, City, PhoneNo) = ('" + Fname + "','" + Lname + "','" + Email + "','" + Address + "','" + City + "','" + Phone + "') WHERE CustomerID ='" + ID + "';" ;
     
    Statement stm= null;
       try{
       stm=conn.createStatement();
       stm.executeUpdate(SQL);
       }catch (SQLException sqle){
       JOptionPane.showMessageDialog(null, sqle.getMessage());
       }
       }
       
       if (result2 == 2){
       String ID = JOptionPane.showInputDialog("Who do you want to look up? Provide date of birth (10 Numbers) ");
       String SQLL = "Select * from Customers where CustomerID = " + ID + ";";
       Statement stm = null;
       ResultSet rs = null;
       try{
       stm=conn.createStatement();
       rs = stm.executeQuery(SQLL);
       String out ="";
       while (rs.next()){
       out +=(rs.getString("FirstName") + " \tl " + rs.getString("LastName") + " \tl " + rs.getString("CustomerID") + " \tl " + rs.getString("Email") + " \tl " + rs.getString("Address") + " \tl " + rs.getString("City") + " \tl " + rs.getString("PhoneNo") + " \tl " + rs.getString("DateJoined") + "\n");
       }
       JOptionPane.showMessageDialog(null, out);
    
       }catch (SQLException sqle){
         JOptionPane.showMessageDialog(null, sqle.getMessage());
       }
       }
       
       if (result2 == 3) {
       String ID = JOptionPane.showInputDialog("Enter CustomerID for the customer you wish to create membership");
       String StartDate = JOptionPane.showInputDialog("Enter the starting date for the membership (YYYY-MM-DD)");
       String Mtier = JOptionPane.showInputDialog("What tier will this membership be (Gold, Silver or Bronze?");
       String MID = UUID.randomUUID().toString();
       String Memend = JOptionPane.showInputDialog("Enter the ending date for the membership (YYYY-MM-DD)");
       String SQL = "INSERT INTO Membership (MembershipID, MembershipStart, MembershipEnd, CustomerID, MembershipTierID) VALUES ('" + MID + "', '" + StartDate + "' , '" + Memend + "' , '" +  ID + "', '" +  Mtier + "');";
       Statement stm= null;
       try{
       stm=conn.createStatement();
       stm.executeUpdate(SQL);
       }catch (SQLException sqle){
       JOptionPane.showMessageDialog(null, sqle.getMessage());
       }
       }
       
       if (result2 == 4){
       String ID2 = JOptionPane.showInputDialog(null, "Provide the membershipID of the membership you want to edit");
       String Memstart2 = JOptionPane.showInputDialog(null, "Edit starting date (YYYY-MM-DD)");
       String MemEnd2 = JOptionPane.showInputDialog(null, "Edit ending date (YYYY-MM-DD)");
       String tier2 = JOptionPane.showInputDialog(null, "Edit membership tier");
       String SQLU = "UPDATE Membership SET (MembershipStart, MembershipEnd, MembershipTierID) = ('" + Memstart2 + "','" + MemEnd2 + "','" + tier2 + "') WHERE CustomerID ='" + ID2 + "';" ;
     
    Statement stm= null;
       try{
       stm=conn.createStatement();
       stm.executeUpdate(SQLU);
       }catch (SQLException sqle){
       JOptionPane.showMessageDialog(null, sqle.getMessage());
       }
       }

       
       
       
       if (result2 == 5){
       String ID = JOptionPane.showInputDialog("Whos memberships would you like to view? Provide date of birth (10 Numbers) ");
       String SQL = "Select * from Membership where CustomerID = " + ID + ";";
       Statement stm = null;
       ResultSet rs = null;
       try{
       stm=conn.createStatement();
       rs = stm.executeQuery(SQL);
       String out ="";
       while (rs.next()){
       out +=(rs.getString("MembershipID") + " \tl " + rs.getString("MembershipTierID") + " \tl " + rs.getString("MembershipStart") + " \tl " + rs.getString("MembershipEnd") + " \tl " + rs.getString("CustomerID") + " \tl " +  "\n");
       }
       JOptionPane.showMessageDialog(null, out);
      
       }catch (SQLException sqle){
       JOptionPane.showMessageDialog(null, sqle.getMessage());
       }
       }
       
       if (result2 ==6){
       System.exit(0);
       }
       }
       
       
        if (result == 1) {
        Object[] options3 = {"View Gym", "Edit Gym info", "View Rooms", "View Equipment", "New Equipment", "Move Equipment", "Quit"};
        JPanel panel3 = new JPanel();
        panel3.add(new JLabel("Gyms, Rooms & Equipment"));
        int result3 = JOptionPane.showOptionDialog(null, panel3, "Enter a Number",
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
        options3, null);
        
        if (result3 == 0){
        String SQLL = "Select * from Gyms;";
        Statement stm = null;
        ResultSet rs = null;
        try{
        stm=conn.createStatement();
        rs = stm.executeQuery(SQLL);
        String out ="";
        while (rs.next()){
        out +=(rs.getString("GymID") + " \tl " + rs.getString("GymPhoneNo") + " \tl " + rs.getString("Address") + " \tl " + rs.getString("City") + " \tl " + rs.getString("TimeOpen") + " \tl " + rs.getString("TimeClose") + "\n");
        }
        JOptionPane.showMessageDialog(null, out);
    
        }catch (SQLException sqle){
          JOptionPane.showMessageDialog(null, sqle.getMessage());
        }
        }
        if (result3 == 1){
        String GID = JOptionPane.showInputDialog(null, "Provide the GymID of the gym you want to edit");
        String phone = JOptionPane.showInputDialog(null, "Edit phone number");
        String add = JOptionPane.showInputDialog(null, "Edit address");
        String timeo = JOptionPane.showInputDialog(null, "Edit opening time");
        String timec = JOptionPane.showInputDialog(null, "Edit closing time");
        String SQL = "UPDATE Gyms SET (GymPhoneNo, Address, TimeOpen, TimeClose) = ('" + phone + "','" + add + "','" + timeo + "','" + timec + "') WHERE GymID ='" + GID + "';" ;
     
        Statement stm= null;
        try{
        stm=conn.createStatement();
        stm.executeUpdate(SQL);
        }catch (SQLException sqle){
        JOptionPane.showMessageDialog(null, sqle.getMessage());
        }
        }
        if (result3 == 2){
        String ID = JOptionPane.showInputDialog("Enter the GymID for the gym you would like to view rooms");
        String SQLL = "Select * from Rooms where GymID = '" + ID + "';";
        Statement stm = null;
        ResultSet rs = null;
        try{
        stm=conn.createStatement();
        rs = stm.executeQuery(SQLL);
        String out ="";
        while (rs.next()){
        out +=(rs.getString("GymID") + " \tl " + rs.getString("RoomID") + " \tl " + rs.getString("RoomName") + "\n");
        }
        JOptionPane.showMessageDialog(null, out);
    
        }catch (SQLException sqle){
          JOptionPane.showMessageDialog(null, sqle.getMessage());
        }
        }
        
        if (result3 == 3){
        String ID = JOptionPane.showInputDialog("Enter the GymID for the gym you want to view equipment");
        String SQLL = "Select * from GymEquipment where GymID = '" + ID + "';";
        Statement stm = null;
        ResultSet rs = null;
        try{
        stm=conn.createStatement();
        rs = stm.executeQuery(SQLL);
        String out ="";
        while (rs.next()){
        out +=(rs.getString("GymEquipmentID") + " \tl " + rs.getString("GymID") + " \tl " + rs.getString("EquipmentType") + "\n");
        }
        JOptionPane.showMessageDialog(null, out);
        
        }catch (SQLException sqle){
          JOptionPane.showMessageDialog(null, sqle.getMessage());

        }       
        }
        if (result3 == 4){
        String type = JOptionPane.showInputDialog("What type of Equipment?");
        String GymID = JOptionPane.showInputDialog("At which location will this equipment be?");
        String ID = UUID.randomUUID().toString();
        String SQL = "INSERT INTO GymEquipment (GymID, EquipmentType, GymEquipmentID) VALUES ('" + GymID + "', '" + type + "', '" + ID + "');";
        Statement stm= null;
        try{
        stm=conn.createStatement();
        stm.executeUpdate(SQL);
        }catch (SQLException sqle){
        JOptionPane.showMessageDialog(null, sqle.getMessage());
        }
        }
        
        
        if (result3 == 5){
        String ID = JOptionPane.showInputDialog(null, "Provide the EquipmentID of the equipment you want to move");
        String GID = JOptionPane.showInputDialog(null, "Enter GymID of new location");
        String SQL = "UPDATE GymEquipment SET (GymID) = ('" + GID +  "') WHERE GymEquipmentID ='" + ID + "';" ;
     
        Statement stm= null;
        try{
        stm=conn.createStatement();
        stm.executeUpdate(SQL);
        }catch (SQLException sqle){
        JOptionPane.showMessageDialog(null, sqle.getMessage());
        }
        }
        
        if (result3 ==6){
       System.exit(0);
       }
       }


        

        
        


        if (result == 2) {
        Object[] options4 = {"View Courses", "Edit Course", "New Course", "View Instructors", "New Instructor", "Quit"};
        JPanel panel4 = new JPanel();
        panel4.add(new JLabel("Courses & Instructors"));
        int result4 = JOptionPane.showOptionDialog(null, panel4, "Enter a Number",
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
        options4, null);
        
        if (result4 == 0){
        String SQLL = "Select * from Courses;";
        Statement stm = null;
        ResultSet rs = null;
        try{
        stm=conn.createStatement();
        rs = stm.executeQuery(SQLL);
        String out ="";
        while (rs.next()){
        out +=(rs.getString("CourseID") + " \tl " + rs.getString("InstructorID") + " \tl " + rs.getString("CourseName") + " \tl " + rs.getString("GymID") + " \tl " + rs.getString("RoomID") + " \tl " + rs.getString("Date") + " \tl " + rs.getString("Time") + "\n");
        }
        JOptionPane.showMessageDialog(null, out);
        
        }catch (SQLException sqle){
          JOptionPane.showMessageDialog(null, sqle.getMessage());
        
        }
        }
        
        if (result4 == 1){
        String GID = JOptionPane.showInputDialog(null, "Provide the CourseID of the course you want to edit");
        String CN = JOptionPane.showInputDialog(null, "Edit Course Name");
        String Inst = JOptionPane.showInputDialog(null, "Edit Instructor");
        String time = JOptionPane.showInputDialog(null, "Edit time (HH:MM)");
        String date = JOptionPane.showInputDialog(null, "Edit date(YYYY-MM-DD)");
        String GyID = JOptionPane.showInputDialog(null, "At which gym will this course take place at? (Enter GymID");
        String RID = JOptionPane.showInputDialog(null, "Which room will the course take place in? (Enter RoomID");
        
        String SQL = "UPDATE Courses SET (CourseName, InstructorID, time, date, GymID, RoomID) = ('" + CN + "','" + Inst + "','" + time + "','" + date +  "','" + GyID +  "','" + RID + "') WHERE CourseID ='" + GID + "';" ;
     
        Statement stm= null;
        try{
        stm=conn.createStatement();
        stm.executeUpdate(SQL);
        }catch (SQLException sqle){
        JOptionPane.showMessageDialog(null, sqle.getMessage());
        }
        }
        
        if (result4 == 2){
        String CN = JOptionPane.showInputDialog("Enter course name");
        String IID = JOptionPane.showInputDialog("Enter InstructorID for the instuctor that will hold this course");
        String time = JOptionPane.showInputDialog("Enter time of course (HH:MM");
        String date = JOptionPane.showInputDialog("Enter date of course (YYYY-MM-DD)");
        String GID = JOptionPane.showInputDialog("Enter GymID");
        String RID = JOptionPane.showInputDialog("Enter RoomID");
        String ID = UUID.randomUUID().toString();
        String SQL = "INSERT INTO Courses (CourseID, CourseName, InstructorID, Time, Date, GymID, RoomID) VALUES ('" + ID + "', '" + CN + "', '" + IID + "', '" + time + "', '" + date + "', '" + GID + "', '" + RID + "');";
        Statement stm= null;
        try{
        stm=conn.createStatement();
        stm.executeUpdate(SQL);
        }catch (SQLException sqle){
        JOptionPane.showMessageDialog(null, sqle.getMessage());
        }
        }

        if (result4 == 3){
        String SQLL = "Select * from Instructors;";
        Statement stm = null;
        ResultSet rs = null;
        try{
        stm=conn.createStatement();
        rs = stm.executeQuery(SQLL);
        String out ="";
        while (rs.next()){
        out +=(rs.getString("InstructorID") + " \tl " + rs.getString("FirstName") + " \tl " + rs.getString("LastName") +  "\n");
        }
        JOptionPane.showMessageDialog(null, out);
        
        }catch (SQLException sqle){
          JOptionPane.showMessageDialog(null, sqle.getMessage());
        
        }
        }

        if (result4 == 4){
        String ID = JOptionPane.showInputDialog("Enter Instructor ID (YYMMDDXXXX)");
        String FN = JOptionPane.showInputDialog("Enter First Name");
        String LN = JOptionPane.showInputDialog("Enter last Name");
        String SQL = "INSERT INTO Instructors (InstructorID, FirstName, LastName) VALUES ('" + ID + "', '" + FN + "', '" + LN + "');";
        Statement stm= null;
        try{
        stm=conn.createStatement();
        stm.executeUpdate(SQL);
        }catch (SQLException sqle){
        JOptionPane.showMessageDialog(null, sqle.getMessage());
        }
        }
        
        
        
        if (result4 == 5){
        System.exit(0);
        }
        }


        
        if (result ==3){
        
        Object[] options5 = {"Enroll Customer in Course", "View CourseEnrollment by Customer", "View CourseEnrollment by Course", "Quit"};
        JPanel panel5 = new JPanel();
        panel5.add(new JLabel("Enrollment"));
        int result5 = JOptionPane.showOptionDialog(null, panel5, "Enter a Number",
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
        options5, null);
        
        
        if (result5 == 0){
        String CID = JOptionPane.showInputDialog("Enter CustomerID for the customer you wish to enroll");
        String COID = JOptionPane.showInputDialog("Enter CourseID for the course you wih to enroll the customer in");
        String EID = UUID.randomUUID().toString();
        String SQL = "INSERT INTO CourseEnrollment (EnrollmentID, CourseID, CustomerID) VALUES ('" + EID + "', '" + COID + "', '" + CID + "');";
        Statement stm= null;
        try{
        stm=conn.createStatement();
        stm.executeUpdate(SQL);
        }catch (SQLException sqle){
        JOptionPane.showMessageDialog(null, sqle.getMessage());
        }
        }

        
        
        if (result5 == 1){
        String CID =JOptionPane.showInputDialog("Enter CustomerID (YYMMDDXXXX)");
        String SQLL = "Select CourseEnrollment.CourseID, Courses.CourseName, Courses.Time, Courses.Date, CourseEnrollment.CustomerID, Customers.FirstName, Customers.LastName from CourseEnrollment INNER JOIN Courses ON Courses.CourseID = CourseEnrollment.CourseID INNER JOIN Customers ON CourseEnrollment.CustomerID = Customers.CustomerID WHERE CourseEnrollment.CustomerID ='" + CID +"';";
        Statement stm = null;
        ResultSet rs = null;
        try{
        stm=conn.createStatement();
        rs = stm.executeQuery(SQLL);
        String out ="";
        while (rs.next()){
        out +=(rs.getString("CustomerID") + " \tl " + rs.getString("FirstName") + " \tl " + rs.getString("LastName") +  " \tl " + rs.getString("CourseName") + " \tl " + rs.getString("Time") + " \tl " + rs.getString("Date") + " \tl " + rs.getString("CourseID") + "\n");
        }
        JOptionPane.showMessageDialog(null, out);
        
        }catch (SQLException sqle){
          JOptionPane.showMessageDialog(null, sqle.getMessage());
        
        }
        }
        
        
        if (result5 == 2){
        String CID =JOptionPane.showInputDialog("Enter CourseID");
        String SQLL = "Select CourseEnrollment.CourseID, Courses.CourseName, Courses.Time, Courses.Date, CourseEnrollment.CustomerID, Customers.FirstName, Customers.LastName from CourseEnrollment INNER JOIN Courses ON Courses.CourseID = CourseEnrollment.CourseID INNER JOIN Customers ON CourseEnrollment.CustomerID = Customers.CustomerID WHERE CourseEnrollment.CourseID ='" + CID +"';";
        Statement stm = null;
        ResultSet rs = null;
        try{
        stm=conn.createStatement();
        rs = stm.executeQuery(SQLL);
        String out ="";
        while (rs.next()){
        out +=(rs.getString("CourseID") + " \tl " + rs.getString("CourseName") + " \tl " + rs.getString("Time") +  " \tl " + rs.getString("Date") + " \tl " + rs.getString("CustomerID") + " \tl " + rs.getString("FirstName") + " \tl " + rs.getString("LastName") + "\n");
        }
        JOptionPane.showMessageDialog(null, out);
        
        }catch (SQLException sqle){
          JOptionPane.showMessageDialog(null, sqle.getMessage());
        
        }
        }

        
        
        
        
        
         if (result5 == 3){
        System.exit(0);
        }
        }


        


        if (result == 4) {
        System.exit(0);
        
        
        
        }
}
}
}   
      
       
       
       


       
       


        

    
     
        
   /* if (result == 0) {
         String Fname = JOptionPane.showInputDialog(null, "Provide first name");
         String Lname = JOptionPane.showInputDialog(null, "Provide last name");
         String DOB = JOptionPane.showInputDialog(null, "Provide date of birth (10 numbers)");
         String Email = JOptionPane.showInputDialog(null, "Provide email");
         String Address = JOptionPane.showInputDialog(null,"Provide address");
         String City = JOptionPane.showInputDialog(null,"Provide City");
         String Phone = JOptionPane.showInputDialog(null, "Provide phone number.");
         String Date = JOptionPane.showInputDialog(null, "Provide the date of today (YYYY-MM-DD)");
         String SQL = "INSERT INTO Customers (FirstName, LastName, CustomerID, Email, Address, City, PhoneNo, DateJoined) VALUES ('" + Fname + "', '" +Lname + "', '" + DOB + "', '" + Email + "','" + Address + "','" + City + "','" + Phone + "','" + Date + "');";
        Statement stm= null;
       try{
       stm=conn.createStatement();
       stm.executeUpdate(SQL);
       }catch (SQLException sqle){
       JOptionPane.showMessageDialog(null, sqle.getMessage());
       }
       }
    
    
    
    if (result == 1) {
         String ID = JOptionPane.showInputDialog(null, "Provide date of birth of the customer whos info you want to update (10 numbers)");
         String Fname = JOptionPane.showInputDialog(null, "Provide first name");
         String Lname = JOptionPane.showInputDialog(null, "Provide last name");
         String Email = JOptionPane.showInputDialog(null, "Provide email");
         String Address = JOptionPane.showInputDialog(null,"Provide address");
         String City = JOptionPane.showInputDialog(null,"Provide City");
         String Phone = JOptionPane.showInputDialog(null, "Provide phone number.");
         String SQL = "UPDATE Customers SET (FirstName, Lastname, Email, Address, City, PhoneNo) = ('" + Fname + "','" + Lname + "','" + Email + "','" + Address + "','" + City + "','" + Phone + "') WHERE CustomerID ='" + ID + "';" ;
     
    Statement stm= null;
       try{
       stm=conn.createStatement();
       stm.executeUpdate(SQL);
       }catch (SQLException sqle){
       JOptionPane.showMessageDialog(null, sqle.getMessage());
       }
        
    }
    
    if (result ==2){
    String ID = JOptionPane.showInputDialog("Who do you want to look up? Provide date of birth (10 Numbers) ");
    String SQLL = "Select * from Customers where CustomerID = " + ID + ";";
    Statement stm = null;
    ResultSet rs = null;
    try{
    stm=conn.createStatement();
    rs = stm.executeQuery(SQLL);
    String out ="";
    while (rs.next()){
    out +=(rs.getString("FirstName") + " \tl " + rs.getString("LastName") + " \tl " + rs.getString("CustomerID") + " \tl " + rs.getString("Email") + " \tl " + rs.getString("Address") + " \tl " + rs.getString("City") + " \tl " + rs.getString("PhoneNo") + " \tl " + rs.getString("DateJoined") + "\n");
    }
    JOptionPane.showMessageDialog(null, out);
    
    }catch (SQLException sqle){
       JOptionPane.showMessageDialog(null, sqle.getMessage());
       }
    
        }

    
    if (result ==3){
    String ID = JOptionPane.showInputDialog("Enter CustomerID for the customer you wish to create membership");
    String StartDate = JOptionPane.showInputDialog("Enter the starting date for the membership (YYYY-MM-DD)");
    String Mtier = JOptionPane.showInputDialog("What tier will this membership be?");
    String MID = UUID.randomUUID().toString();
    String MCard = UUID.randomUUID().toString();
    String SQL = "INSERT INTO Membership (MembershipID, MembershipStart, CustomerID, MemberCard, MembershipTierID) VALUES ('" + MID + "', '" + StartDate + "', '" + ID + "', '" + MCard + "','" + Mtier + "');";
       Statement stm= null;
       try{
       stm=conn.createStatement();
       stm.executeUpdate(SQL);
       }catch (SQLException sqle){
       JOptionPane.showMessageDialog(null, sqle.getMessage());
       }
    
    
    
    
    
    }
    
    if (result ==4){
    
    String ID = JOptionPane.showInputDialog("Whos memberships would you like to view? Provide date of birth (10 Numbers) ");
    String SQL = "Select * from Membership where CustomerID = " + ID + ";";
    Statement stm = null;
    ResultSet rs = null;
    try{
    stm=conn.createStatement();
    rs = stm.executeQuery(SQL);
    String out ="";
    while (rs.next()){
    out +=(rs.getString("MembershipID") + " \tl " + rs.getString("MembershipTierID") + " \tl " + rs.getString("MembershipStart") + " \tl " + rs.getString("MembershipEnd") + " \tl " + rs.getString("CustomerID") + " \tl " + rs.getString("MemberCard") + "\n");
    }
    JOptionPane.showMessageDialog(null, out);
    
    }catch (SQLException sqle){
       JOptionPane.showMessageDialog(null, sqle.getMessage());
       }
   
    if (result ==5){
    
    }
    
    if (result ==6){
    
    }
    
    if (result ==7){
    
    }
    
    if (result ==8){
    
    }
    
    if (result ==9){
    
    }
    
    if (result ==10){
    
    }
    
    if (result ==11){
    
    }
    
    if (result ==12){
    
    }    


    
    
    
    
    
    
    }
    
    if (result ==13){
    System.exit(0);
    
    }
    
    }
    
}
}
*/