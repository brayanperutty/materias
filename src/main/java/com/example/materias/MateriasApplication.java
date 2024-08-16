package com.example.materias;

import com.example.materias.entity.Estudiante;
import com.example.materias.entity.Materia;
import com.example.materias.entity.Nota;
import com.example.materias.repository.EstudianteRepository;
import com.example.materias.repository.MateriaRepository;
import com.example.materias.repository.NotaRepository;
import com.example.materias.responses.estudiante.EstudianteErrorResponses;
import com.example.materias.responses.materia.MateriaErrorResponse;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Random;

@SpringBootApplication
@EnableScheduling
public class MateriasApplication {

	private final MateriaRepository materiaRepository;

	private final EstudianteRepository estudianteRepository;

	private final NotaRepository notaRepository;

	private final MateriaErrorResponse materiaErrorResponse;
	private final EstudianteErrorResponses estudianteErrorResponses;

	private final Random random = new Random();

	public MateriasApplication(MateriaRepository materiaRepository, EstudianteRepository estudianteRepository, NotaRepository notaRepository, MateriaErrorResponse materiaErrorResponse, EstudianteErrorResponses estudianteErrorResponses) {
		this.materiaRepository = materiaRepository;
		this.estudianteRepository = estudianteRepository;
		this.notaRepository = notaRepository;
		this.materiaErrorResponse = materiaErrorResponse;
		this.estudianteErrorResponses = estudianteErrorResponses;
	}

	public static void main(String[] args) {
		SpringApplication.run(MateriasApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(){
		return args -> {
			if(materiaRepository.count() == 0){
				createMaterias();
			}

			if(estudianteRepository.count() == 0){
				createEstudiantes();
			}

			if(notaRepository.count() == 0){
				createInscripciones();
			}
		};
	}

	public void createMaterias(){
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

	public void createEstudiantes(){

		Faker faker = new Faker();

		for (int i = 1; i <= 30; i++){
            Estudiante estudiante;
			String code = i< 10 ? "00"+i : "0"+i;
            if(i<10){
                estudiante = Estudiante.builder()
                        .nombre(faker.name().fullName())
                        .codigo("00" + i)
						.promedio(0.0f)
                        .build();
            }else{
                estudiante = Estudiante.builder()
                        .nombre(faker.name().fullName())
                        .codigo("0" + i)
						.promedio(0.0f)
                        .build();
            }
            estudianteRepository.save(estudiante);
        }
	}

	public void createInscripciones(){

		for(int i = 1; i<=3; i++){

			int cantidadEstudiantesAInscribir = random.nextInt(20) + 11;

			Materia materia = materiaRepository.findById(i).orElseThrow(() -> new IllegalArgumentException(materiaErrorResponse.getNoEncontrada()));

			int inscripcionesHechas = 0;
			while (inscripcionesHechas < cantidadEstudiantesAInscribir){

				int estudianteRandom = random.nextInt(30) + 1;

				if(!(notaRepository.validateEstudianteMateria(i, estudianteRandom))){

					Estudiante estudiante = estudianteRepository.findById(estudianteRandom).orElseThrow(() -> new IllegalArgumentException(estudianteErrorResponses.getNoEncontrado()));

					Nota nota = new Nota();
					nota.setMateria(materia);
					nota.setEstudiante(estudiante);

					notaRepository.save(nota);
					inscripcionesHechas++;
				}
			}
		}
	}

}
