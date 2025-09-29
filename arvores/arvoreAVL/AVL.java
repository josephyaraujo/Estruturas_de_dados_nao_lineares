package arvores.arvoreAVL;
import arvores.arvoreBP.ABP;

public class AVL extends ABP implements ArvoreAVL {
    AVLNo raiz;
    int tamanho;

    public AVL(Object o) {
        super(o);
        raiz = new AVLNo(null, o, 0);
    }

    @Override
    public void rotacao(AVLNo atual, AVLNo pai) {
        if (pai.getFB() == 2) { // Desbalanceamento à esquerda
            if (atual.getFB() > 0) {
                rotacaoSimplesDireita(pai, atual);
            } else { //Rotação dupla à direita
                if (atual.getFilhoDireito() != null) {
                    rotacaoSimplesEsquerda(atual, (AVLNo) atual.getFilhoDireito());
                }
                if (pai.getFilhoEsquerdo() != null) {
                    rotacaoSimplesDireita(pai, (AVLNo) pai.getFilhoEsquerdo());
                }
            }
        } else if (pai.getFB() == -2) { // Desbalanceamento à direita
            if (atual.getFB() < 0) {
                rotacaoSimplesEsquerda(pai, atual);
            } else { // Rotação dupla à esquerda
                if (atual.getFilhoEsquerdo() != null) {
                    rotacaoSimplesDireita(atual, (AVLNo) atual.getFilhoEsquerdo()); 
                }
                if (pai.getFilhoDireito() != null) {
                    rotacaoSimplesEsquerda(pai, (AVLNo) pai.getFilhoDireito());
                }
            }
        }
    }

    @Override
    public void altFBinsercao(AVLNo n) {
        while (n != null) {
            if (n.getPai() != null) {
                if (n == n.getPai().getFilhoEsquerdo()) {
                    ((AVLNo) n.getPai()).setFB(((AVLNo) n.getPai()).getFB() + 1);
                } else {
                    ((AVLNo) n.getPai()).setFB(((AVLNo) n.getPai()).getFB() - 1);
                }

                if (((AVLNo) n.getPai()).getFB() == 0) {
                    break; // A árvore está balanceada
                
                } else if (Math.abs(((AVLNo) n.getPai()).getFB()) == 2) {
                    rotacao((AVLNo) n.getPai(), (AVLNo) n.getPai().getPai());
                    break; // Após a rotação, a árvore está balanceada
                }
            }
            n = (AVLNo) n.getPai();
        }
    }

    @Override
    public void altFBremocao(AVLNo n) {
        while (n != null) {
            if (n.getPai() != null) {
                if (n == n.getPai().getFilhoEsquerdo()) {
                    ((AVLNo) n.getPai()).setFB(((AVLNo) n.getPai()).getFB() - 1);
                } else {
                    ((AVLNo) n.getPai()).setFB(((AVLNo) n.getPai()).getFB() + 1);
                }
                
                if (((AVLNo) n.getPai()).getFB() != 0) {
                    break; // A árvore está balanceada
                
                } 
            }
            if (Math.abs(n.getFB()) == 2) {
                AVLNo filho = n.getFB() == 2 ? (AVLNo) n.getFilhoEsquerdo() : (AVLNo) n.getFilhoDireito();
                rotacao(n, filho);
            }
            n = (AVLNo) n.getPai();
        }
    }

    // Implementação da rotação simples à direita
    @Override
    public void rotacaoSimplesDireita(AVLNo pai, AVLNo atual) {
        AVLNo filhoEsquerdo = (AVLNo) atual.getFilhoEsquerdo();
        atual.setFilhoEsquerdo(filhoEsquerdo.getFilhoDireito());
        if (filhoEsquerdo.getFilhoDireito() != null) {
            ((AVLNo) filhoEsquerdo.getFilhoDireito()).setPai(atual);
        }
        filhoEsquerdo.setFilhoDireito(atual);
        filhoEsquerdo.setPai(pai);

        if (pai == null) {
            raiz = filhoEsquerdo;
        } else if (pai.getFilhoEsquerdo() == atual) {
            pai.setFilhoEsquerdo(filhoEsquerdo);
        } else {
            pai.setFilhoDireito(filhoEsquerdo);
        }
        atual.setPai(filhoEsquerdo);

        // Atualiza os fatores de balanceamento
        atual.setFB(atual.getFB() + 1 - Math.min(filhoEsquerdo.getFB(), 0));
        filhoEsquerdo.setFB(filhoEsquerdo.getFB() + 1 + Math.max(atual.getFB(), 0));
    }

    // Implementação da rotação simples à esquerda
    @Override
    public void rotacaoSimplesEsquerda(AVLNo pai, AVLNo atual) {
        AVLNo filhoDireito = (AVLNo) atual.getFilhoDireito();
        atual.setFilhoDireito(filhoDireito.getFilhoEsquerdo());
        if (filhoDireito.getFilhoEsquerdo() != null) {
            ((AVLNo) filhoDireito.getFilhoEsquerdo()).setPai(atual);
        }
        filhoDireito.setFilhoEsquerdo(atual);
        filhoDireito.setPai(pai);

        if (pai == null) {
            raiz = filhoDireito;
        } else if (pai.getFilhoEsquerdo() == atual) {
            pai.setFilhoEsquerdo(filhoDireito);
        } else {
            pai.setFilhoDireito(filhoDireito);
        }
        atual.setPai(filhoDireito);

        // Atualiza os fatores de balanceamento
        atual.setFB(atual.getFB() - 1 - Math.max(filhoDireito.getFB(), 0));
        filhoDireito.setFB(filhoDireito.getFB() - 1 + Math.min(atual.getFB(), 0));
    }
}

    