package com.cmeza.spring.security.jwt.application.properties;

import com.cmeza.spring.security.jwt.domain.usecase.utils.JwtConstants;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Validated
@ConstructorBinding
public final class JwtPeriodSuffixProperties {

    /**
     * Hour prefix
     */
    @NotEmpty
    private final String hour;
    /**
     * Minute prefix
     */
    @NotEmpty
    private final String minute;
    /**
     * Second prefix
     */
    @NotEmpty
    private final String second;
    /**
     * Millisecond prefix
     */
    @NotEmpty
    private final String millisecond;
    /**
     * Year prefix
     */
    @NotEmpty
    private final String year;
    /**
     * Month prefix
     */
    @NotEmpty
    private final String month;
    /**
     * Week prefix
     */
    @NotEmpty
    private final String week;
    /**
     * Day prefix
     */
    @NotEmpty
    private final String day;

    public JwtPeriodSuffixProperties(
            @DefaultValue(JwtConstants.HOUR_SUFFIX) String hour,
            @DefaultValue(JwtConstants.MINUTE_SUFFIX) String minute,
            @DefaultValue(JwtConstants.SECOND_SUFFIX) String second,
            @DefaultValue(JwtConstants.MILLISECOND_SUFFIX) String millisecond,
            @DefaultValue(JwtConstants.YEAR_SUFFIX) String year,
            @DefaultValue(JwtConstants.MONTH_SUFFIX) String month,
            @DefaultValue(JwtConstants.WEEK_SUFFIX) String week,
            @DefaultValue(JwtConstants.DAY_SUFFIX) String day) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.millisecond = millisecond;
        this.year = year;
        this.month = month;
        this.week = week;
        this.day = day;
    }

    public PeriodFormatter getPeriodFormatter() {
        return new PeriodFormatterBuilder()
                .appendHours().appendSuffix(hour, hour + " ")
                .appendMinutes().appendSuffix(minute, minute + " ")
                .appendSeconds().appendSuffix(second, second + " ")
                .appendMillis().appendSuffix(millisecond, millisecond + " ")
                .appendYears().appendSuffix(year, year + " ")
                .appendMonths().appendSuffix(month, month + " ")
                .appendWeeks().appendSuffix(week, week + " ")
                .appendDays().appendSuffix(day, day + " ")
                .toFormatter();
    }

}
