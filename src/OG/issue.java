/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package OG;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;

/**
 *
 * @author kotar
 */
public final class issue extends javax.swing.JFrame {

    /**
     * Creates new form issue
     */
    public issue() {
        initComponents();
        showPieChart();
        showLineChart();
        showHistogramChart();
    }
    
    public void showHistogramChart() {
    // create dataset
    HistogramDataset dataset = new HistogramDataset();
    try {
        Connection con = BACKENDCONNECTION.getConnection();
        String sql = "SELECT book_name, COUNT(*) AS book_count FROM book_details where book_name";
        Statement st = con.createStatement();
        ResultSet resultSet = st.executeQuery(sql);
        while (resultSet.next()) {
            dataset.addSeries(resultSet.getString("book_count"), new double[]{resultSet.getDouble("quantity")}, 10);
        }
    } catch (SQLException e) {
    }
    JFreeChart chart = ChartFactory.createHistogram("book Details", "Book Name", "book Count", dataset, PlotOrientation.VERTICAL, false, true, false);
    chart.setBackgroundPaint(Color.white);
//double[] values = { 95, 49, 14, 59, 50, 66, 47, 40, 1, 67,
//                            12, 58, 28, 63, 14, 9, 31, 17, 94, 71,
//                            49, 64, 73, 97, 15, 63, 10, 12, 31, 62,
//                            93, 49, 74, 90, 59, 14, 15, 88, 26, 57,
//                            77, 44, 58, 91, 10, 67, 57, 19, 88, 84                                
//                          };
// 
// 
//        HistogramDataset dataset = new HistogramDataset();
//        dataset.addSeries("key", values, 20);
//        
//         JFreeChart chart = ChartFactory.createHistogram("Histogram","Book", "Frequency", dataset,PlotOrientation.VERTICAL, false,true,false);
//            XYPlot plot= chart.getXYPlot();
//        plot.setBackgroundPaint(Color.WHITE);
    ChartPanel chartPanel = new ChartPanel(chart);
    panelHistogramChart.removeAll();
    panelHistogramChart.add(chartPanel, BorderLayout.CENTER);
    panelHistogramChart.validate();
}

    
    public void showLineChart() {
    DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();
//    try {
//        Connection con = BACKENDCONNECTION.getConnection();
//        String sql = "SELECT (*)book_details, issue_count FROM book_details where Quantity=?	";
//        Statement st = con.createStatement();
//        ResultSet resultSet = st.executeQuery(sql);
//        while (resultSet.next()) {
//            lineDataset.setValue(resultSet.getInt("issue_count"), "Issues", resultSet.getString("Quantity	"));
//        }
//    } catch (SQLException e) {
//    }
//    
//    JFreeChart lineChart = ChartFactory.createLineChart("Issue Book Summary", "Month", "Issue Count", lineDataset, PlotOrientation.VERTICAL, true, true, false);
//    
//    lineChart.getCategoryPlot().setBackgroundPaint(Color.white);
//    lineChart.getCategoryPlot().setDomainGridlinePaint(Color.black);
//    lineChart.getCategoryPlot().setRangeGridlinePaint(Color.black);

DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(200, "Amount", "january");
        dataset.setValue(150, "Amount", "february");
        dataset.setValue(18, "Amount", "march");
        dataset.setValue(100, "Amount", "april");
        dataset.setValue(80, "Amount", "may");
        dataset.setValue(250, "Amount", "june");
        
        JFreeChart chart = ChartFactory.createBarChart("contribution","monthly","amount", 
                dataset, PlotOrientation.VERTICAL, false,true,false);
        
        CategoryPlot categoryPlot = chart.getCategoryPlot();
        //categoryPlot.setRangeGridlinePaint(Color.BLUE);
        categoryPlot.setBackgroundPaint(Color.WHITE);
        BarRenderer renderer = (BarRenderer) categoryPlot.getRenderer();
        Color clr3 = new Color(204,0,51);
        renderer.setSeriesPaint(0, clr3);
        
        ChartPanel barpChartPanel = new ChartPanel(chart);
        panelLineChart.removeAll();
        panelLineChart.add(barpChartPanel, BorderLayout.CENTER);
        panelLineChart.validate();
    
//    ChartPanel lineChartPanel = new ChartPanel(lineChart);
//    panelLineChart.removeAll();
//    panelLineChart.add(lineChartPanel, BorderLayout.CENTER);
//    panelLineChart.validate();
}
    
