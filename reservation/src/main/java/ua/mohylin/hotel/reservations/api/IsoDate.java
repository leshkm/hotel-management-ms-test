package ua.mohylin.hotel.reservations.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * A shortcut to indicate date in ISO format
 */
@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsoDate {
}
