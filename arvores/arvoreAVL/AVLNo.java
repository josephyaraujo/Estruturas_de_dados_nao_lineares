package arvores.arvoreAVL;
import arvores.arvoreBP.No;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
