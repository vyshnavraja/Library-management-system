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

public final class HomePage extends javax.swing.JFrame {

    String bookName, author;
    int bookId, quantity;
    DefaultTableModel model;
    String studentName, course, branch;
    int studentID;

    public HomePage() {
        initComponents();
        showPieChart();
        setBookDetailstotable();
        setStudentDetailstotable();
        GetValueFromDatabase();
        Defaulterlist();
        setdata();
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
            ResultSet rz = st.executeQuery("select count(*) from student_details");
            if (rz.next()) {
                int count = rz.getInt(1);
                count_stuent.setText(String.valueOf(count));

            }
            ResultSet rc = st.executeQuery("SELECT COUNT(*) FROM issue_book WHERE status = 'Pending'");
            if (rc.next()) {
                int count = rc.getInt(1);
                count_issued.setText(String.valueOf(count));
            }

            PreparedStatement phs = con.prepareStatement("SELECT COUNT(*) FROM issue_book WHERE due_date < ? AND status = ?");
            phs.setString(1, today);
            phs.setString(2, "pending");
            ResultSet ras = phs.executeQuery();
            if (ras.next()) {
                int counts = ras.getInt(1);
                count_defulter.setText(String.valueOf(counts));
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
                model = (DefaultTableModel) tbl_book2.getModel();
                model.addRow(obj);
                model = (DefaultTableModel) tbl_book1.getModel();
                model.addRow(obj);
            }
        } catch (ClassNotFoundException | SQLException e) {
        }
    }

    public boolean addbook() {
        boolean isadded = false;
       if (txt_bookid.getText().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please enter a book ID!");
        return false;
    }
    try {
        bookId = Integer.parseInt(txt_bookid.getText());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Please enter a valid book ID!");
        return false;
    }
    
    // Validate bookName
    if (txt_bookname.getText().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please enter a book name!");
        return false;
    }
    bookName = txt_bookname.getText();
    
    // Validate author
    if (txt_authorname.getText().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please enter an author name!");
        return false;
    }
    author = txt_authorname.getText();
    
    // Validate quantity
    if (txt_quantity.getText().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please enter a quantity!");
        return false;
    }
    try {
        quantity = Integer.parseInt(txt_quantity.getText());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Please enter a valid quantity!");
        return false;
    }
        
  
        try {
            Connection con = BACKENDCONNECTION.getConnection();
            String sql = "insert into book_details values(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookId);
            ps.setString(2, bookName);
            ps.setString(3, author);
            ps.setInt(4, quantity);
            int rowCount = ps.executeUpdate();
            isadded = rowCount > 0;
        } catch (SQLException e) {
        }
        return isadded;
    }

    //clear table
    public void clearTable() {
        DefaultTableModel tablemodel = (DefaultTableModel) tbl_book2.getModel();
        tablemodel.setRowCount(0);
    }

    public boolean updatebook() {
        boolean isupdated = false;
        bookId = Integer.parseInt(txt_bookid.getText());
        bookName = txt_bookname.getText();
        author = txt_authorname.getText();
        quantity = Integer.parseInt(txt_quantity.getText());
        try {
            Connection con = BACKENDCONNECTION.getConnection();
            String sql = "update book_details set book_name = ?,Author = ?,Quantity = ? where book_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(4, bookId);
            ps.setString(1, bookName);
            ps.setString(2, author);
            ps.setInt(3, quantity);
            int rowCount = ps.executeUpdate();
            isupdated = rowCount > 0;
        } catch (SQLException e) {
        }
        return isupdated;
    }

    public boolean Deletebook() {
        boolean isDeleted = false;
        bookId = Integer.parseInt(txt_bookid.getText());
        try {
            Connection con = BACKENDCONNECTION.getConnection();
            String sql = "Delete from book_details where book_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookId);
            int rowCount = ps.executeUpdate();
            isDeleted = rowCount > 0;
        } catch (SQLException e) {
        }
        return isDeleted;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////student//////////////////////////////////////////////////
    //setstudent details
    public void setStudentDetailstotable() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/roy", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from student_details");
            while (rs.next()) {
                String studenttableId = rs.getString("student_id");
                String studenttableName = rs.getString("name");
                String coursed = rs.getString("course");
                String Quantity = rs.getString("branch");

                Object[] obj = {studenttableId, studenttableName, coursed, Quantity};
                model = (DefaultTableModel) tbl_student.getModel();
                model.addRow(obj);
                model = (DefaultTableModel) tbl_student1.getModel();
                model.addRow(obj);

            }
        } catch (ClassNotFoundException | SQLException e) {
        }
    }

    public boolean addstudent() {
        boolean isadded = false;

        try {     
String studentName = txt_stuentname.getText();
if (!studentName.matches("^[a-zA-Z\\s]+$")) {
    JOptionPane.showMessageDialog(null, "Invalid Student Name!");
    return false;
}

String course = combo_course.getSelectedItem().toString();
if (course.equals("")) {
    JOptionPane.showMessageDialog(null, "Please select a Course!");
    return false;
}

String branch = combo_branch.getSelectedItem().toString();
if (branch.equals("")) {
    JOptionPane.showMessageDialog(null, "Please select a Branch!");
    return false;
}

        

            Connection con = BACKENDCONNECTION.getConnection();
            String sql = "insert into student_details values(?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentID);
            ps.setString(2, studentName);
            ps.setString(3, course);
            ps.setString(4, branch);
            int rowCount = ps.executeUpdate();
            isadded = rowCount > 0;
        } catch (SQLException e) {
        }
        return isadded;
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

    public boolean Deletestudent() {
        boolean isDeleted = false;
        studentID = Integer.parseInt(txt_studentid.getText());
        try {
            Connection con = BACKENDCONNECTION.getConnection();
            String sql = "Delete from student_details where student_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentID);
            int rowCount = ps.executeUpdate();
            isDeleted = rowCount > 0;
        } catch (SQLException e) {
        }
        return isDeleted;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////end of student//////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////issue books///////////////////////////////////////////////////////
    public void getbookdetails() {
        int bookIda = Integer.parseInt(issue_bookid.getText());

        try {
            Connection con = BACKENDCONNECTION.getConnection();
            String sql = "select * from book_details where book_id= ? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookIda);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lbl_bookid.setText(rs.getString("book_id"));
                lbl_bookname.setText(rs.getString("book_name"));
                lbl_author.setText(rs.getString("Author"));
                lbl_quantity.setText(rs.getString("Quantity"));
                lbl_errorbook.setText("");
            } else {
                lbl_errorbook.setText("invalid book id");
            }
        } catch (SQLException e) {
        }
    }

    public void getstudentdetails() {
        int studentida = Integer.parseInt(issue_studentid.getText());
        try {
            Connection con = BACKENDCONNECTION.getConnection();
            String sql = "select * from student_details where student_id= ? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentida);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lbl_studentid.setText(rs.getString("student_id"));
                lbl_studentname.setText(rs.getString("name"));
                lbl_course.setText(rs.getString("course"));
                lbl_branch.setText(rs.getString("branch"));
                lbl_errorstudent.setText("");

            } else {
                lbl_errorstudent.setText("invalid student id");
            }
        } catch (SQLException e) {
        }
    }
    //insert book details

    public boolean issuebook() {
        boolean isinserted = false;
        int bookIda = Integer.parseInt(issue_bookid.getText());
        int studentida = Integer.parseInt(issue_studentid.getText());
        String book_name = lbl_bookname.getText();
        String student_name = lbl_studentname.getText();
        Date uIssueDate = date_issuedate.getDate();
        Date udueDate = date_duedate.getDate();
        long l1 = uIssueDate.getTime();
        long l2 = udueDate.getTime();
        java.sql.Date sissueDate = new java.sql.Date(l1);
        java.sql.Date sudueDate = new java.sql.Date(l2);

        try {
            Connection con = BACKENDCONNECTION.getConnection();
            String sql = "insert into issue_book (book_id,book_name,student_id,"
                    + "name,issue_date,due_date,status)values(?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookIda);
            ps.setString(2, book_name);
            ps.setInt(3, studentida);
            ps.setString(4, student_name);
            ps.setDate(5, sissueDate);
            ps.setDate(6, sudueDate);
            ps.setString(7, "pending");
            int rc = ps.executeUpdate();
            isinserted = rc > 0;
        } catch (SQLException e) {
        }
        return isinserted;

    }

    //update book count
    public void updatecount() {

        int bookIda = Integer.parseInt(issue_bookid.getText());
        try {
            Connection con = BACKENDCONNECTION.getConnection();
            String sql = "update book_details set Quantity = Quantity - 1 where book_id =?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookIda);
            int rc = ps.executeUpdate();
            if (rc > 0) {
                JOptionPane.showMessageDialog(this, "updated");
                int initailcount = Integer.parseInt(lbl_quantity.getText());
                lbl_quantity.setText(Integer.toString(initailcount - 1));
            } else {
                JOptionPane.showMessageDialog(this, "imposible");
            }

        } catch (HeadlessException | NumberFormatException | SQLException e) {
        }
    }

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
    public void Returndetails() {

        int bookissueid = Integer.parseInt(return_bookid.getText());
        int studentissueid = Integer.parseInt(return_student_id.getText());
        try {
            Connection con = BACKENDCONNECTION.getConnection();
            String sql = "select * from issue_book where book_id = ? and student_id =? and status =?  ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookissueid);
            ps.setInt(2, studentissueid);
            ps.setString(3, "pending");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                issued_id.setText(rs.getString("id"));
                issued_book_name.setText(rs.getString("book_name"));
                issued_student_name.setText(rs.getString("name"));
                issued_date.setText(rs.getString("issue_date"));
                issued_due_date.setText(rs.getString("due_date"));
                issued_errors.setText("");
            } else {
                issued_errors.setText("");
                issued_id.setText("");
                issued_book_name.setText("");
                issued_student_name.setText("");
                issued_date.setText("");
                issued_due_date.setText("");
                issued_errors.setText("No Record Found");
            }
        } catch (SQLException e) {
        }

    }

    // to return book
    public boolean Returnbook() {

        boolean isreturned = false;
        int bookissueid = Integer.parseInt(return_bookid.getText());
        int studentissueid = Integer.parseInt(return_student_id.getText());

        try {
            Connection con = BACKENDCONNECTION.getConnection();
            String sql = "update issue_book set status=? where book_id=? and student_id=? and status=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "returned");
            ps.setInt(2, bookissueid);
            ps.setInt(3, studentissueid);
            ps.setString(4, "pending");
            int rc = ps.executeUpdate();
            isreturned = rc > 0;
        } catch (SQLException e) {
        }
        return isreturned;

    }

    public void updatereturnCount() {

        int bookissueid = Integer.parseInt(return_bookid.getText());
        try {
            Connection con = BACKENDCONNECTION.getConnection();
            String sql = "update book_details set Quantity = Quantity + 1 where book_id =?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookissueid);
            ps.executeUpdate();

        } catch (HeadlessException | NumberFormatException | SQLException e) {
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////End of Return //////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////view records ////////////////////////////////////////////////////////////

    public void GetValueFromDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/roy", "root", "");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from issue_book");
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
//to fetch the record by date

    public void datesearch() {

        Date uFromdate = date_from_view.getDate();
        Date uTodate = date_to_view.getDate();
        long l1 = uFromdate.getTime();
        long l2 = uTodate.getTime();
        java.sql.Date Fromdate = new java.sql.Date(l1);
        java.sql.Date Todate = new java.sql.Date(l2);
        try {
            Connection con = BACKENDCONNECTION.getConnection();
            String sql = "select * from issue_book where issue_date BETWEEN ? and ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, Fromdate);
            ps.setDate(2, Todate);

            ResultSet rs = ps.executeQuery();

            if (rs.next() == false) {

                JOptionPane.showMessageDialog(this, "no record found");
            } else {
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
            }

        } catch (SQLException e) {

        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////end of view///////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////Defaulter////////////////////////////////////////////////////////////////////////
    public void Defaulterlist() {
        long l = System.currentTimeMillis();
        java.sql.Date todaysdate = new java.sql.Date(l);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/roy", "root", "");
            PreparedStatement ps = con.prepareStatement("select * from issue_book where due_date < ? and status = ? ");
            ps.setDate(1, (java.sql.Date) todaysdate);
            ps.setString(2, "pending");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String tablebookid = rs.getString("id");
                String tablebookname = rs.getString("book_name");
                String tablestudentname = rs.getString("name");
                String tableissuedate = rs.getString("issue_date");
                String tablwduedate = rs.getString("due_date");
                String tablestatus = rs.getString("status");

                Object[] obj = {tablebookid, tablebookname, tablestudentname, tableissuedate, tablwduedate, tablestatus};
                model = (DefaultTableModel) Dl.getModel();
                model.addRow(obj);

            }
        } catch (ClassNotFoundException | SQLException e) {
        }
    }
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
        BODY_PANEL = new javax.swing.JSplitPane();
        DASHBOARD_PANEL = new javax.swing.JPanel();
        pnl_bok = new javax.swing.JLabel();
        pnl_home = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        pnl_stu = new javax.swing.JLabel();
        pnl_def = new javax.swing.JLabel();
        pnl_pie = new javax.swing.JLabel();
        pnl_vie = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pnl_rtn = new javax.swing.JLabel();
        pnl_iss = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        MAIN_CARD = new javax.swing.JPanel();
        HOME = new javax.swing.JPanel();
        pnl_book = new javax.swing.JPanel();
        count_book = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        pnl_student = new javax.swing.JPanel();
        count_stuent = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        count_issued = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        count_defulter = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_student1 = new rojeru_san.complementos.RSTableMetro();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_book1 = new rojeru_san.complementos.RSTableMetro();
        VIEW_RECORDS = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jLabel79 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        t = new rojeru_san.complementos.RSTableMetro();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        date_search2 = new rojerusan.RSMaterialButtonRectangle();
        jLabel82 = new javax.swing.JLabel();
        date_search3 = new rojerusan.RSMaterialButtonRectangle();
        date_from_view = new com.toedter.calendar.JDateChooser();
        date_to_view = new com.toedter.calendar.JDateChooser();
        jLabel73 = new javax.swing.JLabel();
        RETURN_BOOKS = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        return_bookid = new app.bolivia.swing.JCTextField();
        return_student_id = new app.bolivia.swing.JCTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        issued_errors = new javax.swing.JLabel();
        rSMaterialButtonRectangle1 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle8 = new rojerusan.RSMaterialButtonRectangle();
        jPanel40 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        issued_due_date = new javax.swing.JLabel();
        issued_date = new javax.swing.JLabel();
        issued_student_name = new javax.swing.JLabel();
        issued_book_name = new javax.swing.JLabel();
        issued_id = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        MANAGE_BOOKS = new javax.swing.JPanel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel12 = new javax.swing.JPanel();
        txt_bookid = new app.bolivia.swing.JCTextField();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        txt_bookname = new app.bolivia.swing.JCTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        txt_authorname = new app.bolivia.swing.JCTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        txt_quantity = new app.bolivia.swing.JCTextField();
        jLabel47 = new javax.swing.JLabel();
        rSMaterialButtonRectangle2 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle3 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle4 = new rojerusan.RSMaterialButtonRectangle();
        jLabel21 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_book2 = new rojeru_san.complementos.RSTableMetro();
        jLabel48 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        MANAGE_STUDENTS = new javax.swing.JPanel();
        jSplitPane4 = new javax.swing.JSplitPane();
        jPanel16 = new javax.swing.JPanel();
        txt_studentid = new app.bolivia.swing.JCTextField();
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
        rSMaterialButtonRectangle5 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle6 = new rojerusan.RSMaterialButtonRectangle();
        rSMaterialButtonRectangle7 = new rojerusan.RSMaterialButtonRectangle();
        combo_branch = new javax.swing.JComboBox<>();
        combo_course = new javax.swing.JComboBox<>();
        imafr = new javax.swing.JLabel();
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
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        issuebook = new rojerusan.RSMaterialButtonCircle();
        date_issuedate = new com.toedter.calendar.JDateChooser();
        date_duedate = new com.toedter.calendar.JDateChooser();
        jPanel23 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        lbl_branch = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        lbl_studentid = new javax.swing.JLabel();
        lbl_studentname = new javax.swing.JLabel();
        lbl_course = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        lbl_errorstudent = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        lbl_errorbook = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        lbl_bookid = new javax.swing.JLabel();
        lbl_bookname = new javax.swing.JLabel();
        lbl_author = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        lbl_quantity = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        DEFAULTERS_LIST = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jPanel43 = new javax.swing.JPanel();
        jLabel83 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        Dl = new rojeru_san.complementos.RSTableMetro();
        jLabel5 = new javax.swing.JLabel();
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
        jLabel3.setText("(ADMIN)");
        HEAD_PANEL.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 70, 30));

        t_name.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        t_name.setForeground(new java.awt.Color(255, 255, 255));
        t_name.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/adminIcons/male_user_50px.png"))); // NOI18N
        t_name.setText(" Admin");
        HEAD_PANEL.add(t_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 10, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("DIGITAL LIBRARY");
        HEAD_PANEL.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, -1));

        DASHBOARD_PANEL.setBackground(new java.awt.Color(0, 51, 51));
        DASHBOARD_PANEL.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_bok.setBackground(new java.awt.Color(0, 153, 153));
        pnl_bok.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        pnl_bok.setForeground(new java.awt.Color(255, 255, 255));
        pnl_bok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/r/39.png"))); // NOI18N
        pnl_bok.setText("   Books");
        pnl_bok.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_bokMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnl_bokMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnl_bokMouseExited(evt);
            }
        });
        DASHBOARD_PANEL.add(pnl_bok, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 210, -1));

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
        DASHBOARD_PANEL.add(pnl_home, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, 53));

        jLabel7.setBackground(new java.awt.Color(0, 153, 153));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Manage");
        DASHBOARD_PANEL.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        pnl_stu.setBackground(new java.awt.Color(0, 153, 153));
        pnl_stu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        pnl_stu.setForeground(new java.awt.Color(255, 255, 255));
        pnl_stu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/r/38.png"))); // NOI18N
        pnl_stu.setText("  Students");
        pnl_stu.setMaximumSize(new java.awt.Dimension(117, 46));
        pnl_stu.setMinimumSize(new java.awt.Dimension(117, 46));
        pnl_stu.setPreferredSize(new java.awt.Dimension(117, 46));
        pnl_stu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_stuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnl_stuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnl_stuMouseExited(evt);
            }
        });
        DASHBOARD_PANEL.add(pnl_stu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 210, -1));

        pnl_def.setBackground(new java.awt.Color(0, 153, 153));
        pnl_def.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        pnl_def.setForeground(new java.awt.Color(255, 255, 255));
        pnl_def.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/r/34.png"))); // NOI18N
        pnl_def.setText(" Defaulters");
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
        DASHBOARD_PANEL.add(pnl_def, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 200, -1));

        pnl_pie.setBackground(new java.awt.Color(0, 153, 153));
        pnl_pie.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        pnl_pie.setForeground(new java.awt.Color(255, 255, 255));
        pnl_pie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/adminIcons/icons8_Library_32px.png"))); // NOI18N
        pnl_pie.setText("  Pie Graph");
        pnl_pie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_pieMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnl_pieMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnl_pieMouseExited(evt);
            }
        });
        DASHBOARD_PANEL.add(pnl_pie, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 190, 50));

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
        DASHBOARD_PANEL.add(pnl_vie, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 210, 60));

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

        pnl_rtn.setBackground(new java.awt.Color(0, 153, 153));
        pnl_rtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        pnl_rtn.setForeground(new java.awt.Color(255, 255, 255));
        pnl_rtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/r/36.png"))); // NOI18N
        pnl_rtn.setText("Return Book");
        pnl_rtn.setMaximumSize(new java.awt.Dimension(117, 46));
        pnl_rtn.setMinimumSize(new java.awt.Dimension(117, 46));
        pnl_rtn.setPreferredSize(new java.awt.Dimension(117, 46));
        pnl_rtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnl_rtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnl_rtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnl_rtnMouseExited(evt);
            }
        });
        DASHBOARD_PANEL.add(pnl_rtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 200, -1));

        pnl_iss.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        pnl_iss.setForeground(new java.awt.Color(255, 255, 255));
        pnl_iss.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/r/37.png"))); // NOI18N
        pnl_iss.setText("  Issue Book");
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
        DASHBOARD_PANEL.add(pnl_iss, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 210, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/a.jpg"))); // NOI18N
        jLabel4.setText("r");
        DASHBOARD_PANEL.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 600));

        BODY_PANEL.setLeftComponent(DASHBOARD_PANEL);

        MAIN_CARD.setBackground(new java.awt.Color(255, 255, 255));
        MAIN_CARD.setLayout(new java.awt.CardLayout());

        HOME.setBackground(new java.awt.Color(255, 255, 255));
        HOME.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                HOMEMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                HOMEMouseExited(evt);
            }
        });
        HOME.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_book.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(0, 51, 51)));
        pnl_book.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        count_book.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 50)); // NOI18N
        count_book.setForeground(new java.awt.Color(0, 153, 153));
        count_book.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/adminIcons/icons8_Book_Shelf_50px.png"))); // NOI18N
        count_book.setText("10");
        pnl_book.add(count_book, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        HOME.add(pnl_book, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 260, 140));

        jLabel16.setFont(new java.awt.Font("Segoe UI Variable", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 102));
        jLabel16.setText("Student Details");
        HOME.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, -1, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI Variable", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 51, 102));
        jLabel18.setText("STUDENTS");
        HOME.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 20, -1, -1));

        pnl_student.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(0, 102, 102)));
        pnl_student.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        count_stuent.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 50)); // NOI18N
        count_stuent.setForeground(new java.awt.Color(0, 153, 153));
        count_stuent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/adminIcons/icons8_People_50px.png"))); // NOI18N
        count_stuent.setText("10");
        pnl_student.add(count_stuent, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        HOME.add(pnl_student, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 60, 260, 140));

        jLabel20.setFont(new java.awt.Font("Segoe UI Variable", 1, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 51, 102));
        jLabel20.setText("ISSUED");
        HOME.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 20, -1, -1));

        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(0, 51, 51)));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        count_issued.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 50)); // NOI18N
        count_issued.setForeground(new java.awt.Color(0, 153, 153));
        count_issued.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/adminIcons/icons8_Sell_50px.png"))); // NOI18N
        count_issued.setText("10");
        jPanel8.add(count_issued, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        HOME.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 60, 260, 140));

        jLabel22.setFont(new java.awt.Font("Segoe UI Variable", 1, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 0, 0));
        jLabel22.setText("DEFAULTER");
        HOME.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 20, -1, -1));

        jPanel9.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(0, 102, 102)));
        jPanel9.setForeground(new java.awt.Color(204, 0, 51));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        count_defulter.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 50)); // NOI18N
        count_defulter.setForeground(new java.awt.Color(204, 0, 0));
        count_defulter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adminIcons/adminIcons/icons8_List_of_Thumbnails_50px.png"))); // NOI18N
        count_defulter.setText("10");
        jPanel9.add(count_defulter, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        HOME.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 60, 260, 140));

        jLabel24.setFont(new java.awt.Font("Segoe UI Variable", 1, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 51, 102));
        jLabel24.setText("BOOKS");
        HOME.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));

        jLabel25.setFont(new java.awt.Font("Segoe UI Variable", 1, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(102, 102, 102));
        jLabel25.setText("Book Details");
        HOME.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 230, -1, -1));

        tbl_student1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book id", "Name", "Course", "Branch"
            }
        ));
        tbl_student1.setColorBackgoundHead(new java.awt.Color(0, 51, 51));
        tbl_student1.setColorBordeFilas(new java.awt.Color(255, 255, 255));
        tbl_student1.setColorBordeHead(new java.awt.Color(255, 255, 255));
        tbl_student1.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        tbl_student1.setColorFilasForeground1(new java.awt.Color(0, 0, 0));
        tbl_student1.setColorFilasForeground2(new java.awt.Color(0, 0, 0));
        tbl_student1.setColorSelBackgound(new java.awt.Color(0, 153, 153));
        tbl_student1.setRowHeight(40);
        tbl_student1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbl_student1MouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_student1);

        HOME.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 490, 170));

        tbl_book1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book id", "Name", "Author", "Quantity"
            }
        ));
        tbl_book1.setColorBackgoundHead(new java.awt.Color(0, 51, 51));
        tbl_book1.setColorBordeFilas(new java.awt.Color(255, 255, 255));
        tbl_book1.setColorBordeHead(new java.awt.Color(255, 255, 255));
        tbl_book1.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        tbl_book1.setColorFilasForeground1(new java.awt.Color(0, 0, 0));
        tbl_book1.setColorFilasForeground2(new java.awt.Color(0, 0, 0));
        tbl_book1.setColorSelBackgound(new java.awt.Color(0, 153, 153));
        tbl_book1.setRowHeight(40);
        tbl_book1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbl_book1MouseEntered(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_book1);

        HOME.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 270, 490, 170));

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
        jLabel79.setText("VIEW RECORDS");
        jPanel39.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 6, 343, -1));

        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/a.jpg"))); // NOI18N
        jLabel74.setText("r");
        jPanel39.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 100));

        jPanel38.add(jPanel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 17, 1083, -1));

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
        t.setColorBackgoundHead(new java.awt.Color(0, 51, 51));
        t.setColorBordeFilas(new java.awt.Color(255, 255, 255));
        t.setColorBordeHead(new java.awt.Color(255, 255, 255));
        t.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        t.setColorFilasForeground1(new java.awt.Color(0, 51, 51));
        t.setColorFilasForeground2(new java.awt.Color(0, 51, 51));
        t.setColorSelBackgound(new java.awt.Color(0, 153, 153));
        t.setRowHeight(40);
        t.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(t);

        jPanel38.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(371, 209, 718, 298));

        jLabel80.setBackground(new java.awt.Color(255, 255, 255));
        jLabel80.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(255, 255, 255));
        jLabel80.setText("From Date :");
        jPanel38.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 245, -1, 22));

        jLabel81.setBackground(new java.awt.Color(204, 255, 102));
        jLabel81.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(255, 255, 255));
        jLabel81.setText("To Date :");
        jPanel38.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 307, -1, 22));

        date_search2.setBackground(new java.awt.Color(255, 255, 255));
        date_search2.setForeground(new java.awt.Color(0, 51, 51));
        date_search2.setText("SEARCH");
        date_search2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                date_search2ActionPerformed(evt);
            }
        });
        jPanel38.add(date_search2, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 402, 166, 40));

        jLabel82.setBackground(new java.awt.Color(255, 255, 255));
        jLabel82.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(255, 255, 255));
        jLabel82.setText("SEARCH");
        jPanel38.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 173, -1, 30));

        date_search3.setBackground(new java.awt.Color(255, 255, 255));
        date_search3.setForeground(new java.awt.Color(0, 51, 51));
        date_search3.setText("ALl");
        date_search3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                date_search3ActionPerformed(evt);
            }
        });
        jPanel38.add(date_search3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 402, 166, 40));
        jPanel38.add(date_from_view, new org.netbeans.lib.awtextra.AbsoluteConstraints(99, 245, 183, 35));
        jPanel38.add(date_to_view, new org.netbeans.lib.awtextra.AbsoluteConstraints(99, 307, 183, 36));

        jLabel73.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/a.jpg"))); // NOI18N
        jLabel73.setText("r");
        jLabel73.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel73MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel73MouseExited(evt);
            }
        });
        jPanel38.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 690));

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

        RETURN_BOOKS.setBackground(new java.awt.Color(204, 0, 102));

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel71.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(0, 51, 51));
        jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AddNewBookIcons/AddNewBookIcons/icons8_Books_52px_1.png"))); // NOI18N
        jLabel71.setText("Return Book");
        jPanel31.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 230, -1));

        jPanel32.setBackground(new java.awt.Color(0, 102, 102));

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel31.add(jPanel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 260, 5));

        return_bookid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 51, 0)));
        return_bookid.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        return_bookid.setPhColor(new java.awt.Color(102, 51, 0));
        return_bookid.setPlaceholder("Enter Book  Id");
        return_bookid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                return_bookidFocusLost(evt);
            }
        });
        return_bookid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                return_bookidActionPerformed(evt);
            }
        });
        jPanel31.add(return_bookid, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 160, -1, -1));

        return_student_id.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 51, 0)));
        return_student_id.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        return_student_id.setPhColor(new java.awt.Color(153, 51, 0));
        return_student_id.setPlaceholder("Enter Student Id");
        return_student_id.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                return_student_idFocusLost(evt);
            }
        });
        return_student_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                return_student_idActionPerformed(evt);
            }
        });
        jPanel31.add(return_student_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, -1, -1));

        jLabel31.setBackground(new java.awt.Color(204, 255, 102));
        jLabel31.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(0, 51, 51));
        jLabel31.setText("Student Id :");
        jPanel31.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, -1, 30));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/x.png"))); // NOI18N
        jPanel31.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, 1170, 390));

        jLabel33.setBackground(new java.awt.Color(204, 255, 102));
        jLabel33.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 51, 51));
        jLabel33.setText("Book Id :");
        jPanel31.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, -1, 30));

        issued_errors.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        issued_errors.setForeground(new java.awt.Color(102, 0, 51));
        jPanel31.add(issued_errors, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 350, 350, 30));

        rSMaterialButtonRectangle1.setBackground(new java.awt.Color(0, 51, 51));
        rSMaterialButtonRectangle1.setText("find");
        rSMaterialButtonRectangle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle1ActionPerformed(evt);
            }
        });
        jPanel31.add(rSMaterialButtonRectangle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 280, 120, 40));

        rSMaterialButtonRectangle8.setBackground(new java.awt.Color(0, 102, 102));
        rSMaterialButtonRectangle8.setText("Return");
        rSMaterialButtonRectangle8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle8MouseClicked(evt);
            }
        });
        rSMaterialButtonRectangle8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle8ActionPerformed(evt);
            }
        });
        jPanel31.add(rSMaterialButtonRectangle8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 280, 120, 40));

        jPanel40.setBackground(new java.awt.Color(102, 0, 102));
        jPanel40.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(102, 0, 102)));
        jPanel40.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel72.setFont(new java.awt.Font("Segoe UI Variable", 1, 24)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText("Issued Book Details");
        jPanel40.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(143, 13, -1, -1));

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 12, Short.MAX_VALUE)
            .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel40.add(jPanel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(143, 51, -1, -1));

        issued_due_date.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        issued_due_date.setForeground(new java.awt.Color(255, 255, 255));
        jPanel40.add(issued_due_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 239, 278, 30));

        issued_date.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        issued_date.setForeground(new java.awt.Color(255, 255, 255));
        jPanel40.add(issued_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(133, 203, 295, 30));

        issued_student_name.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        issued_student_name.setForeground(new java.awt.Color(255, 255, 255));
        jPanel40.add(issued_student_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(151, 161, 277, 30));

        issued_book_name.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        issued_book_name.setForeground(new java.awt.Color(255, 255, 255));
        jPanel40.add(issued_book_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 121, 296, 30));

        issued_id.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        issued_id.setForeground(new java.awt.Color(255, 255, 255));
        jPanel40.add(issued_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 81, 299, 30));

        jLabel34.setBackground(new java.awt.Color(255, 255, 255));
        jLabel34.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Issue Id :");
        jPanel40.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 81, -1, 30));

        jLabel37.setBackground(new java.awt.Color(204, 255, 102));
        jLabel37.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Book Name :");
        jPanel40.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 121, -1, 30));

        jLabel38.setBackground(new java.awt.Color(204, 255, 102));
        jLabel38.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Student Name :");
        jPanel40.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 161, -1, 30));

        jLabel39.setBackground(new java.awt.Color(204, 255, 102));
        jLabel39.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Issue Date:");
        jPanel40.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 201, -1, 30));

        jLabel30.setBackground(new java.awt.Color(204, 255, 102));
        jLabel30.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Due Date:");
        jPanel40.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 241, -1, 30));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/a.jpg"))); // NOI18N
        jLabel23.setText("r");
        jPanel40.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 330));

        jPanel31.add(jPanel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, 450, 320));

        javax.swing.GroupLayout RETURN_BOOKSLayout = new javax.swing.GroupLayout(RETURN_BOOKS);
        RETURN_BOOKS.setLayout(RETURN_BOOKSLayout);
        RETURN_BOOKSLayout.setHorizontalGroup(
            RETURN_BOOKSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RETURN_BOOKSLayout.createSequentialGroup()
                .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        RETURN_BOOKSLayout.setVerticalGroup(
            RETURN_BOOKSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        MAIN_CARD.add(RETURN_BOOKS, "card2");

        MANAGE_BOOKS.setBackground(new java.awt.Color(255, 255, 255));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_bookid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_bookid.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_bookid.setPlaceholder("Enter Book ID");
        txt_bookid.setSelectionColor(new java.awt.Color(0, 153, 153));
        txt_bookid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_bookidFocusLost(evt);
            }
        });
        txt_bookid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_bookidActionPerformed(evt);
            }
        });
        jPanel12.add(txt_bookid, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 310, -1));

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AddNewBookIcons/AddNewBookIcons/icons8_Contact_26px.png"))); // NOI18N
        jPanel12.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jLabel41.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setText("Enter Book ID");
        jPanel12.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, -1, -1));

        jLabel42.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Enter Book Name");
        jPanel12.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, -1, -1));

        txt_bookname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_bookname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_bookname.setPlaceholder("Enter Book Name");
        txt_bookname.setSelectionColor(new java.awt.Color(0, 153, 153));
        txt_bookname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_booknameFocusLost(evt);
            }
        });
        txt_bookname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_booknameActionPerformed(evt);
            }
        });
        jPanel12.add(txt_bookname, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 310, -1));

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AddNewBookIcons/AddNewBookIcons/icons8_Moleskine_26px.png"))); // NOI18N
        jPanel12.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        jLabel44.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Author Name");
        jPanel12.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, -1, -1));

        txt_authorname.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_authorname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_authorname.setPlaceholder("Enter Author Name");
        txt_authorname.setSelectionColor(new java.awt.Color(0, 153, 153));
        txt_authorname.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_authornameFocusLost(evt);
            }
        });
        txt_authorname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_authornameActionPerformed(evt);
            }
        });
        jPanel12.add(txt_authorname, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, 310, 30));

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AddNewBookIcons/AddNewBookIcons/icons8_Collaborator_Male_26px.png"))); // NOI18N
        jPanel12.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, -1, -1));

        jLabel46.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Quantity");
        jPanel12.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 360, -1, -1));

        txt_quantity.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_quantity.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_quantity.setPlaceholder("Enter Number Of Books");
        txt_quantity.setSelectionColor(new java.awt.Color(0, 153, 153));
        txt_quantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_quantityFocusLost(evt);
            }
        });
        txt_quantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_quantityActionPerformed(evt);
            }
        });
        jPanel12.add(txt_quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 390, 310, -1));

        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AddNewBookIcons/AddNewBookIcons/icons8_Unit_26px.png"))); // NOI18N
        jPanel12.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, -1, -1));

        rSMaterialButtonRectangle2.setBackground(new java.awt.Color(255, 255, 255));
        rSMaterialButtonRectangle2.setForeground(new java.awt.Color(0, 51, 51));
        rSMaterialButtonRectangle2.setText("Add");
        rSMaterialButtonRectangle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle2ActionPerformed(evt);
            }
        });
        jPanel12.add(rSMaterialButtonRectangle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 470, 160, 40));

        rSMaterialButtonRectangle3.setBackground(new java.awt.Color(0, 102, 102));
        rSMaterialButtonRectangle3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        rSMaterialButtonRectangle3.setText("delete");
        rSMaterialButtonRectangle3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle3ActionPerformed(evt);
            }
        });
        jPanel12.add(rSMaterialButtonRectangle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 470, 130, 40));

        rSMaterialButtonRectangle4.setBackground(new java.awt.Color(0, 102, 102));
        rSMaterialButtonRectangle4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        rSMaterialButtonRectangle4.setText("Update");
        rSMaterialButtonRectangle4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle4ActionPerformed(evt);
            }
        });
        jPanel12.add(rSMaterialButtonRectangle4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 130, 40));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/a.jpg"))); // NOI18N
        jLabel21.setText("r");
        jPanel12.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 580));

        jSplitPane3.setLeftComponent(jPanel12);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_book2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Book id", "Name", "Author", "Quantity"
            }
        ));
        tbl_book2.setColorBackgoundHead(new java.awt.Color(0, 51, 51));
        tbl_book2.setColorBordeFilas(new java.awt.Color(255, 255, 255));
        tbl_book2.setColorBordeHead(new java.awt.Color(255, 255, 255));
        tbl_book2.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        tbl_book2.setColorFilasForeground1(new java.awt.Color(0, 51, 51));
        tbl_book2.setColorFilasForeground2(new java.awt.Color(0, 51, 51));
        tbl_book2.setColorSelBackgound(new java.awt.Color(0, 153, 153));
        tbl_book2.setRowHeight(40);
        tbl_book2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_book2MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbl_book2);

        jPanel14.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 600, 370));

        jLabel48.setFont(new java.awt.Font("Segoe UI Variable", 1, 24)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(102, 102, 102));
        jLabel48.setText("Manage Books");
        jPanel14.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, -1));

        jPanel15.setBackground(new java.awt.Color(0, 102, 102));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel14.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 60, 270, 5));

        jLabel49.setBackground(new java.awt.Color(255, 255, 255));
        jLabel49.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(0, 102, 153));
        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AddNewBookIcons/AddNewBookIcons/icons8_Rewind_48px.png"))); // NOI18N
        jLabel49.setText("Back");
        jLabel49.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel49MouseClicked(evt);
            }
        });
        jPanel14.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, -1, -1));

        jSplitPane3.setRightComponent(jPanel14);

        javax.swing.GroupLayout MANAGE_BOOKSLayout = new javax.swing.GroupLayout(MANAGE_BOOKS);
        MANAGE_BOOKS.setLayout(MANAGE_BOOKSLayout);
        MANAGE_BOOKSLayout.setHorizontalGroup(
            MANAGE_BOOKSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane3)
        );
        MANAGE_BOOKSLayout.setVerticalGroup(
            MANAGE_BOOKSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane3)
        );

        MAIN_CARD.add(MANAGE_BOOKS, "card2");

        MANAGE_STUDENTS.setBackground(new java.awt.Color(255, 255, 255));

        jPanel16.setBackground(new java.awt.Color(102, 0, 102));
        jPanel16.setPreferredSize(new java.awt.Dimension(200, 700));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_studentid.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(255, 255, 255)));
        txt_studentid.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txt_studentid.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_studentid.setPlaceholder("Enter Book ID");
        txt_studentid.setSelectionColor(new java.awt.Color(0, 0, 0));
        txt_studentid.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_studentidFocusLost(evt);
            }
        });
        txt_studentid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_studentidActionPerformed(evt);
            }
        });
        jPanel16.add(txt_studentid, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 300, -1));

        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AddNewBookIcons/AddNewBookIcons/icons8_Contact_26px.png"))); // NOI18N
        jPanel16.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jLabel51.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(255, 255, 255));
        jLabel51.setText("Enter Book ID");
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

        rSMaterialButtonRectangle5.setBackground(new java.awt.Color(255, 255, 255));
        rSMaterialButtonRectangle5.setForeground(new java.awt.Color(51, 0, 255));
        rSMaterialButtonRectangle5.setText("Add");
        rSMaterialButtonRectangle5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle5ActionPerformed(evt);
            }
        });
        jPanel16.add(rSMaterialButtonRectangle5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 450, 280, 40));

        rSMaterialButtonRectangle6.setBackground(new java.awt.Color(0, 102, 102));
        rSMaterialButtonRectangle6.setText("delete");
        rSMaterialButtonRectangle6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle6ActionPerformed(evt);
            }
        });
        jPanel16.add(rSMaterialButtonRectangle6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 500, 140, 40));

        rSMaterialButtonRectangle7.setBackground(new java.awt.Color(0, 102, 102));
        rSMaterialButtonRectangle7.setText("Update");
        rSMaterialButtonRectangle7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle7ActionPerformed(evt);
            }
        });
        jPanel16.add(rSMaterialButtonRectangle7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 500, 140, 40));

        combo_branch.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        combo_branch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "   ", "IT", "CSE", "SCA", "OTHERS", " " }));
        jPanel16.add(combo_branch, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, 300, 30));

        combo_course.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        combo_course.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "      ", "BCA", "BSC", "MSC", "MBA", "MCA" }));
        jPanel16.add(combo_course, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 290, 300, 30));

        imafr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/a.jpg"))); // NOI18N
        imafr.setText("r");
        jPanel16.add(imafr, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 430, 700));

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
        tbl_student.setColorBackgoundHead(new java.awt.Color(0, 51, 51));
        tbl_student.setColorBordeFilas(new java.awt.Color(255, 255, 255));
        tbl_student.setColorBordeHead(new java.awt.Color(255, 255, 255));
        tbl_student.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        tbl_student.setColorFilasForeground1(new java.awt.Color(0, 51, 51));
        tbl_student.setColorFilasForeground2(new java.awt.Color(0, 51, 51));
        tbl_student.setColorSelBackgound(new java.awt.Color(0, 102, 102));
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
        jLabel58.setText("Manage Students");
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
        jLabel60.setText("Issue Book");
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

        jLabel26.setBackground(new java.awt.Color(204, 255, 102));
        jLabel26.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 51, 51));
        jLabel26.setText("Issue Date :");
        jPanel20.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, 30));

        jLabel27.setBackground(new java.awt.Color(204, 255, 102));
        jLabel27.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 51, 51));
        jLabel27.setText("Student Id :");
        jPanel20.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, 30));

        jLabel28.setBackground(new java.awt.Color(204, 255, 102));
        jLabel28.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 51, 51));
        jLabel28.setText("Due Date :");
        jPanel20.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, -1, 30));

        issuebook.setBackground(new java.awt.Color(0, 51, 51));
        issuebook.setText("Issue Book");
        issuebook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                issuebookActionPerformed(evt);
            }
        });
        jPanel20.add(issuebook, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 350, 60));
        jPanel20.add(date_issuedate, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 310, 250, 40));
        jPanel20.add(date_duedate, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 380, 250, 40));

        jPanel23.setBackground(new java.awt.Color(102, 0, 102));
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel61.setFont(new java.awt.Font("Segoe UI Black", 0, 29)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(255, 255, 255));
        jLabel61.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AddNewBookIcons/AddNewBookIcons/icons8_Student_Registration_100px_2.png"))); // NOI18N
        jLabel61.setText("Student Details");
        jPanel23.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 370, 110));

        jLabel62.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(255, 255, 255));
        jLabel62.setText("Branch :");
        jPanel23.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 380, -1, -1));

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel23.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 590, 1150, 80));

        lbl_branch.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lbl_branch.setForeground(new java.awt.Color(255, 255, 255));
        jPanel23.add(lbl_branch, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 380, 140, 30));

        jLabel63.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(255, 255, 255));
        jLabel63.setText("Student Name :");
        jPanel23.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 140, -1));

        jLabel64.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(255, 255, 255));
        jLabel64.setText("Course :");
        jPanel23.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, -1, -1));

        jLabel65.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(255, 255, 255));
        jLabel65.setText("student id:");
        jPanel23.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 190, -1, -1));

        lbl_studentid.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lbl_studentid.setForeground(new java.awt.Color(255, 255, 255));
        jPanel23.add(lbl_studentid, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 160, 30));

        lbl_studentname.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lbl_studentname.setForeground(new java.awt.Color(255, 255, 255));
        jPanel23.add(lbl_studentname, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 170, 30));

        lbl_course.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lbl_course.setForeground(new java.awt.Color(255, 255, 255));
        jPanel23.add(lbl_course, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 310, 160, 30));

        jPanel25.setBackground(new java.awt.Color(0, 51, 51));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel23.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 260, 5));

        lbl_errorstudent.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lbl_errorstudent.setForeground(new java.awt.Color(255, 255, 255));
        jPanel23.add(lbl_errorstudent, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 470, 190, 30));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/a.jpg"))); // NOI18N
        jLabel15.setText("r");
        jPanel23.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 350, 590));

        jPanel27.setBackground(new java.awt.Color(102, 0, 102));
        jPanel27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel66.setFont(new java.awt.Font("Segoe UI Black", 0, 29)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 255, 255));
        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AddNewBookIcons/AddNewBookIcons/icons8_Literature_100px_1.png"))); // NOI18N
        jLabel66.setText("Book Details");
        jPanel27.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 340, 110));

        jLabel67.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText("Quantity :");
        jPanel27.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 380, -1, -1));

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel27.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 590, 450, 100));

        lbl_errorbook.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lbl_errorbook.setForeground(new java.awt.Color(255, 255, 255));
        jPanel27.add(lbl_errorbook, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 470, 190, 30));

        jLabel68.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(255, 255, 255));
        jLabel68.setText("Book Name :");
        jPanel27.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 120, -1));

        jLabel69.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText("Author :");
        jPanel27.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, -1, -1));

        jLabel70.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setText("Book id:");
        jPanel27.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, -1, -1));

        lbl_bookid.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lbl_bookid.setForeground(new java.awt.Color(255, 255, 255));
        jPanel27.add(lbl_bookid, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 180, 30));

        lbl_bookname.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lbl_bookname.setForeground(new java.awt.Color(255, 255, 255));
        jPanel27.add(lbl_bookname, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 190, 30));

        lbl_author.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lbl_author.setForeground(new java.awt.Color(255, 255, 255));
        jPanel27.add(lbl_author, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 310, 190, 30));

        jPanel29.setBackground(new java.awt.Color(0, 51, 51));

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel27.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 260, 5));

        lbl_quantity.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lbl_quantity.setForeground(new java.awt.Color(255, 255, 255));
        jPanel27.add(lbl_quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 380, 190, 30));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/a.jpg"))); // NOI18N
        jLabel17.setText("r");
        jPanel27.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 600));

        javax.swing.GroupLayout ISSUE_BOOKSLayout = new javax.swing.GroupLayout(ISSUE_BOOKS);
        ISSUE_BOOKS.setLayout(ISSUE_BOOKSLayout);
        ISSUE_BOOKSLayout.setHorizontalGroup(
            ISSUE_BOOKSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ISSUE_BOOKSLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ISSUE_BOOKSLayout.setVerticalGroup(
            ISSUE_BOOKSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ISSUE_BOOKSLayout.createSequentialGroup()
                .addGroup(ISSUE_BOOKSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(ISSUE_BOOKSLayout.createSequentialGroup()
                        .addGroup(ISSUE_BOOKSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, 592, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MAIN_CARD.add(ISSUE_BOOKS, "card7");

        DEFAULTERS_LIST.setBackground(new java.awt.Color(102, 0, 51));

        jPanel42.setBackground(new java.awt.Color(102, 0, 102));
        jPanel42.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel43.setBackground(new java.awt.Color(102, 0, 102));
        jPanel43.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 0, new java.awt.Color(255, 255, 255)));
        jPanel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel43MouseClicked(evt);
            }
        });
        jPanel43.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel83.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(255, 255, 255));
        jLabel83.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/AddNewBookIcons/AddNewBookIcons/icons8_Edit_Property_50px.png"))); // NOI18N
        jLabel83.setText("DEFAULTERS LIST");
        jPanel43.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(423, 6, 395, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/a.jpg"))); // NOI18N
        jLabel6.setText("r");
        jPanel43.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1210, 50));

        jPanel42.add(jPanel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 17, 1204, -1));

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel42.add(jPanel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 700, 1216, -1));

        Dl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "BOOK", "STUDENT", "ISSUE DATE", "DUE DATE", "STATUS"
            }
        ));
        Dl.setColorBackgoundHead(new java.awt.Color(0, 51, 51));
        Dl.setColorBordeFilas(new java.awt.Color(255, 255, 255));
        Dl.setColorBordeHead(new java.awt.Color(255, 255, 255));
        Dl.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        Dl.setColorFilasForeground1(new java.awt.Color(0, 51, 51));
        Dl.setColorFilasForeground2(new java.awt.Color(0, 51, 51));
        Dl.setColorSelBackgound(new java.awt.Color(0, 153, 153));
        Dl.setRowHeight(40);
        Dl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DlMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(Dl);

        jPanel42.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(47, 148, 1010, 298));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/a.jpg"))); // NOI18N
        jLabel5.setText("r");
        jPanel42.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 700));

        javax.swing.GroupLayout DEFAULTERS_LISTLayout = new javax.swing.GroupLayout(DEFAULTERS_LIST);
        DEFAULTERS_LIST.setLayout(DEFAULTERS_LISTLayout);
        DEFAULTERS_LISTLayout.setHorizontalGroup(
            DEFAULTERS_LISTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        DEFAULTERS_LISTLayout.setVerticalGroup(
            DEFAULTERS_LISTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        MAIN_CARD.add(DEFAULTERS_LIST, "card2");

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
                    .addComponent(BODY_PANEL, javax.swing.GroupLayout.PREFERRED_SIZE, 1500, Short.MAX_VALUE)
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

    private void pnl_pieMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_pieMouseClicked
        MAIN_CARD.removeAll();
        MAIN_CARD.add(PIE_CHART);
        MAIN_CARD.repaint();
        MAIN_CARD.revalidate();
        pnl_pie.setForeground(Color.red);// TODO add your handling code here:
    }//GEN-LAST:event_pnl_pieMouseClicked

    private void pnl_bokMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_bokMouseClicked
        MAIN_CARD.removeAll();
        MAIN_CARD.add(MANAGE_BOOKS);
        MAIN_CARD.repaint();
        MAIN_CARD.revalidate();
        pnl_bok.setForeground(Color.red);
    }//GEN-LAST:event_pnl_bokMouseClicked

    private void txt_bookidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_bookidFocusLost
        //        if ( vailduser() ==true)
        //        {
        //            JOptionPane.showMessageDialog(this, "username already exist");
        //
        //        }
    }//GEN-LAST:event_txt_bookidFocusLost

    private void txt_bookidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_bookidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_bookidActionPerformed

    private void txt_booknameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_booknameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_booknameFocusLost

    private void txt_booknameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_booknameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_booknameActionPerformed

    private void txt_authornameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_authornameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_authornameFocusLost

    private void txt_authornameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_authornameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_authornameActionPerformed

    private void txt_quantityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_quantityFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_quantityFocusLost

    private void txt_quantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_quantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_quantityActionPerformed

    private void tbl_book2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_book2MouseClicked
        int rowno = tbl_book2.getSelectedRow();
        TableModel tablemodel = tbl_book2.getModel();
        txt_bookid.setText(tablemodel.getValueAt(rowno, 0).toString());
        txt_bookname.setText(tablemodel.getValueAt(rowno, 1).toString());
        txt_authorname.setText(tablemodel.getValueAt(rowno, 2).toString());
        txt_quantity.setText(tablemodel.getValueAt(rowno, 3).toString());
    }//GEN-LAST:event_tbl_book2MouseClicked

    private void jLabel49MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel49MouseClicked
        HomePage home = new HomePage();
        home.setVisible(true);
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel49MouseClicked

    private void rSMaterialButtonRectangle2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle2ActionPerformed
        if (addbook() == true) {
            JOptionPane.showMessageDialog(this, "book Added");
            clearTable();
            setBookDetailstotable();
        } else {
            JOptionPane.showMessageDialog(this, "book Adding failed");
        }
    }//GEN-LAST:event_rSMaterialButtonRectangle2ActionPerformed

    private void rSMaterialButtonRectangle3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle3ActionPerformed
        if (Deletebook() == true) {
            JOptionPane.showMessageDialog(this, "book Deleted");
            clearTable();
            setBookDetailstotable();
        } else {
            JOptionPane.showMessageDialog(this, "book Deletion failed");
        }
    }//GEN-LAST:event_rSMaterialButtonRectangle3ActionPerformed

    private void rSMaterialButtonRectangle4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle4ActionPerformed
        if (updatebook() == true) {
            JOptionPane.showMessageDialog(this, "book updated");
            clearTable();
            setBookDetailstotable();
        } else {
            JOptionPane.showMessageDialog(this, "book updation failed");
        }
    }//GEN-LAST:event_rSMaterialButtonRectangle4ActionPerformed

    private void txt_studentidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_studentidFocusLost

    }//GEN-LAST:event_txt_studentidFocusLost

    private void txt_studentidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_studentidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_studentidActionPerformed

    private void txt_stuentnameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_stuentnameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_stuentnameFocusLost

    private void txt_stuentnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_stuentnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_stuentnameActionPerformed

    private void rSMaterialButtonRectangle5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle5ActionPerformed
       
        if (addstudent() == true) {
            JOptionPane.showMessageDialog(this, "student Added");
            clearstudentTable();
            setStudentDetailstotable();
        } else {
            JOptionPane.showMessageDialog(this, "student Adding failed");
        }
    }//GEN-LAST:event_rSMaterialButtonRectangle5ActionPerformed

    private void rSMaterialButtonRectangle6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle6ActionPerformed
        if (Deletestudent() == true) {
            JOptionPane.showMessageDialog(this, "student Deleted");
            clearstudentTable();
            setStudentDetailstotable();
        } else {
            JOptionPane.showMessageDialog(this, "student Deletion failed");
        }
    }//GEN-LAST:event_rSMaterialButtonRectangle6ActionPerformed

    private void rSMaterialButtonRectangle7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle7ActionPerformed
        if (updatestudent() == true) {
            JOptionPane.showMessageDialog(this, "student updated");
            clearstudentTable();
            setStudentDetailstotable();
        } else {
            JOptionPane.showMessageDialog(this, "student updation failed");
        }
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
        HomePage home = new HomePage();
        home.setVisible(true);
        dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel59MouseClicked

    private void pnl_stuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_stuMouseClicked
        MAIN_CARD.removeAll();
        MAIN_CARD.add(MANAGE_STUDENTS);
        MAIN_CARD.repaint();
        MAIN_CARD.revalidate();
        pnl_stu.setForeground(Color.red);
