package com.intellij.jpa.facet;

import com.intellij.persistence.facet.PersistenceFacetBase;
import java.util.Map;

public class JpaFacetImpl {
    public static class UnitDataSourceMap<T> {
        private final Map<String, String> myMap;
        private final PersistenceFacetBase myFacet;

        public UnitDataSourceMap(Map<String, String> map, PersistenceFacetBase facet) {
            myMap = map;
            myFacet = facet;
        }

        public String getDataSourceID(T unit) {
            return myMap != null ? myMap.get(String.valueOf(unit)) : null;
        }

        public void setDataSourceID(T unit, String dataSourceId) {
            if (myMap != null) {
                myMap.put(String.valueOf(unit), dataSourceId);
            }
        }
    }
}
