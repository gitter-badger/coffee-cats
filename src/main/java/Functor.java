
import java.util.function.Function;

interface Functor <F> {
    F map(F fa, Function f);
}
// public interface Functor <F<?>> {
//   public <A,B> F<B> map(F<A>)(
// }