package cz.ondrejpittl.rest.dtos;

import cz.ondrejpittl.dev.Dev;
import cz.ondrejpittl.persistence.domain.Tag;

public class ValidationErrorDTO {

    private String field;

    private String message;

    private Object value;


    public ValidationErrorDTO() {}

    public ValidationErrorDTO(String field, String message, Object value) {
        this.field = field;
        this.message = message;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ValidationErrorDTO)) return false;
        ValidationErrorDTO e = (ValidationErrorDTO) obj;

        boolean rs = this.getField().equals(e.getField())
            && (
                (this.getMessage() == null && e.getMessage() == null)
                || (this.getMessage() != null && e.getMessage() != null && this.getMessage().equals(e.getMessage()))
            );

        return rs;
    }
}
