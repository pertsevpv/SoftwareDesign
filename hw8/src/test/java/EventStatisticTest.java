import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class EventStatisticTest {
  private SetableClock clock;
  private EventStatistic eventStatistic;

  private static final String EVENT1 = "Event1";
  private static final String EVENT2 = "Event2";
  private static final String EVENT3 = "Event3";

  @Before
  public void setup() {
    clock = new SetableClock(Instant.now());
    eventStatistic = new EventStatisticImpl(clock);
  }

  @Test
  public void testEmptyStatistic() {
    assertThat(eventStatistic.getEventStatisticByName(EVENT1)).isZero();
  }

  @Test
  public void testStatisticByNames() {
    eventStatistic.incEvent(EVENT1);
    eventStatistic.incEvent(EVENT1);
    eventStatistic.incEvent(EVENT2);

    assertThat(eventStatistic.getEventStatisticByName(EVENT1))
        .isEqualTo(1.0 / 30);

    assertThat(eventStatistic.getEventStatisticByName(EVENT2))
        .isEqualTo(1.0 / 60);
  }

  @Test
  public void testFreshEvents() {
    eventStatistic.incEvent(EVENT1);
    eventStatistic.incEvent(EVENT1);
    clock.plus(30, ChronoUnit.MINUTES);

    assertThat(eventStatistic.getEventStatisticByName(EVENT1))
        .isEqualTo(1.0 / 30);
  }

  @Test
  public void testOldEvents() {
    eventStatistic.incEvent(EVENT1);
    eventStatistic.incEvent(EVENT1);
    clock.plus(1, ChronoUnit.HOURS);

    assertThat(eventStatistic.getEventStatisticByName(EVENT1)).isZero();
  }

  @Test
  public void testFreshOldEvents() {
    eventStatistic.incEvent(EVENT1);
    eventStatistic.incEvent(EVENT1);
    clock.plus(30, ChronoUnit.MINUTES);

    eventStatistic.incEvent(EVENT1);
    eventStatistic.incEvent(EVENT1);
    eventStatistic.incEvent(EVENT1);
    clock.plus(30, ChronoUnit.MINUTES);

    assertThat(eventStatistic.getEventStatisticByName(EVENT1))
        .isEqualTo(1.0 / 20);
  }

  @Test
  public void testStatistic() {
    eventStatistic.incEvent(EVENT1);
    clock.plus(30, ChronoUnit.MINUTES);

    eventStatistic.incEvent(EVENT2);
    eventStatistic.incEvent(EVENT2);
    clock.plus(30, ChronoUnit.MINUTES);

    eventStatistic.incEvent(EVENT3);

    assertThat(eventStatistic.getAllEventStatistic())
        .containsEntry(EVENT1, 0.0);
    assertThat(eventStatistic.getAllEventStatistic())
        .containsEntry(EVENT2, 1.0 / 30);
    assertThat(eventStatistic.getAllEventStatistic())
        .containsEntry(EVENT3, 1.0 / 60);
  }

}
