package org.jinq.jpa.jpqlquery;

public class FromAliasExpression extends Expression
{
   From from;
   public FromAliasExpression(From from)
   {
      this.from = from;
   }
   
   @Override
   public void generateQuery(QueryGenerationState queryState)
   {
      queryState.appendQuery(queryState.getFromAlias(from));
   }
}
