package dsa;
public class DronException extends Exception {
    private int codigo;

    public DronException(int codigo, String mensaje) {
        super(mensaje);
        this.codigo = codigo;
    }

    public int getCodigo() { return codigo; }
}
