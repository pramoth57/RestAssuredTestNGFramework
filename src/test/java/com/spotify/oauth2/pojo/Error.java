package com.spotify.oauth2.pojo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;


@Getter
@Setter
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Error {

    @JsonProperty("error")
    private InnerError error;

    @JsonProperty("error")
    public InnerError getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(InnerError error) {
        this.error = error;
    }

}
