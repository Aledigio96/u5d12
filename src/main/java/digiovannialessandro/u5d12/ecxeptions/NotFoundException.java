package digiovannialessandro.u5d11.ecxeptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(Integer id) {
        super("La risorsa con id " + id + " non è stata trovata!");
    }
    public NotFoundException(String msg){super(msg);}
}
