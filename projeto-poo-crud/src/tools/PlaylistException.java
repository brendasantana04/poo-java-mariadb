package tools;

public class PlaylistException extends Exception {
    
    public PlaylistException(String message) { 
        super(message);
    }

    public PlaylistException() { 
        super();
    }

    public PlaylistException( Throwable t ) { 
        super(t);
    }
}
