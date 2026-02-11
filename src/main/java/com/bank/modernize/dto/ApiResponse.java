package com.bank.modernize.dto;

<<<<<<< HEAD
=======
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
>>>>>>> origin/main
public class ApiResponse {
    private String status;
    private String message;
    private Object data;
<<<<<<< HEAD

    public ApiResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
=======
>>>>>>> origin/main
}
