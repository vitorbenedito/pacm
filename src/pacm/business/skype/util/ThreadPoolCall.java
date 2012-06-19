package pacm.business.skype.util;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ThreadPoolCall extends ThreadPoolExecutor {
    
    JLabel label;
    JButton button;
    
    public ThreadPoolCall(JLabel label,JButton button) {
        super(  1, // core threads
                1, // max threads
                1, // timeout
                TimeUnit.MINUTES, // timeout units
                new LinkedBlockingQueue() // work queue
        );
        
        this.label = label;
        this.button = button;
    }

    
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        
       
        
        if(t != null) {
            System.out.println("Got an error: " + t);
            label.setText("Erro ao realizar a ligação");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadPoolCall.class.getName()).log(Level.SEVERE, null, ex);
            }
            label.setText("");
            button.setEnabled(true);
        } else {
            System.out.println("Everything's fine--situation normal!");
            label.setText("");
            button.setEnabled(true);
            
        }
    }
}