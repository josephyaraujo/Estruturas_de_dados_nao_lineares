package ArvoreRubroNegra;

public class ArvoreRN extends ABP {

    public ArvoreRN (Object o) {
        super(o); //Chama o construtor da ABP
        raiz.setCor('N'); //Raiz sempre negra
    }

    public void inserirRN(Object o) { //Implementação da inserção em árvore rubro-negra
        No novoNo = this.addChild(o); //Adiciona o novo nó como em uma ABP normal
        balancearRN(novoNo); //Balanceia a árvore após a inserção
    }

    public void balancearRN(No n) { //Balanceamento da árvore rubro-negra
        No pai = n.getPai();
        if (pai == null || pai.getCor() == 'N') { //Caso base: nó é raiz ou pai é negro
            //Execução é interrompida pois a árvore está balanceada
        } else { //Caso o pai seja rubro
            No avo = pai.getPai();
            if (avo != null) {
                No tio = avo.getDireito() == pai ? avo.getEsquerdo() : avo.getDireito();
                if (tio != null && tio.getCor() == 'R') { //Caso 1: tio é rubro
                    repintamento(pai, tio, avo);
                } else { // Caso 2 e 3: tio é negro
                    rotacoesRN(n, pai, avo);
                }
            }
        }
    }

    public void repintamento(No pai, No tio, No avo) { //Pai e tio serão recoloridos de negro e o avô de rubro, podendo precisar de balanceamento
        pai.setCor('N');
        tio.setCor('N');
        if (avo != raiz){ // Se o avô não for a raiz
            avo.setCor('R');
        }        
        balancearRN(avo); //Verifica se o avô precisa de balanceamento
    }

    public void rotacoesRN(No n, No pai, No avo) {
        if (pai == avo.getEsquerdo() && n == pai.getEsquerdo()) { //pai e n são filhos esquerdos
            rotacaoDireita(avo);
        } else if (pai == avo.getEsquerdo() && n == pai.getDireito()) { //pai é filho esquerdo e n é filho direito
            rotacaoEsquerda(pai);
            rotacaoDireita(avo);
        } else if (pai == avo.getDireito() && n == pai.getDireito()) { //pai e n são filhos direitos
            rotacaoEsquerda(avo);
        } else if (pai == avo.getDireito() && n == pai.getEsquerdo()) { //pai é filho direito e n é filho esquerdo
            rotacaoDireita(pai);
            rotacaoEsquerda(avo);
        }
    }

    public No rotacaoDireita (No n) {
        No filhoPromovido = n.getEsquerdo(); //Sucessor que assumirá a posição do nó n
        if (n.getPai() == null) { //Se n for a raiz
            filhoPromovido.setPai(null); //Novo nó se torna raiz
            raiz = filhoPromovido;
        } else {
            filhoPromovido.setPai(n.getPai()); //Pai do sucessor se torna o pai do nó n
            if (n == n.getPai().getEsquerdo()) { //Verifica se n é filho esquerdo ou direito
                n.getPai().setEsquerdo(filhoPromovido); //Atualiza o filho esquerdo do pai de n
            } else {
                n.getPai().setDireito(filhoPromovido); //Atualiza o filho direito do pai de n
            }
        }
        n.setPai(filhoPromovido); //Atualiza o pai de n para o sucessor
        n.setEsquerdo(filhoPromovido.getDireito()); //Atualiza o filho esquerdo de n para o filho direito do sucessor
        if (filhoPromovido.getDireito() != null) {
            filhoPromovido.getDireito().setPai(n); //Atualiza o pai do filho direito do sucessor para n
        }
        filhoPromovido.setDireito(n); //Atualiza o filho direito do sucessor para n
        //Recolore os nós
        filhoPromovido.setCor('N');
        n.setCor('R');
        return filhoPromovido;
    }

    public No rotacaoEsquerda (No n) {
        No filhoPromovido = n.getDireito(); //Sucessor que assumirá a posição do nó n
        if (n.getPai() == null) { //Se n for a raiz
            filhoPromovido.setPai(null); //Novo nó se torna raiz
            raiz = filhoPromovido;
        } else {
            filhoPromovido.setPai(n.getPai()); //Pai do sucessor se torna o pai do nó n
            if (n == n.getPai().getEsquerdo()) { //Verifica se n é filho esquerdo ou direito
                n.getPai().setEsquerdo(filhoPromovido); //Atualiza o filho esquerdo do pai de n
            } else {
                n.getPai().setDireito(filhoPromovido); //Atualiza o filho direito do pai de n
            }
        }
        n.setPai(filhoPromovido); //Atualiza o pai de n para o sucessor
        n.setDireito(filhoPromovido.getEsquerdo()); //Atualiza o filho direito de n para o filho esquerdo do sucessor
        if (filhoPromovido.getEsquerdo() != null) {
            filhoPromovido.getEsquerdo().setPai(n); //Atualiza o pai do filho esquerdo do sucessor para n
        }
        filhoPromovido.setEsquerdo(n); //Atualiza o filho esquerdo do sucessor para n
        //Recolore os nós
        filhoPromovido.setCor('N');
        n.setCor('R');
        return filhoPromovido;
    }

