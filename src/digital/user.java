package digital;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import library.BACKENDCONNECTION;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public final class user extends javax.swing.JFrame {

    String bookName, author;
    int bookId, quantity;
    DefaultTableModel model;
    String studentName, course, branch;
    int studentID;

    public user() {
        initComponents();
        showPieChart();
         GetValueFromDatabase();
//        Defaulterlist();
        setdata();
        setBookDetailstotable();
        setStudentDetailstotable();
    }

    //Calling username from login
    public void y(String user) {
        t_name.setText(user);
    }

    //setting values to the count in homepage
    public void setdata() {
        Statement st = null;
        PreparedStatement ps = null;
        String today = LocalDate.now().toString();
        try {
            Connection con = BACKENDCONNECTION.getConnection();
            st = con.createStatement();
            ResultSet resultSet = st.executeQuery("select count(*) from book_details");
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                count_book.setText(String.valueOf(count));
            }
           

        } catch (SQLException e) {

        }
    }

    //   createing pie chart
    public void showPieChart() {

        //create dataset
        DefaultPieDataset barDataset = new DefaultPieDataset();

        try {
            Connection con = BACKENDCONNECTION.getConnection();
            String sql = "SELECT book_name,COUNT(*) AS issue_count FROM issue_book group by book_id";
            Statement st = con.createStatement();
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                barDataset.setValue(resultSet.getString("book_name"), Double.valueOf(resultSet.getDouble("issue_count")));
            }
        } catch (SQLException e) {
        }
        //create charts
        JFreeChart piechart = ChartFactory.createPieChart("Issue book Details", barDataset, false, true, false);

        PiePlot piePlot = (PiePlot) piechart.getPlot();

        //changing pie chart blocks colors
        piePlot.setSectionPaint("a", new Color(255, 255, 102));
        piePlot.setSectionPaint("b", new Color(102, 255, 102));
        piePlot.setSectionPaint("c", new Color(255, 102, 153));
        piePlot.setSectionPaint("d", new Color(0, 204, 204));

        piePlot.setBackgroundPaint(Color.white);

        //create chartPanel to display chart(graph)
        ChartPanel pieChartPanel = new ChartPanel(piechart);
        ChartPanel barChartPanel = new ChartPanel(piechart);
        Admin_piechart1.removeAll();
        Admin_piechart1.add(barChartPanel, BorderLayout.CENTER);
        Admin_piechart1.validate();

    }

    //setting book details
  
    public void setBookDetailstotable() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/roy", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from book_details");
            while (rs.next()) {
                String booktableId = rs.getString("book_id");
                String booktableName = rs.getString("book_name");
                String Author = rs.getString("Author");
                String Quantity = rs.getString("Quantity");

                Object[] obj = {booktableId, booktableName, Author, Quantity};
               
                model = (DefaultTableModel) tbl_book1.getModel();
                model.addRow(obj);
            }
        } catch (ClassNotFoundException | SQLException e) {
        }
    }

   

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////student//////////////////////////////////////////////////
    //setstudent details
   

  
  public void setStudentDetailstotable() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/roy", "root", "");
            Statement st = con.createStatement();
            String use = t_name.getText().toLowerCase();
            System.out.println(use);
            ResultSet rs = st.executeQuery("select * from student_details where name='user'");
            while (rs.next()) {
                String studenttableId = rs.getString("student_id");
                String studenttableName = rs.getString("name");
                String coursed = rs.getString("course");
                String Quantity = rs.getString("branch");

                Object[] obj = {studenttableId, studenttableName, coursed, Quantity};
                model = (DefaultTableModel) tbl_student.getModel();
                model.addRow(obj);
               

            }
        } catch (ClassNotFoundException | SQLException e) {
        }
    }
    //clear table
    public void clearstudentTable() {
        DefaultTableModel tablemodel = (DefaultTableModel) tbl_student.getModel();
        tablemodel.setRowCount(0);
    }

    public boolean updatestudent() {
        boolean isupdated = false;
        studentID = Integer.parseInt(txt_studentid.getText());
        studentName = txt_stuentname.getText();
        course = combo_course.getSelectedItem().toString();
        branch = combo_branch.getSelectedItem().toString();
        try {
            Connection con = BACKENDCONNECTION.getConnection();
            String sql = "update student_details set name = ?,course = ?,branch = ? where student_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(4, studentID);
            ps.setString(1, studentName);
            ps.setString(2, course);
            ps.setString(3, branch);
            int rowCount = ps.executeUpdate();
            isupdated = rowCount > 0;
        } catch (SQLException e) {
        }
        return isupdated;
    }

  
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////end of student//////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////issue books///////////////////////////////////////////////////////
//    public void getbookdetails() {
//        int bookIda = Integer.parseInt(issue_bookid.getText());
//
//        try {
//            Connection con = BACKENDCONNECTION.getConnection();
//            String sql = "select * from book_details where book_id= ? ";
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setInt(1, bookIda);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                lbl_bookid.setText(rs.getString("book_id"));
//                lbl_bookname.setText(rs.getString("book_name"));
//                lbl_author.setText(rs.getString("Author"));
//                lbl_quantity.setText(rs.getString("Quantity"));
//                lbl_errorbook.setText("");
//            } else {
//                lbl_errorbook.setText("invalid book id");
//            }
//        } catch (SQLException e) {
//        }
//    }
//
//    public void getstudentdetails() {
//        int studentida = Integer.parseInt(issue_studentid.getText());
//        try {
//            Connection con = BACKENDCONNECTION.getConnection();
//            String sql = "select * from student_details where student_id= ? ";
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setInt(1, studentida);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                lbl_studentid.setText(rs.getString("student_id"));
//                lbl_studentname.setText(rs.getString("name"));
//                lbl_course.setText(rs.getString("course"));
//                lbl_branch.setText(rs.getString("branch"));
//                lbl_errorstudent.setText("");
//
//            } else {
//                lbl_errorstudent.setText("invalid student id");
//            }
//        } catch (SQLException e) {
//        }
//    }
    //insert book details

