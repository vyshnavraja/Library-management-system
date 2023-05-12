package digital;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import library.BACKENDCONNECTION;


/**
 *
 * @author vyshnav
 */
public class Loginpage extends javax.swing.JFrame {
  
    public Loginpage() {
        initComponents();
    }

     public boolean validatelogin(){
    String name =Sign_username.getText();
    String pass =Signup_page.getText();
   
    if (name.equals("")){
     JOptionPane.showMessageDialog(this, "Please enter username");
      
      return false;
    }
    if (pass.equals("")){
     JOptionPane.showMessageDialog(this, "Please enter password");
      return false;
    }
           return true;

    }
     //verify credencials
//      public void login() {
//    String name = Sign_username.getText();
//    String pass = Signup_page.getText();
//
//    try {
//        Connection con = BACKENDCONNECTION.getConnection();
//        String sql = "SELECT * FROM users WHERE name=? AND password=? AND status=?";
//        PreparedStatement ps = con.prepareStatement(sql);
//        ps.setString(1, name);
//        ps.setString(2, pass);
//        ps.setString(3, "admin"); // or "librarian", depending on the user's status
//        ResultSet rs = ps.executeQuery();
//        if (rs.next()) {
//            String user = name;
//            String status = rs.getString("status");
//            if (status.equals("admin")) {
//                // login as admin
//               HomePage home = new HomePage();
//               home.y(user.toUpperCase());
//               home.setVisible(true);
//               dispose();
//            } else if (status.equals("librarian")) {
//                // login as librarian
//                LibrarianPage librarian = new LibrarianPage();
//                librarian.y(user.toUpperCase());
//                librarian.setVisible(true);
//                dispose();
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Wrong username or password");
//        }
//    } catch (HeadlessException | SQLException e) {
//        System.err.println(e);
//    }
//}
public void login() {
    String name = Sign_username.getText();
    String pass = Signup_page.getText();

    try {
        Connection con = BACKENDCONNECTION.getConnection();
        String sql = "SELECT * FROM users WHERE name = ? AND password = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, pass);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            String status = rs.getString("status");
            if (status.equals("admin")) {
                // User is an admin, log in as admin
                String user = name;
                HomePage home = new HomePage();
               home.y(user.toUpperCase());
               home.setVisible(true);
               dispose();
            } else if (status.equals("librarian")) {
                // User is a librarian, log in as librarian
                String user = name;
                 LibrarianPage librarian = new LibrarianPage();
                librarian.y(user.toUpperCase());
                librarian.setVisible(true);
                dispose();
            } else if (status.equals("user")) {
                // User is a librarian, log in as librarian
                String user = name;
                 user u = new user();
                u.y(user.toUpperCase());
                u.setVisible(true);
                dispose();
            } else {
                // User has an invalid status, show error message
                JOptionPane.showMessageDialog(this, "Invalid status");
            }
        } else {
            // Invalid username or password
            JOptionPane.showMessageDialog(this, "Invalid username or password");
        }
    } catch (HeadlessException | SQLException e) {
        e.printStackTrace();
    }
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
        jLabel6 = new javax.swing.JLabel();
        rSMaterialButtonCircle1 = new rojerusan.RSMaterialButtonCircle();
        Sign_Signup = new rojerusan.RSMaterialButtonCircle();
        Sign_username = new app.bolivia.swing.JCTextField();
        jLabel15 = new javax.swing.JLabel();
        Signup_page = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setForeground(new java.awt.Color(51, 0, 102));
        jLabel11.setText("Anonymous");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(84, 24, -1, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons/library-3.png.png"))); // NOI18N
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 770, 670));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 0, 102));
        jLabel13.setText("Digital library");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 20, -1, 80));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(153, 0, 153));
        jLabel14.setText("Welcome To");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, -1, -1));

        jPanel2.setBackground(new java.awt.Color(102, 0, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Hi, welcome");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Login Page");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 70, 256, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Username");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 220, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons/icons8_Account_50px.png"))); // NOI18N
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Password");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, -1, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/icons/icons8_Secure_50px.png"))); // NOI18N
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, -1, -1));

        rSMaterialButtonCircle1.setBackground(new java.awt.Color(0, 51, 51));
        rSMaterialButtonCircle1.setText("LOGIN");
        rSMaterialButtonCircle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonCircle1ActionPerformed(evt);
            }
        });
        rSMaterialButtonCircle1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rSMaterialButtonCircle1KeyPressed(evt);
            }
        });
        jPanel2.add(rSMaterialButtonCircle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 440, 268, 50));

        Sign_Signup.setBackground(new java.awt.Color(255, 255, 255));
        Sign_Signup.setForeground(new java.awt.Color(0, 0, 0));
        Sign_Signup.setText("SIGNUP");
        Sign_Signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Sign_SignupActionPerformed(evt);
            }
        });
        jPanel2.add(Sign_Signup, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 520, 289, 49));

        Sign_username.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        Sign_username.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        Sign_username.setPhColor(new java.awt.Color(255, 255, 255));
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
        Sign_username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sign_usernameKeyPressed(evt);
            }
        });
        jPanel2.add(Sign_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, -1, -1));

        jLabel15.setFont(new java.awt.Font("Rockwell", 0, 48)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("x");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel15MouseExited(evt);
            }
        });
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 20, -1, 39));

        Signup_page.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        Signup_page.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 255, 255)));
        Signup_page.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Signup_pageKeyPressed(evt);
            }
        });
        jPanel2.add(Signup_page, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 340, 208, 42));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/a.jpg"))); // NOI18N
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(-1, -4, 360, 760));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1033, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rSMaterialButtonCircle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle1ActionPerformed
    if  ( validatelogin())
   {
       login();
   }
    }//GEN-LAST:event_rSMaterialButtonCircle1ActionPerformed

    private void Sign_SignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Sign_SignupActionPerformed
        new SignUp().setVisible(true);
        dispose();
    }//GEN-LAST:event_Sign_SignupActionPerformed

    private void Sign_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Sign_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Sign_usernameActionPerformed

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
         jLabel15.setForeground(Color.red);
        int close = JOptionPane.showConfirmDialog(this, "Do you want to close this application", "EXIT", JOptionPane.YES_NO_OPTION);
        if (close == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_jLabel15MouseClicked

    private void Sign_usernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Sign_usernameFocusLost

    }//GEN-LAST:event_Sign_usernameFocusLost

    private void rSMaterialButtonCircle1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rSMaterialButtonCircle1KeyPressed
     


    }//GEN-LAST:event_rSMaterialButtonCircle1KeyPressed

    private void Signup_pageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Signup_pageKeyPressed
   int keyCode = evt.getKeyCode();
    if (keyCode == KeyEvent.VK_ENTER) {
        if  ( validatelogin())
   {
       login();
   }
        // Perform action when Enter key is pressed
    } else if (keyCode == KeyEvent.VK_ESCAPE) {
        // Perform action when Escape key is pressed
    }
    }//GEN-LAST:event_Signup_pageKeyPressed

    private void Sign_usernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sign_usernameKeyPressed
        int keyCode = evt.getKeyCode();
       if (keyCode == KeyEvent.VK_ENTER) {
        Signup_page.requestFocusInWindow();
    } else if (keyCode == KeyEvent.VK_ESCAPE) {
        // Perform action when Escape key is pressed
    }
    }//GEN-LAST:event_Sign_usernameKeyPressed

    private void jLabel15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseExited
        jLabel15.setForeground(Color.white);  // TODO add your handling code here:
    }//GEN-LAST:event_jLabel15MouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(() -> {
            new Loginpage().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSMaterialButtonCircle Sign_Signup;
    private app.bolivia.swing.JCTextField Sign_username;
    private javax.swing.JPasswordField Signup_page;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private rojerusan.RSMaterialButtonCircle rSMaterialButtonCircle1;
    // End of variables declaration//GEN-END:variables
}
