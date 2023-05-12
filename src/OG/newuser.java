package OG;




import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;


/**
 *
 * @author kotar
 */
public class newuser extends javax.swing.JFrame {

    /**
     * Creates new form newuser
     */
    public newuser() {
        
        initComponents();
    }

public boolean valid(){
    String firstName = u_fn.getText();
        String lastName = u_ln.getText();
        String phone = u_p.getText();
        String currentAddress = u_ca.getName();
        String residentialAddress = u_ra.getText();
        Date dateOfBirth = u_dob.getDate();
         long l1 = dateOfBirth.getTime();
        java.sql.Date sissueDate = new java.sql.Date(l1);
        String password = u_p.getText();
        String details = u_t.getText();
        String email = u_e.getText();
        String val = key.getText();
        
        String[] Captcha = {"IO89TC87","A75FKLS0","785TY98D","J0A0V0A0","MJIKBOHD","hQ7804yv","c8VveG8c"};
        boolean isValidCaptcha = false;
        for (String c : Captcha) {
            if (val.equals(c)) {
            isValidCaptcha = true;
            break;
            }
        }

        if (!isValidCaptcha) {
            JOptionPane.showMessageDialog(this, "Invalid Captcha");
            }   
        
        if(firstName.equals("") || lastName.equals("") || phone.equals("")){
            JOptionPane.showMessageDialog(this, "Please enter mandatory fields");
            return false;
        }
        if (email.equals("") || !email.matches("^.+@.+\\..+$")){
            JOptionPane.showMessageDialog(this, "Please enter valid Email");
            return false;
        }
        
        return true;
}
public void login(){
        // Customer details
        String firstName = u_fn.getText();
        String lastName = u_ln.getText();
        String phone = u_p.getText();
        String currentAddress = u_ca.getName();
        String residentialAddress = u_ra.getText();
        Date dateOfBirth = u_dob.getDate();
         long l1 = dateOfBirth.getTime();
        java.sql.Date sissueDate = new java.sql.Date(l1);
        String password = u_p.getText();
        String details = u_t.getText();
        String email = u_e.getText();
        

        // SQL query for inserting a new customer record
        String query = "INSERT INTO users (email,password,name,contact,sissueDate,details,status) VALUES (?, ?, ?, ?, ?, ?,'user')";
        try (  Connection con = BACKENDCONNECTION.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            // Set parameter values for the SQL query
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, firstName);
//            stmt.setString(4, lastName);
            stmt.setString(4, phone);
            stmt.setDate(5, sissueDate);
            stmt.setString(6, details);
            

            // Execute the SQL query and get the number of rows inserted
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new customer was inserted successfully!");
            }

        } catch (SQLException ex) {
            System.out.println("An error occurred while inserting the customer: " + ex.getMessage());
        }
        
        String query1 = "INSERT INTO student_details (name) VALUES (?)";
        try (  Connection con = BACKENDCONNECTION.getConnection();
             PreparedStatement stmt = con.prepareStatement(query1)) {

            // Set parameter values for the SQL query
                   stmt.setString(1, firstName);
//           
            

            // Execute the SQL query and get the number of rows inserted
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new customer was inserted successfully!");
            }

        } catch (SQLException ex) {
            System.out.println("An error occurred while inserting the customer: " + ex.getMessage());
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        u_ph = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        u_fn = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        u_ln = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        u_ra = new javax.swing.JTextArea();
        u_ca = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        u_p = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        u_t = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        u_e = new javax.swing.JTextField();
        u_dob = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        Clear = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        key = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(242,242,242));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 346, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 738, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 951, -1, -1));

        jPanel3.setBackground(new java.awt.Color(242,242,242));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 52, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(352, 951, -1, 726));

        kGradientPanel2.setkEndColor(new java.awt.Color(204, 255, 255));
        kGradientPanel2.setkStartColor(new java.awt.Color(255, 255, 255));
        kGradientPanel2.setPreferredSize(new java.awt.Dimension(942, 380));
        kGradientPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 36)); // NOI18N
        jLabel3.setText("Sign Up");
        jLabel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        kGradientPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(444, 66, -1, -1));

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Last Name*");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        kGradientPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 254, -1, 31));

        u_ph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                u_phActionPerformed(evt);
            }
        });
        u_ph.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                u_phKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                u_phKeyTyped(evt);
            }
        });
        kGradientPanel2.add(u_ph, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 305, 187, 31));

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Password*");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        kGradientPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(572, 254, 75, 31));

        u_fn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                u_fnActionPerformed(evt);
            }
        });
        u_fn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                u_fnKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                u_fnKeyTyped(evt);
            }
        });
        kGradientPanel2.add(u_fn, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 205, 187, 31));

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("First Name*");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        kGradientPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 204, -1, 31));

        u_ln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                u_lnActionPerformed(evt);
            }
        });
        u_ln.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                u_lnKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                u_lnKeyTyped(evt);
            }
        });
        kGradientPanel2.add(u_ln, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 255, 187, 31));

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Current Address");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        kGradientPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 354, -1, 31));

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Phone*");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        kGradientPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 304, -1, 31));

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Residential Address*");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        kGradientPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 479, -1, 31));

        u_ra.setColumns(20);
        u_ra.setRows(5);
        jScrollPane1.setViewportView(u_ra);

        kGradientPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 479, 187, -1));

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        u_ca.setViewportView(jTextArea2);

        kGradientPanel2.add(u_ca, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 363, 187, -1));

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("DOB*");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        kGradientPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 210, -1, 31));

        u_p.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                u_pActionPerformed(evt);
            }
        });
        u_p.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                u_pKeyTyped(evt);
            }
        });
        kGradientPanel2.add(u_p, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 260, 234, 31));

        u_t.setColumns(20);
        u_t.setRows(5);
        jScrollPane3.setViewportView(u_t);

        kGradientPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 304, -1, -1));

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Tell us about yourself");
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        kGradientPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(572, 304, -1, 31));

        u_e.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                u_eComponentAdded(evt);
            }
        });
        u_e.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                u_eActionPerformed(evt);
            }
        });
        kGradientPanel2.add(u_e, new org.netbeans.lib.awtextra.AbsoluteConstraints(761, 409, 177, 31));

        u_dob.setPreferredSize(new java.awt.Dimension(64, 22));
        kGradientPanel2.add(u_dob, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 204, 234, 32));

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Captcha*");
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        kGradientPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 480, -1, 31));

        jButton6.setBackground(new java.awt.Color(255, 102, 102));
        jButton6.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N
        jButton6.setText("Back");
        jButton6.setBorder(null);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        kGradientPanel2.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 650, 70, 35));

        jButton7.setBackground(new java.awt.Color(255, 204, 204));
        jButton7.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N
        jButton7.setText("GENERATE KEY");
        jButton7.setBorder(null);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        kGradientPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 590, 100, 35));

        Clear.setBackground(new java.awt.Color(255, 255, 153));
        Clear.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N
        Clear.setText("Clear");
        Clear.setBorder(null);
        Clear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Clear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClearMouseClicked(evt);
            }
        });
        Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearActionPerformed(evt);
            }
        });
        kGradientPanel2.add(Clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 650, 70, 35));

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Email*");
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        kGradientPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 410, -1, 31));

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 0, 0));
        jLabel11.setText("<html>\n<div style=\"text-align:center;\">\n      NOTE <br> Press NEXT and ask for KEY\n  </div>\n<html>\n");
        kGradientPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 530, -1, -1));

        key.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                keyComponentAdded(evt);
            }
        });
        key.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keyActionPerformed(evt);
            }
        });
        kGradientPanel2.add(key, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 480, 177, 31));

        jButton8.setBackground(new java.awt.Color(102, 255, 102));
        jButton8.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N
        jButton8.setText("Submit");
        jButton8.setBorder(null);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton8MouseClicked(evt);
            }
        });
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        kGradientPanel2.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 650, 70, 35));

        getContentPane().add(kGradientPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 0, 1150, 860));

        kGradientPanel1.setkEndColor(new java.awt.Color(0, 153, 153));
        kGradientPanel1.setkStartColor(new java.awt.Color(204, 255, 204));
        kGradientPanel1.setPreferredSize(new java.awt.Dimension(346, 726));
        kGradientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/Signup.gif"))); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(346, 726));
        jLabel1.setRequestFocusEnabled(false);
        kGradientPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 340, 463));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("<html>\n  <head>\n    <style>\n      .blue {\n        color: blue;\n      }\n      .red {\n        color: red;\n      }\nh1 {\n        text-align: center;\n      }\n    </style>\n  </head>\n  <body>\n    <h1><span class=\"blue\">Thank you</span> <span class=\"red\"><br>for upgrading to Digital</span></h1>\n  </body>\n</html>\n");
        kGradientPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 326, 156));

        getContentPane().add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 890));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void u_eActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_u_eActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_u_eActionPerformed

    private void u_pActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_u_pActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_u_pActionPerformed

    private void u_lnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_u_lnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_u_lnActionPerformed

    private void u_fnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_u_fnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_u_fnActionPerformed

    private void u_phActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_u_phActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_u_phActionPerformed

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        Login l = new Login();
        l.show();
        dispose();

    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton7MouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
