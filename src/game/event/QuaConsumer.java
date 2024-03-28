package game.event;

import java.util.Objects;

public interface QuaConsumer <T, T1, T2, T3>
{
     public void accept(T arg, T1 arg1, T2 arg2, T3 arg3);

    default QuaConsumer<T, T1, T2, T3> andThen(QuaConsumer<? super T, ? super T1, ? super T2, ? super T3> after)
    {
        Objects.requireNonNull(after);
        return (t, t1, t2, t3) ->
        {
            accept(t, t1, t2, t3);
            after.accept(t, t1, t2, t3);
        };
    }
}
