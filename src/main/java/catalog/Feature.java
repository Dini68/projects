package catalog;

import java.util.List;

public interface Feature {

    List<String> getContributors();
    String getTitle();
    boolean isPrinted();
}