// TODO add your handling code here:
    }//GEN-LAST:event_pnl_stuMouseClicked

    private void pnl_rtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_rtnMouseClicked
        MAIN_CARD.removeAll();
        MAIN_CARD.add(RETURN_BOOKS);
        MAIN_CARD.repaint();
        MAIN_CARD.revalidate();
        pnl_rtn.setForeground(Color.red);
// TODO add your handling code here:
    }//GEN-LAST:event_pnl_rtnMouseClicked

    private void issue_bookidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_issue_bookidFocusLost
        if (!issue_bookid.getText().equals("")) {
            getbookdetails();
        }
    }//GEN-LAST:event_issue_bookidFocusLost

    private void issue_bookidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_issue_bookidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_issue_bookidActionPerformed

    private void issue_studentidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_issue_studentidFocusLost
        if (!issue_studentid.getText().equals("")) {
            getstudentdetails();
        }
    }//GEN-LAST:event_issue_studentidFocusLost

    private void issue_studentidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_issue_studentidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_issue_studentidActionPerformed

    private void issuebookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_issuebookActionPerformed
        if (lbl_quantity.getText().equals("0")) {
            JOptionPane.showMessageDialog(this, "book is not available");

        } else {

            if (isAlreadyissued() == false) {
                if (issuebook() == true) {
                    JOptionPane.showMessageDialog(this, "book inserted succesfully");
                    updatecount();
                } else {
                    JOptionPane.showMessageDialog(this, "book inserted failed");
                }

            } else {
                JOptionPane.showMessageDialog(this, "this student already has the same book");
            }
        }
    }//GEN-LAST:event_issuebookActionPerformed

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
        MAIN_CARD.add(DEFAULTERS_LIST);
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

    private void return_bookidFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_return_bookidFocusLost

    }//GEN-LAST:event_return_bookidFocusLost

    private void return_bookidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_return_bookidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_return_bookidActionPerformed

    private void return_student_idFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_return_student_idFocusLost

    }//GEN-LAST:event_return_student_idFocusLost

    private void return_student_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_return_student_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_return_student_idActionPerformed

    private void rSMaterialButtonRectangle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle1ActionPerformed
        Returndetails();
    }//GEN-LAST:event_rSMaterialButtonRectangle1ActionPerformed

    private void rSMaterialButtonRectangle8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle8MouseClicked

    private void rSMaterialButtonRectangle8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle8ActionPerformed
        if (Returnbook() == true) {
            updatereturnCount();
            JOptionPane.showMessageDialog(this, "returned sucessfull");

        } else {
            JOptionPane.showMessageDialog(this, "returned sucessfull");
        }
    }//GEN-LAST:event_rSMaterialButtonRectangle8ActionPerformed

    private void date_search2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_date_search2ActionPerformed
        if (date_from_view.getDate() != null && date_to_view.getDate() != null) {
            clearviewTable();
            datesearch();
        } else {
            JOptionPane.showMessageDialog(this, "please select a date");
        }
        //
        //        datesearch();
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

    private void jPanel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel43MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel43MouseClicked

    private void DlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DlMouseClicked

    }//GEN-LAST:event_DlMouseClicked

    private void pnl_homeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_homeMouseEntered
        pnl_home.setForeground(Color.cyan);


    }//GEN-LAST:event_pnl_homeMouseEntered

    private void pnl_bokMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_bokMouseEntered
        pnl_bok.setForeground(Color.CYAN);  // TODO add your handling code here:
    }//GEN-LAST:event_pnl_bokMouseEntered

    private void pnl_bokMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_bokMouseExited
        pnl_bok.setForeground(Color.white);
    }//GEN-LAST:event_pnl_bokMouseExited
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

    private void pnl_pieMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_pieMouseEntered
        pnl_pie.setForeground(Color.cyan);
    }//GEN-LAST:event_pnl_pieMouseEntered

    private void pnl_pieMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_pieMouseExited
        pnl_pie.setForeground(Color.white);
    }//GEN-LAST:event_pnl_pieMouseExited

    private void pnl_stuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_stuMouseEntered
        pnl_stu.setForeground(Color.cyan);
    }//GEN-LAST:event_pnl_stuMouseEntered

    private void pnl_stuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_stuMouseExited
        pnl_stu.setForeground(Color.white);
    }//GEN-LAST:event_pnl_stuMouseExited

    private void pnl_issMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_issMouseEntered
        pnl_iss.setForeground(Color.cyan);
    }//GEN-LAST:event_pnl_issMouseEntered

    private void pnl_defMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_defMouseEntered
        pnl_def.setForeground(Color.cyan);
    }//GEN-LAST:event_pnl_defMouseEntered

    private void pnl_issMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_issMouseExited
        pnl_iss.setForeground(Color.white);
    }//GEN-LAST:event_pnl_issMouseExited

    private void pnl_rtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_rtnMouseEntered
        pnl_rtn.setForeground(Color.cyan);
    }//GEN-LAST:event_pnl_rtnMouseEntered

    private void pnl_rtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnl_rtnMouseExited
        pnl_rtn.setForeground(Color.white);
    }//GEN-LAST:event_pnl_rtnMouseExited

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

    private void jLabel73MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel73MouseEntered