//        if(valid()){
//            login();
//        }
        ChatBot bot = new ChatBot();
        bot.show();
//        String text1 = jTextField2.getText();
//        String text2 = jTextField3.getText();
//        String text3 = jTextField1.getText();
//        String text4 = jTextArea1.getText();
//        String text5 = jDateChooser1.getDate();
//        String text6 = jTextField5.getText();
//        String text7 = jTextField6.getText();
//        if (text1.isEmpty() || text2.isEmpty() || text3.isEmpty() || text4.isEmpty() || text5.isEmpty() || text6.isEmpty() || text7.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//        else{
//            
//        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void ClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClearMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ClearMouseClicked

    private void ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearActionPerformed
        // TODO add your handling code here:
        u_fn.setText(null);
        u_ln.setText(null);
        u_ph.setText(null);
        jTextArea2.setText(null);
        u_ra.setText(null);
        u_p.setText(null);
        u_t.setText(null);
        u_e.setText(null);
    }//GEN-LAST:event_ClearActionPerformed

    private void u_eComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_u_eComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_u_eComponentAdded

    private void u_fnKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_u_fnKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(!Character.isAlphabetic(c)){
            evt.consume();
        }
    }//GEN-LAST:event_u_fnKeyTyped

    private void u_lnKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_u_lnKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(!Character.isAlphabetic(c)){
            evt.consume();
        }
    }//GEN-LAST:event_u_lnKeyTyped

    private void u_phKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_u_phKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if(!Character.isDigit(c)){
            evt.consume();
        }
    }//GEN-LAST:event_u_phKeyTyped

    private void u_pKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_u_pKeyTyped