//    public boolean issuebook() {
//        boolean isinserted = false;
//        int bookIda = Integer.parseInt(issue_bookid.getText());
//        int studentida = Integer.parseInt(issue_studentid.getText());
//        String book_name = lbl_bookname.getText();
//        String student_name = lbl_studentid.getText();
//        Date uIssueDate = date_issuedate.getDate();
//        Date udueDate = date_duedate.getDate();
//        long l1 = uIssueDate.getTime();
//        long l2 = udueDate.getTime();
//        java.sql.Date sissueDate = new java.sql.Date(l1);
//        java.sql.Date sudueDate = new java.sql.Date(l2);
//
//        try {
//            Connection con = BACKENDCONNECTION.getConnection();
//            String sql = "insert into issue_book (book_id,book_name,student_id,"
//                    + "name,issue_date,due_date,status)values(?,?,?,?,?,?,?)";
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setInt(1, bookIda);
//            ps.setString(2, book_name);
//            ps.setInt(3, studentida);
//            ps.setString(4, student_name);
//            ps.setDate(5, sissueDate);
//            ps.setDate(6, sudueDate);
//            ps.setString(7, "pending");
//            int rc = ps.executeUpdate();
//            isinserted = rc > 0;
//        } catch (SQLException e) {
//        }
//        return isinserted;
//
//    }

    //update book count
