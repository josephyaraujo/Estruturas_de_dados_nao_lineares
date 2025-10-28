package arvores.arvoreAVL;
import arvores.arvoreBP.ABP;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AVL2 extends ABP implements ArvoreAVL {
    AVLNo raiz;
    int tamanho;

    public AVL2(Object o) {
        super(o);
        raiz = new AVLNo(null, o, 0);
    }

    @Override
    public AVLNo rotacao(AVLNo desbalanceado, AVLNo filho) {
        if (desbalanceado == null || filho == null) {
            throw new RuntimeException("O nó desbalanceado e o filho não podem ser nulos para a rotação.");
        }
        if (desbalanceado.getFB() == 2) { // Desbalanceamento à esquerda
            if (filho.getFB() >= 0) { // Rotação simples à direita
                return rotacaoSimplesDireita(desbalanceado, filho);
                
            } else { //Rotação dupla à direita
                rotacaoSimplesEsquerda(filho, (AVLNo) filho.getFilhoDireito());
                return rotacaoSimplesDireita(desbalanceado, (AVLNo) desbalanceado.getFilhoEsquerdo());
            }
        } else if (desbalanceado.getFB() == -2) { // Desbalanceamento à direita
            if (filho.getFB() <= 0) { // Rotação simples à esquerda
                return rotacaoSimplesEsquerda(desbalanceado, filho);

            } else { // Rotação dupla à esquerda
                rotacaoSimplesDireita(filho, (AVLNo) filho.getFilhoEsquerdo());
                return rotacaoSimplesEsquerda(desbalanceado, (AVLNo) desbalanceado.getFilhoDireito());
            }
        }
    return null; // Retorna null se não houver desbalanceamento
    }

    @Override
    public void altFBinsercao(AVLNo n) {
        AVLNo atual = n;
        while (atual != null) {
            if (Math.abs(atual.getFB()) == 2) { // Verifica se o nó está desbalanceado (se e +2 ou -2)
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
                AVLNo novaRaiz = rotacao(atual, atual.getFB() > 0 ? (AVLNo) atual.getFilhoEsquerdo() : (AVLNo) atual.getFilhoDireito());
                atual = (novaRaiz != null) ? (AVLNo) novaRaiz.getPai() : (AVLNo) atual.getPai(); // Atualiza o nó atual para a nova raiz após a rotação
                continue; // Reavalia o nó atual após a rotação
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
    public AVLNo rotacaoSimplesDireita(AVLNo pai, AVLNo atual) {
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

        // Recalcula fatores localmente a partir das alturas (consistente com formataNosRecursivo)
        int fbPai = altura((AVLNo) pai.getFilhoEsquerdo()) - altura((AVLNo) pai.getFilhoDireito());
        int fbAtual = altura((AVLNo) atual.getFilhoEsquerdo()) - altura((AVLNo) atual.getFilhoDireito());
        pai.setFB(fbPai);
        atual.setFB(fbAtual);

        return atual; // Retorna o novo nó raiz após a rotação
    }

    // Implementação da rotação simples à esquerda
    @Override
    public AVLNo rotacaoSimplesEsquerda(AVLNo pai, AVLNo atual) {
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

        // Recalcula fatores localmente a partir das alturas (consistente com formataNosRecursivo)
        int fbPai = altura((AVLNo) pai.getFilhoEsquerdo()) - altura((AVLNo) pai.getFilhoDireito());
        int fbAtual = altura((AVLNo) atual.getFilhoEsquerdo()) - altura((AVLNo) atual.getFilhoDireito());
        pai.setFB(fbPai);
        atual.setFB(fbAtual);

        return atual; // Retorna o novo nó raiz após a rotação
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
        if (pai.getValor().equals(o)) {
            throw new RuntimeException("O valor já existe na árvore");
        }
        AVLNo novoNo = new AVLNo(pai, o, 0);

        if ((int) pai.getValor() > (int) o) {
            pai.setFilhoEsquerdo(novoNo);
            pai.setFB(pai.getFB() + 1);
        } else {
            pai.setFilhoDireito(novoNo);
            pai.setFB(pai.getFB() - 1);
        }
        tamanho++;
        altFBinsercao(pai);
    }

    @Override
    public Object remover(Object o) {
        AVLNo no = (AVLNo) buscar(raiz, o);
        if (no == null) {
            throw new RuntimeException("O valor não foi encontrado na árvore");
        }
        AVLNo noParaBalancear = null; // Nó pai para iniciar o reequilíbrio

        if (no.getFilhoEsquerdo() == null && no.getFilhoDireito() == null) { // Nó folha
            if (no.getPai() == null) {
                raiz = null; // A árvore ficará vazia
            } else {
                noParaBalancear = (AVLNo) no.getPai();
                if (no == no.getPai().getFilhoEsquerdo()) {
                    no.getPai().setFilhoEsquerdo(null);
                } else {
                    no.getPai().setFilhoDireito(null);
                }
            }
        } else if (temFilhoEsquerdo(no) ^ temFilhoDireito(no)) { // Nó com um filho
            AVLNo filho = (AVLNo) (temFilhoEsquerdo(no) ? no.getFilhoEsquerdo() : no.getFilhoDireito());

            if (no.getPai() == null) {
                raiz = filho;
                filho.setPai(null);
                noParaBalancear = null; //quando a raiz é removida, não há nó pai para balancear
            } else {
                noParaBalancear = (AVLNo) no.getPai();

                if (no == no.getPai().getFilhoEsquerdo()) {
                    no.getPai().setFilhoEsquerdo(filho);
                    filho.setPai(no.getPai());
                } else {
                    no.getPai().setFilhoDireito(filho);
                    filho.setPai(no.getPai());
                }
            }
        } else { // Nó com dois filhos
            AVLNo sucessor = (AVLNo) no.getFilhoDireito();
            while (sucessor.getFilhoEsquerdo() != null) {
                sucessor = (AVLNo) sucessor.getFilhoEsquerdo();
            }
            Object substituto = sucessor.getValor();
            remover(substituto);
            no.setValor(substituto);
            return o; // Retorna após a remoção do sucessor
        }
        tamanho--;
        if (noParaBalancear != null) {
            altFBremocao(noParaBalancear); // Inicia o reequilíbrio a partir do nó desbalanceado
        }
        return o; // Retorna o valor removido
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
    public AVLNo buscar (AVLNo n, Object o) {
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
    //Métodos auxiliares para impressão da árvore com fatores de balanceamento
    public void formataNos() {
        formataNosRecursivo(raiz);
    }

    private int formataNosRecursivo(AVLNo no) {
        if (no == null) return 0;

        int alturaEsq = formataNosRecursivo((AVLNo) no.getFilhoEsquerdo());
        int alturaDir = formataNosRecursivo((AVLNo) no.getFilhoDireito());

        no.setFB(alturaEsq - alturaDir);
        return Math.max(alturaEsq, alturaDir) + 1;
    }
    public void printArvoreComFB() {
        if (raiz == null) {
            System.out.println("(árvore vazia)");
            return;
        }

        // Formata os nós para garantir que os nós estão corretos
        //formataNos();

        int altura = altura(raiz);
        List<List<String>> levels = new ArrayList<>();
        Queue<AVLNo> queue = new LinkedList<>();
        queue.add(raiz);
        
        // Coleta todos os nós por nível
        for (int i = 0; i <= altura; i++) {
            List<String> level = new ArrayList<>();
            int levelSize = queue.size();
            
            for (int j = 0; j < levelSize; j++) {
                AVLNo node = queue.poll();
                if (node != null) {
                    level.add(String.format("%2d[%d]", node.getValor(), node.getFB()));
                    queue.add((AVLNo) node.getFilhoEsquerdo());
                    queue.add((AVLNo) node.getFilhoDireito());
                } else {
                    level.add("      ");
                    queue.add(null);
                    queue.add(null);
                }
            }
            levels.add(level);
        }
        
        // Imprime os níveis formatados
        for (int i = 0; i < levels.size(); i++) {
            List<String> level = levels.get(i);
            int spacesBefore = (int) Math.pow(2, (altura - i)) - 1;
            int spacesBetween = (int) Math.pow(2, (altura - i + 1)) - 1;
            
            // Espaços antes do primeiro nó
            for (int s = 0; s < spacesBefore; s++) {
                System.out.print("      ");
            }
            
            // Imprime os nós
            for (int j = 0; j < level.size(); j++) {
                System.out.print(level.get(j));
                if (j < level.size() - 1) {
                    for (int s = 0; s < spacesBetween; s++) {
                        System.out.print("      ");
                    }
                }
            }
            System.out.println();
        }
    }
}