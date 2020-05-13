package br.com.jokenspock.enums;

public enum MensagemValidacao {
	
	SEM_JOGADAS_CONSULTA("N�o existem jogadas a exibir."),
	SEM_JOGADAS_REMOVER("N�o h� jogadas a remover"),
	ERRO_CONSULTA("N�o foi poss�vel obter as jogadas"),
	SEM_JOGADA_ENVIADA("Nenhuma jogada foi enviada."),
	SEM_VENCEDOR("N�o consegui determinar quem ganhou :("),
	SUCESSO("Jogador %s Vit�ria"),
	ERRO_CADASTRO("Erro ao cadastrar jogadas."),
	SUCESSO_CADASTRO("Jogadas cadastradas com sucesso."),
	SEM_JOGADAS_REMOVIDAS("N�o foram encontradas as jogadas para remover"),;
	
	private String mensagem;
	
	MensagemValidacao (String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
