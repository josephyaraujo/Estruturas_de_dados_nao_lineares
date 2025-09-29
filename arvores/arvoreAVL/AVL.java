package arvores.arvoreAVL;
import arvores.arvoreBP.ABP;
import java.util.ArrayList;
import java.util.List;

public class AVL extends ABP implements ArvoreAVL {
    AVLNo raiz;
    int tamanho;

    public AVL(Object o) {
        super(o);
        raiz = new AVLNo(null, o, 0);
    }

    @Override
    public void rotacao(AVLNo pai, AVLNo atual) {
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
        if (pai.getPai() != null) {
            atual.setPai(pai.getPai());
            if (pai == pai.getPai().getFilhoEsquerdo()) {
                pai.getPai().setFilhoEsquerdo(atual);
            } else {
                pai.getPai().setFilhoDireito(atual);
            }
        } else {
            atual.setPai(null);
            raiz = atual;
        }
        if (atual.getFilhoDireito() != null) {
            pai.setFilhoEsquerdo(atual.getFilhoDireito());
            atual.getFilhoDireito().setPai(pai);
        } else {
            pai.setFilhoEsquerdo(null);
        }
        atual.setFilhoDireito(pai);
        pai.setPai(atual);

        // Atualiza os fatores de balanceamento
        int fb_pai = pai.getFB() - 1 - Math.max(atual.getFB(), 0);
        atual.setFB(atual.getFB() - 1 + Math.min(fb_pai, 0));
        pai.setFB(fb_pai);
    }

    // Implementação da rotação simples à esquerda
    @Override
    public void rotacaoSimplesEsquerda(AVLNo pai, AVLNo atual) {
        if (pai.getPai() != null) {
            atual.setPai(pai.getPai());
            if (pai == pai.getPai().getFilhoEsquerdo()) {
                pai.getPai().setFilhoEsquerdo(atual);
            } else {
                pai.getPai().setFilhoDireito(atual);
            }
        } else {
            atual.setPai(null);
            raiz = atual;
        }
        if (atual.getFilhoEsquerdo() != null) {
            pai.setFilhoDireito(atual.getFilhoEsquerdo());
            atual.getFilhoEsquerdo().setPai(pai);
        } else {
            pai.setFilhoDireito(null);
        }
        atual.setFilhoEsquerdo(pai);
        pai.setPai(atual);

        // Atualiza os fatores de balanceamento
        int fb_pai = pai.getFB() + 1 - Math.min(atual.getFB(), 0);
        atual.setFB(atual.getFB() + 1 + Math.max(fb_pai, 0));
        pai.setFB(fb_pai);
    }

