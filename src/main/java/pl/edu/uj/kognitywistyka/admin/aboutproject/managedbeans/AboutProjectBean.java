package pl.edu.uj.kognitywistyka.admin.aboutproject.managedbeans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import pl.edu.uj.kognitywistyka.admin.aboutproject.bo.AboutProjectBo;
import pl.edu.uj.kognitywistyka.admin.aboutproject.model.AboutProject;

@ManagedBean
@RequestScoped
public class AboutProjectBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// Dependency injection via Spring
	@ManagedProperty(name="aboutProjectBo", value="#{aboutProjectBo}")
	AboutProjectBo aboutProjectBo;

	private String description;

	public String getDescription() {
		if(aboutProjectBo != null)
			return aboutProjectBo.findLatestAboutDescription().getDescription();
		else
			return "";
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAboutProjectBo(AboutProjectBo aboutProjectBo) {
		this.aboutProjectBo = aboutProjectBo;
	}

	public AboutProject getAboutDescription() {
		return aboutProjectBo.findLatestAboutDescription();
	}
	
	public String addAboutDescription() {
		AboutProject aboutProject = new AboutProject();
		aboutProject.setDescription(description);
		
		aboutProjectBo.addAboutDescription(aboutProject);
		
		clearForm();
		return "";
	}

	private void clearForm() {
		setDescription("");
	}
	
}