    private void substituirNo(No pai, No antigo, No novo) { //Substitui 'antigo' por 'novo' como filho de 'pai'. Se pai == null, atualiza a raiz.
        if (pai == null) { // antigo era a raiz
            raiz = novo;
            if (novo != null) { 
                novo.setPai(null);
            }
            return;
        }
        if (pai.getEsquerdo() == antigo) { // Se 'antigo' é filho esquerdo de 'pai'
            pai.setEsquerdo(novo);
        } else if (pai.getDireito() == antigo) { // Se 'antigo' é filho direito de 'pai'
            pai.setDireito(novo);
        }
        if (novo != null) { // Atualiza o pai de 'novo' se não for nulo
            novo.setPai(pai);
        }
    }

    private boolean isBlack(No n) { return n == null || n.getCor() == 'N'; }
    private boolean isRed(No n) { return n != null && n.getCor() == 'R'; }

    public No removerRN(Object o) {
        No n = treeSearch(raiz, o); //Busca o nó a ser removido
        if (n == null || (int)n.getChave() != (int)o) { //Verifica se o nó existe
            return null; // Nó não encontrado, nada a fazer
        }
        int quantidadeFilhos = n.getNFilhos();

        switch (quantidadeFilhos) {
            case 0 -> { // Nó folha - remover diretamente
                No pai = n.getPai();
                if (n == raiz) {
                    substituirNo(null, n, null); // Árvore fica vazia
                    tamanho--;
                } else if (n.getCor() == 'R') { // Nó rubro
                    substituirNo(pai, n, null);
                    tamanho--;
                } else if (n.getCor() == 'N') { // Nó negro
                    boolean ehEsquerdo = (pai.getEsquerdo() == n);
                    substituirNo(pai, n, null);
                    tamanho--;
                    checarDuploNegro(pai, ehEsquerdo); //Cai direto pro caso 3
                }
            }
            case 1 -> { // Nó com um filho - Substituir o nó pelo seu filho
                No filho = hasLeft(n) ? n.getEsquerdo() : n.getDireito(); //Obtem o filho existente 
                No pai = n.getPai();
                if (n == raiz) {
                    substituirNo(null, n, filho);
                    raiz.setCor('N'); //Garante que a nova raiz seja negra
                    tamanho--;
                } else if (n.getCor() == 'R') { // Nó rubro
                    filho.setCor('R'); // Filho assume a cor do nó removido
                    substituirNo(pai, n, filho);
                    tamanho--;
                } else if (n.getCor() == 'N') { //Nó negro com filho rubro ou negro
                    boolean ehEsquerdo = (filho == n.getEsquerdo());
                    if (filho.getCor() == 'R') { // Filho rubro
                        filho.setCor('N'); // Recolore o filho para negro - início do caso 2
                        substituirNo(pai, n, filho);
                        tamanho--;
                    } else { // Filho negro - cai direto no caso 3
                        substituirNo(pai, n, filho);
                        tamanho--;
                        checarDuploNegro(pai, ehEsquerdo);
                    }
                }
            }
            case 2 -> { // Nó com dois filhos - Encontrar o sucessor e substituir (vai para os 4 casos)
                removerPaiComDoisFilhos(n);
            }
        }
        return n;
    }

    private void removerPaiComDoisFilhos(No n) {
        No sucessor = sucessor(n.getDireito());
        No paiSucessor = sucessor.getPai();

        boolean ehEsquerdo = (sucessor == paiSucessor.getEsquerdo());

        if (n.getCor() == 'R' && sucessor.getCor() == 'R'){ // 1: Ambos os nós são rubros
            removerRN(sucessor.getChave());
        }else if (n.getCor() == 'N' && sucessor.getCor() == 'R') { // 2: n é negro e sucessor é rubro
            sucessor.setCor('N');
            removerRN(sucessor.getChave());
        } else if (n.getCor() == 'N' && sucessor.getCor() == 'N') { // 3: Ambos os nós são negros
            removerRN(sucessor.getChave());
            checarDuploNegro(paiSucessor, ehEsquerdo);
        } else if (n.getCor() == 'R' && sucessor.getCor() == 'N') { // 4: n é rubro e sucessor é negro
            sucessor.setCor('R');
            removerRN(sucessor.getChave());
            checarDuploNegro(paiSucessor, ehEsquerdo);
        }
    }
    
