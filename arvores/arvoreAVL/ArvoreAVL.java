package arvores.arvoreAVL;

public interface ArvoreAVL {
    public AVLNo rotacao(AVLNo atual, AVLNo pai);
    public void altFBinsercao(AVLNo n);
    public void altFBremocao(AVLNo n);
    public AVLNo rotacaoSimplesDireita(AVLNo n, AVLNo atual);
    public AVLNo rotacaoSimplesEsquerda(AVLNo n, AVLNo atual);
}