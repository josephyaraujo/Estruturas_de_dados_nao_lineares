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
    public void rotacao(AVLNo desbalanceado, AVLNo filho) {
        if (desbalanceado == null || filho == null) return;
        
        if (desbalanceado.getFB() == 2) { // Desbalanceamento à esquerda
            if (filho.getFB() >= 0) { // Left-Left
                rotacaoSimplesDireita(desbalanceado, filho);
            } else { // Left-Right
                rotacaoSimplesEsquerda(filho, (AVLNo) filho.getFilhoDireito());
                rotacaoSimplesDireita(desbalanceado, (AVLNo) desbalanceado.getFilhoEsquerdo());
            }
        } else if (desbalanceado.getFB() == -2) { // Desbalanceamento à direita
            if (filho.getFB() <= 0) { // Right-Right
                rotacaoSimplesEsquerda(desbalanceado, filho);
            } else { // Right-Left
                rotacaoSimplesDireita(filho, (AVLNo) filho.getFilhoEsquerdo());
                rotacaoSimplesEsquerda(desbalanceado, (AVLNo) desbalanceado.getFilhoDireito());
            }
        }
    }

    @Override
    public void altFBinsercao(AVLNo n) {
        AVLNo atual = n;
        while (atual != null) {
            // Verifica se precisa de rotação
            if (Math.abs(atual.getFB()) == 2) {
                // Determina qual filho está no caminho do desbalanceamento
                AVLNo filho = atual.getFB() > 0 ? 
                    (AVLNo) atual.getFilhoEsquerdo() : 
                    (AVLNo) atual.getFilhoDireito();
                
                // Chama o método rotacao que já decide o tipo de rotação
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

            // Se o FB do pai ficou 0, para a propagação
            if (atual.getPai() != null && ((AVLNo) atual.getPai()).getFB() == 0) {
                break;
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
                // Determina qual filho está no caminho do desbalanceamento
                AVLNo filho = atual.getFB() >= 0 ? 
                    (AVLNo) atual.getFilhoEsquerdo() : 
                    (AVLNo) atual.getFilhoDireito();
                
                // Chama o método rotacao que já decide o tipo de rotação
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
            if (atual.getPai() != null && ((AVLNo) atual.getPai()).getFB() != 0) {
            break;
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
        
        AVLNo parent = buscar(raiz, o);
        
        // Verifica se o valor já existe
        if (parent != null && parent.getValor().equals(o)) {
            return; // ou lançar exceção: throw new RuntimeException("Valor duplicado");
        }

        AVLNo novoNo = new AVLNo(parent, o, 0);
        if (parent != null && (int) o < (int) parent.getValor()) {
            parent.setFilhoEsquerdo(novoNo);
            parent.setFB(parent.getFB() + 1);
        } else if (parent != null) {
            parent.setFilhoDireito(novoNo);
            parent.setFB(parent.getFB() - 1);
        }
        tamanho++;
        if (parent != null) {
            altFBinsercao(parent);
        }
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
        } else if (node.getFilhoEsquerdo() == null || node.getFilhoDireito() == null) {
            // Caso 2: Nó com um filho
            AVLNo filho = (node.getFilhoEsquerdo() != null) ? 
                (AVLNo) node.getFilhoEsquerdo() : 
                (AVLNo) node.getFilhoDireito();
            
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
            
            // Remove o sucessor (que terá no máximo um filho direito)
            // Primeiro, encontramos o pai do sucessor para balanceamento
            paiParaBalancear = (AVLNo) sucessor.getPai();
            if (sucessor.getPai() != node) {
                // Se o sucessor não é filho direto do nó a ser removido
                paiParaBalancear = (AVLNo) sucessor.getPai();
            }
            
            // Chama remover recursivamente para o sucessor
            remover(substituto);
            
            // Substitui o valor do nó
            node.setValor(substituto);
            
            // Não precisa decrementar tamanho aqui pois já foi feito na chamada recursiva
            return o;
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
    public void recalcularFBs() {
        recalcularFBRecursivo(raiz);
    }

    private int recalcularFBRecursivo(AVLNo no) {
        if (no == null) return 0;
        
        int alturaEsq = recalcularFBRecursivo((AVLNo) no.getFilhoEsquerdo());
        int alturaDir = recalcularFBRecursivo((AVLNo) no.getFilhoDireito());
        
        no.setFB(alturaEsq - alturaDir);
        return Math.max(alturaEsq, alturaDir) + 1;
    }
    public void printArvoreComFB() {
        if (raiz == null) {
            System.out.println("(árvore vazia)");
            return;
        }
        
        // Recalcula os FBs para garantir que estão corretos
        recalcularFBs();
        
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

    