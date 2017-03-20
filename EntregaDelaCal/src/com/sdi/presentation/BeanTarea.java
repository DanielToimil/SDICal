package com.sdi.presentation;
import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.sdi.model.Task;

@ManagedBean(name="alumno")
@SessionScoped
public class BeanTarea extends Task implements Serializable {
  private static final long serialVersionUID = 55556L;
  
  public BeanTarea() {
    iniciaTarea(null);
  }
  

  public void setTarea(Task tarea) {
    setId(tarea.getId());
    setCategoryId(tarea.getCategoryId());
    setTitle(tarea.getTitle());
    setComments(tarea.getComments());
    setCreated(tarea.getCreated());
    setFinished(tarea.getFinished());
    setPlanned(tarea.getPlanned());
    setUserId(tarea.getUserId());
  }
  

    public void iniciaTarea(ActionEvent event) {
      FacesContext facesContext = FacesContext.getCurrentInstance();
          ResourceBundle bundle = 
           facesContext.getApplication().getResourceBundle(facesContext, "msgs");
          setId(null);
          setCategoryId(null);
          setUserId(null);
          setTitle(bundle.getString("valorDefectoTitle"));
          setComments(bundle.getString("valorDefectoComments"));
          setCreated(null);
          setFinished(null);
          setPlanned(null);
    }        
}
