package catalog;

public class SearchCriteria {

    private String contributor;
    private String title;

    public SearchCriteria(String title, String contributor) {
        this.contributor = contributor;
        this.title = title;
    }

    public String getContributor() {
        return contributor;
    }

    public String getTitle() {
        return title;
    }

    public static SearchCriteria createByBoth(String title, String contributor) {
        if (Validators.isBlank(title)) {
            throw new IllegalArgumentException("Wrong data");
        }
        if (Validators.isBlank(contributor)) {
            throw new IllegalArgumentException("Wrong data");
        }
        return new SearchCriteria(title, contributor);
    }

    public static SearchCriteria createByContributor(String contributor) {
        if (Validators.isBlank(contributor)) {
            throw new IllegalArgumentException("Wrong data");
        }
        return new SearchCriteria("", contributor);
    }

    public static SearchCriteria createByTitle(String title) {
        if (Validators.isBlank(title)) {
            throw new IllegalArgumentException("Wrong data");
        }
        return new SearchCriteria(title, "");
    }

    public boolean hasContributor() {
        return !getContributor().isBlank();
    }

    public boolean hasTitle() {
        return !getTitle().isBlank();
    }
}
