package ca.genovese.coffeecats.cats.data;

import ca.genovese.coffeecats.algebra.Eq;
import ca.genovese.coffeecats.cats.Applicative;
import ca.genovese.coffeecats.cats.MonoidK;
import ca.genovese.coffeecats.util.Kind;
import ca.genovese.coffeecats.util.Unit;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Xor<A, B> {

  static <A, B> Xor<A, B> left(A a) {
    return new Left<>(a);
  }

  static <A, B> Xor<A, B> right(B b) {
    return new Right<>(b);
  }

  default <C> C fold(Function<A, C> f1, Function<B, C> f2) {
    return (this instanceof Left) ? f1.apply(this.asLeft().value) : f2.apply(this.asRight().value);
  }

  default Left<A, B> asLeft() {
    return (Left<A, B>) this;
  }

  default Right<A, B> asRight() {
    return (Right<A, B>) this;
  }

  default boolean isLeft() {
    return fold(a -> true, b -> false);
  }

  default boolean isRight() {
    return fold(a -> false, b -> true);
  }

  default Xor<B, A> swap() {
    return fold(Xor::right, Xor::left);
  }

  default Unit foreach(Consumer<B> f) {
    return foreach(Unit.fromConsumer(f));
  }

  default Unit foreach(Function<B, Unit> f) {
    return fold(a -> Unit.instance, f);
  }

  default B getOrElse(Supplier<B> fallback) {
    return fold(a -> fallback.get(), Function.identity());
  }

  default Xor<A, B> orElse(Supplier<Xor<A, B>> fallback) {
    return fold(a -> fallback.get(), b -> this);
  }

  default B valueOr(Function<A, B> fallback) {
    return fold(fallback, Function.identity());
  }

  default boolean forall(Predicate<B> p) {
    return fold(a -> true, p::test);
  }

  default boolean exists(Predicate<B> p) {
    return fold(a -> false, p::test);
  }

  default Xor<A, B> ensure(Supplier<A> ifLeft, Predicate<B> p) {
    return fold(a -> this, b -> p.test(b) ? this : left(ifLeft.get()));
  }

  default <F> Kind<F, B> to(MonoidK<F> mkf, Applicative<F> af) {
    return fold(a -> mkf.empty(), af::pure);
  }

  default <C, D> Xor<C, D> bimap(Function<A, C> fa, Function<B, D> fb) {
    return fold(a -> left(fa.apply(a)), b -> right(fb.apply(b)));
  }

  default <D> Xor<A, D> map(Function<B, D> fb) {
    return fold(Xor::left, b -> right(fb.apply(b)));
  }

  default <C> Xor<C, B> leftMap(Function<A, C> fa) {
    return fold(a -> new Left<>(fa.apply(a)), Xor::right);
  }

  default <D> Xor<A, D> flatMap(Function<B, Xor<A, D>> fb) {
    return fold(Xor::left, fb);
  }

  default boolean equalTo(Xor<A, B> that, Eq<A> ea, Eq<B> eb) {
    return fold(a -> that.fold(a2 -> ea.eqv(a, a2), b2 -> false), b -> that.fold(a2 -> false, b2 -> eb.eqv(b, b2)));
  }

/*
    def traverse[F[_], AA >: A, D](f: B => F[D])(implicit F: Applicative[F]): F[AA Xor D] = this match {
      case l @ Xor.Left(_) => F.pure(l)
      case Xor.Right(b) => F.map(f(b))(Xor.right _)
    }

    def foldLeft[C](c: C)(f: (C, B) => C): C = fold(_ => c, f(c, _))

    def foldRight[C](c: C)(f: (B, C) => C): C = fold(_ => c, f(_, c))

    def partialFold[C](f: B => Fold[C]): Fold[C] =
      fold(_ => Fold.Pass, f)

    def merge[AA >: A](implicit ev: B <:< AA): AA = fold(identity, ev.apply)

    final def append[AA >: A, BB >: B](that: AA Xor BB)(implicit AA: Semigroup[AA], BB: Semigroup[BB]): AA Xor BB =
    this match {
      case Xor.Left(a1) => that match {
        case Xor.Left(a2) => Xor.Left(AA.combine(a1, a2))
        case Xor.Right(b2) => Xor.Left(a1)
      }
      case Xor.Right(b1) => that match {
        case Xor.Left(a2) => Xor.Left(a2)
        case Xor.Right(b2) => Xor.Right(BB.combine(b1, b2))
      }
    }

    def show[AA >: A, BB >: B](implicit AA: Show[AA], BB: Show[BB]): String = fold(
      a => s"Xor.Left(${AA.show(a)})",
      b => s"Xor.Right(${BB.show(b)})"
    )

        def toIor: A Ior B = fold(Ior.left, Ior.right)

    def toEither: Either[A, B] = fold(Left(_), Right(_))

    def toOption: Option[B] = fold(_ => None, Some(_))

    def toList: List[B] = fold(_ => Nil, _ :: Nil)

    def toValidated: Validated[A,B] = fold(Validated.Invalid.apply, Validated.Valid.apply)

    def withValidated[AA,BB](f: Validated[A,B] => Validated[AA,BB]): AA Xor BB =
      f(toValidated).toXor

    def compare[AA >: A, BB >: B](that: AA Xor BB)(implicit AA: Order[AA], BB: Order[BB]): Int = fold(
      a => that.fold(AA.compare(a, _), _ => -1),
      b => that.fold(_ => 1, BB.compare(b, _))
    )

    def partialCompare[AA >: A, BB >: B](that: AA Xor BB)(implicit AA: PartialOrder[AA], BB: PartialOrder[BB]):
    Double = fold(
      a => that.fold(AA.partialCompare(a, _), _ => -1),
      b => that.fold(_ => 1, BB.partialCompare(b, _))
    )



 */

  class Left<A, X> implements Xor<A, X> {
    public final A value;

    public Left(A a) {
      this.value = a;
    }
  }

  class Right<X, B> implements Xor<X, B> {
    public final B value;

    public Right(B b) {
      this.value = b;
    }
  }

}
