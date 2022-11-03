package ua.bondar.validator;

import org.springframework.beans.factory.annotation.Autowired;
import ua.bondar.annotation.DepartmentExistsConstraint;
import ua.bondar.dao.DepartmentDAO;
import ua.bondar.dto.DepartmentDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Deprecated
public class DepartmentDTOValidator implements ConstraintValidator<DepartmentExistsConstraint, DepartmentDTO> {

    private final DepartmentDAO departmentDAO;

    @Autowired
    public DepartmentDTOValidator(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    @Override
    public void initialize(DepartmentExistsConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(DepartmentDTO department, ConstraintValidatorContext constraintValidatorContext) {
        if(department == null){
            return false;
        }

        return departmentDAO.findByName(department.getName().trim()).isPresent();
    }
}
