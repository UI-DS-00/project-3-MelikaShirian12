package exceptions;

public class WrongMethodType extends NumberFormatException
{

    public WrongMethodType() {
        super("invalid method type");
    }
}
