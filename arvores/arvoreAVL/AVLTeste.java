package arvores.arvoreAVL;

public class AVLTeste {
    public static void main(String[] args) {
        AVL arvore = new AVL(10);
        arvore.inserir(5);
        arvore.inserir(15);
        arvore.inserir(2);
        arvore.inserir(8);
        arvore.inserir(22);

        System.out.println("Árvore AVL impressa:");
        arvore.printArvoreComFB();
        System.out.println();

        arvore.inserir(25); // Inserção que causa rotação
        System.out.println("Árvore AVL após inserir 25 (causa rotação):");
        arvore.printArvoreComFB();
        System.out.println();

        arvore.remover(5); // Remoção que causa reequilíbrio
        System.out.println("Árvore AVL após remover 5 (causa reequilíbrio):");
        arvore.printArvoreComFB();
        System.out.println();
    }
}
