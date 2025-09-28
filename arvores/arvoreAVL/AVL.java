package arvores.arvoreAVL;
import arvores.arvoreBP.ABP;
import java.lang.Math;

public class AVL extends ABP implements ArvoreAVL {
    public AVL(Object o) {
        super(o);
    }

    @Override
    public void rotacao(AVLNo atual, AVLNo pai) {
        if (atual.getFB() == 2) { // Desbalanceamento à direita
            if (atual.getFilhoDireito() != null && ((AVLNo) atual.getFilhoDireito()).getFB() >= 0) {
                rotacaoSimplesEsquerda(pai, atual); // Rotação simples à esquerda
            } else {
                rotacaoSimplesDireita(atual, (AVLNo) atual.getFilhoDireito()); // Rotação dupla à esquerda
                rotacaoSimplesEsquerda(pai, atual);
            }
        } else if (atual.getFB() == -2) { // Desbalanceamento à esquerda
            if (atual.getFilhoEsquerdo() != null && ((AVLNo) atual.getFilhoEsquerdo()).getFB() <= 0) {
                rotacaoSimplesDireita(pai, atual); // Rotação simples à direita
            } else {
                rotacaoSimplesEsquerda(atual, (AVLNo) atual.getFilhoEsquerdo()); // Rotação dupla à direita
                rotacaoSimplesDireita(pai, atual);
            }
        }
    }

    @Override
    public void altFBinsercao(AVLNo n) {
        while (n != null) {
            if (n.getPai() != null) {
                if (n == n.getPai().getFilhoEsquerdo()) {
                    n.getPai().setFB(n.getPai().getFB() - 1);
                } else {
                    n.getPai().setFB(n.getPai().getFB() + 1);
                }

                if (n.getPai().getFB() == 0) {
                    break; // A árvore está balanceada
                } else if (Math.abs(n.getPai().getFB()) == 2) {
                    rotacao((AVLNo) n.getPai(), (AVLNo) n.getPai().getPai());
                    break; // Após a rotação, a árvore está balanceada
                }
            }
            n = (AVLNo) n.getPai();
        }
    }

    @Override
    public void altFBremocao(