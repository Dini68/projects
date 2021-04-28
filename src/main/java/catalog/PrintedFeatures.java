package catalog;

import java.util.List;

public class PrintedFeatures implements Feature{

    private String title;
    private int numberOfPages;
    private List<String> authors;

    public PrintedFeatures(String title, int numberOfPages, List<String> authors) {
        if (Validators.isBlank(title)) {
            throw new IllegalArgumentException("Empty title");
        }
        if (numberOfPages <= 0) {
            throw new IllegalArgumentException("Az oldal szám legyen nagyobb, mint 0!");
        }
        if (Validators.isEmpty(authors)) {
            throw new IllegalArgumentException("Nem lehet null.");
        }
        this.title = title;
        this.numberOfPages = numberOfPages;
        this.authors = authors;
    }

    @Override
    public List<String> getContributors() {
        return authors;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean isPrinted() {
        return true;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }
}
