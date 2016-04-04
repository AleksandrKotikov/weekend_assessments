package samples.exoguru.materialtabs.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Windows on 01/04/2016.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongStyleModel {

        private Integer resultCount;
        private List<Result> results = new ArrayList<Result>();
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        /**
         *
         * @return
         * The resultCount
         */
        public Integer getResultCount() {
                return resultCount;
        }

        /**
         *
         * @param resultCount
         * The resultCount
         */
        public void setResultCount(Integer resultCount) {
                this.resultCount = resultCount;
        }

        /**
         *
         * @return
         * The results
         */
        public List<Result> getResults() {
                return results;
        }

        /**
         *
         * @param results
         * The results
         */
        public void setResults(List<Result> results) {
                this.results = results;
        }

        public Map<String, Object> getAdditionalProperties() {
                return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
                this.additionalProperties.put(name, value);
        }

}