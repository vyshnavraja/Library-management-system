package OG;


import java.sql.*;
import java.util.UUID;
import library.BACKENDCONNECTION;
public class test1 extends javax.swing.JFrame {

    /**
     * Creates new form test
     */
    public test1() {
        initComponents();
    }




    private String generateRandomKey() {
        return UUID.randomUUID().toString();
    }

private boolean isBookAvailable(String bookId) {
    Connection connection = BACKENDCONNECTION.getConnection();
    try {
        String query = "SELECT Quantity FROM book_details WHERE book_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int quantity = resultSet.getInt("Quantity");
                    if (quantity <= 0) {
                        System.out.println("No books available.");
                        return false;
                    }
                } else {
                    System.out.println("Book not found.");
                    return false;
                }
            }
        }
    } catch (SQLException e) {
        System.out.println(e);
    }
    return true;
}





    private void insertUserKey(String bookId, String studentId, String keyValue, String keyStatus) {
        Connection connection =BACKENDCONNECTION.getConnection();
         try {
            String query = "INSERT INTO user_key (book_id, student_id, key_value, key_status) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, bookId);
                statement.setString(2, studentId);
                statement.setString(3, keyValue);
                statement.setString(4, keyStatus);

                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Data inserted successfully into user_key table.");
                } else {
                    System.out.println("Failed to insert data into user_key table.");
                }
            }
        } catch (SQLException e) {
           System.out.println(e);
        }
    }

    private void updateBookDetails(String bookId) {
        Connection connection =BACKENDCONNECTION.getConnection();
        try  {
            String query = "UPDATE book_details SET Quantity = Quantity - 1 WHERE book_id IN (SELECT book_id FROM user_key WHERE key_status = 'pending')";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Quantity decremented successfully in book_details table.");
                } else {
                    System.out.println("Failed to decrement Quantity in book_details table.");
                }
            }
        } catch (SQLException e) {
             System.out.println(e);
        }

        }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        qeew = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        qwwe = new javax.swing.JTextField();
        a = new javax.swing.JLabel();
        q3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("book");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, -1, -1));
        jPanel1.add(qeew, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 149, -1));

        jLabel2.setText("student");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, -1, -1));
        jPanel1.add(qwwe, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 149, -1));

        a.setText("id");
        jPanel1.add(a, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 290, -1));

        q3.setText("pre register");
        q3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                q3ActionPerformed(evt);
            }
        });
        jPanel1.add(q3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void q3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_q3ActionPerformed
            
        String bookId = qeew.getText();
        String studentId = qwwe.getText();
        String keyStatus = "pending";

           

            if (isBookAvailable(bookId)) {
                 String keyValue = generateRandomKey();
            a.setText(keyValue);
                insertUserKey(bookId, studentId, keyValue, keyStatus);
                updateBookDetails(bookId);
            }
    }//GEN-LAST:event_q3ActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(test1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new test1().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel a;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton q3;
    private javax.swing.JTextField qeew;
    private javax.swing.JTextField qwwe;
    // End of variables declaration//GEN-END:variables
}
