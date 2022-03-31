/**
 * There is essentially nothing in this file for students to edit.
 */

package the_app;

/**
 * Bootstrap for running TheApp.
 * 
 * @author cheng
 */
public class TheAppRunner {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
        } catch (InstantiationException ex) {
            System.err.println(ex);
        } catch (IllegalAccessException ex) {
            System.err.println(ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            System.err.println(ex);
        }

        java.awt.EventQueue.invokeLater(new TheApp());
    }
}
