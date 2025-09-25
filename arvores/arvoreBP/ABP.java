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
    public Iterator filhos(No n) {
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

    }

    @Override
    public Object remover(Object o) {
        return null;
    }

    @Override
    public void preOrdem(No n) {

    }

    @Override
    public void emOrdem(No n) {

    }

    @Override
    public void posOrdem(No n) {

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
    public boolean  temFilhoEsquerdo(No n) {
        return filhoEsquerdo(n) != null;
    }

    @Override
    public boolean temFilhoDireito(No n) {
        return filhoDireito(n) != null;
    }

    @Override
    public No buscar(No n, Object o) {
        return null;
    }
}