package arvores.arvoreAVL;
import arvores.arvoreBP.ABP;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AVL extends ABP implements ArvoreAVL {
    AVLNo raiz;
    int tamanho;

    public AVL(Object o) {
        super(o);
        raiz = new AVLNo(null, o, 0);
    }

    @Override
    public void altFBinsercao(AVLNo n) {
        while (n.getFB() != 0 && n.getPai() != null) { //verifica se o fator de balanceamento do nó é diferente de 0 e se ele não é raiz
            AVLNo atual = n;
            AVLNo pai = (AVLNo) atual.getPai();
            
            if (atual == pai.getFilhoEsquerdo()) { // Atualiza o fator de balanceamento do pai
                pai.setFB(pai.getFB() + 1);
            } else {
                pai.setFB(pai.getFB() - 1);
            }

            if (pai.getFB() >= 2 || pai.getFB() <= -2) { // Verifica se o nó pai está desbalanceado após a atualização
                rotacao(pai, atual);
                break;
            }
        }
    }

    @Override
    public void altFBremocao(AVLNo n) {
        while (true) {
            AVLNo atual = n;
            AVLNo pai = (AVLNo) atual.getPai();

            if (pai != null) {
                if (atual.getFB() != 0) {
                    break; // Se o fator de balanceamento do nó atual não for 0, a árvore está balanceada
                }

                if (atual == pai.getFilhoEsquerdo()) {
                    pai.setFB(pai.getFB() - 1);
                } else {
                    pai.setFB(pai.getFB() + 1);
                }
            }

            if (atual.getFB() >= 2 || atual.getFB() <= -2) {
                rotacao(atual, atual.getFB() >= 2 ? (AVLNo) atual.getFilhoEsquerdo() : (AVLNo) atual.getFilhoDireito());
            }

            if (pai == null) {
                break; // Se o nó atual for a raiz, termina o processo
            }
        }
    }

    @Override
    public void rotacao(AVLNo desbalanceado, AVLNo filho) {
        if (desbalanceado == null || filho == null) {
            throw new RuntimeException("O nó desbalanceado e o filho não podem ser nulos para a rotação.");
        }
        if (desbalanceado.getFB() >= 2) { // Desbalanceamento à esquerda
            if (filho.getFB() > 0) { // Rotação simples à direita
                rotacaoSimplesDireita(desbalanceado, filho);
                
            } else { //Rotação dupla à direita
                if (filho.getFilhoDireito() != null){ // Se o filho direito existir realiza a rotação interna para esquerda
                    rotacaoSimplesEsquerda(filho, (AVLNo) filho.getFilhoDireito());
                }
                if (desbalanceado.getFilhoEsquerdo() != null){ // Se o filho esquerdo existir realiza a rotação externa para direita
                    rotacaoSimplesDireita(desbalanceado, (AVLNo) desbalanceado.getFilhoEsquerdo());
                }
            }
        } else if (desbalanceado.getFB() <= -2) { // Desbalanceamento à direita
            if (filho.getFB() < 0) { // Rotação simples à esquerda
                rotacaoSimplesEsquerda(desbalanceado, filho);

            } else { // Rotação dupla à esquerda
                if (filho.getFilhoEsquerdo() != null){ // Se o filho esquerdo existir realiza a rotação interna para direita
                    rotacaoSimplesDireita(filho, (AVLNo) filho.getFilhoEsquerdo());
                }
                if (desbalanceado.getFilhoDireito() != null){ // Se o filho direito existir realiza a rotação externa para esquerda
                    rotacaoSimplesEsquerda(desbalanceado, (AVLNo) desbalanceado.getFilhoDireito());
                }            
            }
        }
    }

    // Implementação da rotação simples à direita
    @Override
    public void rotacaoSimplesDireita(AVLNo pai, AVLNo atual) {
        AVLNo avo = (AVLNo) pai.getPai();
        AVLNo netoDireito = (AVLNo) atual.getFilhoDireito();

        if (avo != null) {
            atual.setPai(avo); //seta o pai do atual como avo
            if (pai == avo.getFilhoEsquerdo()) { 
                avo.setFilhoEsquerdo(atual); //se o pai for filho esquerdo do avo, atual vira filho esquerdo do avô
            } else {
                avo.setFilhoDireito(atual); //se o pai for filho direito do avo, atual vira filho direito do avô
            }
        } else {
            atual.setPai(null); //se não tiver avô, atual vira raiz
            raiz = atual;
        }
        if (netoDireito != null) { //se o neto direito existir, pai vira pai do neto direito, que se torna filho esquerdo do pai.
            pai.setFilhoEsquerdo(netoDireito); 
            netoDireito.setPai(pai);
        } else {
            pai.setFilhoEsquerdo(null); //se não tiver neto direito, pai não terá mais filho esquerdo
        }
        atual.setFilhoDireito(pai);
        pai.setPai(atual);

        // Atualiza os fatores de balanceamento
        int fb_pai = pai.getFB() - 1 - Math.max(atual.getFB(), 0);
        int fb_atual = atual.getFB() - 1 + Math.min(fb_pai, 0);
        atual.setFB(fb_atual);
        pai.setFB(fb_pai);
    }
    // Implementação da rotação simples à esquerda
    @Override
    public void rotacaoSimplesEsquerda(AVLNo pai, AVLNo atual) {
        AVLNo avo = (AVLNo) pai.getPai();
        AVLNo netoEsquerdo = (AVLNo) atual.getFilhoEsquerdo();
        
        if (avo != null) {
            atual.setPai(avo);
            if (pai == avo.getFilhoEsquerdo()) { //se o pai for filho esquerdo do avo, atual vira filho esquerdo do avô
                avo.setFilhoEsquerdo(atual);
            } else {
                avo.setFilhoDireito(atual); //senão, atual vira filho direito do avô
            }
        } else {
            atual.setPai(null); //se não tiver avô, atual vira raiz
            raiz = atual;
        }
        if (netoEsquerdo != null) { //se o neto esquerdo existir, pai vira pai do neto esquerdo, que se torna filho direito do pai.
            pai.setFilhoDireito(netoEsquerdo);
            netoEsquerdo.setPai(pai);
        } else {
            pai.setFilhoDireito(null); //se não tiver neto esquerdo, pai não terá mais filho direito
        }
        atual.setFilhoEsquerdo(pai);
        pai.setPai(atual);

        // Atualiza os fatores de balanceamento
        int fb_pai = pai.getFB() + 1 - Math.min(atual.getFB(), 0); 
        int fb_atual = atual.getFB() + 1 + Math.max(fb_pai, 0);
        atual.setFB(fb_atual);
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
        if (pai.getValor().equals(o)) {
            throw new RuntimeException("O valor já existe na árvore");
        }
        AVLNo novoNo = new AVLNo(pai, o, 0);

        if ((int) pai.getValor() > (int) o) { // Se o valor a ser inserido for menor que o do pai, insere à esquerda
            pai.setFilhoEsquerdo(novoNo);
            pai.setFB(pai.getFB() + 1); // Atualiza o fator de balanceamento
            altFBinsercao(pai);
        } else { // Senão, insere à direita
            pai.setFilhoDireito(novoNo);
            pai.setFB(pai.getFB() - 1); // Atualiza o fator de balanceamento
            altFBinsercao(pai);
        }
        tamanho++;
    }

    @Override
    public Object remover(Object o) {
        AVLNo no = (AVLNo) buscar(raiz, o);
        AVLNo noParaBalancear; // Nó pai para iniciar o balanceamento
        if (no == null) {
            throw new RuntimeException("O valor não foi encontrado na árvore");
        }
        else if (no.getFilhoEsquerdo() == null && no.getFilhoDireito() == null) { // Nó folha
            if (no.getPai() == null) {
                raiz = null; // A árvore ficará vazia
            } else {
                noParaBalancear = (AVLNo) no.getPai();
                if (no == noParaBalancear.getFilhoEsquerdo()) { //se o nó for filho esquerdo do pai
                    noParaBalancear.setFilhoEsquerdo(null);
                    noParaBalancear.setFB(noParaBalancear.getFB()-1); // Atualiza o fator de balanceamento
                    altFBremocao(noParaBalancear);
                } else { //se o nó for filho direito do pai
                    noParaBalancear.setFilhoDireito(null);
                    noParaBalancear.setFB(noParaBalancear.getFB()+1); // Atualiza o fator de balanceamento
                    altFBremocao(noParaBalancear);
                }
            }
        } else if (temFilhoEsquerdo(no) ^ temFilhoDireito(no)) { // Nó com um filho
            AVLNo filho = (AVLNo) (temFilhoEsquerdo(no) ? no.getFilhoEsquerdo() : no.getFilhoDireito());
            noParaBalancear = (AVLNo) no.getPai();

            if (noParaBalancear == null) { // Se o nó a ser removido for a raiz
                filho.setPai(null);
            } else {
                if (no == noParaBalancear.getFilhoEsquerdo()) {
                    noParaBalancear.setFilhoEsquerdo(filho);
                    noParaBalancear.setFB(noParaBalancear.getFB()-1); // Atualiza o fator de balanceamento
                    altFBremocao(noParaBalancear);
                } else {
                    noParaBalancear.setFilhoDireito(filho);
                    noParaBalancear.setFB(noParaBalancear.getFB()+1); // Atualiza o fator de balanceamento
                    altFBremocao(noParaBalancear);
                }
                filho.setPai(noParaBalancear);
            }
        } else { // Nó com dois filhos
            AVLNo sucessor = (AVLNo) no.getFilhoDireito();
            while (sucessor.getFilhoEsquerdo() != null) {
                sucessor = (AVLNo) sucessor.getFilhoEsquerdo();
            }
            Object substituto = sucessor.getValor();
            remover(sucessor.getValor());
            no.setValor(substituto);
        }
        tamanho--;
        return o; // Retorna após a remoção do sucessor
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
            return 1 + Math.max(this.altura((AVLNo) node.getFilhoEsquerdo()), this.altura((AVLNo) node.getFilhoDireito()));
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
        formataNos();

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