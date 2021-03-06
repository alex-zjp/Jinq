package org.jinq.orm.stream;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Wraps a regular Stream to provide some Jinq methods for the stream.
 */
public class LazyWrappedStream<T> implements Stream<T>
{
   Stream<T> wrappedStream;
   
   LazyWrappedStream() {}
   LazyWrappedStream(Stream<T> wrappedStream)
   {
      this.wrappedStream = wrappedStream;
   }

   protected void realizeStream()
   {
      // TODO: Perhaps this should only be called when the data from the
      // stream is actually used and not when the stream is being set-up.
      if (wrappedStream == null) wrappedStream = createWrappedStream();
   }
   
   protected <U> Stream<U> wrap(Stream<U> toWrap)
   {
      return toWrap;
   }
   
   protected Stream<T> createWrappedStream() 
   {
      return null;
   }
   
   @Override
   public Stream<T> filter(Predicate<? super T> predicate)
   {
      realizeStream();
      return wrap(wrappedStream.filter(predicate));
   }

   @Override
   public <R> Stream<R> map(Function<? super T, ? extends R> mapper)
   {
      realizeStream();
      return wrap(wrappedStream.map(mapper));
   }

   @Override
   public IntStream mapToInt(ToIntFunction<? super T> mapper)
   {
      realizeStream();
      return wrappedStream.mapToInt(mapper);
   }

   @Override
   public LongStream mapToLong(ToLongFunction<? super T> mapper)
   {
      realizeStream();
      return wrappedStream.mapToLong(mapper);
   }

   @Override
   public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper)
   {
      realizeStream();
      return wrappedStream.mapToDouble(mapper);
   }

   @Override
   public <R> Stream<R> flatMap(
         Function<? super T, ? extends Stream<? extends R>> mapper)
   {
      realizeStream();
      return wrap(wrappedStream.flatMap(mapper));
   }

   @Override
   public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper)
   {
      realizeStream();
      return wrappedStream.flatMapToInt(mapper);
   }

   @Override
   public LongStream flatMapToLong(
         Function<? super T, ? extends LongStream> mapper)
   {
      realizeStream();
      return wrappedStream.flatMapToLong(mapper);
   }

   @Override
   public DoubleStream flatMapToDouble(
         Function<? super T, ? extends DoubleStream> mapper)
   {
      realizeStream();
      return wrappedStream.flatMapToDouble(mapper);
   }

   @Override
   public Stream<T> distinct()
   {
      realizeStream();
      return wrap(wrappedStream.distinct());
   }

   @Override
   public Stream<T> sorted()
   {
      realizeStream();
      return wrap(wrappedStream.sorted());
   }

   @Override
   public Stream<T> sorted(Comparator<? super T> comparator)
   {
      realizeStream();
      return wrap(wrappedStream.sorted(comparator));
   }

   @Override
   public Stream<T> peek(Consumer<? super T> action)
   {
      realizeStream();
      return wrap(wrappedStream.peek(action));
   }

   @Override
   public Stream<T> limit(long maxSize)
   {
      realizeStream();
      return wrap(wrappedStream.limit(maxSize));
   }

   @Override
   public Stream<T> skip(long n)
   {
      realizeStream();
      return wrap(wrappedStream.skip(n));
   }

   @Override
   public void forEach(Consumer<? super T> action)
   {
      realizeStream();
      wrappedStream.forEach(action);
   }

   @Override
   public void forEachOrdered(Consumer<? super T> action)
   {
      realizeStream();
      wrappedStream.forEachOrdered(action);
   }

   @Override
   public Object[] toArray()
   {
      realizeStream();
      return wrappedStream.toArray();
   }

   @Override
   public <A> A[] toArray(IntFunction<A[]> generator)
   {
      realizeStream();
      return wrappedStream.toArray(generator);
   }

   @Override
   public T reduce(T identity, BinaryOperator<T> accumulator)
   {
      realizeStream();
      return wrappedStream.reduce(identity, accumulator);
   }

   @Override
   public Optional<T> reduce(BinaryOperator<T> accumulator)
   {
      realizeStream();
      return wrappedStream.reduce(accumulator);
   }

   @Override
   public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator,
         BinaryOperator<U> combiner)
   {
      realizeStream();
      return wrappedStream.reduce(identity, accumulator, combiner);
   }

   @Override
   public <R> R collect(Supplier<R> supplier,
         BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner)
   {
      realizeStream();
      return wrappedStream.collect(supplier, accumulator, combiner);
   }

   @Override
   public <R, A> R collect(Collector<? super T, A, R> collector)
   {
      realizeStream();
      return wrappedStream.collect(collector);
   }

   @Override
   public Optional<T> min(Comparator<? super T> comparator)
   {
      realizeStream();
      return wrappedStream.min(comparator);
   }

   @Override
   public Optional<T> max(Comparator<? super T> comparator)
   {
      realizeStream();
      return wrappedStream.max(comparator);
   }

   @Override
   public long count()
   {
      realizeStream();
      return wrappedStream.count();
   }

   @Override
   public boolean anyMatch(Predicate<? super T> predicate)
   {
      realizeStream();
      return wrappedStream.anyMatch(predicate);
   }

   @Override
   public boolean allMatch(Predicate<? super T> predicate)
   {
      realizeStream();
      return wrappedStream.allMatch(predicate);
   }

   @Override
   public boolean noneMatch(Predicate<? super T> predicate)
   {
      realizeStream();
      return wrappedStream.noneMatch(predicate);
   }

   @Override
   public Optional<T> findFirst()
   {
      realizeStream();
      return wrappedStream.findFirst();
   }

   @Override
   public Optional<T> findAny()
   {
      realizeStream();
      return wrappedStream.findAny();
   }

   @Override
   public Iterator<T> iterator()
   {
      realizeStream();
      return wrappedStream.iterator();
   }

   @Override
   public Spliterator<T> spliterator()
   {
      realizeStream();
      return wrappedStream.spliterator();
   }

   @Override
   public boolean isParallel()
   {
      realizeStream();
      return wrappedStream.isParallel();
   }

   @Override
   public Stream<T> sequential()
   {
      realizeStream();
      return wrap(wrappedStream.sequential());
   }

   @Override
   public Stream<T> parallel()
   {
      realizeStream();
      return wrap(wrappedStream.parallel());
   }

   @Override
   public Stream<T> unordered()
   {
      realizeStream();
      return wrap(wrappedStream.unordered());
   }

   @Override
   public Stream<T> onClose(Runnable closeHandler)
   {
      realizeStream();
      return wrap(wrappedStream.onClose(closeHandler));
   }

   @Override
   public void close()
   {
      realizeStream();
      wrappedStream.close();
   }
}