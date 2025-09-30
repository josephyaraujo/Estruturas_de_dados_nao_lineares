package arvores.arvoreAVL;
import arvores.arvoreBP.ABP;
import java.util.ArrayList;
import java.util.List;

public class AVL2 extends ABP implements ArvoreAVL {
    AVLNo raiz;
    int tamanho;

    public AVL2(Object o) {
        super(o);
        raiz = new AVLNo(null, o, 0);
    }

    @Override
    public void rotacao(AVLNo pai, AVLNo atual) {
        if (pai == null || atual == null) {
            throw new RuntimeException("Os nós pai e atual não podem ser nulos para a rotação.");
        }
        
        if (pai.getFB() == 2) { // Desbalanceamento à esquerda
            if (atual.getFB() >= 0) { // Left-Left
                rotacaoSimplesDireita(pai, atual);
            } else { // Left-Right
                // Rotações duplas
                AVLNo neto = (AVLNo) atual.getFilhoDireito();
                rotacaoSimplesEsquerda(atual, neto);
                rotacaoSimplesDireita(pai, (AVLNo) pai.getFilhoEsquerdo());
            }
        } else if (pai.getFB() == -2) { // Desbalanceamento à direita
            if (atual.getFB() <= 0) { // Right-Right
                rotacaoSimplesEsquerda(pai, atual);
            } else { // Right-Left
                // Rotações duplas
                AVLNo neto = (AVLNo) atual.getFilhoEsquerdo();
                rotacaoSimplesDireita(atual, neto);
                rotacaoSimplesEsquerda(pai, (AVLNo) pai.getFilhoDireito());
            }
        }
    }

    @Override
    public void altFBinsercao(AVLNo n) {
        AVLNo atual = n;
        while (atual != null) {
            // Verifica se precisa de rotação
            if (Math.abs(atual.getFB()) == 2) {
                AVLNo filho = atual.getFB() > 0 ? 
                    (AVLNo) atual.getFilhoEsquerdo() : 
                    (AVLNo) atual.getFilhoDireito();
                rotacao(atual, filho);
                break;
            }
            
            // Propaga para o pai
            if (atual.getPai() != null) {
                if (atual == atual.getPai().getFilhoEsquerdo()) {
                    ((AVLNo) atual.getPai()).setFB(((AVLNo) atual.getPai()).getFB() + 1);
                } else {
                    ((AVLNo) atual.getPai()).setFB(((AVLNo) atual.getPai()).getFB() - 1);
                }
            }
            
            atual = (AVLNo) atual.getPai();
        }
    }

    @Override
    public void altFBremocao(AVLNo n) {
        AVLNo atual = n;
        while (atual != null) {
            // Verifica se precisa de rotação
            if (Math.abs(atual.getFB()) == 2) {
                AVLNo filho = atual.getFB() >= 0 ? 
                    (AVLNo) atual.getFilhoEsquerdo() : 
                    (AVLNo) atual.getFilhoDireito();
                rotacao(atual, filho);
            }
            
            // Propaga para o pai
            if (atual.getPai() != null) {
                if (atual == atual.getPai().getFilhoEsquerdo()) {
                    ((AVLNo) atual.getPai()).setFB(((AVLNo) atual.getPai()).getFB() - 1);
                } else {
                    ((AVLNo) atual.getPai()).setFB(((AVLNo) atual.getPai()).getFB() + 1);
                }
            }
            
            atual = (AVLNo) atual.getPai();
        }
    }

