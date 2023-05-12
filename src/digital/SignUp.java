package digital;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import library.BACKENDCONNECTION;
import java.sql.ResultSet;
import java.util.Random;

/**
 *
 * @author vyshnav
 */
public class SignUp extends javax.swing.JFrame {
int otp;
String ACCOUNT_SID = "ACfaaee0a11ae4e219d77a93954ba0d754";
String AUTH_TOKEN = "c5f8d86610fb446af4cdb9b372329bf3";
    
    public SignUp() {
        initComponents();
    }

      public void insert(){
    String name =Sign_username.getText();
    String pass =Sign_password.getText();
    String email =Sign_email.getText();
    String contact =Sign_contact.getText();
    String status ="librarian";
    System.out.print(status);
        try {
           Connection con =BACKENDCONNECTION.getConnection();
           String sql ="insert into users(name,password,email,contact,status) values (?,?,?,?,?)";
            PreparedStatement ps =con.prepareStatement(sql);
            ps.setString(1,name); 
            ps.setString(2,pass);
            ps.setString(3,email); 
            ps.setString(4,contact);
            ps.setString(5,status);
            
            int updatedrowcount =ps.executeUpdate();
            
            if (updatedrowcount > 0){
                JOptionPane.showMessageDialog(this, "RECORD INSERT SUCESSFULLY");
                 Loginpage login = new Loginpage();
                 login.setVisible(true);
                 dispose();
                
            }
            else{
                JOptionPane.showMessageDialog(this, "RECORD INSERT FAILED");
            }
        } catch (SQLException e) {
            System.out.print(e);
        }
    }
      
      //Validation
      public boolean validateSignup(){
    String name =Sign_username.getText();
    String pass =Sign_password.getText();
    String email =Sign_email.getText();
    String contact =Sign_contact.getText();
    String ors =sign_otp.getText();
    
    if (name.equals("")){
     JOptionPane.showMessageDialog(this, "Please enter username");
      return false;
    }
    if (pass.equals("")){
     JOptionPane.showMessageDialog(this, "Please enter password");
      return false;
    }
    if (email.equals("") || !email.matches("^.+@.+\\..+$")){
     JOptionPane.showMessageDialog(this, "Please enter valid Email");
      return false;
    }
    if (contact.equals("") || !contact.matches("^[0-9]{10}$")){
     JOptionPane.showMessageDialog(this, "Please enter valid Contact");
      return false;
    }
    if (contact.equals("") || !contact.matches("^[0-9]{10}$")){
     JOptionPane.showMessageDialog(this, "Please enter valid Contact");
      return false;
    }
    if (ors.equals(otp)) {
        System.out.println("Generated OTP: " + otp);
        System.out.println("Generated OTP: " + ors);
        JOptionPane.showMessageDialog(this, "Please enter valid otp");
    return false;
    }
      

        
       return true;

    }
      //check duplicate users
   public boolean vailduser(){
       String name =Sign_username.getText();
          boolean isExist = false;
       
       try {
           Connection con =BACKENDCONNECTION.getConnection();
           String sql ="select * from users where name=?";
            PreparedStatement ps =con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs= ps.executeQuery();
            isExist = rs.next();
       } catch (SQLException e) {
       }
       
        return isExist;
   }   
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Sign_password = new app.bolivia.swing.JCTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Sign_email = new app.bolivia.swing.JCTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        Sign_contact = new app.bolivia.swing.JCTextField();
        jLabel10 = new javax.swing.JLabel();
        Sign_username = new app.bolivia.swing.JCTextField();
        sign_otp = new app.bolivia.swing.JCTextField();
        rSMaterialButtonRectangle1 = new rojerusan.RSMaterialButtonRectangle();
        Sign_Signup = new rojerusan.RSMaterialButtonRectangle();
        Login = new rojerusan.RSMaterialButtonRectangle();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setForeground(new java.awt.Color(51, 0, 0));
        jLabel11.setText("Anonymous");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(84, 24, -1, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons/signup-library-icon.png"))); // NOI18N
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 770, 670));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 0, 102));
        jLabel13.setText("Digital library");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, -1, 80));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(153, 0, 153));
        jLabel14.setText("Welcome To");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, -1, -1));

        jPanel2.setBackground(new java.awt.Color(102, 0, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Registration Form");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("SignUp Page");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 256, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Username");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons/icons8_Account_50px.png"))); // NOI18N
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Password");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, -1, -1));

        Sign_password.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        Sign_password.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Sign_password.setPlaceholder("ENTER PASSWORD");
        Sign_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Sign_passwordActionPerformed(evt);
            }
        });
        jPanel2.add(Sign_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons/icons8_Secure_50px.png"))); // NOI18N
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Email");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 300, -1, -1));

        Sign_email.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        Sign_email.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Sign_email.setPlaceholder("ENTER EMAIL");
        Sign_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Sign_emailActionPerformed(evt);
            }
        });
        jPanel2.add(Sign_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons/icons8_Secured_Letter_50px.png"))); // NOI18N
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Contact");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 380, -1, -1));

        Sign_contact.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        Sign_contact.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Sign_contact.setPlaceholder("ENTER CONTACT");
        Sign_contact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Sign_contactActionPerformed(evt);
            }
        });
        jPanel2.add(Sign_contact, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 410, -1, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons/icons8_Google_Mobile_50px.png"))); // NOI18N
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, -1, -1));

        Sign_username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        Sign_username.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        Sign_username.setPlaceholder("ENTER USERNAME");
        Sign_username.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                Sign_usernameFocusLost(evt);
            }
        });
        Sign_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Sign_usernameActionPerformed(evt);
            }
        });
        jPanel2.add(Sign_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, -1, -1));

        sign_otp.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        sign_otp.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        sign_otp.setPlaceholder("ENTER OTP");
        sign_otp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sign_otpActionPerformed(evt);
            }
        });
        jPanel2.add(sign_otp, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 500, 150, -1));

        rSMaterialButtonRectangle1.setBackground(new java.awt.Color(255, 255, 255));
        rSMaterialButtonRectangle1.setForeground(new java.awt.Color(0, 0, 0));
        rSMaterialButtonRectangle1.setText("GET OTP");
        rSMaterialButtonRectangle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle1ActionPerformed(evt);
            }
        });
        jPanel2.add(rSMaterialButtonRectangle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 490, 110, 50));

        Sign_Signup.setBackground(new java.awt.Color(255, 255, 255));
        Sign_Signup.setForeground(new java.awt.Color(0, 102, 102));
        Sign_Signup.setText("Signup");
        Sign_Signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Sign_SignupActionPerformed(evt);
            }
        });
        jPanel2.add(Sign_Signup, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 570, 170, 50));

        Login.setBackground(new java.awt.Color(0, 102, 102));
        Login.setText("Login");
        Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginActionPerformed(evt);
            }
        });
        jPanel2.add(Login, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 570, 180, 50));

        jLabel15.setFont(new java.awt.Font("Rockwell", 0, 48)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("x");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, -1, 39));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/a.jpg"))); // NOI18N
        jLabel16.setText("r");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 380, 770));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1001, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Sign_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Sign_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Sign_passwordActionPerformed

    private void Sign_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Sign_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Sign_emailActionPerformed

    private void Sign_contactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Sign_contactActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Sign_contactActionPerformed

    private void Sign_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Sign_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Sign_usernameActionPerformed

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel15MouseClicked

    private void Sign_usernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Sign_usernameFocusLost
      if ( vailduser() ==true)
      {
          JOptionPane.showMessageDialog(this, "username already exist");
     
      }
    }//GEN-LAST:event_Sign_usernameFocusLost

    private void sign_otpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sign_otpActionPerformed
      
    }//GEN-LAST:event_sign_otpActionPerformed

    private void rSMaterialButtonRectangle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle1ActionPerformed
         try {
              // Define the length of the OTP
        int length = 6;
        
        // Define the character set for the OTP
        String characterSet = "0123456789";
        
        // Use a random number generator to generate the OTP
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(characterSet.charAt(random.nextInt(characterSet.length())));
        } 
      // Print the generated OTP
        System.out.println("Generated OTP: " + otp);
        String contact =Sign_contact.getText();
	String phoneNumber = "+91"+contact;
