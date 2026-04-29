package app.system.exceptionsHandler;
import java.time.LocalDateTime;

public record ApiErrorResponse (
        LocalDateTime timestamp,
        int status,
        String message
){
}

