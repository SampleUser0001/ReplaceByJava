package bean;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "search",
    "replace"
})

/**
 * 変換Bean
 * search → replace変換する。
 */ 
@Data
public class ReplaceBean {
    @JsonProperty
    private String search;
    
    @JsonProperty
    private String replace;
}