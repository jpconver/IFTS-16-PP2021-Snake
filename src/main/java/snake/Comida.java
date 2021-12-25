
package snake;

import java.awt.Point;


public class Comida {
    private int anchoFood;
    private int largoFood;
    private Point posicion;

    public Comida(int anchoFood, int largoFood, Point posicion) {
        this.anchoFood = anchoFood;
        this.largoFood = largoFood;
        this.posicion = posicion;
    }

    public Point getPosicion() {
        return posicion;
    }

    public void setPosicion(Point posicion) {
        this.posicion = posicion;
    }
    
    
    
}
