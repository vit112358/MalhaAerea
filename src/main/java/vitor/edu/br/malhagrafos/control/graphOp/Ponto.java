package vitor.edu.br.malhagrafos.control.graphOp;

import java.util.Objects;

public class Ponto {

    public double x;
    public double y;

    public Ponto() {

    }

    public Ponto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double d) {
        this.x = d;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double distance(Ponto p) {
        return Math.hypot(x - p.x, y - p.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Ponto other = (Ponto) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "Ponto [x=" + x + ", y=" + y + "]";
    }
}
