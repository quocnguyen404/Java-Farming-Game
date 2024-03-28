package game.event;

import java.util.Objects;

public interface TriConsumer <T, T1, T2>
{
    public void accept(T arg1, T1 arg2, T2 arg3);

    default TriConsumer<T, T1, T2> andThen(TriConsumer<? super T, ? super T1, ? super T2> after)
    {
        Objects.requireNonNull(after);
        return (t, t1, t2) ->
        {
            accept(t, t1, t2);
            after.accept(t, t1, t2);
        };
    }
}
