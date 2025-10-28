package arvores.arvoreBP; 

import java.util.ArrayList;
import java.util.Iterator;

public class ABP {
    No raiz;
    int tamanho;

    public ABP (Object o) {
        raiz = new No(null, o);
        tamanho = 1;
    }

    public int size(){
        return tamanho;
    }

    public int height(No n) {
        if (n == null || isExternal(n)){
            return 0;
        } else {
            int hEsquerda = this.height(n.getFilhoEsquerdo());
            int hDireito = this.height(n.getFilhoDireito());
            
            return 1 + Math.max(hEsquerda, hDireito);
        }
    }

    public int depth(No n){
        if (isRoot(n)){
            return 0;
        } else {
            return 1 + this.depth(n.getPai());
        }
    }

    public boolean isEmpty(){
        return this.tamanho == 0;
    }

    public Iterator<No> children(No n){
        ArrayList<No> a = new ArrayList<>();

        if (n.getFilhoDireito() != null) {
            a.add(n.getFilhoDireito());
        }

        if (n.getFilhoEsquerdo() != null) {
            a.add(n.getFilhoEsquerdo());
        }

        return a.iterator();
    }

    public Iterator<Object> elements() {
        ArrayList<Object> elemns = new ArrayList<>();
        element(root(), elemns);
        return elemns.iterator();
    }

    public void element(No n, ArrayList<Object> e){
        e.add(n.getValor());
        Iterator<No> eIterator = children(n);
        while (eIterator.hasNext()) {
            element(eIterator.next(), e);
        }
    }

    public Iterator<No> nos(){
        ArrayList<No> nos = new ArrayList<>();
        no(root(), nos);
        return nos.iterator();
    }

    public void no(No n, ArrayList<No> noArray){
        noArray.add(n);

        Iterator<No> noIterator = children(n);
        while (noIterator.hasNext()){
            no(noIterator.next(), noArray);
        }
    }

    public No root(){
        return raiz;
    }

    public boolean hasLeft(No n){
        return n.getFilhoEsquerdo() != null;
    }

    public boolean hasRight(No n){
        return n.getFilhoDireito() != null;
    }

    public boolean isInternal(No n){
        return hasLeft(n) || hasRight(n);
    }

    public boolean isExternal(No n){
        return !isInternal(n);
    }

    public boolean isRoot(No n){
        return n == raiz;
    }

    public No treeSearch(No n, Object chave){
        if (n == null) {
            return null;
        }
        //Procura no lado esquerdo
        if ((int) chave < (int) n.getValor()) {
            if (n.getFilhoEsquerdo() == null) {
                return n;
            }
            return treeSearch(n.getFilhoEsquerdo(), chave);
        //Caso seja o mesmo valor encontrado
        } else if ((int) chave == (int) n.getValor()){
            return n;
        } else {
        //Procura no lado direito
            if (n.getFilhoDireito() == null) {
                return n;
            }
            return treeSearch(n.getFilhoDireito(), chave);
        }
    }

    public No inserir(Object chave){
        No noPai = treeSearch(raiz, chave);
        No novoNo = new No(noPai, chave);

        if ((int) noPai.getValor() > (int) chave){
            noPai.setFilhoEsquerdo(novoNo);
        } else {
            noPai.setFilhoDireito(novoNo);
        }

        tamanho++;
        return novoNo;
    }

    public Object remover(Object chave){
        No temp = treeSearch(raiz, chave);

        if (temp == null || (int) temp.getValor() != (int) chave){
            throw new RuntimeException("Chave nao existe");
        }

        Object remover = temp.getValor();

        if (temp.getFilhoDireito() == null && temp.getFilhoEsquerdo() == null) { //não tem filhos
            No pai = temp.getPai();
            if (pai == null){
                raiz = null;
            } else if (pai.getFilhoEsquerdo() == temp) {
                pai.setFilhoEsquerdo(null);
            } else {
                pai.setFilhoDireito(null);
            }

        } else if ((temp.getFilhoDireito() == null && temp.getFilhoEsquerdo() != null) || (temp.getFilhoDireito() != null && temp.getFilhoEsquerdo() == null)) { //tem um filho
            No filho = (hasLeft(temp)) ? temp.getFilhoEsquerdo() : temp.getFilhoDireito();

            No pai = temp.getPai();

            if (pai == null) { // temp é raiz
                raiz = filho;
                filho.setPai(null);
            } else {
                if (pai.getFilhoEsquerdo() == temp) {
                    pai.setFilhoEsquerdo(filho);
                } else {
                    pai.setFilhoDireito(filho);
                }
                filho.setPai(pai);
            }
        } else { //tem dois filhos
            No substituto = sucessor(temp.getFilhoDireito());
            Object tempValor = substituto.getValor();
            remover(substituto.getValor());
            temp.setValor(tempValor);
        }

        tamanho--;
        return remover;
    }

    public No sucessor(No n){
        while (n.getFilhoEsquerdo() != null){
            n = n.getFilhoEsquerdo();
        }

        return n;
    }

    public void preOrder(No n){
        System.out.println(n.getValor());

        if (n.getFilhoEsquerdo() != null){
            preOrder(n.getFilhoEsquerdo());
        }

        if (n.getFilhoDireito() != null){
            preOrder(n.getFilhoDireito());
        }
    }

    public void postOrder(No n) {
        if (n.getFilhoEsquerdo() != null){
            postOrder(n.getFilhoEsquerdo());
        }

        if (n.getFilhoDireito() != null){
          postOrder(n.getFilhoDireito());
        }

        System.out.println(n.getValor());
    }

    public void inOrder(No n) {
        if (n.getFilhoEsquerdo() != null){
            inOrder(n.getFilhoEsquerdo());
        }

        System.out.println(n.getValor());

        if (n.getFilhoDireito() != null){
          inOrder(n.getFilhoDireito());
        }
    }

    public void printArvore(){
        int altura = height(raiz);
        int linhas = altura + 1;
        int colunas = (int) Math.pow(2, linhas) - 1;  //numero total de nós (2^linhas) - 1

        Object[][] matriz = new Object[linhas][colunas];
        
        montar(matriz, raiz, 0, colunas /2); //raiz começa no meio da matriz (colunas/2)
        for (int i = 0; i < linhas; i++){
            for (int j = 0; j < colunas; j++){
                if (matriz[i][j] == null){
                    System.out.print("  ");
                } else {
                    System.out.printf("%3s", matriz[i][j]);
                }
            }
            System.out.println();
        }
    }

    protected void montar(Object[][] matriz, No n, int linha, int coluna){
        if (n == null){ //não tem nada mais 
            return;
        }

        matriz[linha][coluna] = n.getValor();

        //serve para mostrar onde cada nó vai ser posicionado na arvore, quanto mais desce mais distante fica
        int d = (int) Math.pow(2, matriz.length - linha - 2);

        if (n.getFilhoEsquerdo() != null) {
            montar(matriz, n.getFilhoEsquerdo(), linha + 1, coluna - d);
        }

        if (n.getFilhoDireito() != null) {
            montar(matriz, n.getFilhoDireito(), linha + 1, coluna + d);
        }

    }
}
