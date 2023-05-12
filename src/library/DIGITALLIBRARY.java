package library;

import digital.Loginpage;
public class DIGITALLIBRARY {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      java.awt.EventQueue.invokeLater(() -> {
          Loginpage login = new Loginpage();
          login.setVisible(true);
         
      });
   }
    }
    
