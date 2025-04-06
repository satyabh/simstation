package mvc;

import tools.Publisher;

import java.io.Serial;
import java.io.Serializable;

public abstract class Model extends Publisher implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    protected boolean hasUnsavedChanges = false;

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean hasUnsavedChanges() {
        return hasUnsavedChanges;
    }

    public void setUnsavedChanges(boolean hasUnsavedChanges) {
        this.hasUnsavedChanges = hasUnsavedChanges;
    }
}
