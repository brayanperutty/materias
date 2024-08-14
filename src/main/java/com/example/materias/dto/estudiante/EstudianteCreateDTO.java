package com.example.materias.dto.estudiante;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteCreateDTO {

    private static final String REGEX_NOMBRE = "^[A-Za-záéíóúÁÉÍÓÚñÑ\\s]*$";

    @NotNull(message = "El campo código no debe ser nulo")
    @NotBlank(message = "El campo código no debe estar vacío")
    @Pattern(regexp = "^\\d+$", message = "El código solo debe contener números")
    @Size(min = 5, max = 5, message = "El código solo debe contener 5 dígitos")
    private String codigo;

    @NotNull(message = "El campo nombre no debe ser nulo")
    @NotBlank(message = "El campo nombre no debe estar vacío")
    @Pattern(regexp = REGEX_NOMBRE, message = "El nombre debe contener solo letras y espacios.")
    @Size(min = 2, max = 255, message = "El nombre del estudiante debe tener mínimo 2 caractéres")
    private String nombre;
}
