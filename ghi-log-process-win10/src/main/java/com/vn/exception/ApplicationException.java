package com.vn.exception;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author MSI
 */
@Data
public class ApplicationException extends Exception {

    //public static final int ERROR = 2;
    private int code;
    private String message;
    private String language;
    private String attack;
    private boolean hasAttack = false;
    private Object data;

    public ApplicationException(int code) {
        this.code = code;
    }

    public ApplicationException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApplicationException(int code, String attack, boolean hasAttack) {
        this.code = code;
        this.attack = attack;
        this.hasAttack = hasAttack;
    }

    public ApplicationException(int code, String message, String language) {
        this.code = code;
        this.message = this.getMessage(language) + " " + message;
    }

    public ApplicationException(int codeBss, int code, String message, String language) {
        this.code = codeBss;
        this.message = this.getMessage(language) + ": '" + code + ": " + message + "'";
    }

    @Override
    public String getMessage() {
        if (!StringUtils.hasText(message)) {
            return ApplicationCode.getMessage(code);
        }
        return message;
    }

    public String getMessage(String language) {
        if (!StringUtils.hasText(message)) {
            return ApplicationCode.getMessage(code);
        }
        return message;
    }

}
