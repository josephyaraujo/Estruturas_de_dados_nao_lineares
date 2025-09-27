package arvores.arvoreBP;

import java.util.ArrayList;
import java.util.Iterator;

public class ABP implements ArvoreBinariaPesquisa {
    No root;
    int size;

    public ABP(Object o) {
        root = new No (null, o);
        size = 1;
    }

    @Override
    public int tamanho() {
        return size;
    }

    @Override
    public int altura(No n) {
        if (n == null || ehExterno(n)){
            return 0;
        } else {
            int alturaEsquerda = this.altura(n.getFilhoEsquerdo());
            int alturaDireita = this.altura(n.getFilhoDireito());
            return 1 + Math.max(alturaEsquerda, alturaDireita);
        }
    }

    @Override
    public boolean ehVazia() {
        return this.size == 0;
    }

    @Override
    public Iterator<Object> elementos() {
        ArrayList<Object> elemns = new ArrayList<>();
        element(raiz(), elemns);
        return elemns.iterator();
    }

    public void element(No n, ArrayList<Object> e){
        e.add(n.getValor());
        Iterator<No> eIterator = filhos(n);
        while (eIterator.hasNext()){
            element(eIterator.next(), e);
        }
    }

    @Override
    public Iterator<No> nos() {
        ArrayList<No> nos = new ArrayList<>();
        no(raiz(), nos);
        return nos.iterator();
    }

    public void no(No n, ArrayList<No> noArrayList){
        noArrayList.add(n);

        Iterator<No> noIterator = filhos(n);
        while (noIterator.hasNext()){
            no(noIterator.next(), noArrayList);
        }
    }

    @Override
    public No raiz() {
        return root;
    }

    @Override
    public No pai(No n) {
        return n.getPai();
    }

    @Override
    public Iterator<No> filhos(No n) {
        ArrayList<No> a = new ArrayList<>();

        if (n.getFilhoEsquerdo() != null){
            a.add(n.getFilhoEsquerdo());
        }
        if (n.getFilhoDireito() != null){
            a.add(n.getFilhoDireito());
        }
        
        return a.iterator();
    }

    @Override
    public boolean ehInterno(No n) {
        return temFilhoEsquerdo(n) || temFilhoDireito(n);
    }

    @Override
    public boolean ehExterno(No n) {
        return !ehInterno(n);
    }

    @Override
    public boolean ehRaiz(No n) {
        return n == root;
    }

    @Override
    public int profundidade(No n) {
        if (n == null || ehRaiz(n)){
            return 0;
        } else {
            return 1 + profundidade(n.getPai());
        }
    }

    @Override
    public void inserir(Object o) {
        No parent = buscar(raiz(), o);
        No newNode = new No(parent, o);
        
        if ((int) o < (int) parent.getValor()){ 
            parent.setFilhoEsquerdo(newNode);
            newNode.setPai(parent);
        } else {
            parent.setFilhoDireito(newNode);
            newNode.setPai(parent);
        }
        size++;
    }

    @Override
    public Object remover(Object o) {
        No nodeToRemove = buscar(raiz(), o);

        // Caso 0: Nó não encontrado
        if (nodeToRemove == null) {
            throw new RuntimeException("Nó não encontrado na árvore.");
        }
        // Caso 1: Nó é uma folha (sem filhos)
        if (ehExterno(nodeToRemove)){
            if (ehRaiz(nodeToRemove)){
                root = null; // Árvore fica vazia    
            } else {
                No parent = nodeToRemove.getPai();
                if (parent != null) {
                    if (parent.getFilhoEsquerdo() == nodeToRemove){
                        parent.setFilhoEsquerdo(null);
                    } else {
                        parent.setFilhoDireito(null);
                    }
                }
            }
        }
        // Caso 2: Nó tem um filho
        else if (temFilhoEsquerdo(nodeToRemove) ^ temFilhoDireito(nodeToRemove)){
            No child = temFilhoEsquerdo(nodeToRemove) ? nodeToRemove.getFilhoEsquerdo() : nodeToRemove.getFilhoDireito();
            if (ehRaiz(nodeToRemove)){
                root = child;
                child.setPai(null);
            } 
            else {
                No parent = nodeToRemove.getPai();
                if (parent.getFilhoEsquerdo() == nodeToRemove){
                    parent.setFilhoEsquerdo(child);
                } else {
                    parent.setFilhoDireito(child);
                }
                child.setPai(parent);
            }
        }
        // Caso 3: Nó tem dois filhos
        else {
            No successor = nodeToRemove.getFilhoDireito();
            while (temFilhoEsquerdo(successor)){
                successor = successor.getFilhoEsquerdo();
            }
            Object noSubstituto = successor.getValor();
            remover(noSubstituto); // Remove o no successor do lugar de origem
            nodeToRemove.setValor(noSubstituto); // Substitui o valor do no a ser removido pelo valor do successor
        }

        size--;
        return o;
    }

    @Override
    public void preOrdem(No n) { // Raiz, Esquerda, Direita
        if (n != null){
            System.out.print(n.getValor() + " ");
            preOrdem(n.getFilhoEsquerdo());
            preOrdem(n.getFilhoDireito());
        }
    }

    @Override
    public void emOrdem(No n) { // Esquerda, Raiz, Direita
        if (n != null){
            emOrdem(n.getFilhoEsquerdo());
            System.out.print(n.getValor() + " ");
            emOrdem(n.getFilhoDireito());
        }
    }

    @Override
    public void posOrdem(No n) { // Esquerda, Direita, Raiz
        if (n != null){
            posOrdem(n.getFilhoEsquerdo());
            posOrdem(n.getFilhoDireito());
            System.out.print(n.getValor() + " ");
        }
    }

    @Override
    public No filhoEsquerdo(No n) {
        return n.getFilhoEsquerdo();
    }

    @Override
    public No filhoDireito(No n) {
        return n.getFilhoDireito();
    }

    @Override
    public boolean temFilhoEsquerdo(No n) {
        return filhoEsquerdo(n) != null;
    }

    @Override
    public boolean temFilhoDireito(No n) {
        return filhoDireito(n) != null;
    }

    @Override
    public No buscar(No n, Object o) {
        if (n == null){
            return null;
        }
        if ((int) o < (int) n.getValor()){
            if (n.getFilhoEsquerdo() == null){
                return n;
            }
            return buscar(n.getFilhoEsquerdo(), o);
        }
        else if (n.getValor().equals(o)){
            return n;
        }
        else {
            if (n.getFilhoDireito() == null){
                return n;
            }
            return buscar(n.getFilhoDireito(), o);            
        }
    }

// Método adicional para imprimir a árvore (para fins de depuração)
    public void printArvore() {
        printArvore(raiz(), 0);
    }
    
    private void printArvore(No n, int depth) {
        if (n == null) {
            return;
        }
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }
        System.out.println(n.getValor());
        printArvore(n.getFilhoEsquerdo(), depth + 1);
        printArvore(n.getFilhoDireito(), depth + 1);
    }
}