    public void showPieChart(){
        
        //create dataset
      DefaultPieDataset barDataset = new DefaultPieDataset( );
     
        try {
            Connection con =BACKENDCONNECTION.getConnection();
            String sql ="SELECT book_name,COUNT(*) AS issue_count FROM issue_book group by book_id";
             Statement st =con.createStatement();
             ResultSet resultSet =st.executeQuery(sql);
             while (resultSet.next()) {
                 barDataset.setValue(resultSet.getString("book_name"), Double.valueOf(resultSet.getDouble("issue_count")));
             }
        } catch (SQLException e) {
        }
        JFreeChart piechart = ChartFactory.createPieChart("Issue book Details",barDataset, false,true,false);
        PiePlot piePlot =(PiePlot) piechart.getPlot();
      
       //changing pie chart blocks colors
        piePlot.setSectionPaint("a", new Color(255,255,102));
        piePlot.setSectionPaint("b", new Color(102,255,102));
        piePlot.setSectionPaint("c", new Color(255,102,153));
        piePlot.setSectionPaint("d", new Color(0,204,204));
      
       
        piePlot.setBackgroundPaint(Color.white);
        
        ChartPanel barChartPanel = new ChartPanel(piechart);
        panelBarChart.removeAll();
        panelBarChart.add(barChartPanel, BorderLayout.CENTER);
        panelBarChart.validate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelBarChart = new javax.swing.JPanel();
        panelLineChart = new javax.swing.JPanel();
        panelHistogramChart = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1208, 720));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1020, 320));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(238, 130, 238));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/icons8-home-64.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
        });
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 70, 70));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/icons8-book-64.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/icons8-purchase-order-64.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/icons8-combo-chart-65.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel4MousePressed(evt);
            }
        });
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 398, 64, -1));

        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kGradientPanel1.setkEndColor(new java.awt.Color(153, 0, 153));
        kGradientPanel1.setkStartColor(new java.awt.Color(255, 204, 255));
        jPanel8.add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, 90, 730));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 700));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 730));

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/duck.gif"))); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(-80, 300, 420, 420));

        jTabbedPane1.setBackground(new java.awt.Color(0, 0, 0));
        jTabbedPane1.setForeground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 14)); // NOI18N

        panelBarChart.setLayout(new java.awt.BorderLayout());
        jTabbedPane1.addTab("Bar Chat", panelBarChart);

        panelLineChart.setLayout(new java.awt.BorderLayout());
        jTabbedPane1.addTab("Line Chart", panelLineChart);

        panelHistogramChart.setLayout(new java.awt.BorderLayout());
        jTabbedPane1.addTab("Histogram Chart", panelHistogramChart);

        jPanel2.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 910, 530));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/message.png"))); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 20, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 1150, 730));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        // TODO add your handling code here:
//        Color c = new Color(0,0,0);
//        jPanel3.setBackground(c);
    }//GEN-LAST:event_jLabel1MouseExited

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
//        Color s = new Color(238,130,238);
//        jPanel5.setBackground(s);
        Login l = new Login();
        l.show();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel4MousePressed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        ChatBot1 l = new ChatBot1();
        l.show();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        issue l = new issue();
        l.show();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        try{
            File file = new File("C:\\Users\\kotar\\Documents\\NetBeansProjects\\User Module\\src\\Resources\\12205208.pdf");
            if(file.exists()){
                if(Desktop.isDesktopSupported()){
                    Desktop.getDesktop().open(file);
                }
                else {
                    JOptionPane.showMessageDialog(this, "Unsupported");
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jLabel3MouseClicked

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
            java.util.logging.Logger.getLogger(issue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(issue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(issue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(issue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new issue().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JPanel panelBarChart;
    private javax.swing.JPanel panelHistogramChart;
    private javax.swing.JPanel panelLineChart;
    // End of variables declaration//GEN-END:variables
}