//       System.out.println("Message SID: " + phoneNumber);
//       Initialize the Twilio client
      Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
      
      // Send the SMS message
      Message message = Message.creator(
            new PhoneNumber(phoneNumber),
            new PhoneNumber("+12705618853"), // Twilio phone number
            "Your OTP is: " + otp)
         .create();
      
      
      // Print the message SID
      System.out.println("Message SID: " + message.getSid());
      JOptionPane.showMessageDialog(null, "  otp sent");
	//return stringBuffer.toString();
	} catch (HeadlessException e) {
                System.out.print(e);
//	JOptionPane.showMessageDialog(null,"Error SMS "+e);
	//return "Error "+e;
        }
    }//GEN-LAST:event_rSMaterialButtonRectangle1ActionPerformed

    private void Sign_SignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Sign_SignupActionPerformed
       if (validateSignup() == true){
            if(vailduser()== false){
            insert();
        }else
            {
                JOptionPane.showMessageDialog(this, "username already exist");
            }}
    }//GEN-LAST:event_Sign_SignupActionPerformed

    private void LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginActionPerformed
      Loginpage login = new Loginpage();
      login.setVisible(true);
      dispose();
        
        
    }//GEN-LAST:event_LoginActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(() -> {
            new SignUp().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonRectangle Login;
    private rojerusan.RSMaterialButtonRectangle Sign_Signup;
    private app.bolivia.swing.JCTextField Sign_contact;
    private app.bolivia.swing.JCTextField Sign_email;
    private app.bolivia.swing.JCTextField Sign_password;
    private app.bolivia.swing.JCTextField Sign_username;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle1;
    private app.bolivia.swing.JCTextField sign_otp;
    // End of variables declaration//GEN-END:variables
}
