package Enum;

public enum Genero {

    ACCION(1),
    COMEDIA(2),
    DRAMA(3),
    TERROR(4),
    ANIMADA(5);

    private final int id;

    Genero (int id) {

        this.id = id;
    }

    public int getId(){
        return this.id;
    }

}
