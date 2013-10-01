package br.iff.campos.terminalmobileiff;

public class Diario {
	
	private String id;
	private String nome;
	private String nota;
	private String frequencia;
	private String disciplina;
	private String aluno;
	
	public Diario() {
		super();
	}

	public Diario(String id, String nome, String nota, String frequencia,
			String disciplina) {
		super();
		this.id = id;
		this.nome = nome;
		this.nota = nota;
		this.frequencia = frequencia;
		this.disciplina = disciplina;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(String frequencia) {
		this.frequencia = frequencia;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	public String getAluno() {
		return aluno;
	}

	public void setAluno(String aluno) {
		this.aluno = aluno;
	}
	

}
