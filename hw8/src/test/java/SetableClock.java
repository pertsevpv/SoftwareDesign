import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.TemporalUnit;

public class SetableClock extends Clock {
  private Instant now;

  public SetableClock(Instant now) {
    this.now = now;
  }

  @Override
  public Instant instant() {
    return now;
  }

  public void setNow(Instant now) {
    this.now = now;
  }

  public void plus(long amountToAdd, TemporalUnit unit) {
    setNow(now.plus(amountToAdd, unit));
  }

  @Override
  public ZoneId getZone() {
    return null;
  }

  @Override
  public Clock withZone(ZoneId zone) {
    return null;
  }
}
