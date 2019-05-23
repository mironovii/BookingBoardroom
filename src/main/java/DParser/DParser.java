package DParser;

import java.util.Set;

public interface DParser<T> {
    void reader(Set<T> set);

    void writer(Set<T> set);
}
