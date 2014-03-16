package org.jinq.orm.stream;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import ch.epfl.labos.iu.orm.DBSet;
import ch.epfl.labos.iu.orm.Pair;
import ch.epfl.labos.iu.orm.QueryComposer;
import ch.epfl.labos.iu.orm.QueryList;
import ch.epfl.labos.iu.orm.DBSet.AggregateDouble;
import ch.epfl.labos.iu.orm.DBSet.AggregateGroup;
import ch.epfl.labos.iu.orm.DBSet.AggregateInteger;
import ch.epfl.labos.iu.orm.DBSet.AggregateSelect;
import ch.epfl.labos.iu.orm.DBSet.Join;
import ch.epfl.labos.iu.orm.DBSet.Select;
import ch.epfl.labos.iu.orm.DBSet.Where;

public class QueryJinqStream<T> extends NonQueryJinqStream<T> implements JinqStream<T>
{
   QueryComposer<T> queryComposer;
   public QueryJinqStream(QueryComposer<T> query)
   {
      this.queryComposer = query;
   }
   
   protected Stream<T> createWrappedStream() 
   {
      return StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(queryComposer.executeAndReturnResultIterator(), Spliterator.CONCURRENT), 
            false);
   }

   @Override
   public JinqStream<T> where(final Where<T> test)
   {
      QueryComposer<T> newComposer = queryComposer.where(test);
      if (newComposer != null) return new QueryJinqStream<T>(newComposer);
      return super.where(test);
   }

   @Override
   public <U> JinqStream<U> select(Select<T, U> select)
   {
      QueryComposer<U> newComposer = queryComposer.select(select);
      if (newComposer != null) return new QueryJinqStream<U>(newComposer);
      return super.select(select);
   }
   
   @Override
   public <U> JinqStream<Pair<T, U>> join(Join<T,U> join)
   {
      QueryComposer<Pair<T, U>> newComposer = queryComposer.join(join);
      if (newComposer != null) return new QueryJinqStream<Pair<T, U>>(newComposer);
      return super.join(join);
   }
   
   @Override
   public JinqStream<T> unique()
   {
      QueryComposer<T> newComposer = queryComposer.unique();
      if (newComposer != null) return new QueryJinqStream<T>(newComposer);
      return super.unique();
   }
   
   @Override
   public <U, V> JinqStream<Pair<U, V>> group(Select<T, U> select, AggregateGroup<U, T, V> aggregate)
   {
      QueryComposer<Pair<U, V>> newComposer = queryComposer.group(select, aggregate);
      if (newComposer != null) return new QueryJinqStream<Pair<U, V>>(newComposer);
      return super.group(select, aggregate);
   }

   @Override
   public double sumDouble(AggregateDouble<T> aggregate)
   {
      Double val = queryComposer.sumDouble(aggregate);
      if (val != null) return val;
      return super.sumDouble(aggregate);
   }
   
   @Override
   public int sumInt(AggregateInteger<T> aggregate)
   {
      Integer val = queryComposer.sumInt(aggregate);
      if (val != null) return val;
      return super.sumInt(aggregate);
   }

   @Override
   public double maxDouble(AggregateDouble<T> aggregate)
   {
      Double val = queryComposer.maxDouble(aggregate);
      if (val != null) return val;
      return super.maxDouble(aggregate);
   }
   
   @Override
   public int maxInt(AggregateInteger<T> aggregate)
   {
      Integer val = queryComposer.maxInt(aggregate);
      if (val != null) return val;
      return super.maxInt(aggregate);
   }
   
   @Override
   public <U> U selectAggregates(AggregateSelect<T, U> aggregate)
   {
      U val = queryComposer.selectAggregates(aggregate);
      if (val != null) return val;
      return super.selectAggregates(aggregate);
   }
}