    private void checarDuploNegro(No pai, boolean ehEsquerdo) {
        No irmao = ehEsquerdo ? pai.getDireito() : pai.getEsquerdo();
        No sobrinhoInterno = ehEsquerdo
                ? (irmao != null ? irmao.getEsquerdo() : null)
                : (irmao != null ? irmao.getDireito() : null);
        No sobrinhoExterno = ehEsquerdo
                ? (irmao != null ? irmao.getDireito() : null)
                : (irmao != null ? irmao.getEsquerdo() : null);

        // Caso 1: irmão rubro e pai negro -> rotação no pai, pinta pai de rubro e irmão de negro, depois reavalia
        if (irmao != null && isRed(irmao)){
            irmao.setCor('N');
            pai.setCor('R');
            if (ehEsquerdo) {
                rotacaoEsquerda(pai);
            } else { // caso espelhado
                rotacaoDireita(pai);
            }
            checarDuploNegro(pai, ehEsquerdo);
        }

        // Caso 2: irmão negro com sobrinhos negros -> pinta irmão de rubro e checa o pai
        if (isBlack(irmao) && isBlack(sobrinhoInterno) && isBlack(sobrinhoExterno)) {
            if (irmao != null) {
                irmao.setCor('R');
            }
            if (isBlack(pai)) {
                // Caso 2a: pai negro -> propaga para cima até encontrar um nó rubro
                if (pai.getPai() != null) {
                    checarDuploNegro(pai.getPai(), ehEsquerdo);
                }
            } else {
                // Caso 2b: pai rubro -> resolve localmente e pinta pai de negro (caso terminal)
                pai.setCor('N');
            }
        }
        // Caso 3: irmão negro com sobrinho esquerdo é rubro e sobrinho direito é negro -> rotação à direita no irmão e recoloração do irmão para rubro e sobrinho esquerdo para negro
        if (isBlack(irmao) && isRed(sobrinhoInterno) && isBlack(sobrinhoExterno)) {
            if (sobrinhoInterno != null) {
                sobrinhoInterno.setCor('N');
            }
            if (irmao != null) {
                irmao.setCor('R');
                if (ehEsquerdo) {
                    rotacaoDireita(irmao);
                } else {
                    rotacaoEsquerda(irmao);
                }
            }
            checarDuploNegro(pai, ehEsquerdo);
        }
        // Caso 4: irmão negro com sobrinho direito rubro -> rotação no pai para esquerda e troca de cores entre pai e irmão (caso terminal)
        if (isBlack(irmao) && isBlack(sobrinhoInterno) && isRed(sobrinhoExterno)) {
            if (irmao != null) {
                irmao.setCor(pai.getCor());
            }
            if (ehEsquerdo) {
                rotacaoEsquerda(pai);
            } else {
                rotacaoDireita(pai);
            }
            pai.setCor('N');
            if (sobrinhoExterno != null) {
                sobrinhoExterno.setCor('N');
            }
        }
    }
    @Override
    public void printArvore() {
    // Constantes ANSI para colorir o texto no terminal
    final String VERMELHO = "\u001B[31m";
    final String PRETO = "\u001B[30m";
    final String RESET = "\u001B[0m";
    
    int altura = height(raiz); 
    int linhas = altura + 1;
    int colunas = (int) Math.pow(2, linhas) - 1;

    Object[][] matriz = new Object[linhas][colunas];
    
    montar(matriz, raiz, 0, colunas / 2, VERMELHO, PRETO, RESET);
    
    for (int i = 0; i < linhas; i++) {
        for (int j = 0; j < colunas; j++) {
            if (matriz[i][j] == null) {
                System.out.print("  ");
            } else {
                System.out.printf("%3s", matriz[i][j]);
            }
        }
        System.out.println();
    }
}

protected void montar(Object[][] matriz, No n, int linha, int coluna, 
                     String VERMELHO, String PRETO, String RESET) {
    if (n == null) {
        return;
    }

    // Adaptar para usar caracteres como cores em vez de booleanos
    String corTexto = (n.getCor() == 'r' || n.getCor() == 'R') ? VERMELHO : PRETO;

    matriz[linha][coluna] = corTexto + n.getChave() + RESET;

    // Cálculo da distância entre nós em cada nível
    int d = (int) Math.pow(2, matriz.length - linha - 2);

    if (n.getEsquerdo() != null) {
        montar(matriz, n.getEsquerdo(), linha + 1, coluna - d, VERMELHO, PRETO, RESET);
    }

    if (n.getDireito() != null) {
        montar(matriz, n.getDireito(), linha + 1, coluna + d, VERMELHO, PRETO, RESET);
    }
}

}