//        // TODO add your handling code here:
//        char c = evt.getKeyChar();
//        if(!Character.isAlphabetic(c)){
//            evt.consume();
//        }
    }//GEN-LAST:event_u_pKeyTyped

    private void u_phKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_u_phKeyReleased
        // TODO add your handling code here:
        String phone = u_ph.getText();
        if(phone.matches("^[0-9]*$") && phone.length() == 10){
            u_ph.setBackground(Color.green);
        }
        else
            u_ph.setBackground(Color.red);
    }//GEN-LAST:event_u_phKeyReleased

    private void u_fnKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_u_fnKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_u_fnKeyReleased

    private void u_lnKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_u_lnKeyReleased
        // TODO add your handling code here:
        
    }//GEN-LAST:event_u_lnKeyReleased

    private void keyComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_keyComponentAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_keyComponentAdded

    private void keyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_keyActionPerformed

    private void jButton8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MouseClicked
        // TODO add your handling code here:
                if(valid()){
//                login()
                About l = new About();
                l.show();
                dispose();
       }
    }//GEN-LAST:event_jButton8MouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        if(valid()){
            login();
       }
//login();
    }//GEN-LAST:event_jButton8ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(newuser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(newuser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(newuser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(newuser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new newuser().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Clear;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea2;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private javax.swing.JTextField key;
    private javax.swing.JScrollPane u_ca;
    private com.toedter.calendar.JDateChooser u_dob;
    private javax.swing.JTextField u_e;
    private javax.swing.JTextField u_fn;
    private javax.swing.JTextField u_ln;
    private javax.swing.JTextField u_p;
    private javax.swing.JTextField u_ph;
    private javax.swing.JTextArea u_ra;
    private javax.swing.JTextArea u_t;
    // End of variables declaration//GEN-END:variables

    private void close() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
