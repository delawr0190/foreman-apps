package mn.foreman.multminer.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/** The json object obtained from the multminer API. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stats {

    /** The board json property. */
    @JsonProperty("bd")
    public List<Board> boards;

    /** The ms json property. */
    @JsonProperty("ms")
    public List<List<String>> ms;

    /** The mt json property. */
    @JsonProperty("mt")
    public String mt;

    /** A board object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonDeserialize(using = BoardDeserializer.class)
    public static class Board {

        /** The number of chips. */
        @JsonProperty("cn")
        public int chipNumber;

        /** The chip status. */
        @JsonProperty("cs")
        public List<List<String>> chips;
    }
}
