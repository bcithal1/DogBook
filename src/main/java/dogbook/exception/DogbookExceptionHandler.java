package dogbook.exception;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class DogbookExceptionHandler {
    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public ResponseEntity<Message> handleHttpException(HttpClientErrorException e){
        Message message = new Message(e.getMessage());

        return ResponseEntity.status(e.getStatusCode())
            .contentType(MediaType.APPLICATION_JSON)
            .body(message);
    }

    class Message {
        private String message;
        public Message(String message){
            this.message = message;
        }

        public String getMessage(){
            return message;
        }

        public void setMessage(String message){
            this.message = message;
        }
    }
}
