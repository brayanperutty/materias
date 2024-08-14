package com.example.materias.dto.materia;

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
public class MateriaCreateDTO {

    @NotNull(message = "El campo código no debe ser nulo")
    @NotBlank(message = "El campo código no debe estar vacío")
    @Pattern(regexp = "^\\d+$", message = "La cédula solo debe contener números")
    @Size(min = 3, max = 3, message = "El código de la materia debe ser de 3 dígitos")
    private String codigo;

    @NotNull(message = "El campo nombre no debe ser nulo")
    @NotBlank(message = "El campo nombre no debe estar vacío")
    @Size(min = 1, max = 255, message = "El nombre de la materia debe tener mínimo 1 caracter")
    private String nombre;
}
