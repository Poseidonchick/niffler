package niffler.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CategoryJson {
    @JsonProperty("category")
    private String category;
    @JsonProperty("username")
    private String username;
}
