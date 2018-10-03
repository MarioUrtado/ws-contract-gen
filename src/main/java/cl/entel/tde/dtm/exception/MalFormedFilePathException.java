package cl.entel.tde.dtm.exception;

public class MalFormedFilePathException extends Exception {

    public MalFormedFilePathException(String filePath, String fileName) {
        super("Path no cumple con el formato interpretable por la app. El path del archivo [" + filePath + "], con nombre de archivo [" + fileName + "]");
    }


}
