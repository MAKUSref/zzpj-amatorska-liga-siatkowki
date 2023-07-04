package pl.lodz.p.it.ssbd2023.ssbd04.utils.etag;

import jakarta.ws.rs.NameBinding;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface EtagFilter {
}
