import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class EventStatisticImpl implements EventStatistic {

  private final Clock clock;
  private final Map<String, List<Instant>> events = new HashMap<>();

  public EventStatisticImpl(Clock clock) {
    this.clock = clock;
  }

  @Override
  public void incEvent(String name) {
    if (!events.containsKey(name))
      events.put(name, new ArrayList<>());

    events.get(name).add(clock.instant());
  }

  @Override
  public double getEventStatisticByName(String name) {
    if (!events.containsKey(name)) return 0;
    Instant hourAgo = clock.instant().minus(1, ChronoUnit.HOURS);

    return events.get(name).stream()
        .filter(it -> it.isAfter(hourAgo))
        .count() / 60.0;
  }

  @Override
  public Map<String, Double> getAllEventStatistic() {
    return events.entrySet()
        .stream()
        .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> getEventStatisticByName(entry.getKey())
            )
        );
  }

  @Override
  public void printStatistic() {
    Map<String, Double> statistic = getAllEventStatistic();

    for (String name : statistic.keySet())
      System.out.printf("RPM for %s - %f",
          name, statistic.get(name)
      );
  }
}
