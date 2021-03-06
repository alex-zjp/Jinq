package ch.epfl.labos.iu.orm.queryll2.symbolic;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;



public class TypedValueRewriterWalker<I, E extends Exception> extends TypedValuePostfixWalker<I, TypedValue, E>
{
   HashMap<TypedValue, TypedValue> remap;  // to handle aliasing, we need to track changes we made elsewhere in the tree
   TypedValueVisitor<I, TypedValue, E> rewriter;
   
   // Pass in a visitor that when given a value with either
   // return the original value (i.e. no changes made) or 
   // returns a different value which should be used to replace
   // the subtree rooted at the value
   public TypedValueRewriterWalker(TypedValueVisitor<I, TypedValue, E> rewriter)
   {
      this.rewriter = rewriter;
      this.remap = new HashMap<TypedValue, TypedValue>();
   }
   
   @Override public TypedValue defaultValue(TypedValue val, I in) throws E
   {
      // A subtree may be repeated in different parts of the tree, so here we
      // test whether we've seen the subtree before and return the new version
      // of the subtree
      if (remap.containsKey(val)) return remap.get(val);
      TypedValue newVal = val.visit(rewriter, in);
      remap.put(val, newVal);
      return newVal;
   }

   @Override public TypedValue unaryOpValue(TypedValue.UnaryOperationValue val, I in) throws E
   {
      if (remap.containsKey(val)) return remap.get(val);
      TypedValue newOperand = val.operand.visit(rewriter, in);
      TypedValue newVal = val;
      if (newOperand != val.operand)
         newVal = val.withNewChildren(newOperand);
      newVal = newVal.visit(rewriter, in);
      remap.put(val, newVal);
      return newVal;
   }
//   @Override public TypedValue getFieldValue(TypedValue.GetFieldValue val, I in) throws TypedValueVisitorException
//   {
//      if (remap.containsKey(val)) return remap.get(val);
//      TypedValue newOperand = val.operand.visit(rewriter, in);
//      TypedValue newVal = val;
//      if (newOperand != val.operand)
//         newVal = val.withNewChildren(newOperand);
//      newVal = newVal.visit(rewriter, in);
//      remap.put(val, newVal);
//      return newVal;
//   }
//   @Override public TypedValue castValue(TypedValue.CastValue val, I in) throws TypedValueVisitorException
//   {
//      return unaryOpValue(val, in);
//   }

   @Override public TypedValue binaryOpValue(TypedValue.BinaryOperationValue val, I in) throws E
   {
      if (remap.containsKey(val)) return remap.get(val);
      TypedValue newLeft = val.left.visit(rewriter, in); 
      TypedValue newRight = val.right.visit(rewriter, in);
      TypedValue newVal = val;
      if (newLeft != val.left || newRight != val.right)
         newVal = val.withNewChildren(newLeft, newRight);
      newVal = newVal.visit(rewriter, in);
      remap.put(val, newVal);
      return newVal;
   }
//   @Override public TypedValue comparisonOpValue(TypedValue.ComparisonValue val, I in) throws TypedValueVisitorException
//   {
//      if (remap.containsKey(val)) return remap.get(val);
//      TypedValue newLeft = val.left.visit(rewriter, in); 
//      TypedValue newRight = val.left.visit(rewriter, in);
//      TypedValue newVal = val;
//      if (newLeft != val.left || newRight != val.right)
//         newVal = val.withNewChildren(newLeft, newRight);
//      newVal = newVal.visit(rewriter, in);
//      remap.put(val, newVal);
//      return newVal;
//   }
   
   @Override public TypedValue methodCallValue(MethodCallValue val, I in) throws E
   {
      if (remap.containsKey(val)) return remap.get(val);
      MethodCallValue newVal = val;
      List<TypedValue> newArgs = new Vector<TypedValue>(val.args.size());
      boolean isChanged = false;
      for (TypedValue arg: val.args)
      {
         TypedValue newArg = arg.visit(rewriter, in);
         if (newArg != arg)
            isChanged = true;
         newArgs.add(newArg);
      }
      if (isChanged) 
         newVal = val.withNewArgs(newArgs); 
      TypedValue returnVal = newVal.visit(rewriter, in);
      remap.put(val, returnVal);
      return returnVal;
   }
   @Override public TypedValue staticMethodCallValue(MethodCallValue.StaticMethodCallValue val, I in) throws E
   {
      if (remap.containsKey(val)) return remap.get(val);
      MethodCallValue newVal = val;
      List<TypedValue> newArgs = new Vector<TypedValue>(val.args.size());
      boolean isChanged = false;
      for (TypedValue arg: val.args)
      {
         TypedValue newArg = arg.visit(rewriter, in);
         if (newArg != arg)
            isChanged = true;
         newArgs.add(newArg);
      }
      if (isChanged) 
         newVal = val.withNewArgs(newArgs); 
      TypedValue returnVal = newVal.visit(rewriter, in);
      remap.put(val, returnVal);
      return returnVal;
   }
   @Override public TypedValue virtualMethodCallValue(MethodCallValue.VirtualMethodCallValue val, I in) throws E
   {
      if (remap.containsKey(val)) return remap.get(val);
      MethodCallValue newVal = val;
      List<TypedValue> newArgs = new Vector<TypedValue>(val.args.size());
      boolean isChanged = false;
      for (TypedValue arg: val.args)
      {
         TypedValue newArg = arg.visit(rewriter, in);
         if (newArg != arg)
            isChanged = true;
         newArgs.add(newArg);
      }
      TypedValue newBase = val.base.visit(rewriter, in);
      if (isChanged || val.base != newBase) 
         newVal = val.withNewArgs(newArgs, newBase); 
      TypedValue returnVal = newVal.visit(rewriter, in);
      remap.put(val, returnVal);
      return returnVal;
   }
}
