package TImeCAlling.spring.domain.enums;

import lombok.Getter;

import java.time.DayOfWeek;

@Getter
public enum RepeatDay {
    MONDAY("월요일", DayOfWeek.MONDAY),
    TUESDAY("화요일", DayOfWeek.TUESDAY),
    WEDNESDAY("수요일", DayOfWeek.WEDNESDAY),
    THURSDAY("목요일", DayOfWeek.THURSDAY),
    FRIDAY("금요일", DayOfWeek.FRIDAY),
    SATURDAY("토요일", DayOfWeek.SATURDAY),
    SUNDAY("일요일", DayOfWeek.SUNDAY);
    
    private final String description;
    private final DayOfWeek dayOfWeek;
    
    RepeatDay(String description, DayOfWeek dayOfWeek) {
        this.description = description;
        this.dayOfWeek = dayOfWeek;
    }
    
    public static RepeatDay fromDayOfWeek(DayOfWeek dayOfWeek) {
        for (RepeatDay repeatDay : values()) {
            if (repeatDay.getDayOfWeek() == dayOfWeek) {
                return repeatDay;
            }
        }
        throw new IllegalArgumentException("Invalid DayOfWeek: " + dayOfWeek);
    }
}