//       pnl_vie.setForeground(Color.red);  
    }//GEN-LAST:event_jLabel73MouseEntered

    private void jLabel73MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel73MouseExited
        pnl_vie.setForeground(Color.white);
    }//GEN-LAST:event_jLabel73MouseExited

    private void HOMEMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HOMEMouseEntered
        pnl_home.setForeground(Color.red);
    }//GEN-LAST:event_HOMEMouseEntered

    private void HOMEMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HOMEMouseExited
        pnl_home.setForeground(Color.white);   // TODO add your handling code here:
    }//GEN-LAST:event_HOMEMouseExited

    private void jLabel12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseExited
        jLabel12.setForeground(Color.black);
    }//GEN-LAST:event_jLabel12MouseExited

    private void tbl_student1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_student1MouseEntered
        pnl_home.setForeground(Color.red);
    }//GEN-LAST:event_tbl_student1MouseEntered

    private void tbl_book1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_book1MouseEntered
        pnl_home.setForeground(Color.red);
    }//GEN-LAST:event_tbl_book1MouseEntered

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            new HomePage().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Admin_piechart1;
    private javax.swing.JSplitPane BODY_PANEL;
    private javax.swing.JPanel DASHBOARD_PANEL;
    private javax.swing.JPanel DEFAULTERS_LIST;
    private rojeru_san.complementos.RSTableMetro Dl;
    private javax.swing.JPanel HEAD_PANEL;
    private javax.swing.JPanel HOME;
    private javax.swing.JPanel ISSUE_BOOKS;
    private javax.swing.JPanel MAIN_CARD;
    private javax.swing.JPanel MANAGE_BOOKS;
    private javax.swing.JPanel MANAGE_STUDENTS;
    private javax.swing.JPanel PIE_CHART;
    private javax.swing.JPanel RETURN_BOOKS;
    private javax.swing.JPanel VIEW_RECORDS;
    private javax.swing.JComboBox<String> combo_branch;
    private javax.swing.JComboBox<String> combo_course;
    private javax.swing.JLabel count_book;
    private javax.swing.JLabel count_defulter;
    private javax.swing.JLabel count_issued;
    private javax.swing.JLabel count_stuent;
    private com.toedter.calendar.JDateChooser date_duedate;
    private com.toedter.calendar.JDateChooser date_from_view;
    private com.toedter.calendar.JDateChooser date_issuedate;
    private rojerusan.RSMaterialButtonRectangle date_search2;
    private rojerusan.RSMaterialButtonRectangle date_search3;
    private com.toedter.calendar.JDateChooser date_to_view;
    private javax.swing.JLabel imafr;
    private app.bolivia.swing.JCTextField issue_bookid;
    private app.bolivia.swing.JCTextField issue_studentid;
    private rojerusan.RSMaterialButtonCircle issuebook;
    private javax.swing.JLabel issued_book_name;
    private javax.swing.JLabel issued_date;
    private javax.swing.JLabel issued_due_date;
    private javax.swing.JLabel issued_errors;
    private javax.swing.JLabel issued_id;
    private javax.swing.JLabel issued_student_name;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JLabel lbl_author;
    private javax.swing.JLabel lbl_bookid;
    private javax.swing.JLabel lbl_bookname;
    private javax.swing.JLabel lbl_branch;
    private javax.swing.JLabel lbl_course;
    private javax.swing.JLabel lbl_errorbook;
    private javax.swing.JLabel lbl_errorstudent;
    private javax.swing.JLabel lbl_quantity;
    private javax.swing.JLabel lbl_studentid;
    private javax.swing.JLabel lbl_studentname;
    private javax.swing.JLabel pnl_bok;
    private javax.swing.JPanel pnl_book;
    private javax.swing.JLabel pnl_def;
    private javax.swing.JLabel pnl_home;
    private javax.swing.JLabel pnl_iss;
    private javax.swing.JLabel pnl_pie;
    private javax.swing.JLabel pnl_rtn;
    private javax.swing.JLabel pnl_stu;
    private javax.swing.JPanel pnl_student;
    private javax.swing.JLabel pnl_vie;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle1;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle2;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle3;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle4;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle5;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle6;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle7;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle8;
    private app.bolivia.swing.JCTextField return_bookid;
    private app.bolivia.swing.JCTextField return_student_id;
    private rojeru_san.complementos.RSTableMetro t;
    private javax.swing.JLabel t_name;
    private rojeru_san.complementos.RSTableMetro tbl_book1;
    private rojeru_san.complementos.RSTableMetro tbl_book2;
    private rojeru_san.complementos.RSTableMetro tbl_student;
    private rojeru_san.complementos.RSTableMetro tbl_student1;
    private app.bolivia.swing.JCTextField txt_authorname;
    private app.bolivia.swing.JCTextField txt_bookid;
    private app.bolivia.swing.JCTextField txt_bookname;
    private app.bolivia.swing.JCTextField txt_quantity;
    private app.bolivia.swing.JCTextField txt_studentid;
    private app.bolivia.swing.JCTextField txt_stuentname;
    // End of variables declaration//GEN-END:variables
}
