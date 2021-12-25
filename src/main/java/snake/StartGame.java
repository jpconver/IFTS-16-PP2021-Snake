
package snake;

import javax.swing.JFrame;

public class StartGame {
    
    public static void main(String[] args) {
        int anchoWindow=640;
        int largoWindow=480;
    
        System.setProperty("sun.java2d.opengl","true");
        
        
        JFrame window= new JFrame("Juego");
        
        Grafica grafica=new Grafica(anchoWindow, largoWindow);
        
        window.add(grafica);
        
        window.setDefaultCloseOperation(3);
        
        window.pack();

        window.setVisible(true);

        window.setLocationRelativeTo(null);
        
        window.addKeyListener(grafica);
        
        Thread hilo1= new Thread(grafica);
        
        hilo1.start();
    }
    
}
