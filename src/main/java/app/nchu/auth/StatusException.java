package app.nchu.auth;

public class StatusException extends Exception {

    private static final long serialVersionUID = 1L;

    private String code;
    private String description;
    private String detail;

    public StatusException(String code, String description, String detail) {
        this.code = code;
        this.description = description;
        this.detail = detail;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDetail() {
        return this.detail;
    }

    @Override
    public String getMessage() {
        return this.description;
    }

    @Override
    public String toString() {
        return this.code + ": " + this.description + " - " + this.detail;
    }
    
}
