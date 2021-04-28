package catalog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CatalogItem {

    private String registrationNumber;
    private int price;
    private List<Feature> features = new ArrayList<>();

    public CatalogItem(String registrationNumber, int price, Feature... features) {
        this.registrationNumber = registrationNumber;
        this.price = price;
        this.features = Arrays.asList(features);
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public int getPrice() {
        return price;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public int fullLengthAtOneItem() {
        int length = 0;
        for (Feature f: features) {
            if (!f.isPrinted()) {
                length += ((AudioFeatures) f).getLength();
            }
        }
        return length;
    }

    public List<String> getContributors() {
        List<String> result = new ArrayList<>();
        for (Feature f: features) {
            result.addAll(f.getContributors());
        }
        return result;
    }

    public List<String> getTitles() {
        List<String> result = new ArrayList<>();
        for (Feature f: features) {
            result.add(f.getTitle());
        }
        return result;
    }

    public boolean hasAudioFeature() {
        for (Feature f: features) {
            if (!f.isPrinted()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPrintedFeature() {
        for (Feature f: features) {
            if (f.isPrinted()) {
                return true;
            }
        }
        return false;
    }

    public int numberOfPagesAtOneItem() {
        int number = 0;
        for (Feature f: features) {
            if (f.isPrinted()) {
                number += ((PrintedFeatures) f).getNumberOfPages();
            }
        }
        return number;
    }

}
