package arvores.arvoreAVL;
import arvores.arvoreBP.No;

public class AVLNo extends No {
    private int fb; // Fator de balanceamento

    public AVLNo(No pai, Object valor ,int fator) {
        super(pai, valor);
        fb = fator;
    }

    public int getFB() {
        return fb;
    }

    public void setFB(int fator) {
        fb = fator;
    }
    
}
