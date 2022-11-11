package exceptions;

public class WrongInput extends NumberFormatException{

    public WrongInput() {
        super("wrong format");
    }
}
