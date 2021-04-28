package catalog;

import java.util.ArrayList;
import java.util.List;

public class AudioFeatures implements Feature{

    private String title;
    private int length;
    private List<String> composer = new ArrayList<>();
    private List<String> performers;

    public AudioFeatures(String title, int length, List<String> composer, List<String> performers) {
        if (Validators.isBlank(title)) {
            throw new IllegalArgumentException("Empty title");
        }
        if (length <= 0) {
            throw new IllegalArgumentException("A hossz legyen nagyobb, mint 0");
        }
        if (Validators.isEmpty(performers)) {
            throw new IllegalArgumentException("Empty performers");
        }
        this.title = title;
        this.length = length;
        this.performers = performers;
        this.composer = composer;
    }

    public AudioFeatures(String title, int length, List<String> performers) {
        if (Validators.isBlank(title)) {
            throw new IllegalArgumentException("Empty title");
        }
        if (length <= 0) {
            throw new IllegalArgumentException("A hossz legyen nagyobb, mint 0");
        }
        if (Validators.isEmpty(performers)) {
            throw new IllegalArgumentException("Empty performers");
        }
        this.title = title;
        this.length = length;
        this.performers = performers;
    }

    public int getLength() {
        return length;
    }

    @Override
    public List<String> getContributors() {
        List<String> result = new ArrayList<>(composer);
        result.addAll(performers);
        return result;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean isPrinted() {
        return false;
    }
}
