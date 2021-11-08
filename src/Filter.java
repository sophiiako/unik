import java.util.List;

public class Filter {
    public boolean withDocumentation;
    public boolean tested;

    public void Filter() {
        withDocumentation = false;
        tested = false;
    }

    public void changeDocumentationField(boolean status) {
        withDocumentation = status;
    }

    public void changeTestedField(boolean status) {
        tested = status;
    }
}
