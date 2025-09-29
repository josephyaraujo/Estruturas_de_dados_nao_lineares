package arvores.arvoreAVL;

public class AVLTeste {
    public static void main(String[] args) {
        AVL arvore = new AVL();
        arvore.inserir(30);
        arvore.inserir(20);
        arvore.inserir(40);
        arvore.inserir(10);
        arvore.inserir(25);
        arvore.inserir(35);
        arvore.inserir(50);
        arvore.inserir(5);  // Inserção que causa rotação
        arvore.inserir(15); // Inserção que causa rotação

        System.out.println("Árvore AVL impressa:");
        arvore.printArvoreComFB();

        System.out.println("Elementos em ordem:");
        arvore.emOrdem(arvore.raiz);
        System.out.println();

        System.out.println("Elementos em pré-ordem:");
        arvore.preOrdem(arvore.raiz);
        System.out.println();

        System.out.println("Elementos em pós-ordem:");
        arvore.posOrdem(arvore.raiz);
        System.out.println();

        System.out.println("Altura da árvore: " + arvore.altura(arvore.raiz));
        System.out.println("Tamanho da árvore: " + arvore.tamanho());

        System.out.println("Removendo 10...");
        arvore.remover(10);
        System.out.println("Elementos em ordem após remoção:");
        arvore.emOrdem(arvore.raiz);
        System.out.println();

        System.out.println("Removendo 30...");
        arvore.remover(30);
        System.out.println("Elementos em ordem após remoção:");
        arvore.emOrdem(arvore.raiz);
        System.out.println();

        System.out.println("Removendo 20...");
        arvore.remover(20);
        System.out.println("Elementos em ordem após remoção:");
        arvore.emOrdem(arvore.raiz);
        System.out.println();
    }
}
