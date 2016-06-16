package app.models;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class WithHref<T> {

    @JsonUnwrapped
    public final T t;
    public final String url;

    public WithHref(T t, String url) {
        this.t = t;
        this.url = url;
    }
}
