package arvores.arvoreBP;

import java.util.Iterator;

public interface ArvoreBinariaPesquisa {
    // Operações básicas
    public int tamanho();
    public int altura(No n);
    public boolean ehVazia();
    public Iterator<Object> elementos();
    public Iterator<No> nos();

    // Operações de acesso
    public No raiz();
    public No pai(No n);
    public Iterator filhos (No n);

    // Operações de consulta
    public boolean ehInterno (No n);
    public boolean ehExterno (No n);
    public boolean ehRaiz (No n);
    public int profundidade (No n);

    // Operações de inserção e remoção
    public void inserir (Object o);
    public Object remover (Object o);

    // Operações de travessia
    public void preOrdem (No n);
    public void emOrdem (No n);
    public void posOrdem (No n);

    // Outras operações de arvore binaria
    public No filhoEsquerdo (No n);
    public No filhoDireito (No n);
    public No ehFilhoEsquerdo (No n);
    public No ehFilhoDireito (No n);
    
    // Operações de busca
    public No buscar (No n, Object o);
}