    // Implementação da rotação simples à direita
    @Override
    public void rotacaoSimplesDireita(AVLNo pai, AVLNo atual) {
        AVLNo avo = (AVLNo) pai.getPai();
        AVLNo netoDireito = (AVLNo) atual.getFilhoDireito();

        if (avo != null) {
            atual.setPai(avo);
            if (pai == avo.getFilhoEsquerdo()) {
                avo.setFilhoEsquerdo(atual);
            } else {
                avo.setFilhoDireito(atual);
            }
        } else {
            atual.setPai(null);
            raiz = atual;
        }
        if (netoDireito != null) {
            pai.setFilhoEsquerdo(netoDireito);
            netoDireito.setPai(pai);
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
        AVLNo avo = (AVLNo) pai.getPai();
        AVLNo netoEsquerdo = (AVLNo) atual.getFilhoEsquerdo();
        
        if (avo != null) {
            atual.setPai(avo);
            if (pai == avo.getFilhoEsquerdo()) {
                avo.setFilhoEsquerdo(atual);
            } else {
                avo.setFilhoDireito(atual);
            }
        } else {
            atual.setPai(null);
            raiz = atual;
        }
        if (netoEsquerdo != null) {
            pai.setFilhoDireito(netoEsquerdo);
            netoEsquerdo.setPai(pai);
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
        if (raiz == null) {
            raiz = new AVLNo(null, o, 0);
            tamanho = 1;
            return;
        }
        
        AVLNo parent = (AVLNo) buscar(raiz, o);
        
        if (parent.getValor().equals(o)) {
            throw new RuntimeException("O valor já existe na árvore");
        }

        AVLNo novoNo = new AVLNo(parent, o, 0);
        if ((int) o < (int) parent.getValor()) {
            parent.setFilhoEsquerdo(novoNo);
            parent.setFB(parent.getFB() + 1);
        } else {
            parent.setFilhoDireito(novoNo);
            parent.setFB(parent.getFB() - 1);
        }
        tamanho++;
        altFBinsercao(parent);
    }

    @Override
    public Object remover(Object o) {
        AVLNo node = (AVLNo) buscar(raiz, o);
        if (node == null) {
            throw new RuntimeException("O valor não foi encontrado na árvore");
        }

        AVLNo paiParaBalancear = null;
        
        if (node.getFilhoEsquerdo() == null && node.getFilhoDireito() == null) {
            // Caso 1: Nó folha
            if (node.getPai() == null) {
                raiz = null;
            } else {
                paiParaBalancear = (AVLNo) node.getPai();
                if (node == node.getPai().getFilhoEsquerdo()) {
                    node.getPai().setFilhoEsquerdo(null);
                    paiParaBalancear.setFB(paiParaBalancear.getFB() - 1);
                } else {
                    node.getPai().setFilhoDireito(null);
                    paiParaBalancear.setFB(paiParaBalancear.getFB() + 1);
                }
            }
        } else if (temFilhoEsquerdo(node) ^ temFilhoDireito(node)) {
            // Caso 2: Nó com um filho
            AVLNo filho = (AVLNo) (temFilhoEsquerdo(node) ? node.getFilhoEsquerdo() : node.getFilhoDireito());
            
            if (node.getPai() == null) {
                raiz = filho;
                filho.setPai(null);
                paiParaBalancear = filho;
            } else {
                paiParaBalancear = (AVLNo) node.getPai();
                if (node == node.getPai().getFilhoEsquerdo()) {
                    node.getPai().setFilhoEsquerdo(filho);
                    filho.setPai(node.getPai());
                    paiParaBalancear.setFB(paiParaBalancear.getFB() - 1);
                } else {
                    node.getPai().setFilhoDireito(filho);
                    filho.setPai(node.getPai());
                    paiParaBalancear.setFB(paiParaBalancear.getFB() + 1);
                }
            }
        } else {
            // Caso 3: Nó com dois filhos
            AVLNo sucessor = (AVLNo) node.getFilhoDireito();
            while (sucessor.getFilhoEsquerdo() != null) {
                sucessor = (AVLNo) sucessor.getFilhoEsquerdo();
            }
            Object substituto = sucessor.getValor();
            paiParaBalancear = (AVLNo) sucessor.getPai();
            remover(substituto);
            node.setValor(substituto);
        }
        
        tamanho--;
        
        if (paiParaBalancear != null) {
            altFBremocao(paiParaBalancear);
        }
        
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
    public AVLNo buscar(AVLNo n, Object o) {
        if (n == null) {
            return null;
        }
        if ((int) n.getValor() == (int) o) {
            return n;

        } else if ((int) o < (int) n.getValor()) {
            if (n.getFilhoEsquerdo() == null) {
                return n; // Retorna o nó pai para inserção
            }
            return buscar((AVLNo) n.getFilhoEsquerdo(), o);
        } else {
            if (n.getFilhoDireito() == null) {
                return n; // Retorna o nó pai para inserção
            }
            return buscar((AVLNo) n.getFilhoDireito(), o);
        }
    }
    public void verificarBalanceamento() {
        System.out.println("Verificando balanceamento:");
        verificarBalanceamentoRecursivo(raiz);
    }

    private void verificarBalanceamentoRecursivo(AVLNo no) {
        if (no != null) {
            int alturaEsq = altura((AVLNo) no.getFilhoEsquerdo());
            int alturaDir = altura((AVLNo) no.getFilhoDireito());
            int fbCalculado = alturaEsq - alturaDir;
            
            System.out.println("Nó " + no.getValor() + ": FB armazenado=" + no.getFB() + ", FB calculado=" + fbCalculado);
            
            verificarBalanceamentoRecursivo((AVLNo) no.getFilhoEsquerdo());
            verificarBalanceamentoRecursivo((AVLNo) no.getFilhoDireito());
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

    