package ua.bondar.validator;

import org.springframework.beans.factory.annotation.Autowired;
import ua.bondar.annotation.DepartmentUniqueNameConstraint;
import ua.bondar.dao.DepartmentDAO;
import ua.bondar.dto.DepartmentDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Deprecated
public class DepartmentUniqueNameValidator implements ConstraintValidator<DepartmentUniqueNameConstraint, DepartmentDTO> {

    private final DepartmentDAO departmentDAO;

    @Autowired
    public DepartmentUniqueNameValidator(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    @Override
    public void initialize(DepartmentUniqueNameConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(DepartmentDTO departmentDTO, ConstraintValidatorContext constraintValidatorContext) {
        if(departmentDTO == null){
            return false;
        }

        return departmentDAO.findByName(departmentDTO.getName().trim()).isEmpty();
    }
}
