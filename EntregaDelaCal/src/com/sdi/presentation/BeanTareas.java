package com.sdi.presentation;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.sdi.business.AlumnosService;
import com.sdi.business.TaskService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Task;
import com.sdi.model.User;


@ManagedBean(name = "controller")
@SessionScoped
public class BeanTareas implements Serializable {
	private static final long serialVersionUID = 55555L;
	// Se añade este atributo de entidad para recibir el alumno concreto
	// selecionado de la tabla o de un formulario
	// Es necesario inicializarlo para que al entrar desde el formulario de
	// AltaForm.xml se puedan
	// dejar los avalores en un objeto existente.
	// uso de inyección de dependencia
	@ManagedProperty(value = "#{alumno}")
	private BeanTarea tarea;
	
	public BeanTareas() {
		listadoTasks();
	}

	public BeanTarea getTarea() {
		return tarea;
	}

	public void setTarea(BeanTarea tarea) {
		this.tarea = tarea;
	}

	// Se inicia correctamente el MBean inyectado si JSF lo hubiera crea
	// y en caso contrario se crea. (hay que tener en cuenta que es un Bean de
	// sesión)
	// Se usa @PostConstruct, ya que en el contructor no se sabe todavía si el
	// Managed Bean
	// ya estaba construido y en @PostConstruct SI.
	@PostConstruct
	public void init() {
		System.out.println("BeanTareas - PostConstruct");
		// Buscamos el alumno en la sesión. Esto es un patrón factoría
		// claramente.
		tarea = (BeanTarea) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get(new String("tarea"));
		// si no existe lo creamos e inicializamos
		if (tarea == null) {
			System.out.println("BeanTareas - No existia");
			tarea = new BeanTarea();
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("tarea", tarea);
		}
	}

	@PreDestroy
	public void end() {
		System.out.println("BeanTareas - PreDestroy");
	}

	private Task[] tareas = null;

	public Task[] getTareas() {
		return (tareas);
		
	}

	public void setTareas(Task[] tareas) {
		this.tareas = tareas;
	}

	public void iniciaAlumno(ActionEvent event) {
		tarea.setId(null);
		tarea.setCategoryId(null);
		tarea.setTitle("Nombre");
		tarea.setComments("Commentarios");
		tarea.setCreated(null);
		tarea.setFinished(null);
		tarea.setPlanned(null);
	}

	public String listadoTasks() {
		if (userIsNotLoggedIn()) {
			return "casa";
		}

		TaskService service;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			service = Factories.services.createTaskService();
			// De esta forma le damos informaci��n a toArray para poder hacer el
			// casting a Alumno[]
			
			//-------------------------------------------------------------------
			
			//tareas = (Task[]) service..toArray(new Task[0]);
			
			//-------------------------------------------------------------------

			return "exito"; // Nos vamos a la vista listado.xhtml

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos la vista de error
		}

	}

	public String borrar(Task tarea) {
		if (userIsNotLoggedIn()) {
			return "casa";
		}
		TaskService service;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			service = Factories.services.createTaskService();
			// Aliminamos el alumno seleccionado en la tabla
			service.deleteTask(tarea.getId());
			// Actualizamos el javabean de alumnos inyectado en la tabla.
			
			//-------------------------------------------------------------------
			// tareas = (Task[]) service.getAlumnos().toArray(new Task[0]);
			//-------------------------------------------------------------------
			
			return "exito"; // Nos vamos a la vista de listado.

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos a la vista de error
		}

	}

	public String editTask() {
		if (userIsNotLoggedIn()) {
			return "casa";
		}
		
		TaskService service;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			service = Factories.services.createTaskService();
			// Recargamos el alumno seleccionado en la tabla de la base de datos
			// por si hubiera cambios.
			tarea = (BeanTarea) service.findTaskById(tarea.getId());
			return "exito"; // Nos vamos a la vista de Edición.

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos a la vista de error.
		}

	}

	public String salvaTask() {
		if (userIsNotLoggedIn()) {
			return "casa";
		}
		
		TaskService service;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			service = Factories.services.createTaskService();
			// Salvamos o actualizamos el alumno segun sea una operacion de alta
			// o de edici��n
			if (tarea.getId() == null) {
				//service.saveAlumno(alumno);
			} else {
				service.updateTask(tarea);
			}
			// Actualizamos el javabean de alumnos inyectado en la tabla
			//-------------------------------------------------------------------
			// tareas = (Task[]) service.getAlumnos().toArray(new Task[0]);
			//-------------------------------------------------------------------
			return "exito"; // Nos vamos a la vista de listado.

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos a la vista de error.
		}

	}
	
	private boolean userIsNotLoggedIn() {
		User usuariologueado = (User) getObjectFromSession("LOGGEDIN_USER");
		if (usuariologueado != null) {
			System.out.println("usuario	activo:	" + usuariologueado.getName());
			return false;
		}
		return true;
	}

	private Object getObjectFromSession(String key) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get(key);
	}
}
