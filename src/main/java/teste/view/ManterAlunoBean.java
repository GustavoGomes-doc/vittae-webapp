package teste.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import teste.model.Aluno;
import teste.model.Professor;
import teste.model.Curso;
import teste.service.ManterAlunoService;
import teste.service.ManterProfessorService;
import teste.service.ManterCursoService;

@Log4j
@Getter
@Setter
@Named
@ViewScoped
public class ManterAlunoBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ManterAlunoService manterAlunoService;
	@Inject
	private ManterProfessorService manterProfessorService;
	@Inject
	private ManterCursoService manterCursoService;

	private Aluno aluno = new Aluno();
	private List<Aluno> alunos = new ArrayList<Aluno>();
	private List<Professor> professores = new ArrayList<Professor>();
	private List<Curso> cursos = new ArrayList<Curso>();

	
	@PostConstruct
	public void inicializar() {
		log.debug("init pesquisa"); 
		this.setAlunos(manterAlunoService.buscarTodos());
		this.setProfessores(manterProfessorService.buscarTodos());
		this.setCursos(manterCursoService.buscarTodos());
		limpar();
	}
	
	public void salvar() {
		log.info(aluno.toString());
		manterAlunoService.salvar(aluno);
		this.setAlunos(manterAlunoService.buscarTodos());
		
		FacesContext.getCurrentInstance().
        addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
        		"O aluno foi gravado com sucesso!", 
        		aluno.toString()));
		
		log.info("aluno: " + aluno.toString());
	}
	
	public void excluir() {
		try {
			manterAlunoService.excluir(aluno);
			this.alunos = manterAlunoService.buscarTodos();
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Aluno " + aluno.getNome() + " excluído com sucesso.", null));
			log.info("aluno excluido = " + aluno.getNome());
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, 
			new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ocorreu um problema", null));
		}
	}
		
	public void limpar() {

		this.aluno = new Aluno();
	}
	
}
