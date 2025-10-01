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
    public void rotacao(AVLNo desbalanceado, AVLNo filho) {
        if (desbalanceado == null || filho == null) {
            throw new RuntimeException("O nó desbalanceado e o filho não podem ser nulos para a rotação.");
        }
        if (desbalanceado.getFB() == 2) { // Desbalanceamento à esquerda
            if (filho.getFB() >= 0) { // Rotação simples à direita
                rotacaoSimplesDireita(desbalanceado, filho);
                
            } else { //Rotação dupla à direita
                rotacaoSimplesEsquerda(filho, (AVLNo) filho.getFilhoDireito());
                rotacaoSimplesDireita(desbalanceado, (AVLNo) desbalanceado.getFilhoEsquerdo());
            }
        } else if (desbalanceado.getFB() == -2) { // Desbalanceamento à direita
            if (filho.getFB() <= 0) { // Rotação simples à esquerda
                rotacaoSimplesEsquerda(desbalanceado, filho);

            } else { // Rotação dupla à esquerda
                rotacaoSimplesDireita(filho, (AVLNo) filho.getFilhoEsquerdo());
                rotacaoSimplesEsquerda(desbalanceado, (AVLNo) desbalanceado.getFilhoDireito());
            }
        }
    }

    @Override
    public void altFBinsercao(AVLNo n) {
        AVLNo atual = n;
        while (atual != null) {
            if (Math.abs(atual.getFB()) == 2) { // Verifica se o nó está desbalanceado
                // Chama o método de rotação, já definindo o filho envolvido no desbalanceamento 
                rotacao(atual, (AVLNo) (atual.getFB() > 0 ? atual.getFilhoEsquerdo() : atual.getFilhoDireito()));
                break;
            }
            AVLNo pai = (AVLNo) atual.getPai();
         
            if (pai != null) {
                if (atual == pai.getFilhoEsquerdo()) {
                    pai.setFB(pai.getFB() + 1);
                } else {
                    pai.setFB(pai.getFB() - 1);
                }
            }
            if (pai != null && pai.getFB() == 0) {
                break; // Caso o fator de balanceamento do pai seja 0, a árvore está balanceada
            }
            atual = pai; // Move para o nó pai e continua a verificação
        }
    }

    @Override
    public void altFBremocao(AVLNo n) {
        AVLNo atual = n;
        while (atual != null) {
            if (Math.abs(atual.getFB()) == 2) {
                rotacao(atual, atual.getFB() > 0 ? (AVLNo) atual.getFilhoEsquerdo() : (AVLNo) atual.getFilhoDireito());
            }
            AVLNo pai = (AVLNo) atual.getPai();

            if (pai != null) {
                if (atual == pai.getFilhoEsquerdo()) {
                    pai.setFB(pai.getFB() - 1);
                } else {
                    pai.setFB(pai.getFB() + 1);
                }

            }
            if (pai != null && pai.getFB() != 0) {
                break; // Se o fator de balanceamento do pai não for 0, a árvore está balanceada
            }
            atual = pai; // Move para o nó pai e continua a verificação
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

        AVLNo pai = buscar(raiz, o);
        // Se o nó retornado for igual ao valor a ser inserido, lança exceção
        if (pai != null && pai.getValor().equals(o)) {
            throw new RuntimeException("O valor já existe na árvore");
        }
        AVLNo novoNo = new AVLNo(pai, o, 0);

        if (pai != null && (int) pai.getValor() > (int) o) {
            pai.setFilhoEsquerdo(novoNo);
            pai.setFB(pai.getFB() + 1);
        } else {
            parent.setFilhoDireito(novoNo);
            parent.setFB(parent.getFB() - 1);
        }
        tamanho++;
        novoNo.setPai(parent);
        altFBinsercao(parent);
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

    