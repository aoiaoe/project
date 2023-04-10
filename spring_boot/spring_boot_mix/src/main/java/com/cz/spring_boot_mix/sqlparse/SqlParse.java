package com.cz.spring_boot_mix.sqlparse;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.util.TablesNamesFinder;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jzm
 * @date 2023/4/10 : 10:24
 */
public class SqlParse {

    public static void main(String[] args) throws Exception {
        String sqlStr = "select a1, a2 from a where c1 = 1 and b1 = 2";
        parseSql(sqlStr);
    }

    public static void parseSql(String sqlStr) throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(sqlStr);
        System.out.println(statement);
        if (statement instanceof Select) {
            Select select = (Select) statement;
            PlainSelect plainSelect = (PlainSelect) select.getSelectBody();

            SelectExpressionItem selectExpressionItem =
                    (SelectExpressionItem) plainSelect.getSelectItems().get(0);
            System.out.println("columns : " + selectExpressionItem);
            Table table = (Table) plainSelect.getFromItem();
            System.out.println("table : " + table);
            Expression where = plainSelect.getWhere();
            if(where instanceof AndExpression){
                AndExpression andExpression = (AndExpression)where;
                System.out.println(andExpression.getLeftExpression());
                System.out.println(andExpression.getRightExpression());
            }
//            Column a = (Column) equalsTo.getLeftExpression();
//            Column b = (Column) equalsTo.getRightExpression();
        }
    }
}