    @Override
    public void inserir(Object o) {
        AVLNo parent = (AVLNo) buscar(raiz, o);
        AVLNo novoNo = new AVLNo(parent, o, 0);
        if ((int) parent.getValor() > (int) o) {
            parent.setFilhoEsquerdo(novoNo);
            parent.setFB(parent.getFB() + 1);
            novoNo.setPai(parent);
            altFBinsercao(parent);
        } else {
            parent.setFilhoDireito(novoNo);
            parent.setFB(parent.getFB() - 1);
            novoNo.setPai(parent);
            altFBinsercao(parent);
        }
        tamanho++;
    }
    @Override
    public Object remover(Object o) {
        AVLNo node = (AVLNo) buscar(raiz, o);
        if (node == null) {
            throw new RuntimeException("O valor não foi encontrado na árvore");

        } else if (node.getFilhoEsquerdo() == null && node.getFilhoDireito() == null) {
            if (node.getPai() == null) {
                raiz = null; // A árvore ficará vazia
            } else if (node == node.getPai().getFilhoEsquerdo()) {
                node.getPai().setFilhoEsquerdo(null);
                ((AVLNo) node.getPai()).setFB(((AVLNo) node.getPai()).getFB() - 1);
                altFBremocao((AVLNo) node.getPai());
            } else {
                node.getPai().setFilhoDireito(null);
                ((AVLNo) node.getPai()).setFB(((AVLNo) node.getPai()).getFB() + 1);
                altFBremocao((AVLNo) node.getPai());
            }
        } else if (temFilhoEsquerdo(node) ^ temFilhoDireito(node)) {
            AVLNo filho = (AVLNo) (temFilhoEsquerdo(node) ? node.getFilhoEsquerdo() : node.getFilhoDireito());
            
            if (node.getPai() == null) {
                raiz = filho;
                filho.setPai(null);
                altFBremocao(filho);
            } else {
                if (node == node.getPai().getFilhoEsquerdo()) {
                    node.getPai().setFilhoEsquerdo(filho);
                    filho.setPai(node.getPai());
                    ((AVLNo) node.getPai()).setFB(((AVLNo) node.getPai()).getFB() - 1);
                    altFBremocao((AVLNo) node.getPai());
                } else {
                    node.getPai().setFilhoDireito(filho);
                    filho.setPai(node.getPai());
                    ((AVLNo) node.getPai()).setFB(((AVLNo) node.getPai()).getFB() + 1);
                    altFBremocao((AVLNo) node.getPai());
                }
            }
        } else {
            AVLNo sucessor = (AVLNo) node.getFilhoDireito();
            while (sucessor.getFilhoEsquerdo() != null) {
                sucessor = (AVLNo) sucessor.getFilhoEsquerdo();
            }
            Object substituto = sucessor.getValor();
            remover(substituto);
            node.setValor(substituto);
        }
        tamanho--;
        return o;
    }
    public AVLNo filhoEsquerdo(AVLNo node) {
        return (AVLNo) node.getFilhoEsquerdo();
    }
    public AVLNo filhoDireito(AVLNo node) {
        return (AVLNo) node.getFilhoDireito();
    }
    public boolean temFilhoEsquerdo(AVLNo node) {
        return node.getFilhoEsquerdo() != null;
    }
    public boolean temFilhoDireito(AVLNo node) {
        return node.getFilhoDireito() != null;
    }
    public boolean ehExterno(AVLNo node) {
        return !temFilhoEsquerdo(node) && !temFilhoDireito(node);
    }
    public boolean ehInterno(AVLNo node) {
        return !ehExterno(node);
    }
    public boolean ehRaiz(AVLNo node) {
        return node == raiz;
    }
    public int altura(AVLNo node) {
        if (node == null || ehExterno(node)) {
            return 0;
        } else {
            return 1 + Math.max(altura((AVLNo) node.getFilhoEsquerdo()), altura((AVLNo) node.getFilhoDireito()));
        }
    }
    public void printArvoreComFB() {
        if (raiz == null) {
            System.out.println("(árvore vazia)");
            return;
        }
        
        int altura = altura(raiz);
        
        List<List<String>> linhas = new ArrayList<>();
        List<AVLNo> nivelAtual = new ArrayList<>();
        List<AVLNo> proximoNivel = new ArrayList<>();
        
        nivelAtual.add(raiz);
        boolean temNo = true;
        
        // Coleta todos os nós por nível
        while (temNo && altura > 0) {
            List<String> linha = new ArrayList<>();
            temNo = false;
            
            for (AVLNo n : nivelAtual) {
                if (n == null) {
                    linha.add("     "); // 5 espaços para manter alinhamento
                    proximoNivel.add(null);
                    proximoNivel.add(null);
                } else {
                    // Formata: valor[FB] (ex: 5[1], 10[-1])
                    String formato = String.format("%2d[%d]", n.getValor(), n.getFB());
                    linha.add(formato);
                    proximoNivel.add((AVLNo) n.getFilhoEsquerdo());
                    proximoNivel.add((AVLNo) n.getFilhoDireito());
                    
                    if (n.getFilhoEsquerdo() != null || n.getFilhoDireito() != null) {
                        temNo = true;
                    }
                }
            }
            
            linhas.add(linha);
            nivelAtual = new ArrayList<>(proximoNivel);
            proximoNivel.clear();
            altura--;
        }

        // Imprime os nós formatados
        for (int i = 0; i < linhas.size(); i++) {
            List<String> linha = linhas.get(i);
            
            // Cálculo correto dos espaços
            int espacosAntes = (int) Math.pow(2, (linhas.size() - i - 1)) - 1;
            int espacosEntre = (int) Math.pow(2, (linhas.size() - i)) - 1;
            
            // Espaços antes do primeiro nó
            for (int j = 0; j < espacosAntes; j++) {
                System.out.print("     "); // 5 espaços
            }
            
            // Imprime os nós com espaçamento
            for (int j = 0; j < linha.size(); j++) {
                System.out.print(linha.get(j));
                if (j < linha.size() - 1) {
                    for (int k = 0; k < espacosEntre; k++) {
                        System.out.print("     "); // 5 espaços
                    }
                }
            }
            System.out.println();
        }
    }
}

    