package com.example.materias;

import com.example.materias.entity.Estudiante;
import com.example.materias.entity.Materia;
import com.example.materias.repository.EstudianteRepository;
import com.example.materias.repository.MateriaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MateriasApplication {

	private final MateriaRepository materiaRepository;

	private final EstudianteRepository estudianteRepository;

	public MateriasApplication(MateriaRepository materiaRepository, EstudianteRepository estudianteRepository) {
		this.materiaRepository = materiaRepository;
		this.estudianteRepository = estudianteRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(MateriasApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(){
		return args -> {
			if(materiaRepository.count() == 0){
				Materia materiaUno = Materia.builder()
						.nombre("Cálculo 1")
						.codigo("001")
						.build();

				Materia materiaDos = Materia.builder()
						.nombre("Física 1")
						.codigo("002")
						.build();

				Materia materiaTres = Materia.builder()
						.nombre("POO 1")
						.codigo("003")
						.build();

				materiaRepository.save(materiaUno);
				materiaRepository.save(materiaDos);
				materiaRepository.save(materiaTres);
			}

			if(estudianteRepository.count() == 0){
				for (int i = 1; i <= 30; i++){
					if(i<10){
						Estudiante estudiante = Estudiante.builder()
								.nombre("Estudiante "+i)
								.codigo("00"+i)
								.build();
						estudianteRepository.save(estudiante);
					}else{
						Estudiante estudiante = Estudiante.builder()
								.nombre("Estudiante "+i)
								.codigo("0"+i)
								.build();
						estudianteRepository.save(estudiante);
					}
				}
			}
		};
	}

}
