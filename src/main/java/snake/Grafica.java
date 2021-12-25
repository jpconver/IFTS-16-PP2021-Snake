
package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;



public class Grafica extends JPanel implements KeyListener, Runnable{
    private static final long serialVersionUID = 1L;
    private int anchoWindow;
    private int largoWindow;
    private Point snake;
    private int anchoSnake;
    private int largoSnake;
    private String direccion;
    private int anchoFood;
    private int largoFood;
    private List<Point> colaSnake;
    private boolean gameOver;
    private BufferedImage img;
    private Sonidos sonidos;
    boolean si;
    private List<Comida> comida;

    public Grafica(int anchoWindow, int largoWindow) {
        this.anchoWindow = anchoWindow;
        this.largoWindow = largoWindow;
        this.anchoSnake = 10;
        this.largoSnake = 10;
        this.anchoFood = 10;
        this.largoFood = 10;
        this.direccion="DER";
        this.colaSnake=new ArrayList();
        this.si=false;
        this.gameOver=false;
        this.comida=new ArrayList();
        cargarSonidos();
        this.sonidos.repetirSonido("can");
        iniciar();
    }
    
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(anchoWindow,largoWindow);
    }

    @Override
    public void run() {
        while(true){
        actualizaAmbiente();
        repaint();
        esperar(50);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        dibujar(g);
    }
    
    @Override
    public void repaint() {
        super.repaint(); 
    }
    
    public void dibujar(Graphics g){  
        if(gameOver){
            try {
                String path = Paths.get(Grafica.class.getClassLoader().getResource("imagenes/goverPNG.png").toURI()).toString();
                img = ImageIO.read(new File(path));
                g.drawImage(img,0,0, anchoWindow, largoWindow, null);
                
                g.setColor(Color.red);
                g.setFont(new Font("TimesRoman", Font.BOLD, 40));      
                g.drawString("PUNTAJE "+(colaSnake.size()-1), 220, 150);

                g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                g.drawString("N para jugar otra vez!", 220, 200);
                g.drawString("ESC para salir", 220, 250);
            } catch (Exception e) {
                System.out.println(e);
            }  
        }else{
            g.setColor(new Color(43, 110, 64));
            g.fillRect(0,0, 640, 480);
            

            g.setColor(new Color(13, 51, 25));
            if(colaSnake.size()>0){
                for(int i=0; i<colaSnake.size();i++){
                Point s=(Point)colaSnake.get(i);
                g.fillRect(s.x, s.y, anchoSnake, largoSnake);
                }
            }
        
            g.setColor(Color.YELLOW);
            g.fillRect(comida.get(0).getPosicion().x, comida.get(0).getPosicion().y, anchoFood, largoFood);
            
            
            g.setColor(Color.ORANGE);
            g.fillRect(comida.get(1).getPosicion().x, comida.get(1).getPosicion().y, anchoFood, largoFood);
            
        }
    }
    
    public void actualizaAmbiente() {
        eventosTeclas();


        colaSnake.add(0,new Point(snake.x,snake.y));
        colaSnake.remove(colaSnake.size()-1);
        

        for (int i = 1; i < colaSnake.size(); i++) {
            Point nueS=colaSnake.get(i);
            if(snake.x==nueS.x && snake.y == nueS.y){
                gameOver=true;
            }
        }
        

        if(hayColision(snake.x, snake.y, anchoSnake,largoSnake, comida.get(0).getPosicion().x, comida.get(0).getPosicion().y, anchoFood, largoFood)){
            colaSnake.add(0,new Point(snake.x, snake.y));
            System.out.println(colaSnake.size());
            generarComida(0);
            sonidos.tocarSonido("kir");
            si=false;
        }
        
        
        if(hayColision(snake.x, snake.y, anchoSnake,largoSnake, comida.get(1).getPosicion().x, comida.get(1).getPosicion().y, anchoFood, largoFood)){
            colaSnake.add(0,new Point(snake.x, snake.y));
            System.out.println(colaSnake.size());
            generarComida(1);
            sonidos.tocarSonido("pom");
            si=true;
            
        }
        
        if(si){
            esperar(70);
        }
        

    }

    public void generarComida(int index){
        Random rm= new Random();
        comida.get(index).getPosicion().x=(rm.nextInt(anchoWindow));
        comida.get(index).getPosicion().y=(rm.nextInt(largoWindow));
    }
    
    public void iniciar(){
        snake= new Point(320,240);
        colaSnake=new ArrayList();
        colaSnake.add(snake);
        comida.add(0,new Comida(10, 10, new Point(200,100)));
        comida.add(1,new Comida(10, 10, new Point(300,150)));
        
    }
    
    
    public void eventosTeclas() {
        if(!gameOver){
            if (direccion == "DER") {
                snake.x = snake.x + anchoSnake;
                if (snake.x > anchoWindow) {
                    snake.x = 0;
                }
            }else if (direccion == "IZQ") {
                snake.x = snake.x - anchoSnake;
                if (snake.x < 0) {
                    snake.x = anchoWindow - anchoSnake;
                }
            } else if (direccion == "ARRIBA") {
                snake.y = snake.y - largoSnake;
                if (snake.y < 0) {
                    snake.y = largoWindow;
                }
            } else if (direccion == "ABAJO") {
                snake.y = snake.y + largoSnake;
                if (snake.y > largoWindow) {
                    snake.y = 0;
                }
            }
        }
    }

    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	           System.exit(0);
        }else if(e.getKeyCode()== KeyEvent.VK_N){
                gameOver= false;
                iniciar();
        }else if (e.getKeyCode() == 39) {  //derecha
            if (direccion != "IZQ") {
                direccion = "DER";
            }
        } else if (e.getKeyCode() == 37) { // izquierda
            if (direccion != "DER") {
                direccion = "IZQ";    
              }
        } else if (e.getKeyCode() == 38) { // arriba
            if (direccion != "ABAJO") {
                direccion = "ARRIBA";
            }
        } else if (e.getKeyCode() == 40) { //abajo
            if (direccion != "ARRIBA") {
                direccion = "ABAJO";
            }    
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void esperar(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    
    public boolean hayColision(
            int EleX1,int EleY1,int EleAncho1,int EleLargo1,
            int EleX2,int EleY2,int EleAncho2,int EleLargo2
    ){
        if(haySolapamientoDeRango(EleX1, EleX1+EleAncho1, EleX2, EleX2+EleAncho2)
                && haySolapamientoDeRango(EleY1, EleY1+EleLargo1, EleY2, EleY2+EleLargo2))
        {
            return true;
        }
            return false;
    }  
    
    public boolean haySolapamientoDeRango(int a, int b,int c, int d){
        if(a<d && b>c){
            return true;
        }
        return false;
    }
    
    public void cargarSonidos(){
        try {
            sonidos = new Sonidos();
            sonidos.agregarSonido("toc", "sonidos/toc.wav");
            sonidos.agregarSonido("kir", "sonidos/kirby.wav");
            sonidos.agregarSonido("pom", "sonidos/pom.wav");
            sonidos.agregarSonido("can", "sonidos/cantinaW.wav");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
   
    
    

    