//    public void updatecount() {
//
//        int bookIda = Integer.parseInt(issue_bookid.getText());
//        try {
//            Connection con = BACKENDCONNECTION.getConnection();
//            String sql = "update book_details set Quantity = Quantity - 1 where book_id =?";
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setInt(1, bookIda);
//            int rc = ps.executeUpdate();
//            if (rc > 0) {
//                JOptionPane.showMessageDialog(this, "updated");
//                int initailcount = Integer.parseInt(lbl_quantity.getText());
//                lbl_quantity.setText(Integer.toString(initailcount - 1));
//            } else {
//                JOptionPane.showMessageDialog(this, "imposible");
//            }
//
//        } catch (HeadlessException | NumberFormatException | SQLException e) {
//        }
//    }

    //checking wether already alocated or not
    public boolean isAlreadyissued() {
        boolean isAlreadyissued = false;
        int bookIda = Integer.parseInt(issue_bookid.getText());
        int studentid = Integer.parseInt(issue_studentid.getText());

        try {
            Connection con = BACKENDCONNECTION.getConnection();
            String sql = "select * from issue_book where book_id =? and student_id=? and status=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookIda);
            ps.setInt(2, studentid);
            ps.setString(3, "pending");
            ResultSet rc = ps.executeQuery();
            isAlreadyissued = rc.next();

        } catch (SQLException e) {
        }

        return isAlreadyissued;

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////end of issue//////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////Return Book////////////////////////////////////////////////////////////
    // fetch the issue book details
  
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////End of Return //////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////view records ////////////////////////////////////////////////////////////

    public void GetValueFromDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/roy", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from issue_book where name='user'");
            while (rs.next()) {
                String tablebookid = rs.getString("id");
                String tablebookname = rs.getString("book_name");
                String tablestudentname = rs.getString("name");
                String tableissuedate = rs.getString("issue_date");
                String tablwduedate = rs.getString("due_date");
                String tablestatus = rs.getString("status");

                Object[] obj = {tablebookid, tablebookname, tablestudentname, tableissuedate, tablwduedate, tablestatus};
                model = (DefaultTableModel) t.getModel();
                model.addRow(obj);

            }
        } catch (ClassNotFoundException | SQLException e) {
        }
    }

    public void clearviewTable() {
        DefaultTableModel tablemodel = (DefaultTableModel) t.getModel();
        tablemodel.setRowCount(0);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////end of view///////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////Defaulter////////////////////////////////////////////////////////////////////////
//    public void Defaulterlist() {
//        long l = System.currentTimeMillis();
//        java.sql.Date todaysdate = new java.sql.Date(l);
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/roy", "root", "");
//            PreparedStatement ps = con.prepareStatement("select * from issue_book where due_date < ? and status = ? ");
//            ps.setDate(1, (java.sql.Date) todaysdate);
//            ps.setString(2, "pending");
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                String tablebookid = rs.getString("id");
//                String tablebookname = rs.getString("book_name");
//                String tablestudentname = rs.getString("name");
//                String tableissuedate = rs.getString("issue_date");
//                String tablwduedate = rs.getString("due_date");
//                String tablestatus = rs.getString("status");
//
//                Object[] obj = {tablebookid, tablebookname, tablestudentname, tableissuedate, tablwduedate, tablestatus};
//                model = (DefaultTableModel) Dl.getModel();
//                model.addRow(obj);
//
//            }
//        } catch (ClassNotFoundException | SQLException e) {
//        }
//    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////****end****/////////////////////////////////////////////////////////

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        HEAD_PANEL = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        t_name = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        BODY_PANEL = new javax.swing.JSplitPane();
        DASHBOARD_PANEL = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        pnl_home = new javax.swing.JLabel();
        pnl_iss = new javax.swing.JLabel();
        pnl_vie = new javax.swing.JLabel();
        pnl_def = new javax.swing.JLabel();
        MAIN_CARD = new javax.swing.JPanel();
        HOME = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_book1 = new rojeru_san.complementos.RSTableMetro();
        kGradientPanel3 = new keeptoo.KGradientPanel();
        jLabel24 = new javax.swing.JLabel();
        pnl_book = new javax.swing.JPanel();
        count_book = new javax.swing.JLabel();
        VIEW_RECORDS = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        kGradientPanel4 = new keeptoo.KGradientPanel();
        jLabel79 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel41 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        t = new rojeru_san.complementos.RSTableMetro();
        date_search2 = new rojerusan.RSMaterialButtonRectangle();
        jLabel82 = new javax.swing.JLabel();
        date_search3 = new rojerusan.RSMaterialButtonRectangle();
        kGradientPanel5 = new keeptoo.KGradientPanel();
        MANAGE_STUDENTS = new javax.swing.JPanel();
        jSplitPane4 = new javax.swing.JSplitPane();
        jPanel16 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        txt_stuentname = new app.bolivia.swing.JCTextField();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        combo_branch = new javax.swing.JComboBox<>();
        combo_course = new javax.swing.JComboBox<>();
        kGradientPanel6 = new keeptoo.KGradientPanel();
        txt_studentid = new javax.swing.JLabel();
        rSMaterialButtonRectangle7 = new rojerusan.RSMaterialButtonRectangle();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_student = new rojeru_san.complementos.RSTableMetro();
        jLabel58 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        ISSUE_BOOKS = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        issue_bookid = new app.bolivia.swing.JCTextField();
        jLabel9 = new javax.swing.JLabel();
        issue_studentid = new app.bolivia.swing.JCTextField();
        jLabel27 = new javax.swing.JLabel();
        issuebook = new rojerusan.RSMaterialButtonCircle();
        kGradientPanel7 = new keeptoo.KGradientPanel();
        kGradientPanel9 = new keeptoo.KGradientPanel();
        PIE_CHART = new javax.swing.JPanel();
        Admin_piechart1 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        HEAD_PANEL.setBackground(new java.awt.Color(0, 51, 51));
        HEAD_PANEL.setMinimumSize(new java.awt.Dimension(1500, 60));
        HEAD_PANEL.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/adminIcons/icons8_menu_48px_1.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        HEAD_PANEL.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        HEAD_PANEL.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 5, 50));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("X");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
        });
        HEAD_PANEL.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1330, -10, 30, 60));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("(USER)");
        HEAD_PANEL.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 70, 30));

        t_name.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        t_name.setForeground(new java.awt.Color(255, 255, 255));
        t_name.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/adminIcons/male_user_50px.png"))); // NOI18N
        t_name.setText("USER");
        HEAD_PANEL.add(t_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 10, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("DIGITAL LIBRARY");
        HEAD_PANEL.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, -1));

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1500, Short.MAX_VALUE)
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        HEAD_PANEL.add(kGradientPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1500, 60));

        DASHBOARD_PANEL.setBackground(new java.awt.Color(0, 51, 51));
        DASHBOARD_PANEL.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 51));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/adminIcons/icons8_Exit_26px_1.png"))); // NOI18N
        jLabel12.setText("Logout");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel12MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        DASHBOARD_PANEL.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 220, 90));

        kGradientPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_home.setBackground(new java.awt.Color(255, 255, 255));
        pnl_home.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        pnl_home.setForeground(new java.awt.Color(255, 255, 255));
        pnl_home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/adminIcons/icons8_Home_26px_2.png"))); // NOI18N
        pnl_home.setText("  Home Page");
        pnl_home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_homeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnl_homeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnl_homeMouseExited(evt);
            }
        });
        kGradientPanel1.add(pnl_home, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, 53));

        pnl_iss.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        pnl_iss.setForeground(new java.awt.Color(255, 255, 255));
        pnl_iss.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/r/37.png"))); // NOI18N
        pnl_iss.setText("Pre Register");
        pnl_iss.setMaximumSize(new java.awt.Dimension(117, 46));
        pnl_iss.setMinimumSize(new java.awt.Dimension(117, 46));
        pnl_iss.setPreferredSize(new java.awt.Dimension(117, 46));
        pnl_iss.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_issMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnl_issMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnl_issMouseExited(evt);
            }
        });
        kGradientPanel1.add(pnl_iss, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 200, -1));

        pnl_vie.setBackground(new java.awt.Color(0, 153, 153));
        pnl_vie.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        pnl_vie.setForeground(new java.awt.Color(255, 255, 255));
        pnl_vie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/r/35 (1).png"))); // NOI18N
        pnl_vie.setText("View Records");
        pnl_vie.setMaximumSize(new java.awt.Dimension(117, 46));
        pnl_vie.setMinimumSize(new java.awt.Dimension(117, 46));
        pnl_vie.setPreferredSize(new java.awt.Dimension(117, 46));
        pnl_vie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_vieMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnl_vieMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnl_vieMouseExited(evt);
            }
        });
        kGradientPanel1.add(pnl_vie, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 210, 60));

        pnl_def.setBackground(new java.awt.Color(0, 153, 153));
        pnl_def.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        pnl_def.setForeground(new java.awt.Color(255, 255, 255));
        pnl_def.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/r/34.png"))); // NOI18N
        pnl_def.setText("  Profile");
        pnl_def.setMaximumSize(new java.awt.Dimension(117, 46));
        pnl_def.setMinimumSize(new java.awt.Dimension(117, 46));
        pnl_def.setPreferredSize(new java.awt.Dimension(117, 46));
        pnl_def.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_defMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnl_defMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnl_defMouseExited(evt);
            }
        });
        kGradientPanel1.add(pnl_def, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 200, -1));

        DASHBOARD_PANEL.add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 600));

        BODY_PANEL.setLeftComponent(DASHBOARD_PANEL);

        MAIN_CARD.setBackground(new java.awt.Color(255, 255, 255));
        MAIN_CARD.setLayout(new java.awt.CardLayout());

        HOME.setBackground(new java.awt.Color(204, 102, 255));
        HOME.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                HOMEMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                HOMEMouseExited(evt);
            }
        });
        HOME.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setFont(new java.awt.Font("Segoe UI Variable", 1, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Book Details");
        HOME.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, -1, -1));

        tbl_book1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book id", "Name", "Author", "Quantity"
            }
        ));
        tbl_book1.setColorBackgoundHead(new java.awt.Color(0, 0, 204));
        tbl_book1.setColorBordeFilas(new java.awt.Color(255, 255, 255));
        tbl_book1.setColorBordeHead(new java.awt.Color(255, 255, 255));
        tbl_book1.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        tbl_book1.setColorFilasForeground1(new java.awt.Color(0, 0, 0));
        tbl_book1.setColorFilasForeground2(new java.awt.Color(0, 0, 0));
        tbl_book1.setColorSelBackgound(new java.awt.Color(255, 51, 255));
        tbl_book1.setRowHeight(40);
        tbl_book1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbl_book1MouseEntered(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_book1);

        HOME.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 730, 270));

        jLabel24.setFont(new java.awt.Font("Segoe UI Variable", 1, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("BOOKS");

        pnl_book.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(0, 0, 204)));
        pnl_book.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        count_book.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 50)); // NOI18N
        count_book.setForeground(new java.awt.Color(0, 153, 153));
        count_book.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/adminIcons/icons8_Book_Shelf_50px.png"))); // NOI18N
        count_book.setText("10");
        pnl_book.add(count_book, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, -1, -1));

        javax.swing.GroupLayout kGradientPanel3Layout = new javax.swing.GroupLayout(kGradientPanel3);
        kGradientPanel3.setLayout(kGradientPanel3Layout);
        kGradientPanel3Layout.setHorizontalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel3Layout.createSequentialGroup()
                .addContainerGap(853, Short.MAX_VALUE)
                .addGroup(kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel3Layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addComponent(jLabel24))
                    .addComponent(pnl_book, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(157, 157, 157))
        );
        kGradientPanel3Layout.setVerticalGroup(
            kGradientPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel3Layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addComponent(jLabel24)
                .addGap(8, 8, 8)
                .addComponent(pnl_book, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(408, Short.MAX_VALUE))
        );

        HOME.add(kGradientPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 690));

        MAIN_CARD.add(HOME, "card2");

        VIEW_RECORDS.setBackground(new java.awt.Color(102, 0, 51));

        jPanel38.setBackground(new java.awt.Color(102, 0, 102));
        jPanel38.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel39.setBackground(new java.awt.Color(102, 0, 102));
        jPanel39.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(255, 255, 255)));
        jPanel39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel39MouseClicked(evt);
            }
        });
        jPanel39.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel79.setFont(new java.awt.Font("Segoe UI Black", 0, 29)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(255, 255, 255));
        jLabel79.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AddNewBookIcons/AddNewBookIcons/icons8_Student_Registration_100px_2.png"))); // NOI18N
        jLabel79.setText("Search Books");

        javax.swing.GroupLayout kGradientPanel4Layout = new javax.swing.GroupLayout(kGradientPanel4);
        kGradientPanel4.setLayout(kGradientPanel4Layout);
        kGradientPanel4Layout.setHorizontalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(921, Short.MAX_VALUE))
        );
        kGradientPanel4Layout.setVerticalGroup(
            kGradientPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel4Layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addComponent(jLabel79))
        );

        jPanel39.add(kGradientPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 1270, -1));

        jPanel38.add(jPanel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 17, 1083, -1));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel38.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 180, -1));

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel38.add(jPanel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 513, 1095, -1));

        t.setForeground(new java.awt.Color(0, 51, 51));
        t.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "BOOK", "STUDENT", "ISSUE DATE", "DUE DATE", "STATUS"
            }
        ));
        t.setColorBackgoundHead(new java.awt.Color(0, 0, 204));
        t.setColorBordeFilas(new java.awt.Color(255, 255, 255));
        t.setColorBordeHead(new java.awt.Color(255, 255, 255));
        t.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        t.setColorFilasForeground1(new java.awt.Color(0, 51, 51));
        t.setColorFilasForeground2(new java.awt.Color(0, 51, 51));
        t.setColorSelBackgound(new java.awt.Color(255, 102, 255));
        t.setRowHeight(40);
        t.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(t);

        jPanel38.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(371, 209, 718, 298));

        date_search2.setBackground(new java.awt.Color(255, 255, 255));
        date_search2.setForeground(new java.awt.Color(0, 51, 51));
        date_search2.setText("SEARCH");
        date_search2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                date_search2ActionPerformed(evt);
            }
        });
        jPanel38.add(date_search2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 340, 166, 40));

        jLabel82.setBackground(new java.awt.Color(255, 255, 255));
        jLabel82.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(255, 255, 255));
        jLabel82.setText("SEARCH");
        jPanel38.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, 30));

        date_search3.setBackground(new java.awt.Color(255, 255, 255));
        date_search3.setForeground(new java.awt.Color(0, 51, 51));
        date_search3.setText("ALl");
        date_search3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                date_search3ActionPerformed(evt);
            }
        });
        jPanel38.add(date_search3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 166, 40));

        javax.swing.GroupLayout kGradientPanel5Layout = new javax.swing.GroupLayout(kGradientPanel5);
        kGradientPanel5.setLayout(kGradientPanel5Layout);
        kGradientPanel5Layout.setHorizontalGroup(
            kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1280, Short.MAX_VALUE)
        );
        kGradientPanel5Layout.setVerticalGroup(
            kGradientPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 690, Short.MAX_VALUE)
        );

        jPanel38.add(kGradientPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 690));

        javax.swing.GroupLayout VIEW_RECORDSLayout = new javax.swing.GroupLayout(VIEW_RECORDS);
        VIEW_RECORDS.setLayout(VIEW_RECORDSLayout);
        VIEW_RECORDSLayout.setHorizontalGroup(
            VIEW_RECORDSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel38, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        VIEW_RECORDSLayout.setVerticalGroup(
            VIEW_RECORDSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        MAIN_CARD.add(VIEW_RECORDS, "card2");

        MANAGE_STUDENTS.setBackground(new java.awt.Color(255, 255, 255));

        jPanel16.setBackground(new java.awt.Color(102, 0, 102));
        jPanel16.setPreferredSize(new java.awt.Dimension(200, 700));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AddNewBookIcons/AddNewBookIcons/icons8_Contact_26px.png"))); // NOI18N
        jPanel16.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jLabel51.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Enter Student ID");
        jPanel16.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, -1, -1));

        jLabel52.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Enter Student Name");
        jPanel16.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, -1, -1));

        txt_stuentname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_stuentname.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_stuentname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_stuentname.setPlaceholder("Enter Student Name");
        txt_stuentname.setSelectionColor(new java.awt.Color(0, 0, 0));
        txt_stuentname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_stuentnameFocusLost(evt);
            }
        });
        txt_stuentname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_stuentnameActionPerformed(evt);
            }
        });
        jPanel16.add(txt_stuentname, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, 300, -1));

        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AddNewBookIcons/AddNewBookIcons/icons8_Moleskine_26px.png"))); // NOI18N
        jPanel16.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));

        jLabel54.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("Select Course");
        jPanel16.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, -1, -1));

        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AddNewBookIcons/AddNewBookIcons/icons8_Collaborator_Male_26px.png"))); // NOI18N
        jPanel16.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, -1, -1));

        jLabel56.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("Select Branch");
        jPanel16.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 350, -1, -1));

        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AddNewBookIcons/AddNewBookIcons/icons8_Unit_26px.png"))); // NOI18N
        jPanel16.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, -1, -1));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 430, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel16.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 430, 100));

        combo_branch.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        combo_branch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "   ", "IT", "CSE", "SCA", "OTHERS", " " }));
        jPanel16.add(combo_branch, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, 300, 30));

        combo_course.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        combo_course.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "      ", "BCA", "BSC", "MSC", "MBA", "MCA" }));
        jPanel16.add(combo_course, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 290, 300, 30));

        rSMaterialButtonRectangle7.setBackground(new java.awt.Color(255, 0, 255));
        rSMaterialButtonRectangle7.setText("Update");
        rSMaterialButtonRectangle7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel6Layout = new javax.swing.GroupLayout(kGradientPanel6);
        kGradientPanel6.setLayout(kGradientPanel6Layout);
        kGradientPanel6Layout.setHorizontalGroup(
            kGradientPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel6Layout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addGroup(kGradientPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rSMaterialButtonRectangle7, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_studentid, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
        );
        kGradientPanel6Layout.setVerticalGroup(
            kGradientPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel6Layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(txt_studentid, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 335, Short.MAX_VALUE)
                .addComponent(rSMaterialButtonRectangle7, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94))
        );

        jPanel16.add(kGradientPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 430, 610));

        jSplitPane4.setLeftComponent(jPanel16);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_student.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "student id", "Name", "course", "Branch"
            }
        ));
        tbl_student.setColorBackgoundHead(new java.awt.Color(0, 0, 255));
        tbl_student.setColorBordeFilas(new java.awt.Color(255, 255, 255));
        tbl_student.setColorBordeHead(new java.awt.Color(255, 255, 255));
        tbl_student.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        tbl_student.setColorFilasForeground1(new java.awt.Color(0, 51, 51));
        tbl_student.setColorFilasForeground2(new java.awt.Color(0, 51, 51));
        tbl_student.setColorSelBackgound(new java.awt.Color(255, 51, 255));
        tbl_student.setRowHeight(40);
        tbl_student.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_studentMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tbl_student);

        jPanel18.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 600, 370));

        jLabel58.setFont(new java.awt.Font("Segoe UI Variable", 1, 24)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(102, 102, 102));
        jLabel58.setText("Manage Details");
        jPanel18.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

        jPanel19.setBackground(new java.awt.Color(0, 51, 51));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel18.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 270, 5));

        jLabel59.setBackground(new java.awt.Color(255, 255, 255));
        jLabel59.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(0, 102, 153));
        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AddNewBookIcons/AddNewBookIcons/icons8_Rewind_48px.png"))); // NOI18N
        jLabel59.setText("Back");
        jLabel59.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel59MouseClicked(evt);
            }
        });
        jPanel18.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, -1, -1));

        jSplitPane4.setRightComponent(jPanel18);

        javax.swing.GroupLayout MANAGE_STUDENTSLayout = new javax.swing.GroupLayout(MANAGE_STUDENTS);
        MANAGE_STUDENTS.setLayout(MANAGE_STUDENTSLayout);
        MANAGE_STUDENTSLayout.setHorizontalGroup(
            MANAGE_STUDENTSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane4)
        );
        MANAGE_STUDENTSLayout.setVerticalGroup(
            MANAGE_STUDENTSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane4)
        );

        MAIN_CARD.add(MANAGE_STUDENTS, "card2");

        ISSUE_BOOKS.setBackground(new java.awt.Color(255, 255, 255));

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel60.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(0, 51, 51));
        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AddNewBookIcons/AddNewBookIcons/icons8_Books_52px_1.png"))); // NOI18N
        jLabel60.setText("Pre Register");
        jPanel20.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 200, -1));

        jPanel21.setBackground(new java.awt.Color(0, 51, 51));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel20.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 260, 5));

        issue_bookid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(102, 0, 102)));
        issue_bookid.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        issue_bookid.setPhColor(new java.awt.Color(0, 51, 51));
        issue_bookid.setPlaceholder("Enter Book  Id");
        issue_bookid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                issue_bookidFocusLost(evt);
            }
        });
        issue_bookid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issue_bookidActionPerformed(evt);
            }
        });
        jPanel20.add(issue_bookid, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, -1, -1));

        jLabel9.setBackground(new java.awt.Color(204, 255, 102));
        jLabel9.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 51));
        jLabel9.setText("Book Id :");
        jPanel20.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, 30));

        issue_studentid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(102, 0, 102)));
        issue_studentid.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        issue_studentid.setPhColor(new java.awt.Color(0, 51, 51));
        issue_studentid.setPlaceholder("Enter Student Id");
        issue_studentid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                issue_studentidFocusLost(evt);
            }
        });
        issue_studentid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issue_studentidActionPerformed(evt);
            }
        });
        jPanel20.add(issue_studentid, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, -1, -1));

        jLabel27.setBackground(new java.awt.Color(204, 255, 102));
        jLabel27.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 51, 51));
        jLabel27.setText("Student Id :");
        jPanel20.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, 30));

        issuebook.setBackground(new java.awt.Color(0, 51, 51));
        issuebook.setText("REGISTER");
        issuebook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issuebookActionPerformed(evt);
            }
        });
        jPanel20.add(issuebook, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 350, 60));

        javax.swing.GroupLayout kGradientPanel7Layout = new javax.swing.GroupLayout(kGradientPanel7);
        kGradientPanel7.setLayout(kGradientPanel7Layout);
        kGradientPanel7Layout.setHorizontalGroup(
            kGradientPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 369, Short.MAX_VALUE)
        );
        kGradientPanel7Layout.setVerticalGroup(
            kGradientPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout kGradientPanel9Layout = new javax.swing.GroupLayout(kGradientPanel9);
        kGradientPanel9.setLayout(kGradientPanel9Layout);
        kGradientPanel9Layout.setHorizontalGroup(
            kGradientPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 369, Short.MAX_VALUE)
        );
        kGradientPanel9Layout.setVerticalGroup(
            kGradientPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout ISSUE_BOOKSLayout = new javax.swing.GroupLayout(ISSUE_BOOKS);
        ISSUE_BOOKS.setLayout(ISSUE_BOOKSLayout);
        ISSUE_BOOKSLayout.setHorizontalGroup(
            ISSUE_BOOKSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ISSUE_BOOKSLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(kGradientPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kGradientPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(121, Short.MAX_VALUE))
        );
        ISSUE_BOOKSLayout.setVerticalGroup(
            ISSUE_BOOKSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ISSUE_BOOKSLayout.createSequentialGroup()
                .addGroup(ISSUE_BOOKSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(kGradientPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
                    .addComponent(kGradientPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MAIN_CARD.add(ISSUE_BOOKS, "card7");

        PIE_CHART.setBackground(new java.awt.Color(102, 0, 102));
        PIE_CHART.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Admin_piechart1.setLayout(new java.awt.BorderLayout());
        PIE_CHART.add(Admin_piechart1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 700, 440));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/a.jpg"))); // NOI18N
        jLabel19.setText("r");
        PIE_CHART.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1220, 700));

        MAIN_CARD.add(PIE_CHART, "card9");

        BODY_PANEL.setRightComponent(MAIN_CARD);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(BODY_PANEL, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(HEAD_PANEL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(HEAD_PANEL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BODY_PANEL, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        jLabel2.setForeground(Color.red);
        int close = JOptionPane.showConfirmDialog(this, "Do you want to close this application", "EXIT", JOptionPane.YES_NO_OPTION);
        if (close == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void pnl_homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_homeMouseClicked

        MAIN_CARD.removeAll();
        MAIN_CARD.add(HOME);
        MAIN_CARD.repaint();
        MAIN_CARD.revalidate();
        pnl_home.setForeground(Color.red);
        // TODO add your handling code here:
    }//GEN-LAST:event_pnl_homeMouseClicked

    private void txt_stuentnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_stuentnameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_stuentnameFocusLost

    private void txt_stuentnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_stuentnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_stuentnameActionPerformed

    private void rSMaterialButtonRectangle7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle7ActionPerformed
       
    }//GEN-LAST:event_rSMaterialButtonRectangle7ActionPerformed

    private void tbl_studentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_studentMouseClicked
        int rowno = tbl_student.getSelectedRow();
        TableModel tablemodel = tbl_student.getModel();
        txt_studentid.setText(tablemodel.getValueAt(rowno, 0).toString());
        txt_stuentname.setText(tablemodel.getValueAt(rowno, 1).toString());
        combo_course.setSelectedItem(tablemodel.getValueAt(rowno, 2).toString());
        combo_branch.setSelectedItem(tablemodel.getValueAt(rowno, 3).toString());
    }//GEN-LAST:event_tbl_studentMouseClicked

    private void jLabel59MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel59MouseClicked
        user home = new user();
        home.setVisible(true);
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel59MouseClicked

    private void pnl_issMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_issMouseClicked

        MAIN_CARD.removeAll();
        MAIN_CARD.add(ISSUE_BOOKS);
        MAIN_CARD.repaint();
        MAIN_CARD.revalidate();
        pnl_iss.setForeground(Color.red);
// TODO add your handling code here:
    }//GEN-LAST:event_pnl_issMouseClicked

    private void pnl_vieMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_vieMouseClicked
        MAIN_CARD.removeAll();
        MAIN_CARD.add(VIEW_RECORDS);
        MAIN_CARD.repaint();
        MAIN_CARD.revalidate();
        pnl_vie.setForeground(Color.red);
// TODO add your handling code here:
    }//GEN-LAST:event_pnl_vieMouseClicked

    private void pnl_defMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_defMouseClicked
        MAIN_CARD.removeAll();
        MAIN_CARD.add(MANAGE_STUDENTS);
        MAIN_CARD.repaint();
        MAIN_CARD.revalidate();
        pnl_def.setForeground(Color.red);
// TODO add your handling code here:
    }//GEN-LAST:event_pnl_defMouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        jLabel12.setForeground(Color.red);
        int close = JOptionPane.showConfirmDialog(this, "Do you want to logout", "LOGOUT", JOptionPane.YES_NO_OPTION);
        if (close == 0) {
            Loginpage login = new Loginpage();
            login.setVisible(true);
            dispose();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel12MouseClicked

    private void date_search2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_date_search2ActionPerformed
        
    }//GEN-LAST:event_date_search2ActionPerformed

    private void jPanel39MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel39MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel39MouseClicked

    private void tMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tMouseClicked

    }//GEN-LAST:event_tMouseClicked

    private void date_search3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_date_search3ActionPerformed
        clearviewTable();
        GetValueFromDatabase();// TODO add your handling code here:
    }//GEN-LAST:event_date_search3ActionPerformed

    private void pnl_homeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_homeMouseEntered
        pnl_home.setForeground(Color.cyan);


    }//GEN-LAST:event_pnl_homeMouseEntered
    int x = 230;
    int a = 0;
    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        if (x == 230) {
            DASHBOARD_PANEL.setSize(230, 900);
            Thread th = new Thread() {
                @Override
                public void run() {
                    try {

                        for (int i = 230; i >= 0; i--) {
                            Thread.sleep(1);
                            DASHBOARD_PANEL.setSize(i, 900);

                            a++;
                        }
                    } catch (InterruptedException e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            };
            th.start();
            x = 0;
        } else if (x == 0) {
            DASHBOARD_PANEL.setSize(x, 900);
            Thread th = new Thread() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i <= x; i++) {
                            Thread.sleep(1);
                            DASHBOARD_PANEL.setSize(i, 900);
                        }
                    } catch (InterruptedException e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            };
            th.start();
            x = 230;
        }
    }//GEN-LAST:event_jLabel1MouseClicked

    private void pnl_homeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_homeMouseExited
        pnl_home.setForeground(Color.white);
    }//GEN-LAST:event_pnl_homeMouseExited

    private void pnl_issMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_issMouseEntered
        pnl_iss.setForeground(Color.cyan);
    }//GEN-LAST:event_pnl_issMouseEntered

    private void pnl_defMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_defMouseEntered
        pnl_def.setForeground(Color.cyan);
    }//GEN-LAST:event_pnl_defMouseEntered

    private void pnl_issMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_issMouseExited
        pnl_iss.setForeground(Color.white);
    }//GEN-LAST:event_pnl_issMouseExited

    private void pnl_vieMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_vieMouseEntered
        pnl_vie.setForeground(Color.cyan);
    }//GEN-LAST:event_pnl_vieMouseEntered

    private void pnl_vieMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_vieMouseExited
        pnl_vie.setForeground(Color.white);
    }//GEN-LAST:event_pnl_vieMouseExited

    private void pnl_defMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_defMouseExited
        pnl_def.setForeground(Color.white);
    }//GEN-LAST:event_pnl_defMouseExited

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
        jLabel2.setForeground(Color.white);   // TODO add your handling code here:
    }//GEN-LAST:event_jLabel2MouseExited

    private void HOMEMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HOMEMouseEntered
        pnl_home.setForeground(Color.red);
    }//GEN-LAST:event_HOMEMouseEntered

    private void HOMEMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HOMEMouseExited
        pnl_home.setForeground(Color.white);   // TODO add your handling code here:
    }//GEN-LAST:event_HOMEMouseExited

    private void jLabel12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseExited
        jLabel12.setForeground(Color.black);
    }//GEN-LAST:event_jLabel12MouseExited

    private void tbl_book1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_book1MouseEntered
        pnl_home.setForeground(Color.red);
    }//GEN-LAST:event_tbl_book1MouseEntered

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void issuebookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_issuebookActionPerformed
        //        if (lbl_quantity.getText().equals("0")) {
            //            JOptionPane.showMessageDialog(this, "book is not available");
            //
            //        } else {
            //
            //            if (isAlreadyissued() == false) {
                //                if (issuebook() == true) {
                    //                    JOptionPane.showMessageDialog(this, "book inserted succesfully");
                    //                    updatecount();
                    //                } else {
                    //                    JOptionPane.showMessageDialog(this, "book inserted failed");
                    //                }
                //
                //            } else {
                //                JOptionPane.showMessageDialog(this, "this student already has the same book");
                //            }
            //        }
    }//GEN-LAST:event_issuebookActionPerformed

    private void issue_studentidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_issue_studentidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_issue_studentidActionPerformed

    private void issue_studentidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_issue_studentidFocusLost
        if (!issue_studentid.getText().equals("")) {
            //            getstudentdetails();
        }
    }//GEN-LAST:event_issue_studentidFocusLost

    private void issue_bookidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_issue_bookidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_issue_bookidActionPerformed

    private void issue_bookidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_issue_bookidFocusLost
        if (!issue_bookid.getText().equals("")) {
            //            getbookdetails();
        }
    }//GEN-LAST:event_issue_bookidFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            new user().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Admin_piechart1;
    private javax.swing.JSplitPane BODY_PANEL;
    private javax.swing.JPanel DASHBOARD_PANEL;
    private javax.swing.JPanel HEAD_PANEL;
    private javax.swing.JPanel HOME;
    private javax.swing.JPanel ISSUE_BOOKS;
    private javax.swing.JPanel MAIN_CARD;
    private javax.swing.JPanel MANAGE_STUDENTS;
    private javax.swing.JPanel PIE_CHART;
    private javax.swing.JPanel VIEW_RECORDS;
    private javax.swing.JComboBox<String> combo_branch;
    private javax.swing.JComboBox<String> combo_course;
    private javax.swing.JLabel count_book;
    private rojerusan.RSMaterialButtonRectangle date_search2;
    private rojerusan.RSMaterialButtonRectangle date_search3;
    private app.bolivia.swing.JCTextField issue_bookid;
    private app.bolivia.swing.JCTextField issue_studentid;
    private rojerusan.RSMaterialButtonCircle issuebook;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JTextField jTextField1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private keeptoo.KGradientPanel kGradientPanel3;
    private keeptoo.KGradientPanel kGradientPanel4;
    private keeptoo.KGradientPanel kGradientPanel5;
    private keeptoo.KGradientPanel kGradientPanel6;
    private keeptoo.KGradientPanel kGradientPanel7;
    private keeptoo.KGradientPanel kGradientPanel9;
    private javax.swing.JPanel pnl_book;
    private javax.swing.JLabel pnl_def;
    private javax.swing.JLabel pnl_home;
    private javax.swing.JLabel pnl_iss;
    private javax.swing.JLabel pnl_vie;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle7;
    private rojeru_san.complementos.RSTableMetro t;
    private javax.swing.JLabel t_name;
    private rojeru_san.complementos.RSTableMetro tbl_book1;
    private rojeru_san.complementos.RSTableMetro tbl_student;
    private javax.swing.JLabel txt_studentid;
    private app.bolivia.swing.JCTextField txt_stuentname;
    // End of variables declaration//GEN-END:variables
}
