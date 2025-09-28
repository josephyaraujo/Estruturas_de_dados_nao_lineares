package arvores.arvoreAVL;
import arvores.arvoreBP.No;

public class AVLNo extends No {
    private int fb; // Fator de balanceamento

    public AVLNo(No pai, Object valor ,int f) {
        super(pai, valor);
        fb = f;
    }

    public int getFB() {
        return fb;
    }

    public void setFB(int f) {
        fb = f;
    }
    
}
