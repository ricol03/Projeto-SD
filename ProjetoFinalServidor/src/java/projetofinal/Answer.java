package projetofinal;

import java.time.LocalDateTime;

/**
 * @author Guilherme Rodrigues e Rodrigo Pereira
 */

public class Answer {
    private LocalDateTime datetime;
    private Object data;
    
    public Answer() {}
    
    public Answer(LocalDateTime aDateTime, Object aData) {       
        datetime = aDateTime;
        data = aData;
    }
    
    public LocalDateTime getDatetime() {
        return datetime;
    }

    public Object getData() {
        return data;
    }

}
