package arvores.arvoreAVL;

public class AVLTeste {
    public static void main(String[] args) {
        AVL arvore = new AVL(10);
        arvore.printArvoreComFB();
        System.out.println("--------------------------");
        System.out.println();

        arvore.inserir(5);
        arvore.printArvoreComFB();
        System.out.println("--------------------------");
        System.out.println();

        arvore.inserir(15);
        arvore.printArvoreComFB();
        System.out.println("--------------------------");
        System.out.println();

        arvore.inserir(2);
        arvore.printArvoreComFB();
        System.out.println("--------------------------");
        System.out.println();
        
        arvore.inserir(8);
        arvore.printArvoreComFB(); 
        System.out.println("--------------------------");
        System.out.println();

        arvore.inserir(22);
        arvore.printArvoreComFB();
        System.out.println("--------------------------");
        System.out.println();

        System.out.println("Árvore AVL impressa:");
        arvore.printArvoreComFB();
        System.out.println("--------------------------");
        System.out.println();

        arvore.inserir(25); // Inserção que causa rotação
        System.out.println("Árvore AVL após inserir 25 (causa rotação):");
        arvore.printArvoreComFB();
        System.out.println("--------------------------");
        System.out.println();

        arvore.remover(5); // Remoção que causa reequilíbrio
        System.out.println("Árvore AVL após remover 5 (causa reequilíbrio):");
        arvore.printArvoreComFB();
        System.out.println();
    }
}