package com.generalthink.common.application;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.generalthink.common.datastructure.MyArrayList;
import com.generalthink.common.datastructure.MyStackWithArray;
import com.generalthink.common.datastructure.MyStackWithLink;

/**
 * 使用逆波兰式实现简易的java计算器.
 * @author TKing
 */
public class MyCalculator {

	//表达式
	private String expression;
	
	private final String BASIC_OPERATOR = "+-*/()";
	
	//操作符优先级,定义+-*/的优先级,数字越大优先级越高
	private static Map<String, Integer> operatorPriorityMap;

	static {
		operatorPriorityMap =  new HashMap<String, Integer>(16);
		//+-拥有相同的优先级,*/拥有相同的优先级
		operatorPriorityMap.put("+", 0);
		operatorPriorityMap.put("-", 0);
		operatorPriorityMap.put("*", 1);
		operatorPriorityMap.put("/", 1);
		
		//括号的优先级最大
		operatorPriorityMap.put("(", 10000);
		operatorPriorityMap.put(")", 10000);
	}
	
	public MyCalculator(String expression) {
		this.expression = expression;
	}
	
	/**
	 * .
	 * @return 计算结果
	 */
	public double calulate() {
		MyArrayList<String> expressionList = parseExpression();
		MyStackWithArray<String> resultStack = new MyStackWithArray<String>();
		String operateStr;
		String operateNum1,operateNum2;
		for(int i = 0,size = expressionList.size();i < size;i++) {
			operateStr = expressionList.get(i);
			if(isNum(operateStr)) {
				resultStack.push(operateStr);
			} else {
				operateNum2 = resultStack.pop();
				operateNum1 = resultStack.pop();
				double result = cal(operateNum1, operateStr, operateNum2);
				if(i == size -1) {
					return result;
				} else {
					resultStack.push(String.valueOf(result));
				}
			}
		}
		return 0d;
	}
	
	private double cal(String numStr1,String op,String numStr2) {
		double num1 = Double.parseDouble(numStr1);
		double num2 = Double.parseDouble(numStr2);
		double result = 0d;
		switch (op) {
		case "+":
			result = num1 + num2;
			break;
		case "-":
			result = num1 - num2;
			break;
		case "*":
			result = num1 * num2;
			break;
		case "/":
			result = num1 / num2;
			break;
		default:
			break;
		}
		return result;
	}
	
	private boolean isNum(String str) {
		if(str.matches("[0-9]+[0-9.]*")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 表达式解析,将运算表达式解析为逆波兰表达式.
	 * @return 逆波兰表达式
	 */
	public MyArrayList<String> parseExpression() {
		
		//操作符栈,存储操作符的临时栈
		MyStackWithLink<String> operatorStack = new MyStackWithLink<String>();
		MyArrayList<String> resultExpressionList = new MyArrayList<String>();
		
		
		StringTokenizer expressionTokenizer = new StringTokenizer(expression, BASIC_OPERATOR, true);
		
		String operateString;
		while(expressionTokenizer.hasMoreTokens()) {
			operateString = expressionTokenizer.nextToken();
			if(isNum(operateString)) {
				resultExpressionList.add(operateString);
			} else {
				if(operatorStack.isEmpty()) {
					operatorStack.push(operateString);
				} else {
					String topOperateOfStack = operatorStack.peek();
					int topOperatePriority = operatorPriorityMap.get(topOperateOfStack).intValue();
					int tmpOperatePriority = operatorPriorityMap.get(operateString).intValue();
					 
					//除非正在处理)否则(不会从栈中弹出
					if("(".equals(topOperateOfStack)) {
						operatorStack.push(operateString);
					} else if(")".equals(operateString)) {
						//此时应将()之间的所有操作符出栈
						while(true) {
							String topOperate = operatorStack.pop();
							if("(".equals(topOperate)) {
								break;
							} else {
								resultExpressionList.add(topOperate);
							}
						}
					} else {
						if(tmpOperatePriority > topOperatePriority) {
							operatorStack.push(operateString);
						} else {
							//如果栈顶元素优先级不小于即将入栈的操作符的优先级,则一直出栈
							while(tmpOperatePriority <= topOperatePriority) {
								resultExpressionList.add(operatorStack.pop());
								if(operatorStack.isEmpty()) {
									break;
								}
								if("(".equals(operatorStack.peek())) {
									break;
								}
								topOperatePriority = operatorPriorityMap.get(operatorStack.peek());
							}
							operatorStack.push(operateString);
						}
					}
				}
			}
		}

		while(!operatorStack.isEmpty()) {
			resultExpressionList.add(operatorStack.pop());
		}
		
		return resultExpressionList;
	}
	public static void main(String[] args) {
		MyCalculator cal = new MyCalculator("3.6 + 2*(4*5-11)/3-6/3+12".replaceAll("\\s+", ""));
		System.out.println(cal.calulate());
	}
}
