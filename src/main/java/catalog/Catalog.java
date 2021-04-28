package catalog;

import java.util.ArrayList;
import java.util.List;

public class Catalog {

    private List<CatalogItem> catalogItems = new ArrayList<>();

    public List<CatalogItem> getCatalogItems() {
        return catalogItems;
    }

    public void addItem(CatalogItem catalogItem) {
        catalogItems.add(catalogItem);
    }

    public void deleteItemByRegistrationNumber(String regNumber) {
        CatalogItem actItem = null;
        for (CatalogItem ci: catalogItems) {
            if (ci.getRegistrationNumber().equals(regNumber)) {
                actItem = ci;
            }
        }
        if (actItem != null) {
            catalogItems.remove(actItem);
        }
    }

    public double averagePageNumberOver(int minNumber) {
        if (minNumber == 0) {
            throw new IllegalArgumentException("Page number must be positive");
        }
        double average = 0;
        int sum = 0;
        int count = 0;
        for (CatalogItem ci: catalogItems) {
            for (Feature f: ci.getFeatures()) {
                if (f.isPrinted()) {
                    int actPageNumber = ((PrintedFeatures) f).getNumberOfPages();
                    if ( actPageNumber > minNumber) {
                        sum += actPageNumber;
                        count ++;
                    }
                }
            }
        }
        if (sum == 0) {
            throw new IllegalArgumentException("No page");
        }
        average = sum / count;
        return average;
    }

    public List<CatalogItem> getAudioLibraryItems() {
        List<CatalogItem> result = new ArrayList<>();
        for (CatalogItem ci: catalogItems) {
            if (ci.hasAudioFeature()) {
                result.add(ci);
            }
        }
        return result;
    }

    public List<CatalogItem> getPrintedLibraryItems() {
        List<CatalogItem> result = new ArrayList<>();
        for (CatalogItem ci: catalogItems) {
            if (ci.hasPrintedFeature()) {
                result.add(ci);
            }
        }
        return result;
    }

    public int getAllPageNumber() {
        int number = 0;
        for (CatalogItem ci: catalogItems) {
            number += ci.numberOfPagesAtOneItem();
        }
        return number;
    }

    public int getFullLength() {
        int length = 0;
        for (CatalogItem ci: catalogItems) {
            length += ci.fullLengthAtOneItem();
        }
         return length;
    }

    public List<CatalogItem> findByCriteria(SearchCriteria searchCriteria) {
        List<CatalogItem> result = new ArrayList<>();
        for (CatalogItem ci: catalogItems) {
            if (ci.getContributors().contains(searchCriteria.getContributor()) ||
                    ci.getTitles().contains(searchCriteria.getTitle())) {
                result.add(ci);
            }
        }
        return result;
    }


}
